package matchingtrade.common.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import matchingtrade.authentication.UserAuthentication;
import org.apache.cxf.jaxrs.ext.MessageContext;

public class SessionProvider {

	private MessageContext context;
	
	public SessionProvider(MessageContext context) {
		this.context = context;
	}
	
	public UserAuthentication getUser() {
    	HttpServletRequest request = context.getHttpServletRequest();
    	HttpSession  session = request.getSession(true);
		UserAuthentication user = (UserAuthentication) session.getAttribute("user");
        return user;
	}
	
}
