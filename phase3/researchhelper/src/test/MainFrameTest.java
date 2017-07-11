

import static org.junit.Assert.*;

import org.junit.Test;

import views.MainFrame;

public class MainFrameTest {

	@Test
	public void mainFrameTest() {
		MainFrame mf = new MainFrame();
		assertEquals(MainFrame.class, mf.getClass());
	}

}
