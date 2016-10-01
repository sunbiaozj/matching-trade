package matchingtrade.service.interceptor;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.UriInfo;

import org.apache.cxf.jaxrs.impl.ContainerResponseContextImpl;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

import matchingtrade.common.Pagination;
import matchingtrade.common.SearchResult;
import matchingtrade.service.json.JsonArrayList;
import matchingtrade.service.json.JsonLink;
import matchingtrade.service.json.JsonResponse;
import matchingtrade.service.json.TradeItemJson;
import matchingtrade.service.json.TradeListJson;
import matchingtrade.service.json.UserJson;
import matchingtrade.test.random.TradeItemRandom;
import matchingtrade.test.random.TradeListRandom;
import matchingtrade.test.random.UserRandom;

/**
 * @author rafael.santos.bra@gmail.com
 */
public class ResponseInterceptorTest {

	private static final String baseUri = "http://localhost:8080/testBaseUri";
    private static ContainerRequestContext request = null;
    private static final String requestUri = "http://localhost:8080/testRequestUri";
    
    private class ContainerResponseContextMock extends ContainerResponseContextImpl {
    	private Object entity;
    	private int status;
    	
    	public ContainerResponseContextMock() {
    		super(null, null, null, null);
    	}
    	
    	@Override
    	public void setEntity(Object entity) {
    		this.entity = entity;
    	}
    	
    	@Override
    	public Object getEntity() {
    		return this.entity;
    	}

    	@Override
    	public int getStatus() {
    		return this.status;
    	}
    	
    	@Override
    	public void setStatus(int status) {
    		this.status = status;
    	}
    	
    	
    }

    @BeforeClass
    public static void beforeClass() throws URISyntaxException, IOException {
        UriInfo uriInfo = Mockito.mock(UriInfo.class);
        // Mock request
        Mockito.when(uriInfo.getRequestUri()).thenReturn(new URI(requestUri));
        Mockito.when(uriInfo.getBaseUri()).thenReturn(new URI(baseUri));
        request = Mockito.mock(ContainerRequestContext.class);
        Mockito.when(request.getUriInfo()).thenReturn(uriInfo);
    }
    
    @Test
    public void filter() throws IOException {
    	UserRandom userRandom = new UserRandom();
    	UserJson userJson = userRandom.next(1);
        
        ResponseInterceptor ri = new ResponseInterceptor();
        ContainerResponseContextMock response = new ContainerResponseContextMock();
        
        response.setEntity(null);
        ri.filter(request, response);
        assertEquals(404, response.getStatus());

        response.setEntity(userJson);
        ri.filter(request, response);
        assertEquals(200, response.getStatus());
        
        response.setEntity("test");
        ri.filter(request, response);
        assertEquals(200, response.getStatus());
    }
    

    @Test
    public void user() throws IOException {
    	UserRandom userRandom = new UserRandom();
    	UserJson userJson = userRandom.next(1);

        ResponseInterceptor ri = new ResponseInterceptor();
        JsonResponse jsonResponse = (JsonResponse) ri.buildResponseEntity(request, userJson);
        UserJson jsonData = (UserJson)jsonResponse.getData();
        
        String resourceRootUri = baseUri + "/users/";

        JsonLink selfLink = new JsonLink();
        selfLink.setRel("_self");
        selfLink.setHref(resourceRootUri + userJson.getUserId());
        assertTrue(jsonData.get_links().contains(selfLink));
        JsonLink tradeListsLink = new JsonLink();
        tradeListsLink.setRel("tradelists");
        tradeListsLink.setHref(resourceRootUri + userJson.getUserId() + "/tradelists");
        assertTrue(jsonData.get_links().contains(tradeListsLink));
    }
    
    @Test
    public void tradeList() throws IOException {
    	TradeListRandom tradeListRandom = new TradeListRandom();
    	TradeListJson tlJson = tradeListRandom.next(1);

        ResponseInterceptor ri = new ResponseInterceptor();
        JsonResponse jsonResponse = (JsonResponse) ri.buildResponseEntity(request, tlJson);
        TradeListJson jsonData = (TradeListJson) jsonResponse.getData();

        String resourceRootUri = baseUri + "/tradelists/";
        
        JsonLink selfLink = new JsonLink();
        selfLink.setRel("_self");
        selfLink.setHref(resourceRootUri + tlJson.getTradeListId());
        assertTrue(jsonData.get_links().contains(selfLink));
        JsonLink tradeListsLink = new JsonLink();
        tradeListsLink.setRel("tradeitems");
        tradeListsLink.setHref(resourceRootUri + tlJson.getTradeListId() + "/tradeitems");
        assertTrue(jsonData.get_links().contains(tradeListsLink));
    }
    
    @Test
    public void tradeItems() throws IOException {
    	TradeItemRandom tradeItemRandom = new TradeItemRandom();
    	TradeItemJson tiJson = tradeItemRandom.next(1);

        ResponseInterceptor ri = new ResponseInterceptor();
        JsonResponse jsonResponse = (JsonResponse) ri.buildResponseEntity(request, tiJson);
        TradeItemJson jsonData = (TradeItemJson) jsonResponse.getData();

        String resourceRootUri = baseUri + "/tradeitems/";
        
        JsonLink selfLink = new JsonLink();
        selfLink.setRel("_self");
        selfLink.setHref(resourceRootUri + tiJson.getTradeItemId());
        assertTrue(jsonData.get_links().contains(selfLink));
    }
    
	@Test
    public void searchResult() throws IOException {
    	List<TradeListJson> resultList = new JsonArrayList<>();
    	TradeListRandom tradeListRandom = new TradeListRandom();
    	resultList.add(tradeListRandom.next(1));
    	resultList.add(tradeListRandom.next(2));
    	resultList.add(tradeListRandom.next(3));
    	
    	SearchResult<TradeListJson> searchResult = new SearchResult<>(resultList, new Pagination(1, 2));
    	
        ResponseInterceptor ri = new ResponseInterceptor();
        JsonResponse jsonResponse = (JsonResponse) ri.buildResponseEntity(request, searchResult);
        
        assertNotNull(jsonResponse.get_pagination());
        assertNotNull(jsonResponse.getData());
        assertEquals(jsonResponse.get_requestedUri(), requestUri);
	}

}