package views;
 
import java.awt.Font;
import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractButton;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.util.HashSet;
 
import QueryEngine.QueryEngineAuthor;
import project.commons.JTextFieldLimit;
import project.commons.Result;
import java.awt.SystemColor;
 
@SuppressWarnings("serial")
public class AuthorSearchPanel extends JSplitPane{
    private JTextField authorNameField;
    private SearchPanel SearchPanel;
    private JSplitPane filterAndResultPanel;
    private JTable table;
    private JLabel searchHeading;
    private JLabel firstNameLabel;
    private JButton authorSearchButton;
    private JButton titleSearchButton;
    private JButton papersSearchButton;
    private JButton searchButton;
    private JButton cancelButton;
    private JButton btnGoToFavorities;      
    private JPanel contentPane;     
    private ResultsPanel resultView;
 
    public AuthorSearchPanel(SearchPanel SearchPanel,JPanel searchPane, JSplitPane filterAndResultPanel, JTable table, JPanel contentPane ) {
        this.SearchPanel = SearchPanel;
        this.filterAndResultPanel = filterAndResultPanel;
        this.table = table;
        this.contentPane = contentPane;     
        addGUIToSearchPane(searchPane);
    }
 
    public void addGUIToSearchPane(JPanel searchPane) {
        searchPane.removeAll();
        filterAndResultPanel.removeAll();
 
        searchHeading = createHeading();
        firstNameLabel = createFirstNameLabel();
        authorNameField = createFirstNameField();
 
        authorSearchButton = createAuthorSearchButton();
        papersSearchButton = createPapersSearchButton(searchPane);
        titleSearchButton = createKeywordsSearchButton(searchPane);
        searchButton = createSearchButton(searchPane);
        createGoToFavoritesButton(contentPane);
        createCancelButton();
 
        createLayout(searchPane);
    }
 
    private void createCancelButton() {
        cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new ActionListener() {       
            public void actionPerformed(ActionEvent e) {    
                authorNameField.setText("");
                JSplitPane oldFilterAndResult = (JSplitPane) SearchPanel.getRightComponent();
                oldFilterAndResult.removeAll();                 
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
 
    public JLabel getFirstNameLabel() {
        return this.firstNameLabel;
    }
 
    private JLabel createFirstNameLabel() {
        firstNameLabel = new JLabel("First Name:");
        firstNameLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
        return firstNameLabel;
    }
 
    private JLabel createHeading() {
        searchHeading = new JLabel("Author Search Page");
        searchHeading.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 30));
        return searchHeading;
    }
 
    private JTextField createFirstNameField() {
         
                 
        authorNameField = new JTextField(); 
        authorNameField.setDocument
        (new JTextFieldLimit(30,false));
        authorNameField.setColumns(10);

        return authorNameField;
    } 
 
    private JButton createAuthorSearchButton() {
        authorSearchButton = new JButton("Author Search");
        authorSearchButton.setBackground(SystemColor.activeCaption);
        authorSearchButton.setEnabled(false);
        return authorSearchButton;
    }
 
    private JButton createPapersSearchButton(JPanel searchPane) {
        papersSearchButton = new JButton("Publication/Papers Search");
        papersSearchButton.setBackground(SystemColor.inactiveCaption);
        papersSearchButton.addActionListener(new ActionListener() {
            @SuppressWarnings("unused")
            public void actionPerformed(ActionEvent e) {
                JSplitPane oldFilterAndResult = (JSplitPane) SearchPanel.getRightComponent();
                resultView = null;
                oldFilterAndResult.removeAll(); 
                JSplitPane emptyFilterAndResultPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, 
                        true, null, null);
                PapersSearchPanel p = new PapersSearchPanel(SearchPanel, searchPane, emptyFilterAndResultPane, table, contentPane);
            }
        });
        return papersSearchButton;
    }
 
    private JButton createKeywordsSearchButton(JPanel searchPane) {
        titleSearchButton = new JButton("Title Search");
        titleSearchButton.setBackground(SystemColor.inactiveCaption);
        titleSearchButton.addActionListener(new ActionListener() {
            @SuppressWarnings("unused")
            public void actionPerformed(ActionEvent e) {
                JSplitPane oldFilterAndResult = (JSplitPane) SearchPanel.getRightComponent();
                oldFilterAndResult.removeAll(); 
                resultView = null;
                JSplitPane emptyFilterAndResultPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, 
                        true, null, null);
                KeywordsSearchPanel p = new KeywordsSearchPanel(SearchPanel, searchPane, emptyFilterAndResultPane, table, contentPane);
 
            }
        });
        return titleSearchButton;
    }
 
    private JButton createSearchButton(JPanel searchPane) {
        searchButton = new JButton("Search");
 
        searchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //JPanel filterPane = new JPanel();
 
 
                String s= "";
                s = authorNameField.getText();
                List<Result> authorlist = new ArrayList<Result>();
                try {
                    authorlist=QueryEngineAuthor.retrieveAuthor(s);
                    //ResultTable rt = new ResultTable(authorlist);
                } catch (SQLException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
 
                JScrollPane resultPane = new JScrollPane();
                resultView = new ResultsPanel(resultPane, authorlist);      
                FilterPanel filterView = new FilterPanel(resultView.getTable());
                filterAndResultPanel = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, 
                        true, filterView, resultPane);
                SearchPanel.setRightComponent(filterAndResultPanel);
 
            }
        });
 
        return searchButton;
    }
 
 
 
    public AuthorSearchPanel getAuthorPanel() {
        return this;
    }
 
    private GroupLayout createLayout(JPanel searchPane) {
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
                                                        .addComponent(titleSearchButton, GroupLayout.PREFERRED_SIZE, 232, GroupLayout.PREFERRED_SIZE)
                                                        .addGap(19))
                                                .addGroup(authorSearchLayout.createSequentialGroup()
                                                        .addComponent(firstNameLabel, GroupLayout.PREFERRED_SIZE, 90, GroupLayout.PREFERRED_SIZE)
                                                        .addGap(28)
                                                        .addComponent(authorNameField, GroupLayout.PREFERRED_SIZE, 173, GroupLayout.PREFERRED_SIZE)
                                                        .addPreferredGap(ComponentPlacement.RELATED)
                                                        .addGap(15))))
                                .addGroup(authorSearchLayout.createSequentialGroup()
                                        .addGap(46)
                                        .addComponent(searchHeading, GroupLayout.PREFERRED_SIZE, 353, GroupLayout.PREFERRED_SIZE)))
                        .addGap(39)
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
                                .addComponent(searchHeading, GroupLayout.PREFERRED_SIZE, 54, GroupLayout.PREFERRED_SIZE))
                        .addGap(64)
                        .addGroup(authorSearchLayout.createParallelGroup(Alignment.LEADING)
                                .addGroup(authorSearchLayout.createParallelGroup(Alignment.BASELINE)
                                        .addComponent(firstNameLabel, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
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
        return authorSearchLayout;
    }

	public JButton getGotoFavorires() {
		return this.btnGoToFavorities;
	}

	public JButton getPaperSearchButton() {
		return this.papersSearchButton;
	}
	
	public JButton getSearchButton() {
		return this.searchButton;
	}

	public JButton getCancelButton() {
		return this.cancelButton;
	}
	
	public JButton getTitleSearchButton(JPanel searchPane){
		return this.createKeywordsSearchButton(searchPane);
	}
}