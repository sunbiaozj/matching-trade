package matchingtrade.common.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtil {
	
	private static ObjectMapper mapper = new ObjectMapper();
	
	public static String toJson(Object o) {
		String result = null;
		try {
			result = mapper.writeValueAsString(o);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

}
