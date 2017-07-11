import static org.junit.Assert.*;

import java.awt.CardLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.junit.Test;

import views.HomePanel;

public class HomePanelTest extends JFrame{
	

	private JPanel contentPane;

	@Test
	public void startSearchButtonTest() {
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new CardLayout(0, 0));
		HomePanel hp = new HomePanel(contentPane);
		hp = hp.getHomePanel();
		JButton startSearchButton = hp.getStartSearchButton();
		assertEquals("Committee Helper", hp.getHeading().getText());
		startSearchButton.doClick();
	}

}
