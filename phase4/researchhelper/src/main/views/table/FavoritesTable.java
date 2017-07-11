package views.table;

import java.util.HashSet;

import javax.swing.JCheckBox;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import project.commons.Result;


/**
 * @author neethuprasad
 *
 * @param <T>
 */// child class of table
public class FavoritesTable<T> extends Table<T>{
	T resultList;

	// constructor
	public FavoritesTable(T resultlist) {		
		super(resultlist);
		this.resultList = resultlist;
		initializeTable(resultlist);
	}

	
	/* (non-Javadoc)
	 * @see views.table.Table#setColumnNames()
	 */// sets the column headers
	@Override
	public String[] setColumnNames() {
		String[] columnNames = {"Full Name",
				"Title",
				"Year",
				"Paper's Url", 
				"Number of Papers",
		"Remove"};
		return columnNames;
	}

	
	/* (non-Javadoc)
	 * @see views.table.Table#initializeTable(java.lang.Object)
	 */// initializes and load the table with favorites list
	@Override
	public void initializeTable(T resultlist) {
		
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
        table.setVisible(true);
        table.setRowHeight(30);
 
		
		Object[][] data = new Object[((HashSet<Result>)this.resultList).size()][];
		int row = 0;
		for( Result obj: (HashSet<Result>)this.resultList ) {
			Object []rowData =  {obj.getAuthorName(), obj.getTitle(),obj.getYear(), obj.geturl(), obj.getCount(), "Remove"};
			data[row] = rowData;
			row++;
		} 

		dm.setDataVector(data, setColumnNames());	
		// button renderer and editor for column 5
		setButtonRenderer(5);
		setCellEditor(5);
	}
}
