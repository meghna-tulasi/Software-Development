import static org.junit.Assert.*;

import java.awt.CardLayout;
import java.awt.Component;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
import views.HomePanel;
import views.FavoritesPanel;

import views.ResultsPanel;
import views.SearchPanel;

public class FavoritesPanelTest extends JFrame{
	

	private JPanel contentPane;
	private JSplitPane sp;
	private JTable table;
	private Object value;

	@Test
	public void favoritesPanelButtonTest()  {
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new CardLayout(0, 0));
		SearchPanel searchPanel = new SearchPanel(contentPane);
		JScrollPane resultPane = new JScrollPane();
		HashSet<Result> result = new HashSet<Result>();
		
		Result r = new Result("Hina Shah", "Understanding Exception Handling: Viewpoints of Novices and Experts.", "2010", "db/journals/tse/tse36.html#ShahGH10", "1");
		result.add(r);
		
		
		FavoritesPanel fp = new FavoritesPanel(contentPane, result, sp);
		sp = fp.getFavoritesPanel();
		JButton goToSearch = fp.getGotoSearch();
		goToSearch.doClick();
		
		assertEquals("Favorites List", fp.getFavLabel().getText());
		
	}
	
	
	
	

}