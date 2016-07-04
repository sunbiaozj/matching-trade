package matchingtrade.test.mocked;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.PathSegment;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

public class UriInfoMocked implements UriInfo {

	private static final String BASE_URI = "http://unittest/";

	@Override
	public URI resolve(URI arg0) {
		return null;
	}

	@Override
	public URI relativize(URI arg0) {
		return null;
	}

	@Override
	public UriBuilder getRequestUriBuilder() {
		return null;
	}

	@Override
	public URI getRequestUri() {
		return null;
	}

	@Override
	public MultivaluedMap<String, String> getQueryParameters(boolean arg0) {
		return null;
	}

	@Override
	public MultivaluedMap<String, String> getQueryParameters() {
		return null;
	}

	@Override
	public List<PathSegment> getPathSegments(boolean arg0) {
		return null;
	}

	@Override
	public List<PathSegment> getPathSegments() {
		return null;
	}

	@Override
	public MultivaluedMap<String, String> getPathParameters(boolean arg0) {
		return null;
	}

	@Override
	public MultivaluedMap<String, String> getPathParameters() {
		return null;
	}

	@Override
	public String getPath(boolean arg0) {
		return null;
	}

	@Override
	public String getPath() {
		return null;
	}

	@Override
	public List<String> getMatchedURIs(boolean arg0) {
		return null;
	}

	@Override
	public List<String> getMatchedURIs() {
		return null;
	}

	@Override
	public List<Object> getMatchedResources() {
		return null;
	}

	@Override
	public UriBuilder getBaseUriBuilder() {
		return null;
	}

	@Override
	public URI getBaseUri() {
		try {
			return new URI(BASE_URI);
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public UriBuilder getAbsolutePathBuilder() {
		return null;
	}

	@Override
	public URI getAbsolutePath() {
		return null;
	}
}
