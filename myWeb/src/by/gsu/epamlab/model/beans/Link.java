package by.gsu.epamlab.model.beans;

public class Link {

	private String url;
	private String text;
	private String linkClass;

	public Link(String url, String text) {
		super();
		this.url = url;
		this.text = text;
	}

	public Link(String url, String text, String linkClass) {
		super();
		this.url = url;
		this.text = text;
		this.linkClass = linkClass;
	}

	public String getUrl() {
		return url;
	}

	public String getText() {
		return text;
	}

	public String getLinkClass() {
		return linkClass;
	}
}