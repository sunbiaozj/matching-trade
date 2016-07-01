package mtrade;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;

import matchingtrade.persistence.dao.PersonDao;
import matchingtrade.persistence.entity.TradeItem;

@Path("/hello")
public class HelloWorld {

	@Autowired
	PersonDao pDao;

	@GET
    @Path("/echo/{input}")
    @Produces("text/plain")
    public String ping(@PathParam("input") String input) {
        return input;
    }

    @POST
    @Produces("application/json")
    @Consumes("application/json")
    @Path("/jsonBean")
    public Response modifyJson(JsonBean input) {
        TradeItem p = new TradeItem();
        p.setName(input.getVal1());
        p.setCountry(input.getVal2());
        System.out.println("saving:..........." + p);
        pDao.save(p);
    	input.setVal2(input.getVal1());
        return Response.ok().entity(input).build();
    }
    
    @GET
    @Produces("application/json")
    @Consumes("application/json")
    @Path("/person")
    public Response getPerson() {
    	
    	List<TradeItem> result = pDao.search();
        return Response.ok().entity(result).build();
    }
}

