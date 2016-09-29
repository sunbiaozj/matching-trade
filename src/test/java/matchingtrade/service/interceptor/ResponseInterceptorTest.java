package matchingtrade.service.interceptor;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Random;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.core.UriInfo;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

import matchingtrade.service.json.JsonLink;
import matchingtrade.service.json.JsonResponse;
import matchingtrade.service.json.TradeListJson;
import matchingtrade.service.json.UserJson;
import matchingtrade.test.random.RandomString;

/**
 * Created by rsantos on 30/09/16.
 */
public class ResponseInterceptorTest {

	private static final String baseUri = "http://localhost:8080/testBaseUri";
    private Random random = new Random();
    private RandomString randomString = new RandomString();
    private static ContainerRequestContext request = null;
    private static final String requestUri = "http://localhost:8080/testRequestUri";
    private ContainerResponseContext response = Mockito.spy(ContainerResponseContext.class);

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
    public void user() throws IOException {
        UserJson userJson = new UserJson();
        userJson.setName(randomString.nextName());
        userJson.setName(randomString.nextEmail());
        userJson.setUserId(random.nextInt(1000));

        Mockito.when(response.getEntity()).thenReturn(userJson);
        ResponseInterceptor ri = new ResponseInterceptor();

        JsonResponse jsonResponse = (JsonResponse) ri.buildResponseEntity(request, response, userJson);
        UserJson jsonData = (UserJson)jsonResponse.getData();
        
        String resourceRootUri = baseUri + "/users/";

        JsonLink selfLink = new JsonLink();
        selfLink.setRel("_self");
        selfLink.setHref(resourceRootUri + userJson.getUserId());
        Assert.assertTrue(jsonData.get_links().contains(selfLink));
        JsonLink tradeListsLink = new JsonLink();
        tradeListsLink.setRel("tradelists");
        tradeListsLink.setHref(resourceRootUri + userJson.getUserId() + "/tradelists");
        Assert.assertTrue(jsonData.get_links().contains(tradeListsLink));
    }
    
    @Test
    public void tradeList() throws IOException {
    	TradeListJson tlJson = new TradeListJson();
    	tlJson.setName(randomString.nextName());
    	tlJson.setTradeListId(random.nextInt(1000));

        Mockito.when(response.getEntity()).thenReturn(tlJson);
        ResponseInterceptor ri = new ResponseInterceptor();

        JsonResponse jsonResponse = (JsonResponse) ri.buildResponseEntity(request, response, tlJson);
        TradeListJson jsonData = (TradeListJson) jsonResponse.getData();

        String resourceRootUri = baseUri + "/tradelists/";
        
        JsonLink selfLink = new JsonLink();
        selfLink.setRel("_self");
        selfLink.setHref(resourceRootUri + tlJson.getTradeListId());
        Assert.assertTrue(jsonData.get_links().contains(selfLink));
        JsonLink tradeListsLink = new JsonLink();
        tradeListsLink.setRel("tradeitems");
        tradeListsLink.setHref(resourceRootUri + tlJson.getTradeListId() + "/tradeitems");
        Assert.assertTrue(jsonData.get_links().contains(tradeListsLink));
    }

}
