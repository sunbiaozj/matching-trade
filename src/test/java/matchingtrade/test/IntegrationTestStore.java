package matchingtrade.test;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

public class IntegrationTestStore {
	
	private static Map<String, Object> store = new HashMap<>();
	
	public static void put(String key, Object object) {
		store.put(key, object);
	}
	
	public static Object get(String key) {
		return store.get(key);
	}
	
}