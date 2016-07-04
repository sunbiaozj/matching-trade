package matchingtrade.service.json;

/**
 * Stores link attributes for hypermedia usage.
 * 
 * @author rafael.santos.bra
 */
public class JsonLink {

	private String rel;
	private String href;

	public String getRel() {
		return rel;
	}

	public void setRel(String rel) {
		this.rel = rel;
	}

	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}
}
