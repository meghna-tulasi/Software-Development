import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ CommitteeTest.class, ConnectionTest.class, QueryEngineAuthorTest.class, QueryEngineKeywordTest.class,
		QueryEnginePaperTest.class, ResultTest.class, CoAuthorDataTest.class, QueryEngineCoAuthorsTest.class })
public class CodeTestSuite {

}
