package views.table;

import java.util.List;

import javax.swing.JTable;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import project.commons.Result;


/**
 * @author meghnatulasi
 *
 * @param <T>
 *///// Child class of Table
//used to display the search results
public class ResultsTable<T> extends Table<T>{
	private T resultList ;

	
	/**
	 * @param resultlist
	 */// constructor
	// initializes the result list
	public ResultsTable(T resultlist) {	
		super(resultlist);
		this.resultList = resultlist;
		initializeTable(resultlist);
	}

	// defines the column labels
	/* (non-Javadoc)
	 * @see views.table.Table#setColumnNames()
	 */// defines the column labels
	@Override
	public String[] setColumnNames() {
		String[] columnNames = {"Full Name",
				"Title",
				"Year",
				"Paper's Url", 
				"Number of Papers",
		"Favorites"};
		return columnNames;
	}

	// initializes and populates the table
	@Override
	public void initializeTable(T resultlist) {
		
		Object[][] data = new Object[((List<Result>)resultlist).size()][];
		int size = ((List<Result>)resultlist).size();
		for(int i=0;i<size;i++){
			data[i]=new String[6];
		}
		int r = 0;
		for(Result re : (List<Result>)resultlist)
		{
			if(r>50000)
			{break;}
			else
			{
				data[r][0] = re.getAuthorName();
				data[r][1]=re.getTitle();
				data[r][2]=re.getYear();
				data[r][3]=re.geturl();
				data[r][4]=re.getCount();
				data[r][5]="ADD";
				r++;
			}
		}

		// add column name and data to table model
		dm.setDataVector(data, setColumnNames());
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
		table.setRowHeight(30);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(dm);
		table.setRowSorter(sorter);
		
		// sets the button renderer & cell editor for column 5 ie Add to favorites
		setButtonRenderer(5);
		setCellEditor(5);
	}
}
