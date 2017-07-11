package views;

import java.awt.Font;
import java.util.HashSet;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import project.commons.Result;

public class ResultsPanel {
	private JScrollPane resultPane;
	private JTable table;
	private HashSet<Result> favorites;
	
	public ResultsPanel(JScrollPane resultPane, List<Result> elementlist) {
		this.resultPane = resultPane;
		addGUIToResultsPane(elementlist);
	}
	
	public void addGUIToResultsPane(List<Result> elementlist) {
		ResultTable obj = new ResultTable(elementlist);
		favorites = obj.getFavorites();
		table = obj.getTable();
		table.setSize(500,500);
		resultPane.setViewportView( table );
	}
	
	public JTable getTable() {
		return this.table;
	}
	
	public HashSet<Result> getFavorities() {
		return this.favorites;
	}
}
