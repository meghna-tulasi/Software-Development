package views.panel;

import java.awt.Font;
import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.util.HashSet;

import project.commons.Favorites;
import project.commons.JTextFieldLimit;
import project.commons.Result;
import queryengine.QueryEngineAuthor;

import java.awt.SystemColor;


/**
 * @author meghnatulasi
 * // class for author search panel
// i.e; the search panel with author name label, corresponding text fields , search & clear option

 *
 */
@SuppressWarnings("serial")
public class AuthorSearchPanel extends JSplitPane implements PanelInterface{
	private JTextField authorNameField;
	private SearchPanel SearchPanel;
	private JSplitPane filterAndResultPanel;
	private JTable table;
	private JLabel searchHeading_1;
	private JLabel authorNameLabel;
	private JButton authorSearchButton;
	private JButton titleSearchButton;
	private JButton papersSearchButton;
	private JButton searchButton;
	private JButton cancelButton;
	private JButton btnGoToFavorities;      
	private JPanel contentPane;     
	private ResultsPanel resultView;
	private JPanel searchPane;

	// constructor
	/**constructor initializes search(top panel),filter(bottom right panel) and result(bottom left panel)
	 * @param SearchPanel
	 * @param searchPane
	 * @param filterAndResultPanel
	 * @param table
	 * @param contentPane
	 */
	public AuthorSearchPanel(SearchPanel SearchPanel,JPanel searchPane, JSplitPane filterAndResultPanel, JTable table, JPanel contentPane ) {
		this.SearchPanel = SearchPanel;
		this.filterAndResultPanel = filterAndResultPanel;
		this.table = table;
		this.contentPane = contentPane; 
		this.searchPane = searchPane;
		addGUI();
	}

	
	/* (non-Javadoc)
	 * @see views.panel.PanelInterface#addGUI()
	 addGUI() adds all the GUI elements to the author search panel
	this includes author name label, author name field, author search,
	papers search, title search button, search button, cancel button and
	go to favorites button*/
	
	public void addGUI() {
		// reset the search panel, filter view and results view
		searchPane.removeAll();
		//searchPane = new JPanel();
		filterAndResultPanel.removeAll();

		// create Author search page heading
		searchHeading_1 = createHeading();
		// create label "Author name:"
		authorNameLabel = createAuthorNameLabel();
		// create author name field
		authorNameField = createAuthorNameField();

		// create author, papers & title search button
		authorSearchButton = createAuthorSearchButton();
		papersSearchButton = createPapersSearchButton();
		titleSearchButton = createKeywordsSearchButton();
		searchButton = createSearchButton();

		// create go to favorites and cancel button
		createGoToFavoritesButton();
		createCancelButton();

		// create layout for the Author search panel
		createLayout();
	}

	
	/* (non-Javadoc)
	 * @see views.panel.PanelInterface#createCancelButton()
	 * createCancelButton() creates a cancel button to start a new search
	 */ 
	public JButton createCancelButton() {
		cancelButton = new JButton("Cancel");
		// adds listener to cancel button
		cancelButton.addActionListener(new ActionListener() {       
			public void actionPerformed(ActionEvent e) { 
				// resets the author name field
				authorNameField.setText("");
				// reset the filter and result views
				JSplitPane oldFilterAndResult = (JSplitPane) SearchPanel.getRightComponent();
				oldFilterAndResult.removeAll();                 
			}       
		});
		return cancelButton;
	}


	/* (non-Javadoc)
	 * @see views.panel.PanelInterface#createGoToFavoritesButton()
	 * 	createGoToFavoritesButton() creates go to favorites button where user can view selected author list
	 */
	public JButton createGoToFavoritesButton() {        
		btnGoToFavorities = new JButton("My favorities");  
		// adds a listener
		btnGoToFavorities.addActionListener(new ActionListener() {      
			public void actionPerformed(ActionEvent e) {  
				// create favorites view
				FavoritesPanel favoritesView = new FavoritesPanel(contentPane,SearchPanel);       
				JSplitPane favView = favoritesView.getFavoritesPanel();  
				contentPane.add(favView, "favoritesView");     
				CardLayout card = (CardLayout)contentPane.getLayout();  
				// display the favorites view
				card.show(contentPane, "favoritesView");                        
			}       
		}); 
		return btnGoToFavorities;
	}

	
	/**
	 * @return// getAuthorNameLabel() returns author name label
	 */
	public JLabel getAuthorNameLabel() {
		return this.authorNameLabel;
	}

	
	/**
	 * @return // getAuthorNameLabel() creates and return author name label
	 */
	private JLabel createAuthorNameLabel() {
		authorNameLabel = new JLabel("Author Name:");
		authorNameLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		return authorNameLabel;
	}

	
	/**
	 * @return // createHeading() creates Author Search Page heading & returns it
	 */
	private JLabel createHeading() {
		searchHeading_1 = new JLabel("Author Search Page");
		searchHeading_1.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 30));
		return searchHeading_1;
	}

	
	/**
	 * @return // createAuthorNameField() creates Author Name text field & returns it
	 */
	private JTextField createAuthorNameField() {
		authorNameField = new JTextField(); 
		// limit the author name field to accept only 30 characters
		authorNameField.setDocument
		(new JTextFieldLimit(30,false));
		authorNameField.setColumns(10);

		return authorNameField;
	} 

	
	/* (non-Javadoc)
	 * @see views.panel.PanelInterface#createAuthorSearchButton()
	 */ // creates author search button and is deactivated
	public JButton createAuthorSearchButton() {
		authorSearchButton = new JButton("Author Search");
		authorSearchButton.setBackground(SystemColor.activeCaption);
		authorSearchButton.setEnabled(false);
		return authorSearchButton;
	}

	
	/**
	 * @return // getAuthorSearchButton() return  author search button 
	 */
	public JButton getAuthorSearchButton() {
		return authorSearchButton;	
	}

	
	/* (non-Javadoc)
	 * @see views.panel.PanelInterface#createPapersSearchButton()
	 */// createPapersSearchButton() creates paper search button
	// with is active and on clicking this button, user is displayed with paper search
	// panel and result & filter panel is reset
	public JButton createPapersSearchButton() {
		papersSearchButton = new JButton("Publication/Papers Search");
		papersSearchButton.setBackground(SystemColor.inactiveCaption);
		papersSearchButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JSplitPane oldFilterAndResult = (JSplitPane) SearchPanel.getRightComponent();
				resultView = null;
				oldFilterAndResult.removeAll(); 
				JSplitPane emptyFilterAndResultPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, 
						true, null, null);
				new PapersSearchPanel(SearchPanel, searchPane, emptyFilterAndResultPane, table, contentPane);
			}
		});
		return papersSearchButton;
	}

	
	/* (non-Javadoc)
	 * @see views.panel.PanelInterface#createKeywordsSearchButton()
	 */// createPapersSearchButton() creates paper search button
	// with is active and on clicking this button, user is displayed with paper search
	// panel and result & filter panel is reset
	public JButton createKeywordsSearchButton() {
		titleSearchButton = new JButton("Title Search");
		titleSearchButton.setBackground(SystemColor.inactiveCaption);
		titleSearchButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JSplitPane oldFilterAndResult = (JSplitPane) SearchPanel.getRightComponent();
				oldFilterAndResult.removeAll(); 
				resultView = null;
				JSplitPane emptyFilterAndResultPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, 
						true, null, null);
				emptyFilterAndResultPane.setResizeWeight(0.3);
				new KeywordsSearchPanel(SearchPanel, searchPane, emptyFilterAndResultPane, table, contentPane);

			}
		});
		return titleSearchButton;
	}

	
	/**
	 * @param name to check constraints on author name
	 * @return // checks if the input string is valid, i.e not null and size >0 
	 */
	private boolean validName(String name) {
		return name!=null && name.trim().length()!=0;
	}

	
	/* (non-Javadoc)
	 * @see views.panel.PanelInterface#createSearchButton()
	 */// createSearchButton() creates a search button
	public JButton createSearchButton() {
		searchButton = new JButton("Search");

		searchButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				String s= "";
				s = authorNameField.getText().replaceAll("^ +| +$|( )+", "$1");
				if((!validName(s))) {
					JOptionPane.showMessageDialog(null,"Enter valid name!");

				} else {
					List<Result> authorlist = new ArrayList<Result>();
					try {
						authorlist=QueryEngineAuthor.retrieveAuthor(s);
					} catch (SQLException e1) {
						e1.printStackTrace();
					}

					JScrollPane resultPane = new JScrollPane();
					resultView = new ResultsPanel(resultPane, authorlist);      
					FilterPanel filterView = new FilterPanel(resultView.getTable());
					filterAndResultPanel = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, 
							true, filterView, resultPane);
					SearchPanel.setRightComponent(filterAndResultPanel);
					SearchPanel.setDividerLocation(400);
				}

			}
		});

		return searchButton;
	}

	// return this panel
	public AuthorSearchPanel getAuthorPanel() {
		return this;
	}

	
	/* (non-Javadoc)
	 * @see views.panel.PanelInterface#createLayout()
	 */// createLayout() creates a group layout for the author search panel & returns it 
	public GroupLayout createLayout() {
		GroupLayout authorSearchLayout = new GroupLayout(searchPane);
		authorSearchLayout.setHorizontalGroup(
				authorSearchLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(authorSearchLayout.createSequentialGroup()
						.addGroup(authorSearchLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(authorSearchLayout.createSequentialGroup()
										.addGap(39)
										.addGroup(authorSearchLayout.createParallelGroup(Alignment.LEADING, false)
												.addGroup(authorSearchLayout.createSequentialGroup()
														.addComponent(authorSearchButton, GroupLayout.PREFERRED_SIZE, 244, GroupLayout.PREFERRED_SIZE)
														.addGap(18)
														.addComponent(papersSearchButton, GroupLayout.PREFERRED_SIZE, 232, GroupLayout.PREFERRED_SIZE)
														.addGap(18)
														.addComponent(titleSearchButton, GroupLayout.PREFERRED_SIZE, 232, GroupLayout.PREFERRED_SIZE))
												.addGroup(authorSearchLayout.createSequentialGroup()
														.addComponent(authorNameLabel, GroupLayout.PREFERRED_SIZE, 104, GroupLayout.PREFERRED_SIZE)
														.addGap(38)
														.addComponent(authorNameField, GroupLayout.PREFERRED_SIZE, 173, GroupLayout.PREFERRED_SIZE))))
								.addGroup(authorSearchLayout.createSequentialGroup()
										.addGap(46)
										.addComponent(searchHeading_1, GroupLayout.PREFERRED_SIZE, 353, GroupLayout.PREFERRED_SIZE)))
						.addGap(58)
						.addGroup(authorSearchLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(cancelButton, GroupLayout.DEFAULT_SIZE, 170, Short.MAX_VALUE)
								.addComponent(searchButton, GroupLayout.DEFAULT_SIZE, 170, Short.MAX_VALUE)
								.addComponent(btnGoToFavorities, Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 170, GroupLayout.PREFERRED_SIZE))
						.addGap(45))
				);
		authorSearchLayout.setVerticalGroup(
				authorSearchLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(authorSearchLayout.createSequentialGroup()
						.addGap(40)
						.addGroup(authorSearchLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(btnGoToFavorities, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE)
								.addComponent(searchHeading_1, GroupLayout.PREFERRED_SIZE, 54, GroupLayout.PREFERRED_SIZE))
						.addGap(64)
						.addGroup(authorSearchLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(authorSearchLayout.createParallelGroup(Alignment.BASELINE)
										.addComponent(authorNameLabel, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
										.addComponent(authorNameField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								.addGroup(authorSearchLayout.createSequentialGroup()
										.addComponent(searchButton, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(ComponentPlacement.UNRELATED)
										.addGroup(authorSearchLayout.createParallelGroup(Alignment.BASELINE)
												.addComponent(cancelButton, GroupLayout.PREFERRED_SIZE, 46, GroupLayout.PREFERRED_SIZE)
												.addComponent(papersSearchButton, GroupLayout.PREFERRED_SIZE, 39, GroupLayout.PREFERRED_SIZE)
												.addComponent(titleSearchButton, GroupLayout.PREFERRED_SIZE, 39, GroupLayout.PREFERRED_SIZE)
												.addComponent(authorSearchButton, GroupLayout.PREFERRED_SIZE, 39, GroupLayout.PREFERRED_SIZE))))
						.addGap(61))
				);
		searchPane.setLayout(authorSearchLayout);
		SearchPanel.setDividerLocation(400);
		return authorSearchLayout;
	}

	
	/**
	 * @return// getPaperSearchButton() return paper search button
	 */
	public JButton getPaperSearchButton() {
		return this.papersSearchButton;
	}

	
	/**
	 * @return // getSearchButton() return search button
	 */
	public JButton getSearchButton() {
		return this.searchButton;
	}

	
	/**
	 * @return // getCancelButton() return cancel button
	 */
	public JButton getCancelButton() {
		return this.cancelButton;
	}

	
	/**
	 * @return // getTitleSearchButton() return title search button
	 */
	public JButton getTitleSearchButton(){
		return this.createKeywordsSearchButton();
	}
}