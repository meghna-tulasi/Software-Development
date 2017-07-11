package views.table;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import project.commons.Favorites;


/**
 * @author neethuprasad
 *
 * @param <T>
 */// Abstract Table class
public abstract class Table<T> {
	JTable table;
	protected DefaultTableModel dm;

	// abstract classes for setting table column names and initializing &
	// populating the table with values
	public abstract String[] setColumnNames();
	public abstract void initializeTable(T resultlist);

	// contructor
	// initializes the default table model
	public Table(T resultlist) {
		dm = new DefaultTableModel();			
	}

	// setButtonRenderer(int n) renders button at column n
	public void setButtonRenderer(int n) {
		String s = table.getColumnName(n);
		TableButtonRenderer renderer = new TableButtonRenderer();
		table.getColumn(s).setCellRenderer(renderer.createButtonRenderer(s));

	}

	// setCellEditor(int n) defines the button editor for column n
	public void setCellEditor(int n) {
		String s = table.getColumnName(n);
		TableButtonEditorFactory editor = new TableButtonEditorFactory(table);
		table.getColumn(s).setCellEditor(
				editor.createButtonEditor(s));
	}

	// getTable() returns the table
	public JTable getTable() {
		return this.table;
	}
}

