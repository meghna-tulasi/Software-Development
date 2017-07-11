package views.table;

import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JTable;

//
/**
 * @author meghnatulasi
 *
 */
public class TableButtonEditorFactory {
	private JTable table;
	
	// constructor
	TableButtonEditorFactory(JTable table) {
		this.table = table;
	}
	
	
	/**
	 * @param type
	 * @return// creates a button editor based on input type
	 */
	public DefaultCellEditor createButtonEditor(String type) {
		if(type.contains("Remove")) {
			return new RemoveButtonEditor(new JCheckBox(), table);
		} else if(type.contains("Favorites")){
			return new FavoritesButtonEditor(new JCheckBox(), table);
		} else if(type.contains("Similar")) {
			return new SimilarAuthorButtonEditor(new JCheckBox(), table);
		}
		return null;
	}
}
