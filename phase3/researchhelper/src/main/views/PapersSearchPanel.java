package views;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ListSelectionModel;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.NumberFormatter;

import QueryEngine.QueryEnginePaper;
import project.commons.JTextFieldLimit;
import project.commons.Result;
import views.AuthorSearchPanel;
import views.FilterPanel;
import views.ResultsPanel;

import java.awt.CardLayout;
import java.awt.Font;
import java.awt.SystemColor;


public class PapersSearchPanel extends JSplitPane implements ListSelectionListener{
	private JTable table;
	//private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private SearchPanel SearchPanel;
	private JSplitPane filterAndResultPanel;
	private JPanel contentPane;
	private ResultsPanel resultView;
	private String fromyear = "",toyear = "";
	private List<String> confnames =new ArrayList<String>();
	private String names[] = {"OOPSLA", "ECOOP" , "PLDI", "ICFP", "TOPLAS","TSE","TCE"};
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

	public PapersSearchPanel(SearchPanel SearchPanel, JPanel searchPane, JSplitPane filterAndResultPanel, JTable table, JPanel contentPane ){
		this.SearchPanel = SearchPanel;
		this.filterAndResultPanel = filterAndResultPanel;
		this.table = table;
		this.contentPane = contentPane;
		addGUIToSearchPane(searchPane);
		this.searchPane = searchPane;
	}

	public void addGUIToSearchPane(JPanel searchPane) {
		searchPane.removeAll();
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
		createAuthorSearchButton(searchPane);
		createPapersSearchButton();
		createKeywordsSearchButton(searchPane);
		createGoToFavoritesButton(contentPane);
		createCancelButton();

		createPaperRangeUIElements();

		createServedInCommitteeUIElements();
		createHeading();

		createLayout(searchPane);

	}
	
	public void createCancelButton() {
		btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {		
			public void actionPerformed(ActionEvent e) {
				textField.setText("");
				textField_1.setText("");
				JSplitPane oldFilterAndResult = (JSplitPane) SearchPanel.getRightComponent();
				oldFilterAndResult.removeAll();					
			}		
		});
	}

	public JSplitPane getPapersSearchPanel() {
		return this;
	}

	@SuppressWarnings("unchecked")
	public void valueChanged(ListSelectionEvent e) {
		// TODO Auto-generated method stub

		List<String> obj = (List<String>) conf.getSelectedValuesList();
		for(int i = 0; i < obj.size(); i++)
		{
			confnames.add(obj.get(i));

		}
	}

	public void createNoOfPapersLabel() {
		no_of_papers_label = new JLabel("Number of papers:");
	}

	public void createFromPapers() {
		fromPapers = new JTextField();
		fromPapers.setDocument
		(new JTextFieldLimit(2, true));
		fromPapers.setColumns(10);
		fromPapers.setVisible(false);
	}

	public void createToPapers() {
		toPapers = new JTextField();
		toPapers.setDocument
		(new JTextFieldLimit(2, true));
		toPapers.setColumns(10);
		toPapers.setVisible(false);
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

	public void createServedInCommitteeUIElements() {
		served_in_committe_label = new JLabel("Served in committee?");

		servedYes = new JRadioButton("Yes",true);

		servedNo = new JRadioButton("No");

		servedFrom = new JTextField();
		servedFrom.setColumns(10);
		
		servedFrom.setDocument
		(new JTextFieldLimit(4,true));
		servedFrom.setVisible(true);

		createPapersRangeSeperator();

		servedTill = new JTextField();
		servedTill.setColumns(10);
		servedTill.setDocument
		(new JTextFieldLimit(4,true));
		servedTill.setVisible(true);

		ButtonGroup served_yes_no = new ButtonGroup();
		served_yes_no.add(servedYes);
		served_yes_no.add(servedNo);

		
	

		servedYes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				servedFrom.setVisible(true);
				servedTill.setVisible(true);
			}
		});
		servedNo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				servedFrom.setVisible(false);
				servedTill.setVisible(false);
			}
		});
	}


	public void createPapersRangeSeperator() {
		paperRangeSeperator = new JLabel("-");				
		paperRangeSeperator.setVisible(false);
	}

	public void createNameOfConferenceLabel() {
		name_of_conf_label = new JLabel("Name of conference publication/journal:");
	}

	public void createAuthorSearchButton(JPanel searchPane) {
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
	}

	public void createPapersSearchButton() {
		papersSearchButton = new JButton("Publications/Papers Search");
		papersSearchButton.setBackground(SystemColor.activeCaption);
		papersSearchButton.setEnabled(false);
	}

	public void createSearchButton() {
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

				try {

					if(rdbtnEnable.isSelected() &&(!isNumeric(fromyear) || !isNumeric(toyear)) ) {

						JOptionPane.showMessageDialog(searchPane,"Invalid published to/from year");
						return;

					} else if(rdbtnEnable.isSelected()){
						if(Integer.parseInt(fromyear) > Integer.parseInt(toyear) || Integer.parseInt(fromyear) >= 2017 
								|| Integer.parseInt(toyear) >= 2017) {
							JOptionPane.showMessageDialog(searchPane,"Invalid published year range");
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
							JOptionPane.showMessageDialog(searchPane,"Invalid served year values");
							return;
						} else if(servedYes.isSelected()) {
							if(Integer.parseInt(servedFrom.getText()) > Integer.parseInt(servedTill.getText()) || 
									Integer.parseInt(servedFrom.getText()) >= 2017 || Integer.parseInt(servedTill.getText()) >= 2017) {
								JOptionPane.showMessageDialog(searchPane,"Invalid served year range");
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
				SearchPanel.setRightComponent(filterAndResultPanel);
			}
		});
	}

	public void createKeywordsSearchButton(JPanel searchPane) {
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
	}

	public void createPaperRangeUIElements() {

		rangeRadioButton = new JRadioButton("Specify Range");
		ButtonGroup no_of_papers_group = new ButtonGroup();
		no_of_papers_group.add(rangeRadioButton);
		rangeRadioButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				fromPapers.setVisible(true);
				toPapers.setVisible(true);
				paperRangeSeperator.setVisible(true);
				
			}
		});
	}

	public void createHeading() {
		label_1 = new JLabel("Papers/Publications Search Page");
		label_1.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 30));
	}

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
			}
		});

		rdbtnEnable.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textField.setEnabled(true);
				textField_1.setEnabled(true);
			}
		});
	}

	public void createLayout(JPanel searchPane) {

		GroupLayout gl_contentPane = new GroupLayout(searchPane);
		gl_contentPane.setHorizontalGroup(
				gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
						.addGap(77)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_contentPane.createSequentialGroup()
										.addPreferredGap(ComponentPlacement.UNRELATED)
										.addComponent(authorSearchButton, GroupLayout.PREFERRED_SIZE, 232, GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(ComponentPlacement.RELATED)
										.addComponent(papersSearchButton, GroupLayout.PREFERRED_SIZE, 268, GroupLayout.PREFERRED_SIZE)
										.addGap(18)
										.addComponent(titleSearchButton, GroupLayout.PREFERRED_SIZE, 268, GroupLayout.PREFERRED_SIZE)
										.addContainerGap())
								.addGroup(gl_contentPane.createSequentialGroup()
										.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
												.addGroup(gl_contentPane.createSequentialGroup()
														.addPreferredGap(ComponentPlacement.RELATED)
														.addComponent(rangeRadioButton)
														.addPreferredGap(ComponentPlacement.UNRELATED)
														.addComponent(fromPapers, GroupLayout.PREFERRED_SIZE, 44, GroupLayout.PREFERRED_SIZE)
														.addPreferredGap(ComponentPlacement.RELATED)
														.addComponent(paperRangeSeperator)
														.addPreferredGap(ComponentPlacement.RELATED)
														.addComponent(toPapers, GroupLayout.PREFERRED_SIZE, 46, GroupLayout.PREFERRED_SIZE))
												.addComponent(no_of_papers_label))
										.addPreferredGap(ComponentPlacement.RELATED, 10, Short.MAX_VALUE)
										.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
												.addGroup(gl_contentPane.createSequentialGroup()
														.addComponent(conf, GroupLayout.PREFERRED_SIZE, 272, GroupLayout.PREFERRED_SIZE)
														.addGap(31))
												.addGroup(gl_contentPane.createSequentialGroup()
														.addComponent(name_of_conf_label)
														.addGap(18)))
										.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
												.addGroup(gl_contentPane.createSequentialGroup()
														.addComponent(textField, GroupLayout.PREFERRED_SIZE, 57, GroupLayout.PREFERRED_SIZE)
														.addGap(4)
														.addComponent(lblNewLabel_4, GroupLayout.PREFERRED_SIZE, 15, GroupLayout.PREFERRED_SIZE)
														.addPreferredGap(ComponentPlacement.RELATED)
														.addComponent(textField_1, GroupLayout.PREFERRED_SIZE, 61, GroupLayout.PREFERRED_SIZE))
												.addGroup(gl_contentPane.createSequentialGroup()
														.addComponent(lblNewLabel_3)
														.addPreferredGap(ComponentPlacement.RELATED)
														.addComponent(rdbtnEnable)
														.addPreferredGap(ComponentPlacement.UNRELATED)
														.addComponent(rdbtnDisable))
												.addGroup(gl_contentPane.createSequentialGroup()
														.addComponent(servedYes)
														.addGap(15)
														.addComponent(servedNo, GroupLayout.PREFERRED_SIZE, 57, GroupLayout.PREFERRED_SIZE)
														.addPreferredGap(ComponentPlacement.UNRELATED)
														.addComponent(servedFrom, GroupLayout.PREFERRED_SIZE, 66, GroupLayout.PREFERRED_SIZE)
														.addGap(18)
														.addComponent(servedTill, GroupLayout.PREFERRED_SIZE, 66, GroupLayout.PREFERRED_SIZE))
												.addComponent(served_in_committe_label))
										.addPreferredGap(ComponentPlacement.RELATED, 15, Short.MAX_VALUE)
										.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
												.addComponent(btnNewButton, GroupLayout.PREFERRED_SIZE, 107, GroupLayout.PREFERRED_SIZE)
												.addComponent(btnCancel, GroupLayout.PREFERRED_SIZE, 107, GroupLayout.PREFERRED_SIZE))
										.addGap(43))
								.addGroup(gl_contentPane.createSequentialGroup()
										.addComponent(label_1, GroupLayout.PREFERRED_SIZE, 542, GroupLayout.PREFERRED_SIZE)
										.addGap(287)
										.addComponent(btnGoToFavorities, GroupLayout.PREFERRED_SIZE, 170, GroupLayout.PREFERRED_SIZE)
										.addGap(29))))
				);
		gl_contentPane.setVerticalGroup(
				gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
						.addContainerGap()
						.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(label_1, GroupLayout.PREFERRED_SIZE, 54, GroupLayout.PREFERRED_SIZE)
								.addComponent(btnGoToFavorities, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(ComponentPlacement.RELATED, 9, Short.MAX_VALUE)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
								.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
										.addComponent(no_of_papers_label)
										.addComponent(lblNewLabel_3)
										.addComponent(rdbtnEnable)
										.addComponent(rdbtnDisable))
								.addGroup(gl_contentPane.createSequentialGroup()
										.addComponent(name_of_conf_label)
										.addGap(18))
								.addGroup(gl_contentPane.createSequentialGroup()
										.addComponent(btnNewButton, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(ComponentPlacement.RELATED)))
						.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_contentPane.createSequentialGroup()
										.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
												.addGroup(gl_contentPane.createSequentialGroup()
														.addGap(6)
														.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
																.addComponent(conf)
																.addGroup(gl_contentPane.createSequentialGroup()
																		.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
																				.addComponent(textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
																				.addComponent(textField_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
																				.addComponent(lblNewLabel_4))
																		.addGap(21)
																		.addComponent(served_in_committe_label)
																		.addGap(7)
																		.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
																				.addComponent(servedYes)
																				.addComponent(servedNo)
																				.addComponent(servedFrom, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
																				.addComponent(servedTill, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))))
												.addGroup(gl_contentPane.createSequentialGroup()
														.addGap(18)
														.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
																.addComponent(rangeRadioButton)
																.addComponent(fromPapers, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
																.addComponent(paperRangeSeperator)
																.addComponent(toPapers, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))))
										.addPreferredGap(ComponentPlacement.RELATED)
										.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
												.addComponent(authorSearchButton, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE)
												.addComponent(papersSearchButton, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE)
												.addComponent(titleSearchButton, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE)))
								.addGroup(gl_contentPane.createSequentialGroup()
										.addGap(26)
										.addComponent(btnCancel, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE)))
						)
				);
		searchPane.setLayout(gl_contentPane);
	}

	public boolean isNumeric(String value) {
		try {
			NumberFormat.getInstance().parse(value);
		} catch(Exception e) {
			return false;
		}
		return true;
	}

	@SuppressWarnings("unused")
	private boolean isContainInt(JTextField field) {

		Pattern pt = Pattern.compile("\\d+");
		Matcher mt = pt.matcher(field.getText());
		return mt.find();
	}

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

	public JLabel getNoOfPapersLabel() {
		return this.no_of_papers_label;
	}
}

