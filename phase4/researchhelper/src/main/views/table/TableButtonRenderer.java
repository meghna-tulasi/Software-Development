package views.table;

import java.awt.Component;

import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.TableCellRenderer;

// Renders a button in the table
/**
 * @author neethuprasad
 *
 */
public class TableButtonRenderer {

	public TableCellRenderer createButtonRenderer(String type) {
		return new ButtonRenderer(type);
	}

	
	/**
	 * // creates a button with the label being passed
	 *
	 */
	@SuppressWarnings("serial")
	class ButtonRenderer extends JButton implements TableCellRenderer {
		String buttonType = "";
		public ButtonRenderer(String buttonType) {
			this.buttonType = buttonType;
			setOpaque(true);
		}

		public Component getTableCellRendererComponent(JTable table, Object value,
				boolean isSelected, boolean hasFocus, int row, int column) {
			if (isSelected) {
				setForeground(table.getSelectionForeground());
				setBackground(table.getSelectionBackground());
			} else {
				setForeground(table.getForeground());
				setBackground(UIManager.getColor(buttonType+".background"));
			}

			setText((value == null) ? "" : value.toString());
			return this;
		}
	}
}
