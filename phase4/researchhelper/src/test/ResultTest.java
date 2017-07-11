import static org.junit.Assert.*;

import org.junit.Test;

import project.commons.Result;

public class ResultTest {

	@Test
	public void resultEqualsPassTest(){
		Result r1 = new Result("Hina Shah", "Understanding Exception Handling: Viewpoints of Novices and Experts.",
				"2010", "db/journals/tse/tse36.html#ShahGH10", "1");

		Result r2 = new Result("Hina Shah", "Understanding Exception Handling: Viewpoints of Novices and Experts.",
				"2010", "db/journals/tse/tse36.html#ShahGH10", "1");

		assertEquals(true,r1.equals(r2));
	}
	
	@Test
	public void resultEqualsFailTest(){
		Result r1 = new Result("Hina Sha", "Understanding Exception Handling: Viewpoints of Novices and Experts.",
				"2010", "db/journals/tse/tse36.html#ShahGH10", "1");

		Result r2 = new Result("Hina Shah", "Understanding Exception Handling: Viewpoints of Novices and Experts.",
				"2010", "db/journals/tse/tse36.html#ShahGH10", "1");

		assertEquals(false,r1.equals(r2));
	}
}
