import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ AuthorSearchPanelTest.class, FavoritesPanelTest.class, HomePanelTest.class,
		KeywordSearchPanelTest.class, MainFrameTest.class, PapersSearchPanelTest.class, SearchViewFactoryTest.class})
public class UITestSuite {

}
