package views.table;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;
import java.util.Vector;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import project.commons.Favorites;
import project.commons.Result;

// Button editor for remove button in favorites panel table
/**
 * @author meghnatulasi
 *
 */
class RemoveButtonEditor extends DefaultCellEditor {
    protected JButton button;
    private HashSet<Result> favouritesList;
    private int limit;
    private int row;
    private int column;
    private DefaultTableModel DataModel;

    private String label;

    private boolean isPushed;
    private JTable jt;

    // constructor
    public RemoveButtonEditor(JCheckBox checkBox, JTable table) {
        super(checkBox);
        button = new JButton();
        button.setOpaque(true);
        // remove the row from table and from the favorites list
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                  if (table != null) {
                        fireEditingStopped();
                        TableModel model = table.getModel();
                        if (model instanceof DefaultTableModel) {
                        	Favorites favorites = Favorites.getInstance();
                        	favorites.removeFromFavorites(createResultObject((Vector)((DefaultTableModel) model).getDataVector().elementAt(table.getSelectedRow())));
                            ((DefaultTableModel) model).removeRow(row);
                        }
                    }

                fireEditingStopped();
            }
        });
    }

    /* (non-Javadoc)
     * @see javax.swing.DefaultCellEditor#getTableCellEditorComponent(javax.swing.JTable, java.lang.Object, boolean, int, int)
     */
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

   
    /* (non-Javadoc)
     * @see javax.swing.DefaultCellEditor#getCellEditorValue()
     */ // display removed message
    public Object getCellEditorValue() {
        if (isPushed) {     
            JOptionPane.showMessageDialog(button,"Successfully removed from favorites list");
        }
        isPushed = false;
        return label;
    }

	/**
	 * @param result
	 * @return
	 *///creates result object to display in result panel
	public Result createResultObject(Vector result) {
		if(result == null || result.size() == 0) {
			return null;
		}
		if(result.size() == 6) {
			String name = result.get(0) == null? "":((String)result.get(0)).trim();
			String title = result.get(1) == null? "":((String)result.get(1)).trim();
			String year = result.get(2) == null? "":((String)result.get(2)).trim();
			String url= result.get(3) == null? "": ((String) result.get(3)).trim();
			String count = result.get(4) == null? "":((String) result.get(4)).trim();

			return new Result(name, title, year, url,count);
		} else {
			return null;
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
