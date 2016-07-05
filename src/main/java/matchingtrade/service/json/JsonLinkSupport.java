package matchingtrade.service.json;

import java.util.HashSet;
import java.util.Set;

public abstract class JsonLinkSupport implements Json {
	
	private Set<JsonLink> _links = new HashSet<JsonLink>();

	public Set<JsonLink> get_links() {
		return _links;
	}

	public void set_links(Set<JsonLink> _links) {
		this._links = _links;
	}
}
