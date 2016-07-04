package matchingtrade.service.json;

import java.util.HashSet;
import java.util.Set;


/**
 * Basic Json functionality. Includes Hypermedia (HATEOAS) handling using links.
 * 
 * Suggested reading about Hypermedia (HATEOAS):<br>
 * <a href="https://spring.io/understanding/HATEOAS"/><br>
 * <a href="http://alphahydrae.com/2013/06/rest-and-hypermedia-apis"/><br>
 * <a href="http://stackoverflow.com/questions/9742380/link-headers-vs-link-elements-for-restful-json"/><br>

 * @author rafael.santos.bra@gmail.com
 */
public abstract class Json {

	private Set<JsonLink> _links = new HashSet<JsonLink>();

	public Set<JsonLink> get_links() {
		return _links;
	}
	
	public void set_links(Set<JsonLink> links) {
		_links = links;
	}

}
