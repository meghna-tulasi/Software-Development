package views.table;

// child class of ResultsTable
/**
 * @author neethuprasad
 *
 * @param <T>
 */
public class ResultsTableWithSimilarAuthor<T> extends ResultsTable<T> {

	// constructor
	public ResultsTableWithSimilarAuthor(T resultlist) {
		// call super class constructor to initalize and load table with results
		super(resultlist);
		// adds similar author button to results table
		setSimilarAuthorButton();
	}

	
	/* (non-Javadoc)
	 * @see views.table.ResultsTable#setColumnNames()
	 */// overrides column headers to have similar author
	@Override
	public String[] setColumnNames() {
		String[] columnNames = {"Full Name",
				"Title",
				"Year",
				"Paper's Url", 
				"Number of Papers",
				"Favorites",
		"Similar Author"};
		return columnNames;
	}

	public void setSimilarAuthorButton() {
		// renders similar author button
		setButtonRenderer(6);
		setCellEditor(6);
		// adds this button to all the rows
		for(int i=0;i<dm.getDataVector().size();i++)
			super.dm.setValueAt("Similar Author", i, 6);
	}
}
