import static org.junit.Assert.*;

import java.awt.CardLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;

import org.junit.Test;

import project.commons.Result;
import views.panel.AuthorSearchPanel;
import views.panel.FilterPanel;
import views.panel.ResultsPanel;
import views.panel.SearchPanel;

public class AuthorSearchPanelTest extends JFrame {
	
	private SearchPanel searchPanel;
	private JSplitPane filterAndResultPanel;
	private JTable table;
	private JPanel searchPane = new JPanel();	
	private JPanel contentPane;
	private ResultsPanel resultView;

	@Test
	public void authorSearchPanelViewTest() {
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new CardLayout(0, 0));
		SearchPanel searchPanel = new SearchPanel(contentPane);
		JScrollPane resultPane = new JScrollPane();
		List<Result> authorList = new ArrayList<Result>();
		Result r = new Result("Hina Shah", "Understanding Exception Handling: Viewpoints of Novices and Experts.", "2010", "db/journals/tse/tse36.html#ShahGH10", "1");
		authorList.add(r);
		resultView = new ResultsPanel(resultPane, authorList);
		FilterPanel filterView = new FilterPanel(resultView.getTable());
		filterAndResultPanel = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, 
				true, filterView, resultPane);
		AuthorSearchPanel asp = new AuthorSearchPanel(searchPanel, searchPane, filterAndResultPanel, table, contentPane);
		asp = asp.getAuthorPanel();
		JButton paperSearch = asp.getPaperSearchButton();
		paperSearch.doClick();
		JButton myFavorites = asp.createGoToFavoritesButton();
		myFavorites.doClick();
		JButton titleSearch = asp.getTitleSearchButton();
		titleSearch.doClick();
		JButton search = asp.getSearchButton();
		search.doClick();
		JButton cancel = asp.getCancelButton();
		cancel.doClick();
		assertEquals("Author Name:", asp.getAuthorNameLabel().getText());
		
	}

}
