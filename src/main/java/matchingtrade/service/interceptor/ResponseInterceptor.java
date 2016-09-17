package matchingtrade.service.interceptor;

import java.io.IOException;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import matchingtrade.common.SearchResult;
import matchingtrade.service.json.Json;
import matchingtrade.service.json.JsonArrayList;
import matchingtrade.service.json.JsonFactory;
import matchingtrade.service.json.JsonLinkSupport;
import matchingtrade.service.json.JsonResponse;

/**
 * Response interceptor responsible for generating hypermedia for Json
 * objects.<br>
 * - If Response.getEntity() is a Json object; then, creates Hypermedia for
 * it.<br>
 * -
 * 
 * @author rafael.santos.bra@gmail.com
 */
@Provider
public class ResponseInterceptor implements ContainerResponseFilter {

	private JsonFactory jsonFactory = new JsonFactory();

	@Override
	public void filter(ContainerRequestContext request, ContainerResponseContext response) throws IOException {
		Object responseEntity = response.getEntity();
		if ((responseEntity instanceof SearchResult) || (responseEntity instanceof Json)) {
			JsonResponse jsonResponse = new JsonResponse();
			String requestedUri = request.getUriInfo().getRequestUri().toString();
			jsonResponse.set_requestedUri(requestedUri);

			Object jsonData = null;
			
			if (responseEntity instanceof SearchResult) {
				SearchResult<?> searchResult = (SearchResult<?>) responseEntity;
				jsonResponse.set_pagination(searchResult.getPagination());
				jsonData = searchResult.getResultList();
			} else if (responseEntity instanceof Json) {
				jsonData = responseEntity;
			}

			String baseUri = request.getUriInfo().getBaseUri().toString();
			loadLinks(jsonData, baseUri, jsonResponse);

			// Assign status and the new entity to the Response
			response.setStatus(Response.Status.OK.getStatusCode());
			response.setEntity(jsonResponse);
		}
	}
	
	private void loadLinks(Object json, String baseUri, JsonResponse jsonResponse) {
		if (json == null) {
			return;
		}
		// Creates links for single Json objects
		if (json instanceof JsonLinkSupport) {
			JsonLinkSupport jsonData = (JsonLinkSupport) json;
			jsonData.set_links(jsonFactory.getLinks(baseUri, jsonData));
			jsonResponse.setData(jsonData);
		}

		// Creates links for single JsonArrayList objects
		if (json instanceof JsonArrayList) {
			@SuppressWarnings("unchecked")
			JsonArrayList<Json> jsonData = (JsonArrayList<Json>) json;
			for (Json j : jsonData) {
				if (j instanceof JsonLinkSupport) {
					JsonLinkSupport jsonSupportLink = (JsonLinkSupport) j;
					jsonSupportLink.set_links(jsonFactory.getLinks(baseUri, jsonSupportLink));
				}
			}
			jsonResponse.setData(jsonData);
		}
	}
}
