package views.table;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import project.commons.Favorites;
import project.commons.Result;

// Button editor for favorites
/**
 * @author neethuprasad
 *
 */
@SuppressWarnings("serial")
class FavoritesButtonEditor extends DefaultCellEditor {
	protected JButton button;
	private int row;
	private int column;
	private String label;
	private JTable table;
	private int FAVORITE_LIST_SIZE = 50;

	private boolean isPushed;

	// constructor
	/**
	 * @param checkBox
	 * @param t
	 */// Sets components for favorites
	public FavoritesButtonEditor(JCheckBox checkBox, JTable t) {
		super(checkBox);
		button = new JButton();
		button.setOpaque(true);
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fireEditingStopped();
			}
		});
		this.table = t;
	}

	/* (non-Javadoc)
	 * @see javax.swing.DefaultCellEditor#getTableCellEditorComponent(javax.swing.JTable, java.lang.Object, boolean, int, int)
	 */ //sets table gui components
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
	 */ // defines the action on clicking the button
	public Object getCellEditorValue() {
		if (isPushed) {	
			// if the button is pushed and the selected row does not exist in favorite list, add it
			Favorites favorites = Favorites.getInstance();
			if(favorites.getFavorites().size()< FAVORITE_LIST_SIZE) {						
				Result newObj = createResultObject((Vector)((DefaultTableModel)table.getModel()).getDataVector().elementAt(table.
						convertRowIndexToModel(table.getSelectedRow())));
				addToFavorities(newObj);
			} else {
				// else display error message
				JOptionPane.showMessageDialog(button,"Favorities list is full");
			}
		}
		isPushed = false;
		return new String(label);
	}


	/**
	 * @param resultObj
	 * @return 	// adds the given result object to the favorites list
	 */
	public boolean addToFavorities(Result resultObj) {
		Favorites favorites = Favorites.getInstance();
		if(favorites.getFavorites().size() < FAVORITE_LIST_SIZE) {
			if(favorites.getFavorites().contains(resultObj)) {
				JOptionPane.showMessageDialog(button,"Already exists in favorite list");
				return false;
			} else {
				favorites.addToFavorites(resultObj);
				JOptionPane.showMessageDialog(button,"Added to favorite list");
			}
			return true;
		} 
		return false;		
	}


	public boolean stopCellEditing() {
		isPushed = false;
		return super.stopCellEditing();
	}

	protected void fireEditingStopped() {
		super.fireEditingStopped();
	}


	
	/**
	 * @param result
	 * @return
	 */// creates a result object
	public Result createResultObject(Vector result) {
		if(result == null || result.size() == 0) {
			return null;
		}
		if(result.size() == 6 || result.size() == 7) {
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
}
