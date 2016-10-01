package matchingtrade.service.json;

import java.util.ArrayList;

/**
 * Implementation of <i>ArrayList</i> which only accepts <i>Json</i> elements.
 * 
 * @author rafael.santos.bra@gmail.com
 *
 * @param <T>
 */
public class JsonArrayList<T extends Json> extends ArrayList<T> implements Json {

	private static final long serialVersionUID = 3715581629863783836L;

}
