package matchingtrade.service.interceptor;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.springframework.test.context.ContextConfiguration;


@RunWith(Suite.class)
@Suite.SuiteClasses({
        ResponseInterceptorTest.class,
})

@ContextConfiguration(locations={"/application-context-web.xml"})
public class InterceptorSuite {

}