package matchingtrade.service.json;

/**
 * Basic Json functionality. Includes Hypermedia (HATEOAS) handling using links.
 * 
 * Suggested reading about Hypermedia (HATEOAS):<br>
 * <a href="https://spring.io/understanding/HATEOAS"/><br>
 * <a href="http://alphahydrae.com/2013/06/rest-and-hypermedia-apis"/><br>
 * <a href="http://stackoverflow.com/questions/9742380/link-headers-vs-link-elements-for-restful-json"/><br>

 * @author rafael.santos.bra@gmail.com
 */
public class JsonResponse extends Json {
	
	private String _pagination;
	private Json data;

	public String get_pagination() {
		return _pagination;
	}

	public void set_pagination(String _pagination) {
		this._pagination = _pagination;
	}

	public Json getData() {
		return data;
	}

	public void setData(Json data) {
		this.data = data;
	}

}
