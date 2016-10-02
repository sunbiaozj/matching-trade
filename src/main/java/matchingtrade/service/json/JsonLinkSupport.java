package matchingtrade.service.json;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.annotations.ApiModelProperty;

public abstract class JsonLinkSupport implements Json {
	
	private Set<JsonLink> _links = new HashSet<JsonLink>();

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@ApiModelProperty(name="_links")
	public Set<JsonLink> get_links() {
		return _links;
	}

	public void set_links(Set<JsonLink> _links) {
		this._links = _links;
	}
}
