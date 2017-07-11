package parser;
import java.util.ArrayList;
import java.util.List;

public class Committee {
	
	private String commName;
	private String year;
	private List<Member> members;
	
	
	public Committee(String commName, String year, List<Member> m) {
		super();
		this.commName = commName;
		this.year = year;
		this.setMembers(m);
	}
	public String getCommName() {
		return commName;
	}
	public void setCommName(String commName) {
		this.commName = commName;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public List<Member> getMembers() {
		return members;
	}
	public void setMembers(List<Member> members) {
		this.members = members;
	}
	
	
	

}
