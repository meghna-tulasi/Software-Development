package views.panel;

import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTable;

// factory for creating author, keywords and papers panel
/**
 * @author neethuprasad
 *
 */
public class SearchViewFactory extends AbstractSearchViewFactory {
	private JPanel searchPane;
	private SearchPanel searchPanel;
	private JTable table;
	private JPanel contentPane;
	private JSplitPane emptyFilterResultPanel;
	
	/**
	 * @param searchPane
	 * @param searchPanel
	 * @param table
	 * @param contentPane
	 * @param emptyFilterResultPanel
	 */
	//intializes constructor for initializes search(top panel),filter(bottom right panel) and result(bottom left panel)
	public
	SearchViewFactory(JPanel searchPane, SearchPanel searchPanel, JTable table, JPanel contentPane, JSplitPane emptyFilterResultPanel ) {
		this.searchPane = searchPane;
		this.searchPanel = searchPanel;
		this.table = table;
		this.contentPane = contentPane;
		this.emptyFilterResultPanel = emptyFilterResultPanel;
	}
	
	/**
	 * @param type
	 * @return
	 */
	//createpanel creates a panel with three main searches
	public	PanelInterface createPanel(String type) {
		if(type.contains("author")) {
			return createAuthorSearchPanel();
		} else if(type.contains("keywords")) {
			return createKeywordsSearchPanel();
		} else if(type.contains("papers")) {
			return createPapersSearchPanel();
		} 
		return null;
	}
	
	/* (non-Javadoc)
	 * @see views.panel.AbstractSearchViewFactory#createAuthorSearchPanel()
	 *///creates panel for each search feature
	AuthorSearchPanel createAuthorSearchPanel() {
		return new AuthorSearchPanel(this.searchPanel, this.searchPane, emptyFilterResultPanel, table, contentPane);
		}
	KeywordsSearchPanel createKeywordsSearchPanel() {
		return new KeywordsSearchPanel(this.searchPanel, searchPane, emptyFilterResultPanel, table, contentPane);
		}
	PapersSearchPanel createPapersSearchPanel() {
		return new PapersSearchPanel(this.searchPanel, searchPane, emptyFilterResultPanel, table, contentPane);
		}
	
}
