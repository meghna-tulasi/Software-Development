package project.commons;

// Blueprint for Result object which holds each result record 
public class Result {
	private String name;
	private String title;
	private String year;
	private String url;
	private String count;

	// Constructor gets authorname, publication title, published year, author home page & number of papers are parameters
	public Result(String name, String title, String year, String url, String count) {
		this.setAuthorName(name);
		this.setTitle(title);
		this.year = year;
		this.url = url;
		this.count=count;
	}

	public String getTitle() {
		return this.title;
	}
	
	public String geturl() {
		return this.url;
	}
	public String getAuthorName() {
		return this.name;
	}
	
	public String getYear() {
		return this.year;
	}

	@Override
	public int hashCode()
	{
		return this.name.toString().hashCode() ^ this.url.toString().hashCode() ^
				this.title.toString().hashCode() ^ this.year.toString().hashCode();
	}

	public void setAuthorName(String authorName) {
		this.name = authorName;
	}


	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	public boolean equals(Object other) {
		Result otherResult = (Result) other;
		
		if (otherResult.getAuthorName().equals(this.name) &&
				
				otherResult.geturl().equals(this.url) && 
				otherResult.getTitle().equals(this.title) &&
				otherResult.getYear().equals(this.year) &&	otherResult.getCount().equals(this.count))
		{
			return true;
		}
		return false;
	}

	public  String getCount() {
		return count;
	}
}
