package views;
 
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
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
 
import QueryEngine.QueryEngineAuthor;
import QueryEngine.QueryEngineTitle;
import project.commons.JTextFieldLimit;
import project.commons.Result;
 
public class KeywordsSearchPanel extends JSplitPane{
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
 
 
    public KeywordsSearchPanel(SearchPanel SearchPanel, JPanel searchPane, JSplitPane filterAndResultPanel, JTable table, JPanel contentPane ) {
        this.SearchPanel = SearchPanel;
        this.filterAndResultPanel = filterAndResultPanel;
        this.table = table;
        this.contentPane = contentPane;
        addGUIToSearchPane(searchPane);
    }
 
    public void addGUIToSearchPane(JPanel searchPane) {
        searchPane.removeAll();
        //searchPane = new JPanel();
        filterAndResultPanel.removeAll();
 
        createHeading();
        createTitleLabel();
        createTitleField();
 
        createCancelButton();
        createAuthorSearchButton(searchPane);
        createPapersSearchButton(searchPane);
        createKeywordsSearchButton();
        createSearchButton(searchPane);
        createGoToFavoritesButton(contentPane);
        createLayout(searchPane);
    }
 
    public JSplitPane getKeywordSearchPanel() {
        return this;
    }
 
    public JTextField getTitleField() {
        return this.titleField;
    }
 
    private void createHeading() {
        searchHeading = new JLabel("Keywords Search Page");
        searchHeading.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 30));
    }
 
    private void createTitleLabel() {
        titleLabel = new JLabel("Keywords:");
        titleLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
    }
 
    private void createTitleField() {
        titleField = new JTextField();
        titleField.setColumns(100);
        titleField.setDocument
        (new JTextFieldLimit(30, false));
    }
     
    private void createCancelButton() {
        cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new ActionListener() {       
            public void actionPerformed(ActionEvent e) {    
                titleField.setText("");
                JSplitPane oldFilterAndResult = (JSplitPane) SearchPanel.getRightComponent();
                oldFilterAndResult.removeAll();                 
            }       
        });
    }
 
    private void createAuthorSearchButton(JPanel searchPane) {
        authorSearchButton = new JButton("Author Search");
        authorSearchButton.setBackground(SystemColor.inactiveCaption);
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
    }
     
    private void createGoToFavoritesButton(JPanel contentPane) {        
        btnGoToFavorities = new JButton("My favorities");        
        btnGoToFavorities.addActionListener(new ActionListener() {      
            public void actionPerformed(ActionEvent e) {        
                HashSet<Result> favorites = resultView !=null?resultView.getFavorities():new HashSet<Result>();     
                FavoritesPanel favoritesView = new FavoritesPanel(contentPane,favorites,SearchPanel);       
                JSplitPane favView = favoritesView.getFavoritesPanel();     
                contentPane.add(favView, "favoritesView");      
                CardLayout card = (CardLayout)contentPane.getLayout();      
                card.show(contentPane, "favoritesView");                        
            }       
        });     
    }
 
    private void createPapersSearchButton(JPanel searchPane) {
        papersSearchButton = new JButton("Publication/Papers Search");
        papersSearchButton.setBackground(SystemColor.inactiveCaption);
        papersSearchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JSplitPane oldFilterAndResult = (JSplitPane) SearchPanel.getRightComponent();
                resultView = null;
                oldFilterAndResult.removeAll(); 
                JSplitPane emptyFilterAndResultPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, 
                        true, null, null);
                PapersSearchPanel p = new PapersSearchPanel(SearchPanel, searchPane, emptyFilterAndResultPane, table, contentPane);
            }
        });
    }
 
    private void createKeywordsSearchButton() {
        titleSearchButton = new JButton("Title Search");
        titleSearchButton.setBackground(SystemColor.activeCaption);
        titleSearchButton.setEnabled(false);
        /*titleSearchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JSplitPane oldFilterAndResult = (JSplitPane) SearchPanel.getRightComponent();
                oldFilterAndResult.removeAll();         
            }
        });*/
    }
 
    private void createSearchButton(JPanel searchPane) {
        searchButton = new JButton("Search");
         
        searchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //JPanel filterPane = new JPanel();
                 
                String s= null;
                s = titleField.getText();
                 List<Result> titlelist = new ArrayList<Result>();
                try {
                    titlelist=QueryEngineTitle.retrieveTitle(s);
                    //ResultTable rt = new ResultTable(authorlist);
                } catch (SQLException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
 
                JScrollPane resultPane = new JScrollPane();
                resultView = new ResultsPanel(resultPane, titlelist);       
                FilterPanel filterView = new FilterPanel(resultView.getTable());
                filterAndResultPanel = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, 
                        true, filterView, resultPane);
                SearchPanel.setRightComponent(filterAndResultPanel);
 
            }
        });
    }
 
    private void createLayout(JPanel searchPane) {
         
        JLabel lblEnterKeywords = new JLabel("* Enter keywords seperated by ,");
        lblEnterKeywords.setFont(new Font("Tahoma", Font.ITALIC, 14));
        GroupLayout keywordSearchLayout = new GroupLayout(searchPane);
        keywordSearchLayout.setHorizontalGroup(
            keywordSearchLayout.createParallelGroup(Alignment.LEADING)
                .addGroup(keywordSearchLayout.createSequentialGroup()
                    .addGap(40)
                    .addGroup(keywordSearchLayout.createParallelGroup(Alignment.LEADING)
                        .addGroup(keywordSearchLayout.createSequentialGroup()
                            .addComponent(authorSearchButton, GroupLayout.PREFERRED_SIZE, 244, GroupLayout.PREFERRED_SIZE)
                            .addGap(12)
                            .addComponent(papersSearchButton, GroupLayout.PREFERRED_SIZE, 232, GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(ComponentPlacement.RELATED)
                            .addComponent(titleSearchButton, GroupLayout.PREFERRED_SIZE, 232, GroupLayout.PREFERRED_SIZE)
                            .addGap(18)
                            .addComponent(cancelButton, GroupLayout.DEFAULT_SIZE, 191, Short.MAX_VALUE))
                        .addGroup(keywordSearchLayout.createSequentialGroup()
                            .addGroup(keywordSearchLayout.createParallelGroup(Alignment.LEADING)
                                .addComponent(searchHeading, GroupLayout.PREFERRED_SIZE, 386, GroupLayout.PREFERRED_SIZE)
                                .addGroup(keywordSearchLayout.createSequentialGroup()
                                    .addPreferredGap(ComponentPlacement.RELATED)
                                    .addComponent(titleLabel, GroupLayout.PREFERRED_SIZE, 90, GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(ComponentPlacement.UNRELATED)
                                    .addGroup(keywordSearchLayout.createParallelGroup(Alignment.LEADING)
                                        .addComponent(lblEnterKeywords)
                                        .addComponent(titleField, GroupLayout.PREFERRED_SIZE, 600, GroupLayout.PREFERRED_SIZE))))
                            .addGap(22)
                            .addGroup(keywordSearchLayout.createParallelGroup(Alignment.LEADING)
                                .addGroup(keywordSearchLayout.createSequentialGroup()
                                    .addGap(42)
                                    .addComponent(btnGoToFavorities, GroupLayout.PREFERRED_SIZE, 169, GroupLayout.PREFERRED_SIZE))
                                .addGroup(keywordSearchLayout.createSequentialGroup()
                                    .addGap(29)
                                    .addComponent(searchButton, GroupLayout.DEFAULT_SIZE, 182, Short.MAX_VALUE)))))
                    .addGap(27))
        );
        keywordSearchLayout.setVerticalGroup(
            keywordSearchLayout.createParallelGroup(Alignment.LEADING)
                .addGroup(keywordSearchLayout.createSequentialGroup()
                    .addGroup(keywordSearchLayout.createParallelGroup(Alignment.LEADING)
                        .addGroup(keywordSearchLayout.createSequentialGroup()
                            .addGap(36)
                            .addComponent(btnGoToFavorities))
                        .addGroup(keywordSearchLayout.createSequentialGroup()
                            .addGap(25)
                            .addComponent(searchHeading, GroupLayout.PREFERRED_SIZE, 54, GroupLayout.PREFERRED_SIZE)))
                    .addGap(36)
                    .addGroup(keywordSearchLayout.createParallelGroup(Alignment.LEADING)
                        .addGroup(keywordSearchLayout.createParallelGroup(Alignment.BASELINE)
                            .addComponent(searchButton, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
                            .addComponent(titleField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                        .addGroup(keywordSearchLayout.createSequentialGroup()
                            .addGap(15)
                            .addComponent(titleLabel, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)))
                    .addGap(1)
                    .addComponent(lblEnterKeywords)
                    .addGap(8)
                    .addGroup(keywordSearchLayout.createParallelGroup(Alignment.LEADING)
                        .addGroup(keywordSearchLayout.createSequentialGroup()
                            .addPreferredGap(ComponentPlacement.RELATED, 4, Short.MAX_VALUE)
                            .addGroup(keywordSearchLayout.createParallelGroup(Alignment.BASELINE)
                                .addComponent(authorSearchButton, GroupLayout.PREFERRED_SIZE, 39, GroupLayout.PREFERRED_SIZE)
                                .addComponent(papersSearchButton, GroupLayout.PREFERRED_SIZE, 39, GroupLayout.PREFERRED_SIZE)
                                .addComponent(titleSearchButton, GroupLayout.PREFERRED_SIZE, 39, GroupLayout.PREFERRED_SIZE))
                            .addGap(48))
                        .addGroup(keywordSearchLayout.createSequentialGroup()
                            .addGap(18)
                            .addComponent(cancelButton, GroupLayout.PREFERRED_SIZE, 47, GroupLayout.PREFERRED_SIZE)
                            .addContainerGap())))
        );
        searchPane.setLayout(keywordSearchLayout);
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
	
	public JButton getTitleSearchButton(){
		return this.titleSearchButton;
	}
	
	public JButton getCancelButton(){
		return this.cancelButton;
	}

	public JLabel getKeywordLabel() {
		return this.titleLabel;
	}
}