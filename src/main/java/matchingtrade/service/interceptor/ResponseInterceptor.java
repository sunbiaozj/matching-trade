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
 * If Response.getEntity() is a Json object; then, creates Hypermedia for
 * it.<br>
 * 
 * @author rafael.santos.bra@gmail.com
 */
@Provider
public class ResponseInterceptor implements ContainerResponseFilter {

	private LinkFactory linkFactory = new LinkFactory();

	@Override
	public void filter(ContainerRequestContext request, ContainerResponseContext response) throws IOException {
		Object responseEntity = response.getEntity();

		// Assign status and respective entity to the response
		if (responseEntity == null) {
			response.setStatus(Response.Status.NOT_FOUND.getStatusCode());
		} else if (responseEntity instanceof SearchResult || responseEntity instanceof Json) {
			response.setStatus(Response.Status.OK.getStatusCode());
			JsonResponse jsonResponse = buildResponseEntity(request, responseEntity);
			response.setEntity(jsonResponse);
		}
	}

	/**
	 * Builds a <i>JsonResponse</i>. <i>reponseEntity</i> must be an instance of <i>SearchResult</i> or <i>Json</i>.
	 * @param request
	 * @param responseEntity
	 * @return a JsonResponse with pagination and links based on the <i>responseEntity</i>
	 */
	JsonResponse buildResponseEntity(ContainerRequestContext request, Object responseEntity) {
		JsonResponse result = new JsonResponse();
		Object responseData = null;

		if (responseEntity instanceof SearchResult) {
			SearchResult<?> searchResult = (SearchResult<?>) responseEntity;
			result.set_pagination(searchResult.getPagination());
			responseData = searchResult.getResultList();
		} else if (responseEntity instanceof Json) {
			responseData = responseEntity;
		}

		String requestedUri = request.getUriInfo().getRequestUri().toString();
		result.set_requestedUri(requestedUri);
		String baseUri = request.getUriInfo().getBaseUri().toString();
		Json jsonData = loadLinks(responseData, baseUri);

		result.setData(jsonData);
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
