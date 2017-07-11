package views;
 
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;
 
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
 
import project.commons.Result;
 
import java.awt.CardLayout;
import java.awt.Component;

import javax.swing.AbstractButton;
import javax.swing.DefaultCellEditor;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.JSplitPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.Font;
 
@SuppressWarnings("serial")
public class FavoritesPanel extends JSplitPane {
 
    private JPanel contentPane;
    private JTable table;
    private HashSet<Result> favorites;
    private DefaultTableModel dm ;
    JButton btnGoToSearch;
    JLabel lblFavoritesList;
 
    /**
     * Launch the application.
     */
 
    /**
     * Create the frame.
     */
    public FavoritesPanel(JPanel contentPane, HashSet<Result> favorites, JSplitPane parent) {
        this.setContentPane(contentPane);
        this.favorites = favorites;
 
        dm = new DefaultTableModel();
        // static table columns
        String[] columnNames = {"Full Name",
                "Title",
                "Year",
                "Paper's Url", 
                "Number of Papers",
        "Remove"};
         
 
        int row = 0;
        Object [][] data = new Object[this.favorites.size()][7];
 
        for(Result obj: this.favorites ) {
            Object []rowData =  {obj.getAuthorName(), obj.getTitle(),obj.getYear(), obj.geturl(), obj.getCount(), "Remove"};
            data[row] = rowData;
            //dm.addRow(rowData);
            row++;
        } 
        dm.setDataVector(data, columnNames);
        //dm.setColumnIdentifiers(columnNames);
        table = new JTable(dm)
        {
            public boolean isCellEditable(int row,int column)
            {
                if(column >4)
                    return true;
                else
                    return false;  
            }
        };
        table.setFillsViewportHeight(true);
        table.setSize(500,500);
        table.setVisible(true);
 
        // render buttons in last column
        table.getColumn("Remove").setCellRenderer(new ButtonRenderer());
        // implement cell editor 
        // implement add to favorite list
        table.getColumn("Remove").setCellEditor(
                new ButtonEditor(new JCheckBox()));
 
        TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(dm);
        table.setRowSorter(sorter);
        JPanel headingPane = new JPanel();
 
 
        JScrollPane scrollPane = new JScrollPane();
         
        setOrientation(JSplitPane.VERTICAL_SPLIT);
        setContinuousLayout(true);
        setLeftComponent(headingPane);
        setRightComponent(scrollPane);
 
        scrollPane.setViewportView(table);
 
        lblFavoritesList = new JLabel("Favorites List");
        lblFavoritesList.setFont(new Font("Tahoma", Font.BOLD, 20));
        btnGoToSearch = new JButton("Go to search page");
        btnGoToSearch.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                CardLayout card = (CardLayout)contentPane.getLayout();
                card.show(contentPane, "searchPanel");          
            }
        });
        GroupLayout gl_panel_2 = new GroupLayout(headingPane);
        gl_panel_2.setHorizontalGroup(
                gl_panel_2.createParallelGroup(Alignment.LEADING)
                .addGroup(gl_panel_2.createSequentialGroup()
                        .addGap(130)
                        .addComponent(lblFavoritesList)
                        .addPreferredGap(ComponentPlacement.RELATED, 79, Short.MAX_VALUE)
                        .addComponent(btnGoToSearch))
                );
        gl_panel_2.setVerticalGroup(
                gl_panel_2.createParallelGroup(Alignment.LEADING)
                .addGroup(gl_panel_2.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(gl_panel_2.createParallelGroup(Alignment.BASELINE)
                                .addComponent(lblFavoritesList)
                                .addComponent(btnGoToSearch))
                        .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                );
        headingPane.setLayout(gl_panel_2);
        setOneTouchExpandable(true);
        setDividerLocation(50);
        setResizeWeight(0.3);
         
    }
 
    class ButtonRenderer extends JButton implements TableCellRenderer {
 
        public ButtonRenderer() {
            setOpaque(true);
        }
 
        public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {
            if (isSelected) {
                setForeground(table.getSelectionForeground());
                setBackground(table.getSelectionBackground());
            } else {
                setForeground(table.getForeground());
                setBackground(UIManager.getColor("Remove.background"));
            }
            setText((value == null) ? "" : value.toString());
            return this;
        }
    }
 
    class ButtonEditor extends DefaultCellEditor {
        protected JButton button;
        private HashSet<Result> favouritesList;
        private int limit;
        private int row;
        private int column;
        private DefaultTableModel DataModel;
 
        private String label;
 
        private boolean isPushed;
        private JTable jt;
 
        public ButtonEditor(JCheckBox checkBox) {
            super(checkBox);
            button = new JButton();
            button.setOpaque(true);
            button.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                      if (table != null) {
                            fireEditingStopped();
                            TableModel model = table.getModel();
                            if (model instanceof DefaultTableModel) {
                                ((DefaultTableModel) model).removeRow(row);
                            }
                        }
 
                    fireEditingStopped();
                }
            });
        }
 
        public Component getTableCellEditorComponent(JTable table, Object value,
                boolean isSelected, int row, int column) {
            this.row = row;
            this.column = column;
 
            if (isSelected) {
                button.setForeground(table.getSelectionForeground());
                button.setBackground(table.getSelectionBackground());
            } else {
                button.setForeground(table.getForeground());
                button.setBackground(table.getBackground());
            }
            label = (value == null) ? "" : value.toString();
            button.setText(label);
            isPushed = true;
            return button;
        }
 
        public Object getCellEditorValue() {
            if (isPushed) {     
                /*Result newObj = createResultObject(dm.getDataVector().elementAt(table.getSelectedRow()).toString());
                favorites.remove(newObj);
                dm.removeRow(table.getSelectedRow());
                table.setModel(dm);
                table.repaint();
                table.revalidate();
                dm.fireTableDataChanged();*/
                JOptionPane.showMessageDialog(button,"Successfully removed from favorites list");
            }
            isPushed = false;
            return new String(label);
        }
 
        public Result createResultObject(String result) {
            if(result == null || result.length() == 0) {
                return null;
            }
            result = result.replace("[", "").replace("]", "");
            String[] tokens = result.split(",");
            if(tokens.length == 6) {
                String name = tokens[0] == null? "":tokens[0].trim();
                String title = tokens[1] == null? "":tokens[1].trim();
                String year = tokens[2] == null? "":tokens[2].trim();
                String affiliation = tokens[3] == null? "":tokens[3].trim();
                String homePage = tokens[4] == null? "":tokens[4].trim();
 
                return new Result(name, title, year, affiliation, homePage);
            } else {
                return null;
            }
        }
 
 
        public void removeFromFavorities(Result resultObj) {
            if(favouritesList !=null && favouritesList.contains(resultObj)) {
                favouritesList.remove(resultObj);
            }
        }
 
        public boolean stopCellEditing() {
            isPushed = false;
            return super.stopCellEditing();
        }
 
        protected void fireEditingStopped() {
            super.fireEditingStopped();
        }
    }
 
    public JSplitPane getFavoritesPanel() {
        return this;
    }
 
    public JPanel getContentPane() {
        return contentPane;
    }
 
    public void setContentPane(JPanel contentPane) {
        this.contentPane = contentPane;
    }

	public JButton getGotoSearch() {
		return this.btnGoToSearch;
	}

	public JLabel getFavLabel() {
		return this.lblFavoritesList;
	}
 
}