package views.panel;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.SpinnerNumberModel;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.table.TableRowSorter;

// Filter panel for filtering the results
/**
 * @author neethuprasad
 *
 */
@SuppressWarnings("serial")
public class FilterPanel extends JPanel {
	private JTextField authorNameField;
	private JTable table;
	private JLabel lblNumberOfPapers;
	private JSpinner fromPapers;
	private JLabel lblTo;
	private JSpinner toPapers;
	private JRadioButton onPaper;
	private JRadioButton offPaper;
	private JLabel lblAuthorName;
	private JRadioButton onAuthor;
	private JRadioButton offAuthor;
	private JButton FilterButton;
	@SuppressWarnings("rawtypes")
	private JList list;
	private JLabel lblFilterBy;

	
	/**
	 * @param table holds the filter parameter on the bottonm left
	 */
	public FilterPanel(JTable table) {
		this.table = table;
		addGUIToFilterPane();
	}

	/**
	 *  addGUIToFilterPane() adds all the GUI elements to the filter panel
	this includes number of paper and author name filter label and components*/
	 
	@SuppressWarnings("rawtypes")
	public void addGUIToFilterPane() {
		list = new JList();
		lblFilterBy = new JLabel("Filter by");
		lblFilterBy.setFont(new Font("Tahoma", Font.BOLD, 20));

		// Filter based on number of papers
		filterBasedOnNumberOfPapers();

		// Author filter
		filterBasedOnAuthor();

		createAndAddListenerToFilterButton();

		GroupLayout filterPaneLayout = createFilterPanelLayout();
		setLayout(filterPaneLayout);
	}

	
	/**
	 * // sets filter based on number of papers
	 */
	private void filterBasedOnNumberOfPapers() {
		lblNumberOfPapers = new JLabel("No. of Papers Published:"); 

		onPaper = new JRadioButton("Specify Range");        
		offPaper = new JRadioButton("All Papers", true);

		// range        
		SpinnerNumberModel fromspinnerModel = new SpinnerNumberModel();
		fromspinnerModel.setMinimum(0);

		SpinnerNumberModel tospinnerModel = new SpinnerNumberModel();
		tospinnerModel.setMinimum(0);

		fromPapers = new JSpinner(fromspinnerModel);
		lblTo = new JLabel("-");        
		toPapers = new JSpinner(tospinnerModel);
		fromPapers.setEnabled(false);
		toPapers.setEnabled(false);

		ButtonGroup paperGroup = new ButtonGroup();
		paperGroup.add(onPaper);
		paperGroup.add(offPaper);
		onPaper.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fromPapers.setEnabled(true);
				toPapers.setEnabled(true);
			}
		});
		offPaper.addActionListener(new ActionListener() {
			@SuppressWarnings({ "rawtypes", "unchecked" })
			public void actionPerformed(ActionEvent e) {
				fromPapers.setEnabled(false);
				toPapers.setEnabled(false);
			}
		});
	}

	
	/**
	 * // action event for filter button
	 */
	private void createAndAddListenerToFilterButton() {
		FilterButton = new JButton("Apply Filter");
		FilterButton.addActionListener(new ActionListener() {
			@SuppressWarnings({ "rawtypes", "unchecked" })
			public void actionPerformed(ActionEvent e) {
				// Author qualification data
				String authorname = authorNameField.isEditable()? authorNameField.getText().replaceAll("^ +| +$|( )+", "$1"):null;

				String[] papers = new String[2];
				papers[0] = fromPapers.isEnabled()?fromPapers.getValue().toString():null;
				papers[1] = toPapers.isEnabled()?toPapers.getValue().toString():null;
				if((authorNameField.isEditable() && !validName(authorname))) {
					JOptionPane.showMessageDialog(null,"Invalid name given in filter!");
					
				} else if((fromPapers.isEnabled() && !validRange(papers))) {
					JOptionPane.showMessageDialog(null,"Please select valid range!");
				}
				else {
				
				TableRowSorter sorter = (TableRowSorter) table.getRowSorter();

				RowFilter<Object, Object> filter = new RowFilter<Object, Object>() {
					public boolean include(Entry entry) {
						String name = (String) entry.getValue(0);
						String noOfPapers = (String) entry.getValue(4);
						String[] papers = new String[2];
						if(onPaper.isSelected()) {
							papers[0] = fromPapers.getValue().toString();
							papers[1] = toPapers.getValue().toString();
						} 
						
							if(authorname!= null) {

								if(papers[0] !=null && papers[1] !=null){

									return (name.toLowerCase().contains(authorname.toLowerCase()))
											&& (Integer.parseInt(noOfPapers) >= Integer.parseInt(papers[0]))&&
											(Integer.parseInt(noOfPapers) <= Integer.parseInt(papers[1]));
								} else if(papers[0] !=null) {
									return (name.toLowerCase().contains(authorname.toLowerCase()))
											&& (Integer.parseInt(noOfPapers) >= Integer.parseInt(papers[0]))
											;
								} else if(papers[1]!=null){
									return (name.toLowerCase().contains(authorname.toLowerCase()))
											&& (Integer.parseInt(noOfPapers) <= Integer.parseInt(papers[1]))
											;
								} else {
									return (name.toLowerCase().contains(authorname.toLowerCase()));
								}
							} else if(authorname == null) {
								if(papers[0] !=null && papers[1] !=null){

									return  (Integer.parseInt(noOfPapers) >= Integer.parseInt(papers[0]))&&
											(Integer.parseInt(noOfPapers) <= Integer.parseInt(papers[1]));
								} else if(papers[0] !=null) {
									return  (Integer.parseInt(noOfPapers) >= Integer.parseInt(papers[0]))
											;
								} else if(papers[1] !=null){
									return (Integer.parseInt(noOfPapers) <= Integer.parseInt(papers[1]))
											;
								} 
							} 
						
						return true;
					}
				};

				sorter.setRowFilter(filter);
				table.setRowSorter(sorter);
				}
			}
		});
		
	}

	/**
	 * filterBasedOnAuthor sets author filter parameters and components
	 */
	private void filterBasedOnAuthor() {
		lblAuthorName = new JLabel("Author Name:");
		authorNameField = new JTextField();
		authorNameField.setColumns(10);
		authorNameField.setEditable(false);
		onAuthor = new JRadioButton("Specify Name");    
		offAuthor = new JRadioButton("All Names",true);
		ButtonGroup authorGroup = new ButtonGroup();
		authorGroup.add(onAuthor);
		authorGroup.add(offAuthor);

		onAuthor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				authorNameField.setText("");
				authorNameField.setEditable(true);
			}
		});
		offAuthor.addActionListener(new ActionListener() {
			@SuppressWarnings({ "rawtypes", "unchecked" })
			public void actionPerformed(ActionEvent e) {
				authorNameField.setText("");
				authorNameField.setEditable(false);
			/*	TableRowSorter sorter = (TableRowSorter) table.getRowSorter();
				sorter.setRowFilter(null);
				table.setRowSorter(sorter);*/
			}
		});
	}
	
	/**
	 * @param nums contains number of paper min and max
	 * @return compares the ranges and chacks if the min range is lesser than max.
	 */
	private boolean validRange(String[] nums) {
		if(nums[0] !=null && nums[1]!=null ) {
			if(Integer.parseInt(nums[0]) <= Integer.parseInt(nums[1])) {
				return true; 
			} else {
				return false;
			}
		}
		return false;
	}
	
	/**
	 * @param name is the author name in filter panel
	 * @return true if there is an entry 
	 */
	private boolean validName(String name) {
		return name!=null && name.trim().length()!=0;
	}

	/**
	 * @return //createFilterPanelLayout() creates a group layout for the filter panel & returns it 
	 */
	private GroupLayout createFilterPanelLayout() {


		GroupLayout layout = new GroupLayout(this);
		layout.setHorizontalGroup(
				layout.createParallelGroup(Alignment.LEADING)
				.addGroup(layout.createSequentialGroup()
						.addGroup(layout.createParallelGroup(Alignment.LEADING)
								.addGroup(layout.createSequentialGroup()
										.addGap(87)
										.addComponent(list, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE))
								.addGroup(layout.createSequentialGroup()
										.addGap(23)
										.addGroup(layout.createParallelGroup(Alignment.LEADING)
												.addComponent(lblFilterBy, GroupLayout.PREFERRED_SIZE, 116, GroupLayout.PREFERRED_SIZE)
												.addGroup(layout.createSequentialGroup()
														.addComponent(lblAuthorName)
														.addPreferredGap(ComponentPlacement.RELATED)
														.addGroup(layout.createParallelGroup(Alignment.LEADING)
																.addComponent(offAuthor)
																.addComponent(onAuthor)))
												.addComponent(authorNameField, GroupLayout.PREFERRED_SIZE, 246, GroupLayout.PREFERRED_SIZE)))
								.addGroup(layout.createParallelGroup(Alignment.TRAILING, false)
										.addGroup(Alignment.LEADING, layout.createSequentialGroup()
												.addGap(117)
												.addComponent(FilterButton, GroupLayout.PREFERRED_SIZE, 162, GroupLayout.PREFERRED_SIZE))
										.addGroup(layout.createSequentialGroup()
												.addGap(19)
												.addComponent(lblNumberOfPapers)
												.addPreferredGap(ComponentPlacement.UNRELATED)
												.addGroup(layout.createParallelGroup(Alignment.LEADING)
														.addComponent(offPaper, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
														.addComponent(onPaper, Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 132, GroupLayout.PREFERRED_SIZE))))
								.addGroup(layout.createSequentialGroup()
										.addGap(201)
										.addComponent(fromPapers, GroupLayout.PREFERRED_SIZE, 41, GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(ComponentPlacement.UNRELATED)
										.addComponent(lblTo, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(ComponentPlacement.RELATED)
										.addComponent(toPapers, GroupLayout.PREFERRED_SIZE, 42, GroupLayout.PREFERRED_SIZE)))
						.addContainerGap(99, Short.MAX_VALUE))
				);
		layout.setVerticalGroup(
				layout.createParallelGroup(Alignment.LEADING)
				.addGroup(layout.createSequentialGroup()
						.addGap(24)
						.addComponent(lblFilterBy)
						.addPreferredGap(ComponentPlacement.UNRELATED)
						.addGroup(layout.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblAuthorName)
								.addComponent(onAuthor))
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(offAuthor)
						.addGap(21)
						.addComponent(authorNameField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.UNRELATED)
						.addComponent(list)
						.addGap(39)
						.addGroup(layout.createParallelGroup(Alignment.LEADING)
								.addComponent(lblNumberOfPapers)
								.addComponent(onPaper))
						.addGap(29)
						.addComponent(offPaper)
						.addGap(33)
						.addGroup(layout.createParallelGroup(Alignment.BASELINE)
								.addComponent(fromPapers, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblTo)
								.addComponent(toPapers, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(ComponentPlacement.RELATED, 38, Short.MAX_VALUE)
						.addComponent(FilterButton, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE)
						.addGap(34))
				);
		return layout;
	}
}