package matchingtrade.service.interceptor;

import java.io.IOException;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import matchingtrade.service.json.Json;
import matchingtrade.service.json.JsonArrayList;
import matchingtrade.service.json.JsonFactory;
import matchingtrade.service.json.JsonLinkSupport;
import matchingtrade.service.json.JsonResponse;

/**
 * Response interceptor responsible for generating hypermedia for Json objects.<br>
 * - If Response.getEntity() is a Json object; then, creates Hypermedia for it.<br>
 * - 
 * @author rafael.santos.bra@gmail.com
 */
@Provider
public class ResponseInterceptor implements ContainerResponseFilter {

	private JsonFactory jsonFactory = new JsonFactory();
	
	@Override
	public void filter(ContainerRequestContext request, ContainerResponseContext response) throws IOException {
		Object entity = response.getEntity();

		// If Response.getEntity() is a Json object; then, creates links for it
		if (entity instanceof Json) {
			String absolutePath = request.getUriInfo().getAbsolutePath().toString();
			String baseUri = request.getUriInfo().getBaseUri().toString();
			
			JsonResponse jsonResponse = new JsonResponse();
			jsonResponse.set_requestedUri(absolutePath);

			// Creates links for single Json objects
			if (entity instanceof JsonLinkSupport) {
				JsonLinkSupport jsonData = (JsonLinkSupport) entity;
				jsonData.set_links(jsonFactory.getLinks(baseUri, jsonData));
				jsonResponse.setData(jsonData);
			}
			
			// Creates links for single JsonArrayList objects
			if (entity instanceof JsonArrayList) {
				@SuppressWarnings("unchecked")
				JsonArrayList<Json> jsonData = (JsonArrayList<Json>) entity;
				for (Json j : jsonData) {
					if (j instanceof JsonLinkSupport) {
						JsonLinkSupport jsonSupportLink = (JsonLinkSupport) j;
						jsonSupportLink.set_links(jsonFactory.getLinks(baseUri, jsonSupportLink));
					}
				}
				jsonResponse.setData(jsonData);
			}
			
			// Assign status and the new entity to the Response
			response.setStatus(Response.Status.OK.getStatusCode());
			response.setEntity(jsonResponse);
		}
	}
}
