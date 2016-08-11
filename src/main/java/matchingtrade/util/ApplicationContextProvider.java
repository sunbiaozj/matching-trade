package matchingtrade.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * Utility class to provide spring application context which is initialized via ApplicationContextAware
 * as spring loads its context.
 */
public class ApplicationContextProvider implements ApplicationContextAware {
	
	private static ApplicationContext applicationContext;

	@Override
	public void setApplicationContext(ApplicationContext appContext) throws BeansException {
		applicationContext = appContext;
	}
	
	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}

}
