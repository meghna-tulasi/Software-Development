package views.panel;

import java.util.HashSet;
import java.util.List;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import project.commons.Result;
import views.table.ResultsTableWithSimilarAuthor;
import views.table.Table;

// base panel where results table is displayed
/**
 * @author meghnatulasi
 *
 */
public class ResultsPanel {
	private JScrollPane resultPane;
	private JTable table;
	
	// constructor
	/**
	 * @param resultPane 
	 * @param paperlist
	 *///constructor intializes resultpane and adds gui to paper list
	public ResultsPanel(JScrollPane resultPane, List<Result> paperlist) {
		this.resultPane = resultPane;
		addGUIToResultsPane(paperlist);
	}
	
	
	/**
	 * @param elementlist
	 */// adds the table to this pane and element list to table
	public void addGUIToResultsPane(List<Result> elementlist) {
		Table  t = new ResultsTableWithSimilarAuthor(elementlist);
		this.table = t.getTable();
		resultPane.setViewportView( table );
	}
	
	
	/**
	 * @return
	 */// returns the table
	public JTable getTable() {
		return this.table;
	}
}
