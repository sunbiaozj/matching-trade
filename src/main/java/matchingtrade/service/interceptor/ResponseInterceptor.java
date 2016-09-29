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
import matchingtrade.service.json.LinkFactory;
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

	private LinkFactory linkFactory = new LinkFactory();

	@Override
	public void filter(ContainerRequestContext request, ContainerResponseContext response) throws IOException {
		Object responseEntity = buildResponseEntity(request, response, response.getEntity());
		response.setEntity(responseEntity);
	}

	Object buildResponseEntity(ContainerRequestContext request, ContainerResponseContext response,	Object responseEntity) {
		Object result = responseEntity;
		if (result instanceof SearchResult || result instanceof Json) {
			JsonResponse jsonResponse = new JsonResponse();
			String requestedUri = request.getUriInfo().getRequestUri().toString();
			jsonResponse.set_requestedUri(requestedUri);

			Object responseData = null;
			
			if (result instanceof SearchResult) {
				SearchResult<?> searchResult = (SearchResult<?>) result;
				jsonResponse.set_pagination(searchResult.getPagination());
				responseData = searchResult.getResultList();
			} else if (result instanceof Json) {
				responseData = result;
			}

			String baseUri = request.getUriInfo().getBaseUri().toString();
			Json jsonData = loadLinks(responseData, baseUri);
			jsonResponse.setData(jsonData);

			// Assign status and the new entity to the Response
			response.setStatus(Response.Status.OK.getStatusCode());
			result = jsonResponse;
		}
		return result;
	}
	
	private Json loadLinks(Object json, String baseUri) {
		Json result = null;
		// Creates links for single Json objects
		if (json instanceof JsonLinkSupport) {
			JsonLinkSupport jsonData = (JsonLinkSupport) json;
			jsonData.set_links(linkFactory.getLinks(baseUri, jsonData));
			result = jsonData;
		}

		// Creates links for single JsonArrayList objects
		if (json instanceof JsonArrayList) {
			@SuppressWarnings("unchecked")
			JsonArrayList<Json> jsonData = (JsonArrayList<Json>) json;
			for (Json j : jsonData) {
				if (j instanceof JsonLinkSupport) {
					JsonLinkSupport jsonSupportLink = (JsonLinkSupport) j;
					jsonSupportLink.set_links(linkFactory.getLinks(baseUri, jsonSupportLink));
				}
			}
			result = jsonData;
		}
		return result;
	}
}
