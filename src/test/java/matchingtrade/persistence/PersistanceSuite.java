package matchingtrade.persistence;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.springframework.test.context.ContextConfiguration;

@RunWith(Suite.class)
@SuiteClasses({
		PaginationTest.class
	})

@ContextConfiguration(locations={"/application-context-web.xml"})
public class PersistanceSuite {

} 