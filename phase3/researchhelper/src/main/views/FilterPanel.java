package views;
 
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.TableRowSorter;
 
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
    private JList list;
    private JLabel lblFilterBy;
 
    public FilterPanel(JTable table) {
        this.table = table;
        addGUIToFilterPane();
    }
 
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
 
    private void filterBasedOnNumberOfPapers() {
        lblNumberOfPapers = new JLabel("No. of Papers Published:"); 
 
        onPaper = new JRadioButton("Specify Range");        
        offPaper = new JRadioButton("All Years", true);
 
        // range
         
        SpinnerModel fromspinnerModel = new SpinnerNumberModel(1, //initial value
                1, //min
                10, //max
                1);//step
         
        SpinnerModel tospinnerModel = new SpinnerNumberModel(2, //initial value
                2, //min
                10, //max
                1);//step
         
        fromPapers = new JSpinner(fromspinnerModel);
        lblTo = new JLabel("-");        
        toPapers = new JSpinner(tospinnerModel);
        fromPapers.setEnabled(false);
        toPapers.setEnabled(false);
        lblTo.setVisible(false);
        toPapers.setVisible(false);
        fromPapers.setVisible(false);
 
        ButtonGroup paperGroup = new ButtonGroup();
        paperGroup.add(onPaper);
        paperGroup.add(offPaper);
        onPaper.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                fromPapers.setVisible(true);
                toPapers.setVisible(true);
                fromPapers.setEnabled(true);
                toPapers.setEnabled(true);
                lblTo.setVisible(true);
            }
        });
        offPaper.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                lblTo.setVisible(false);
                toPapers.setVisible(false);
                fromPapers.setVisible(false);
                TableRowSorter sorter = (TableRowSorter) table.getRowSorter();
                sorter.setRowFilter(null);
                table.setRowSorter(sorter);
            }
        });
    }
 
    private void createAndAddListenerToFilterButton() {
        FilterButton = new JButton("Apply Filter");
        FilterButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Author qualification data
                String authorname = authorNameField.isEnabled()? authorNameField.getText():null;
 
                String[] papers = new String[2];
                papers[0] = fromPapers.isEnabled()?fromPapers.getValue().toString():null;
                papers[1] = toPapers.isEnabled()?toPapers.getValue().toString():null;
 
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
        });
    }
 
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
                authorNameField.setVisible(true);
                authorNameField.setText("");
                authorNameField.setEditable(true);
            }
        });
        offAuthor.addActionListener(new ActionListener() {
            @SuppressWarnings("deprecation")
            public void actionPerformed(ActionEvent e) {
                authorNameField.hide();
                //authorNameField.setEditable(false);
                TableRowSorter sorter = (TableRowSorter) table.getRowSorter();
                sorter.setRowFilter(null);
                table.setRowSorter(sorter);
            }
        });
    }
 
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
                        .addGroup(layout.createSequentialGroup()
                            .addGap(117)
                            .addComponent(FilterButton, GroupLayout.PREFERRED_SIZE, 162, GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createSequentialGroup()
                            .addContainerGap()
                            .addComponent(lblNumberOfPapers)
                            .addPreferredGap(ComponentPlacement.UNRELATED)
                            .addGroup(layout.createParallelGroup(Alignment.LEADING)
                                .addComponent(onPaper, GroupLayout.PREFERRED_SIZE, 132, GroupLayout.PREFERRED_SIZE)
                                .addGroup(layout.createParallelGroup(Alignment.TRAILING, false)
                                    .addComponent(offPaper, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGroup(Alignment.LEADING, layout.createSequentialGroup()
                                        .addComponent(fromPapers, GroupLayout.PREFERRED_SIZE, 41, GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(ComponentPlacement.RELATED)
                                        .addComponent(lblTo, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(ComponentPlacement.RELATED)
                                        .addComponent(toPapers, GroupLayout.PREFERRED_SIZE, 42, GroupLayout.PREFERRED_SIZE))))))
                    .addContainerGap(453, Short.MAX_VALUE))
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
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addComponent(authorNameField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addGap(32)
                    .addGroup(layout.createParallelGroup(Alignment.BASELINE)
                        .addComponent(list)
                        .addComponent(lblNumberOfPapers)
                        .addComponent(onPaper))
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addComponent(offPaper)
                    .addGap(18)
                    .addGroup(layout.createParallelGroup(Alignment.BASELINE)
                        .addComponent(fromPapers, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addComponent(toPapers, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblTo))
                    .addPreferredGap(ComponentPlacement.RELATED, 98, Short.MAX_VALUE)
                    .addComponent(FilterButton, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE)
                    .addGap(34))
        );
        return layout;
    }
}