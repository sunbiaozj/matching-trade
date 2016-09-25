package matchingtrade.common.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.cxf.jaxrs.ext.MessageContext;

import matchingtrade.authentication.User;

public class SessionProvider {

	private MessageContext context;
	
	public SessionProvider(MessageContext context) {
		this.context = context;
	}
	
	public User getUser() {
    	HttpServletRequest request = context.getHttpServletRequest();
    	HttpSession  session = request.getSession(true);
        User user = (User) session.getAttribute("user");
        return user;
	}
	
}
