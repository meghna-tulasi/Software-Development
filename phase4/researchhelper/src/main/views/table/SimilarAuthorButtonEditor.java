package views.table;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.util.List;
import java.util.Vector;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import queryengine.QueryEngineCoAuthor;


/**
 * @author meghnatulasi
 *
 */// Button editor for similar author
@SuppressWarnings("serial")
class SimilarAuthorButtonEditor extends DefaultCellEditor {
	protected JButton button;
	private int row;
	private int column;
	private String label;
	private JTable table;
	private boolean isPushed;

	// constructor
	public SimilarAuthorButtonEditor(JCheckBox checkBox, JTable t) {
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
	 */// searches and retrives the list of similar authors
	public Object getCellEditorValue() {
		if (isPushed) {
			QueryEngineCoAuthor QueryCoAuthor = new QueryEngineCoAuthor();

			Vector rowData = 	(Vector)((DefaultTableModel)table.getModel()).getDataVector().elementAt(table.
					convertRowIndexToModel(table.getSelectedRow()));
			try {
				List<String> authorList = QueryCoAuthor.retrieveAuthor(rowData.get(0).toString());

				if(authorList != null && authorList.size() >0) {
					Object[][] data = new Object[authorList.size()][1];
					for(int i=0;i<authorList.size();i++) {
						data[i][0] = authorList.get(i);
					}

					Object[] columnNames = { "Similar Author For "+rowData.get(0).toString() };
					final JTable table = new JTable(data, columnNames) {
						@Override
						public Dimension getPreferredScrollableViewportSize() {
							Dimension d = getPreferredSize();
							int n = getRowHeight();
							return new Dimension(800, (n * data.length));
						}
					};
					JPanel jPanel = new JPanel();
					jPanel.setLayout(new GridLayout());
					JScrollPane sp = new JScrollPane(table);
					jPanel.add(sp);
					JDialog jdialog = new JDialog();
					jdialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
					jdialog.setContentPane(jPanel);
					jdialog.setLocationRelativeTo(null);
					jdialog.pack();
					jdialog.setVisible(true);				
				} else {
					JOptionPane.showMessageDialog(null,"No Match Found");
				}
			}
			catch(SQLException e) {
				System.out.println(e.getMessage());
			}
		}
		isPushed = false;
		return label;
	}

	public boolean stopCellEditing() {
		isPushed = false;
		return super.stopCellEditing();
	}

	protected void fireEditingStopped() {
		super.fireEditingStopped();
	}
}
