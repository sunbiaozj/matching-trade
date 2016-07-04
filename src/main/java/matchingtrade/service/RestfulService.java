package matchingtrade.service;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

public abstract class RestfulService {
	
	@Context
	protected UriInfo uriInfo;
	
	public UriInfo getUriInfo() {
		return uriInfo;
	}

}
