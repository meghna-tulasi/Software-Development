package views.panel;


import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTable;


/**
 * @author meghnatulasi
 *
 */// Base panel for filter, results and author/title/papers search panel
public class SearchPanel extends JSplitPane{

	private JTable table;
	private JPanel contentPane;

	/**
	 * @param contentPane
	 */// intializes content pane
	public SearchPanel(JPanel contentPane) {
		this.contentPane = contentPane;
		createAndAddGUIToSearchPage();
	}

	
	/**
	 * // creates empty lower result and filter split panel and author search upper panel
	// This is default landing search view
	 */
	public void createAndAddGUIToSearchPage() {
		JPanel searchPane = new JPanel();

		JSplitPane emptyFilterAndResultPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, 
				true, null, null);

		setOrientation(JSplitPane.VERTICAL_SPLIT);
		setContinuousLayout(true);
		setLeftComponent(searchPane);
		setRightComponent(emptyFilterAndResultPane);
		setDividerLocation(400);
		emptyFilterAndResultPane.setResizeWeight(0.3);
		AuthorSearchPanel searchView = new AuthorSearchPanel(this, searchPane,emptyFilterAndResultPane, table, contentPane);
	}
}

