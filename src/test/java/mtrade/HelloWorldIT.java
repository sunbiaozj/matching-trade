package mtrade;

import static org.junit.Assert.assertEquals;

import javax.ws.rs.core.Response;

import org.junit.Test;

public class HelloWorldIT {
	@Test
	public void testPing() throws Exception {
		HelloWorld hw = new HelloWorld();
		String pingParam = "Ping String";
		String pingResult = hw.ping(pingParam);
		assertEquals(pingParam, pingResult);
	}

	@Test
	public void testJsonRoundtrip() throws Exception {
		String val1 = "Value one";
		String val2 = "Value two";
		JsonBean modifyJsonParam = new JsonBean();
		modifyJsonParam.setVal1(val1);
		modifyJsonParam.setVal2(val2);
		HelloWorld hw = new HelloWorld();
		Response response = hw.modifyJson(modifyJsonParam);
		assertEquals(Response.Status.OK.getStatusCode(), 200);
		JsonBean modifyJsonResult = (JsonBean) response.getEntity();
		assertEquals(val1, modifyJsonResult.getVal2());
	}
}
