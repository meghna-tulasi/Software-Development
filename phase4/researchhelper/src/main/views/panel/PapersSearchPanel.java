package views.panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
 
import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import project.commons.Favorites;
import project.commons.JTextFieldLimit;
import project.commons.Result;
import queryengine.QueryEnginePaper;
import views.panel.AuthorSearchPanel;
import views.panel.FilterPanel;
import views.panel.ResultsPanel;

import java.awt.CardLayout;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.Color;
 
// PapersSearchPanel  
// i.e; the search panel with all paper search elements
/**
 * @author neethuprasad
 *
 */
@SuppressWarnings("serial")
public class PapersSearchPanel extends JSplitPane implements ListSelectionListener, PanelInterface {
    private JTable table;
    private JTextField textField;
    private JTextField textField_1;
    private SearchPanel SearchPanel;
    private JSplitPane filterAndResultPanel;
    private JPanel contentPane;
    private ResultsPanel resultView;
    private String fromyear = "",toyear = "";
    private List<String> confnames =new ArrayList<String>();
    private String names[] = {"OOPSLA", "ECOOP" , "PLDI", "ICFP", "TOPLAS","TSE","TCE"};
    @SuppressWarnings({ "unchecked", "rawtypes" })
	JList conf= new JList(names) ;
    JLabel no_of_papers_label;
    private JTextField fromPapers;
    private JTextField toPapers;
    private JLabel paperRangeSeperator;
    private JLabel name_of_conf_label;
    private JButton authorSearchButton;
    private JButton papersSearchButton;
    private JButton titleSearchButton;
    private JLabel served_in_committe_label;
    private JRadioButton servedYes;
    private JRadioButton servedNo;
    private JTextField servedFrom;
    private JTextField servedTill;
    private String served_from="",served_till="";
    private JLabel label_1;
    private JButton btnNewButton;
    private JRadioButton rangeRadioButton;
    private JLabel lblNewLabel_3;
    private JLabel lblNewLabel_4;
    private JButton btnCancel;
    private JButton btnGoToFavorities;
    private JPanel searchPane;
    private JRadioButton rdbtnEnable;   
    private JRadioButton rdbtnDisable;
    private JRadioButton rdbtnAllPapers;
 
    // constructor initializes search(top panel),filter(bottom right panel) and result(bottom left panel)
    /**
     * @param SearchPanel
     * @param searchPane
     * @param filterAndResultPanel
     * @param table
     * @param contentPane
     */
    public PapersSearchPanel(SearchPanel SearchPanel, JPanel searchPane, JSplitPane filterAndResultPanel, JTable table, JPanel contentPane ){
        this.SearchPanel = SearchPanel;
        this.filterAndResultPanel = filterAndResultPanel;
        this.table = table;
        this.contentPane = contentPane;
        this.searchPane = searchPane;
        addGUI();
       
    }
 
    // adds UI elements
    public void addGUI() {
    	// reset search and filter & results panel
        searchPane.removeAll();
       //searchPane = new JPanel();
        filterAndResultPanel.removeAll();
 
        // number of papers range
        createNoOfPapersLabel();
        createFromPapers();
        createToPapers();
        createPapersRangeSeperator();
 
        // name of conference
        createNameOfConferenceLabel();
        conf.setLayoutOrientation(JList.HORIZONTAL_WRAP);
        conf.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        conf.addListSelectionListener(this);
 
 
        // create published year
        createPublishedYearUIElements();
 
        createSearchButton();
        createAuthorSearchButton();
        createPapersSearchButton();
        createKeywordsSearchButton();
        createGoToFavoritesButton();
        createCancelButton();
 
        createPaperRangeUIElements();
 
        createServedInCommitteeUIElements();
        createHeading();
 
        createLayout();
 
    }
     
    // creates cancel button
    public JButton createCancelButton() {
        btnCancel = new JButton("Cancel");
        btnCancel.addActionListener(new ActionListener() {      
            public void actionPerformed(ActionEvent e) {
            	// resets all the search elements
                confnames = new ArrayList<String>();
                textField.setText("");
                textField_1.setText("");
                servedFrom.setEnabled(false);
                servedTill.setEnabled(false);
                servedNo.setSelected(true);
                servedFrom.setText("");
                servedTill.setText("");
                JSplitPane oldFilterAndResult = (JSplitPane) SearchPanel.getRightComponent();
                oldFilterAndResult.removeAll();                 
            }       
        });
        return btnCancel;
    }
 
    // adds the selected conference names to a list to be passed to queryengine
    @SuppressWarnings("unchecked")
    public void valueChanged(ListSelectionEvent e) {
        confnames = new ArrayList<String>();
 
        List<String> obj = (List<String>) conf.getSelectedValuesList();
        for(int i = 0; i < obj.size(); i++)
        {
            System.out.println("conf "+obj.get(i));
            confnames.add(obj.get(i));
 
        }
    }
 
    /**
     * creates number of papers label
     */
    public void createNoOfPapersLabel() {
        no_of_papers_label = new JLabel("Number of papers:");
    }
 
    /**
     * creates from papers textfield
     */
    public void createFromPapers() {
        fromPapers = new JTextField();
        fromPapers.setDocument
        (new JTextFieldLimit(2, true));
        fromPapers.setColumns(10);
        fromPapers.setEnabled(false);
    }
 
    /**
     * creates to papers textfield
     */
    public void createToPapers() {
        toPapers = new JTextField();
        toPapers.setDocument
        (new JTextFieldLimit(2, true));
        toPapers.setColumns(10);
        toPapers.setEnabled(false);
    }
 
    // navigate to favorites page
    /* (non-Javadoc)
     * @see views.panel.PanelInterface#createGoToFavoritesButton()
     */
    public JButton createGoToFavoritesButton() {        
        btnGoToFavorities = new JButton("My favorities");       
        btnGoToFavorities.addActionListener(new ActionListener() {      
            @SuppressWarnings("unused")
			public void actionPerformed(ActionEvent e) {           
                FavoritesPanel favoritesView = new FavoritesPanel(contentPane, SearchPanel);     
                JSplitPane favView = favoritesView.getFavoritesPanel();     
                contentPane.add(favView, "favoritesView");      
                CardLayout card = (CardLayout)contentPane.getLayout();      
                card.show(contentPane, "favoritesView");                        
            }       
        }); 
        return btnGoToFavorities;
    }
 
    /**
     * creates served in committee label, textfield for served year range
     */
    public void createServedInCommitteeUIElements() {
        served_in_committe_label = new JLabel("Served in committee?");
 
        servedYes = new JRadioButton("Yes");
 
        servedNo = new JRadioButton("No", true);
 
        servedFrom = new JTextField();
        servedFrom.setColumns(10);
         
        servedFrom.setDocument
        (new JTextFieldLimit(4,true));
        servedFrom.setEnabled(false);
 
        createPapersRangeSeperator();
 
        servedTill = new JTextField();
        servedTill.setColumns(10);
        servedTill.setDocument
        (new JTextFieldLimit(4,true));
        servedTill.setEnabled(false);
 
        ButtonGroup served_yes_no = new ButtonGroup();
        served_yes_no.add(servedYes);
        served_yes_no.add(servedNo);
 
        servedYes.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
 
                servedFrom.setEnabled(true);
                servedTill.setEnabled(true);
            }
        });
        servedNo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                servedFrom.setEnabled(false);
                servedTill.setEnabled(false);
                servedFrom.setText("");
                servedTill.setText("");
                
            }
        });
    }
 
 
    /**
     * creates seperator between ranges
     */
    public void createPapersRangeSeperator() {
        paperRangeSeperator = new JLabel("-");              
    }
 
    /**
     * creates name of conference label
     */
    public void createNameOfConferenceLabel() {
        name_of_conf_label = new JLabel("Name of conference publication/journal:");
    }
 
    /* (non-Javadoc)
     * @see views.panel.PanelInterface#createAuthorSearchButton()
     *///creates author search button to navigate from paper search panel
    public JButton createAuthorSearchButton() {
        authorSearchButton = new JButton("Author Search");
        authorSearchButton.setBackground(SystemColor.inactiveCaption);
        authorSearchButton.addActionListener(new ActionListener() {
            @SuppressWarnings("unused")
            public void actionPerformed(ActionEvent e) {
                JSplitPane oldFilterAndResult = (JSplitPane) SearchPanel.getRightComponent();
                resultView = null;
                oldFilterAndResult.removeAll(); 
                JSplitPane emptyFilterAndResultPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, 
                        true, null, null);; 
                        AuthorSearchPanel p = new AuthorSearchPanel(SearchPanel, searchPane, emptyFilterAndResultPane, table, contentPane);
            }
        });
        return authorSearchButton;
    }
 
    /* (non-Javadoc)
     * @see views.panel.PanelInterface#createPapersSearchButton()
     *///creates paper seach button and is enabled to false
    public JButton createPapersSearchButton() {
        papersSearchButton = new JButton("Publications/Papers Search");
        papersSearchButton.setBackground(SystemColor.activeCaption);
        papersSearchButton.setEnabled(false);
        return papersSearchButton;
    }
 
    /* (non-Javadoc)
     * @see views.panel.PanelInterface#createSearchButton()
     *///creates search results button
    public JButton createSearchButton() {
        btnNewButton = new JButton("Search");
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
 
                fromyear = textField.getText();
                toyear = textField_1.getText();
 
                served_from=servedFrom.getText();
                served_till=servedTill.getText();
                String r1=fromPapers.getText();
                String r2=toPapers.getText();
 
                List<Result> paperlist = new ArrayList<Result>();
                 
                if(rangeRadioButton.isSelected() && (Integer.parseInt(r1) > Integer.parseInt(r2))) {
 
                    JOptionPane.showMessageDialog(searchPane,"Invalid paper range!");
                    return;
                }
 
 
                try {
 
                    if(rdbtnEnable.isSelected() &&(!isNumeric(fromyear) || !isNumeric(toyear)) ) {
 
                        JOptionPane.showMessageDialog(searchPane,"Invalid published to/from year!");
                        return;
 
                    } else if(rdbtnEnable.isSelected()){
                        if(Integer.parseInt(fromyear) > Integer.parseInt(toyear) || Integer.parseInt(fromyear) >= 2017
                                || Integer.parseInt(toyear) >= 2017) {
                            JOptionPane.showMessageDialog(searchPane,"Invalid published year range!");
                            return;
                        }
                    }
 
                    if(servedNo.isSelected())
 
                    {
                        paperlist=QueryEnginePaper.retrievePaper(r1,r2,confnames,fromyear,toyear);
 
                    }
 
                    else
                    {
                        if(servedYes.isSelected() && (servedFrom.getText().isEmpty()) && (servedTill.getText().isEmpty())){
                            paperlist=QueryEnginePaper.retrievePerson(r1,r2,confnames,fromyear,toyear,served_from,served_till);
                            return;
                        }
                        if(servedYes.isSelected()&&(!isNumeric(servedFrom.getText()) || !isNumeric(servedTill.getText()))) {
                            JOptionPane.showMessageDialog(searchPane,"Invalid served year range values!");
                            return;
                        } else if(servedYes.isSelected()) {
                            if(Integer.parseInt(servedFrom.getText()) > Integer.parseInt(servedTill.getText()) || 
                                    Integer.parseInt(servedFrom.getText()) >= 2017 || Integer.parseInt(servedTill.getText()) >= 2017) {
                                JOptionPane.showMessageDialog(searchPane,"Invalid served year range!");
                                return;
                            }
                        }
                        paperlist=QueryEnginePaper.retrievePerson(r1,r2,confnames,fromyear,toyear,served_from,served_till);
                    }
 
                }
                catch(SQLException e1)
                {
                    e1.printStackTrace();
                }
                JScrollPane resultPane = new JScrollPane();
                resultView = new ResultsPanel(resultPane, paperlist);       
                FilterPanel filterView = new FilterPanel(resultView.getTable());
                filterAndResultPanel = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, 
                        true, filterView, resultPane);
                filterAndResultPanel.setResizeWeight(0.3);
                SearchPanel.setRightComponent(filterAndResultPanel);
            }
        });
        return btnNewButton;
    }
 
    /* (non-Javadoc)
     * @see views.panel.PanelInterface#createKeywordsSearchButton()
     *///creates keyword search button
    public JButton createKeywordsSearchButton() {
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
 
    /**
     *
     *///creates paper range UI Elements 
    public void createPaperRangeUIElements() {
 
        rangeRadioButton = new JRadioButton("Specify Range");
        rdbtnAllPapers = new JRadioButton("All Papers", true);
        ButtonGroup no_of_papers_group = new ButtonGroup();
        no_of_papers_group.add(rangeRadioButton);
        no_of_papers_group.add(rdbtnAllPapers);
        rangeRadioButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                 
                fromPapers.setEnabled(true);
                toPapers.setEnabled(true);
                 
                 
            }
        });
        rdbtnAllPapers.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                 
                fromPapers.setEnabled(false);
                toPapers.setEnabled(false);    
                fromPapers.setText("");
                toPapers.setText("");
            }
        });
    }
 
    /**
     * 
     *///creates main heading
    public void createHeading() {
        label_1 = new JLabel("Papers/Publications Search Page");
        label_1.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 30));
    }
 
    /**
     * 
     *///creates published year UI Elements
    public void createPublishedYearUIElements() {
        lblNewLabel_3 = new JLabel("Published Year:");
        textField = new JTextField();
        textField.setColumns(10);
        textField.setDocument
        (new JTextFieldLimit(4,true));
        lblNewLabel_4 = new JLabel("-");
        textField_1 = new JTextField();
        textField_1.setColumns(10);
        textField_1.setDocument
        (new JTextFieldLimit(4,true));
        textField.setEnabled(false);
        textField_1.setEnabled(false);
        rdbtnEnable = new JRadioButton("Specify Year Range");
        rdbtnDisable = new JRadioButton("All Years");
        rdbtnDisable.setSelected(true);
        ButtonGroup published_year_yes_no = new ButtonGroup();
        published_year_yes_no.add(rdbtnEnable);
        published_year_yes_no.add(rdbtnDisable);
  
        rdbtnDisable.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                textField.setEnabled(false);
                textField_1.setEnabled(false);
                textField.setText("");
                textField_1.setText("");
            }
        });
 
        rdbtnEnable.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                textField.setEnabled(true);
                textField_1.setEnabled(true);
            }
        });
    }
 
    /* (non-Javadoc)
     * @see views.panel.PanelInterface#createLayout()
     *///creates a group layout for the paper search panel & returns it  
    public GroupLayout createLayout() {
         
        JLabel lblNumberOf = new JLabel("* Number of papers accept 2 digit , Published year and Served in commitee fields accepts only 4 digit numbers");
        lblNumberOf.setForeground(Color.RED);
        lblNumberOf.setFont(new Font("Tahoma", Font.ITALIC, 16));
 
        GroupLayout gl_contentPane = new GroupLayout(searchPane);
        gl_contentPane.setHorizontalGroup(
        	gl_contentPane.createParallelGroup(Alignment.LEADING)
        		.addGroup(gl_contentPane.createSequentialGroup()
        			.addGap(40)
        			.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
        				.addGroup(gl_contentPane.createSequentialGroup()
        					.addComponent(authorSearchButton, GroupLayout.PREFERRED_SIZE, 232, GroupLayout.PREFERRED_SIZE)
        					.addGap(18)
        					.addComponent(papersSearchButton, GroupLayout.PREFERRED_SIZE, 268, GroupLayout.PREFERRED_SIZE)
        					.addPreferredGap(ComponentPlacement.UNRELATED)
        					.addComponent(titleSearchButton, GroupLayout.PREFERRED_SIZE, 268, GroupLayout.PREFERRED_SIZE))
        				.addComponent(label_1, GroupLayout.PREFERRED_SIZE, 542, GroupLayout.PREFERRED_SIZE)
        				.addGroup(gl_contentPane.createSequentialGroup()
        					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
        						.addGroup(gl_contentPane.createSequentialGroup()
        							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
        								.addComponent(no_of_papers_label)
        								.addComponent(rangeRadioButton)
        								.addGroup(gl_contentPane.createSequentialGroup()
        									.addGap(2)
        									.addComponent(fromPapers, GroupLayout.PREFERRED_SIZE, 44, GroupLayout.PREFERRED_SIZE)
        									.addPreferredGap(ComponentPlacement.RELATED)
        									.addComponent(paperRangeSeperator)
        									.addPreferredGap(ComponentPlacement.UNRELATED)
        									.addComponent(toPapers, GroupLayout.PREFERRED_SIZE, 46, GroupLayout.PREFERRED_SIZE))
        								.addComponent(rdbtnAllPapers))
        							.addGap(62)
        							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
        								.addComponent(name_of_conf_label)
        								.addComponent(conf, GroupLayout.PREFERRED_SIZE, 272, GroupLayout.PREFERRED_SIZE))
        							.addPreferredGap(ComponentPlacement.RELATED, 52, Short.MAX_VALUE)
        							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
        								.addGroup(gl_contentPane.createSequentialGroup()
        									.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING, false)
        										.addComponent(lblNewLabel_3)
        										.addGroup(gl_contentPane.createSequentialGroup()
        											.addComponent(textField, GroupLayout.PREFERRED_SIZE, 57, GroupLayout.PREFERRED_SIZE)
        											.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        											.addComponent(lblNewLabel_4, GroupLayout.PREFERRED_SIZE, 15, GroupLayout.PREFERRED_SIZE)
        											.addGap(9)))
        									.addPreferredGap(ComponentPlacement.RELATED)
        									.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
        										.addComponent(rdbtnEnable)
        										.addComponent(textField_1, GroupLayout.PREFERRED_SIZE, 61, GroupLayout.PREFERRED_SIZE)))
        								.addGroup(gl_contentPane.createSequentialGroup()
        									.addComponent(served_in_committe_label)
        									.addGap(28)
        									.addComponent(servedYes))
        								.addGroup(gl_contentPane.createSequentialGroup()
        									.addComponent(servedFrom, GroupLayout.PREFERRED_SIZE, 66, GroupLayout.PREFERRED_SIZE)
        									.addGap(49)
        									.addComponent(servedTill, GroupLayout.PREFERRED_SIZE, 66, GroupLayout.PREFERRED_SIZE)))
        							.addGap(1))
        						.addComponent(lblNumberOf))
        					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
        						.addGroup(gl_contentPane.createSequentialGroup()
        							.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        							.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
        								.addComponent(btnGoToFavorities, GroupLayout.PREFERRED_SIZE, 278, GroupLayout.PREFERRED_SIZE)
        								.addComponent(btnNewButton, GroupLayout.PREFERRED_SIZE, 278, GroupLayout.PREFERRED_SIZE)
        								.addComponent(btnCancel, GroupLayout.PREFERRED_SIZE, 278, GroupLayout.PREFERRED_SIZE)))
        						.addGroup(gl_contentPane.createSequentialGroup()
        							.addGap(18)
        							.addComponent(rdbtnDisable))
        						.addGroup(gl_contentPane.createSequentialGroup()
        							.addPreferredGap(ComponentPlacement.RELATED)
        							.addComponent(servedNo, GroupLayout.PREFERRED_SIZE, 57, GroupLayout.PREFERRED_SIZE)))))
        			.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        gl_contentPane.setVerticalGroup(
        	gl_contentPane.createParallelGroup(Alignment.LEADING)
        		.addGroup(gl_contentPane.createSequentialGroup()
        			.addContainerGap()
        			.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
        				.addComponent(label_1, GroupLayout.PREFERRED_SIZE, 54, GroupLayout.PREFERRED_SIZE)
        				.addComponent(btnGoToFavorities, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE))
        			.addPreferredGap(ComponentPlacement.UNRELATED)
        			.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
        				.addComponent(no_of_papers_label)
        				.addComponent(name_of_conf_label)
        				.addComponent(lblNewLabel_3)
        				.addComponent(rdbtnEnable)
        				.addComponent(rdbtnDisable))
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
        				.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
        					.addComponent(conf)
        					.addGroup(gl_contentPane.createSequentialGroup()
        						.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
        							.addComponent(textField_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        							.addComponent(lblNewLabel_4)
        							.addComponent(textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        						.addGap(27)
        						.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
        							.addComponent(served_in_committe_label)
        							.addComponent(servedYes)
        							.addComponent(servedNo))))
        				.addGroup(gl_contentPane.createSequentialGroup()
        					.addGap(7)
        					.addComponent(rangeRadioButton)
        					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
        						.addGroup(gl_contentPane.createSequentialGroup()
        							.addGap(64)
        							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
        								.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
        									.addComponent(servedFrom, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        									.addComponent(servedTill, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        								.addComponent(btnNewButton, GroupLayout.PREFERRED_SIZE, 51, GroupLayout.PREFERRED_SIZE)))
        						.addGroup(gl_contentPane.createSequentialGroup()
        							.addGap(1)
        							.addComponent(rdbtnAllPapers)
        							.addGap(10)
        							.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
        								.addComponent(fromPapers, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        								.addComponent(paperRangeSeperator, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
        								.addComponent(toPapers, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))))))
        			.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        			.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
        				.addComponent(btnCancel, GroupLayout.PREFERRED_SIZE, 51, GroupLayout.PREFERRED_SIZE)
        				.addComponent(lblNumberOf))
        			.addGap(7)
        			.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
        				.addComponent(authorSearchButton, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE)
        				.addComponent(papersSearchButton, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE)
        				.addComponent(titleSearchButton, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE))
        			.addGap(10))
        );
        searchPane.setLayout(gl_contentPane);
        return gl_contentPane;
    }
 
    /**
     * @param value 
     * @return //checks if the value input is integer
     */
    public boolean isNumeric(String value) {
        try {
            NumberFormat.getInstance().parse(value);
        } catch(Exception e) {
            return false;
        }
        return true;
    }
 
    /**
     * @param field
     * @return //checks input and matches with pattern
     */
    @SuppressWarnings("unused")
    private boolean isContainInt(JTextField field) {
 
        Pattern pt = Pattern.compile("\\d+");
        Matcher mt = pt.matcher(field.getText());
        return mt.find();
    }
 
    /**
     * @return
     */
    public JButton getCancelButton() {
        return this.btnCancel;
    }
 
    public JButton getSearchButton() {
        return this.btnNewButton;
    }
 
    public PapersSearchPanel getPaperPanel() {
        return this;
    }
 
    public JButton getGoToFavoritesButton() {
        return this.btnGoToFavorities;
    }
 
    public JButton getAuthorSearchButton() {
        return this.authorSearchButton;
    }
    
    public JButton getPaperSearchButton() {
    	return this.papersSearchButton;
    }
 
    public JLabel getNoOfPapersLabel() {
        return this.no_of_papers_label;
    }

	public JButton getTitleSearchButton() {
		return this.titleSearchButton;
	}
}