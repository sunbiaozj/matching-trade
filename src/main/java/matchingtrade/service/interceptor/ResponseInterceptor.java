package matchingtrade.service.interceptor;

import java.io.IOException;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import matchingtrade.service.json.Json;
import matchingtrade.service.json.JsonFactory;
import matchingtrade.service.json.JsonResponse;
import matchingtrade.service.json.TradeItemJson;

@Provider
public class ResponseInterceptor implements ContainerResponseFilter {

	private JsonFactory jsonFactory = new JsonFactory();
	
	@Override
	public void filter(ContainerRequestContext request, ContainerResponseContext response) throws IOException {
		Object entity = response.getEntity();
		
		
		if (entity instanceof Json) {
			Json entityJson = (Json) entity;
			
			String baseUri = request.getUriInfo().getBaseUri().toString();
			if (entity instanceof TradeItemJson) {
				entityJson.set_links(jsonFactory.getLinks(baseUri, entityJson));
			}
			
			
			JsonResponse jsonResponse = new JsonResponse();
			jsonResponse.setData(entityJson);
			response.setStatus(Response.Status.OK.getStatusCode());
			response.setEntity(jsonResponse);
		}
	}
}
