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

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import project.commons.Result;
import views.KeywordsSearchPanel;
import views.FilterPanel;
import views.ResultsPanel;
import views.SearchPanel;

public class KeywordSearchPanelTest extends JFrame{
	private SearchPanel searchPanel;
	private JSplitPane filterAndResultPanel;
	private JTable table;
	private JPanel searchPane = new JPanel();	
	private JPanel contentPane;
	private ResultsPanel resultView;
	
	@Test
	public void KeywordSearchPanelTest()
	{
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
		KeywordsSearchPanel ksp = new KeywordsSearchPanel(searchPanel, searchPane, filterAndResultPanel, table, contentPane);
		ksp = ksp.getKeywordPanel();
		JButton goToFavorites = ksp.getGotoFavorires();
		goToFavorites.doClick();
		JButton paperSearch = ksp.getPaperSearchButton();
		paperSearch.doClick();
		JButton search = ksp.getSearchButton();
		search.doClick();
		JButton cancel = ksp.getCancelButton();
		cancel.doClick();
		assertEquals("Keywords:", ksp.getKeywordLabel().getText());
	}
}