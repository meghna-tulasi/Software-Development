package views;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;
import java.util.List;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import project.commons.Result;


public class ResultTable {
	private HashSet<Result> favourites;
	private int FAVOURITE_LIST_SIZE = 15;

	JTable table;
	private DefaultTableModel dm;
	public ResultTable() {
	}
	public ResultTable(List<Result> authorlist) {
		Object[][] data = new Object[authorlist.size()][];
		for(int i=0;i<authorlist.size();i++){
			data[i]=new String[6];
		}

		int r = 0;
		for(Result re : authorlist)
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
		 dm = new DefaultTableModel();
		 favourites = new HashSet<Result>();


		// static table columns
		String[] columnNames = {"Full Name",
				"Title",
				"Year",
				"Url",
				"Number of Papers",
		"Favorities"};

		// add column name and data to table model
		dm.setDataVector(data, columnNames);

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

		// render buttons in last column
		table.getColumn("Favorities").setCellRenderer(new ButtonRenderer());
		// implement cell editor 
		// implement add to favorite list
		table.getColumn("Favorities").setCellEditor(
				new ButtonEditor(new JCheckBox()));

		TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(dm);
		table.setRowSorter(sorter);
	}


	public JTable getTable() {
		return this.table;
	}
	
	public HashSet<Result> getFavorites() {
		return this.favourites;
	}
	
	class ButtonRenderer extends JButton implements TableCellRenderer {

		public ButtonRenderer() {
			setOpaque(true);
		}

		public Component getTableCellRendererComponent(JTable table, Object value,
				boolean isSelected, boolean hasFocus, int row, int column) {
			if (isSelected) {
				setForeground(table.getSelectionForeground());
				setBackground(table.getSelectionBackground());
			} else {
				setForeground(table.getForeground());
				setBackground(UIManager.getColor("Favorities.background"));
			}
			
			setText((value == null) ? "" : value.toString());
			
			return this;
		}
	}

	class ButtonEditor extends DefaultCellEditor {
		protected JButton button;
		private int row;
		private int column;
		private String label;

		private boolean isPushed;

		public ButtonEditor(JCheckBox checkBox) {
			super(checkBox);
			button = new JButton();
			button.setOpaque(true);
			button.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					fireEditingStopped();
				}
			});
		}

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

		public Object getCellEditorValue() {
			if (isPushed) {			
				if(favourites.size()< FAVOURITE_LIST_SIZE) {	
					Result newObj = createResultObject(dm.getDataVector().elementAt(table.getSelectedRow()).toString());
					addToFavorities(newObj);
				} else {
					JOptionPane.showMessageDialog(button,"Favorities list is full");
				}
			}
			isPushed = false;
			return new String(label);
		}

		public boolean addToFavorities(Result resultObj) {
			if(favourites.size() < FAVOURITE_LIST_SIZE) {
				if(favourites.contains(resultObj)) {
					JOptionPane.showMessageDialog(button,"Already exists in favorite list");
					return false;
				} else {
				favourites.add(resultObj);
				}
				return true;
			} 
			return false;		
		}

		public void removeFromFavorities(Result resultObj) {
			if(favourites !=null && favourites.contains(resultObj)) {
				favourites.remove(resultObj);
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
	public Result createResultObject(String result) {
		if(result == null || result.length() == 0) {
			return null;
		}
		result = result.replace("[", "").replace("]", "");

		String[] tokens = result.split(",");
		if(tokens.length == 6) {
			String name = tokens[0] == null? "":tokens[0].trim();
			String title = tokens[1] == null? "":tokens[1].trim();
			String year = tokens[2] == null? "":tokens[2].trim();
			String url=tokens[3] == null? "":tokens[3].trim();
			String count = tokens[4] == null? "":tokens[4].trim();

			return new Result(name, title, year, url,count);
		} else {
			return null;
		}
	}
}
