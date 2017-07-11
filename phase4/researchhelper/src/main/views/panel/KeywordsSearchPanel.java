package views.panel;

import java.awt.CardLayout;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

import project.commons.Favorites;
import project.commons.JTextFieldLimit;
import project.commons.Result;
import queryengine.QueryEngineAuthor;
import queryengine.QueryEngineTitle;


/** // KeywordsSearchPanel  
//i.e; the search panel with all keywords label and corresponding text field and other search elements
 * @author meghnatulasi
 *
 */
@SuppressWarnings("serial")
public class KeywordsSearchPanel extends JSplitPane implements PanelInterface{
	private JTextField titleField;
	private SearchPanel SearchPanel;
	private JSplitPane filterAndResultPanel;
	private JTable table;
	private JLabel searchHeading; 
	private JLabel titleLabel;
	private JButton authorSearchButton;
	private JButton papersSearchButton;
	private JButton titleSearchButton;
	private JButton searchButton;
	private JButton cancelButton; 
	private JPanel contentPane;
	private ResultsPanel resultView;
	private JButton btnGoToFavorities;  
	private JPanel searchPane;

	// constructor initializes search(top panel),filter(bottom right panel) and result(bottom left panel)
	/**
	 * @param SearchPanel 
	 * @param searchPane
	 * @param filterAndResultPanel
	 * @param table
	 * @param contentPane
	 */
	public KeywordsSearchPanel(SearchPanel SearchPanel, JPanel searchPane, JSplitPane filterAndResultPanel, JTable table, JPanel contentPane ) {
		this.SearchPanel = SearchPanel;
		this.filterAndResultPanel = filterAndResultPanel;
		this.table = table;
		this.contentPane = contentPane;
		this.searchPane = searchPane;
		addGUI();
	}

	
	/* (non-Javadoc)
	 * @see views.panel.PanelInterface#addGUI() // adds the various GUI elements
	 */
	public void addGUI() {
		searchPane.removeAll();
		filterAndResultPanel.removeAll();

		createHeading();
		createTitleLabel();
		createTitleField();

		createCancelButton();
		createAuthorSearchButton();
		createPapersSearchButton();
		createKeywordsSearchButton();
		createSearchButton();
		createGoToFavoritesButton();
		createLayout();
	}

	// create the Keywords Search page heading
	private void createHeading() {
		searchHeading = new JLabel("Keywords Search Page");
		searchHeading.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 30));
	}

	// creates field title
	private void createTitleLabel() {
		titleLabel = new JLabel("Keywords:");
		titleLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
	}

	// creates search field
	private void createTitleField() {
		titleField = new JTextField();
		titleField.setColumns(100);
		titleField.setDocument
		(new JTextFieldLimit(30, false));
	}

	// create cancel button
	public JButton createCancelButton() {
		cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener() {       
			public void actionPerformed(ActionEvent e) {    
				titleField.setText("");
				JSplitPane oldFilterAndResult = (JSplitPane) SearchPanel.getRightComponent();
				oldFilterAndResult.removeAll();                 
			}       
		});
		return cancelButton;
	}

	// creates the author search button
	public JButton createAuthorSearchButton() {
		authorSearchButton = new JButton("Author Search");
		authorSearchButton.setBackground(SystemColor.inactiveCaption);
		// refreshing the results & filter panel and display author search panel
		authorSearchButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JSplitPane oldFilterAndResult = (JSplitPane) SearchPanel.getRightComponent();
				resultView = null;
				oldFilterAndResult.removeAll(); 
				JSplitPane emptyFilterAndResultPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, 
						true, null, null); 
				AuthorSearchPanel p = new AuthorSearchPanel(SearchPanel, searchPane, emptyFilterAndResultPane, table, contentPane);
			}
		});
		return authorSearchButton;
	}

	// creates go to favorites button
	public JButton createGoToFavoritesButton() {        
		btnGoToFavorities = new JButton("My favorities");        
		btnGoToFavorities.addActionListener(new ActionListener() {      
			@SuppressWarnings("unused")
			// navigate to display the list of favorites for current session
			public void actionPerformed(ActionEvent e) {            
				FavoritesPanel favoritesView = new FavoritesPanel(contentPane,SearchPanel);       
				JSplitPane favView = favoritesView.getFavoritesPanel();     
				contentPane.add(favView, "favoritesView");      
				CardLayout card = (CardLayout)contentPane.getLayout();      
				card.show(contentPane, "favoritesView");                        
			}       
		});   
		return btnGoToFavorities;
	}

	// creates paper search button  
	public JButton createPapersSearchButton() {
		papersSearchButton = new JButton("Publication/Papers Search");
		papersSearchButton.setBackground(SystemColor.inactiveCaption);
		// refreshing the results & filter panel and display paper/publication search panel
		papersSearchButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JSplitPane oldFilterAndResult = (JSplitPane) SearchPanel.getRightComponent();
				resultView = null;
				oldFilterAndResult.removeAll(); 
				JSplitPane emptyFilterAndResultPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, 
						true, null, null);

				emptyFilterAndResultPane.setResizeWeight(0.3);
				PapersSearchPanel p = new PapersSearchPanel(SearchPanel, searchPane, emptyFilterAndResultPane, table, contentPane);
			}
		});
		return papersSearchButton;
	}

	// creates a disables keywords search button
	public JButton createKeywordsSearchButton() {
		titleSearchButton = new JButton("Title Search");
		titleSearchButton.setBackground(SystemColor.activeCaption);
		titleSearchButton.setEnabled(false);
		return titleSearchButton;
	}

	// checks if the input string is valid ie not null and size>0
	private boolean validName(String name) {
		return name!=null && name.trim().length()!=0;
	}

	// creates the search button
	public JButton createSearchButton() {
		searchButton = new JButton("Search");

		searchButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				String s= "";
				s = titleField.getText().replaceAll("^ +| +$|( )+", "$1");
				// if the name is not valid , display error message
				if((!validName(s))) {
					JOptionPane.showMessageDialog(null,"Enter valid keyword!");

				} else {
					// else populate the result page with queried result
					List<Result> titlelist = new ArrayList<Result>();
					try {
						titlelist=QueryEngineTitle.retrieveTitle(s);
					} catch (SQLException e1) {
						e1.printStackTrace();
					}

					JScrollPane resultPane = new JScrollPane();
					resultView = new ResultsPanel(resultPane, titlelist);       
					FilterPanel filterView = new FilterPanel(resultView.getTable());
					filterAndResultPanel = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, 
							true, filterView, resultPane);
					SearchPanel.setDividerLocation(400);
					SearchPanel.setRightComponent(filterAndResultPanel);
				}
			}
		});
		return searchButton;
	}

	
	/* (non-Javadoc)
	 * @see views.panel.PanelInterface#createLayout() // creates a layout for keywords search panel
	 */
	public GroupLayout createLayout() {

		JLabel lblEnterKeywords = new JLabel("* Enter all keywords seperated by ,");
		lblEnterKeywords.setFont(new Font("Tahoma", Font.ITALIC, 14));

		JLabel lblMaximum = new JLabel("* Maximum 30 characters");
		lblMaximum.setFont(new Font("Tahoma", Font.ITALIC, 14));
		GroupLayout keywordSearchLayout = new GroupLayout(searchPane);
		keywordSearchLayout.setHorizontalGroup(
				keywordSearchLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, keywordSearchLayout.createSequentialGroup()
						.addGap(40)
						.addGroup(keywordSearchLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(keywordSearchLayout.createSequentialGroup()
										.addComponent(searchHeading, GroupLayout.DEFAULT_SIZE, 457, Short.MAX_VALUE)
										.addGap(340))
								.addGroup(keywordSearchLayout.createSequentialGroup()
										.addPreferredGap(ComponentPlacement.RELATED)
										.addComponent(titleLabel, GroupLayout.PREFERRED_SIZE, 116, Short.MAX_VALUE)
										.addPreferredGap(ComponentPlacement.UNRELATED)
										.addGroup(keywordSearchLayout.createParallelGroup(Alignment.LEADING)
												.addComponent(lblEnterKeywords)
												.addComponent(lblMaximum, GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE)
												.addComponent(titleField, GroupLayout.PREFERRED_SIZE, 590, GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(ComponentPlacement.RELATED, 76, Short.MAX_VALUE))
								.addGroup(keywordSearchLayout.createSequentialGroup()
										.addPreferredGap(ComponentPlacement.RELATED)
										.addComponent(authorSearchButton, GroupLayout.DEFAULT_SIZE, 239, Short.MAX_VALUE)
										.addPreferredGap(ComponentPlacement.RELATED)
										.addComponent(papersSearchButton, GroupLayout.PREFERRED_SIZE, 232, Short.MAX_VALUE)
										.addPreferredGap(ComponentPlacement.UNRELATED)
										.addComponent(titleSearchButton, GroupLayout.DEFAULT_SIZE, 226, Short.MAX_VALUE)
										.addGap(76)))
						.addGap(11)
						.addGroup(keywordSearchLayout.createParallelGroup(Alignment.TRAILING)
								.addComponent(cancelButton, GroupLayout.DEFAULT_SIZE, 213, Short.MAX_VALUE)
								.addGroup(Alignment.LEADING, keywordSearchLayout.createSequentialGroup()
										.addComponent(btnGoToFavorities, GroupLayout.DEFAULT_SIZE, 211, Short.MAX_VALUE)
										.addGap(2))
								.addComponent(searchButton, GroupLayout.DEFAULT_SIZE, 213, Short.MAX_VALUE))
						.addGap(27))
				);
		keywordSearchLayout.setVerticalGroup(
				keywordSearchLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(keywordSearchLayout.createSequentialGroup()
						.addGap(25)
						.addGroup(keywordSearchLayout.createParallelGroup(Alignment.TRAILING, false)
								.addComponent(btnGoToFavorities, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(searchHeading, GroupLayout.DEFAULT_SIZE, 54, Short.MAX_VALUE))
						.addGap(36)
						.addGroup(keywordSearchLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(keywordSearchLayout.createParallelGroup(Alignment.BASELINE)
										.addComponent(searchButton, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
										.addComponent(titleField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								.addGroup(keywordSearchLayout.createSequentialGroup()
										.addGap(15)
										.addComponent(titleLabel, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)))
						.addGroup(keywordSearchLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(keywordSearchLayout.createSequentialGroup()
										.addGap(9)
										.addComponent(lblEnterKeywords)
										.addPreferredGap(ComponentPlacement.RELATED)
										.addComponent(lblMaximum, GroupLayout.PREFERRED_SIZE, 17, GroupLayout.PREFERRED_SIZE)
										.addGap(26)
										.addGroup(keywordSearchLayout.createParallelGroup(Alignment.TRAILING)
												.addGroup(keywordSearchLayout.createParallelGroup(Alignment.BASELINE)
														.addComponent(titleSearchButton, GroupLayout.PREFERRED_SIZE, 39, GroupLayout.PREFERRED_SIZE)
														.addComponent(papersSearchButton, GroupLayout.PREFERRED_SIZE, 39, GroupLayout.PREFERRED_SIZE))
												.addComponent(authorSearchButton, GroupLayout.PREFERRED_SIZE, 39, GroupLayout.PREFERRED_SIZE)))
								.addGroup(keywordSearchLayout.createSequentialGroup()
										.addGap(18)
										.addComponent(cancelButton, GroupLayout.PREFERRED_SIZE, 47, GroupLayout.PREFERRED_SIZE)))
						.addContainerGap(18, Short.MAX_VALUE))
				);
		SearchPanel.setDividerLocation(400);
		searchPane.setLayout(keywordSearchLayout);
		return keywordSearchLayout;
	}

	public KeywordsSearchPanel getKeywordPanel() {
		return this;
	}

	public JButton getGotoFavorires(){
		return this.btnGoToFavorities;
	}

	public JButton getPaperSearchButton(){
		return this.papersSearchButton;
	}

	public JButton getSearchButton(){
		return this.searchButton;
	}

	public JButton getAuthorSearchButton(){
		return this.authorSearchButton;
	}

	public JButton getCancelButton(){
		return this.cancelButton;
	}

	public JLabel getKeywordLabel() {
		return this.titleLabel;
	}



}