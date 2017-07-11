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
import views.FilterPanel;
import views.PapersSearchPanel;
import views.ResultsPanel;
import views.SearchPanel;

public class PapersSearchPanelTest extends JFrame {

	private JPanel contentPane;
	private SearchPanel searchPanel;
	private JPanel searchPane = new JPanel();
	private ResultsPanel resultView;
	private JSplitPane filterAndResultPanel;
	private JTable table;

	@Test
	public void paperSearchPanelViewTest() {
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
		PapersSearchPanel psp = new PapersSearchPanel(searchPanel, searchPane, filterAndResultPanel, table, contentPane);
		psp = psp.getPaperPanel();
		JButton goToFavorites = psp.getGoToFavoritesButton();
		goToFavorites.doClick();
		JButton authorSearch = psp.getAuthorSearchButton();
		authorSearch.doClick();
		JButton search = psp.getSearchButton();
		search.doClick();
		JButton cancel = psp.getCancelButton();
		cancel.doClick();
		assertEquals("Number of papers:", psp.getNoOfPapersLabel().getText());		
	}

}
