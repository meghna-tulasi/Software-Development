package Model;

public class Author implements Person {
	
	public String getAffilatedTo() {
		return affilatedTo;
	}
	public void setAffilatedTo(String affilatedTo) {
		this.affilatedTo = affilatedTo;
	}
	public Education getEducation() {
		return education;
	}
	public void setEducation(Education education) {
		this.education = education;
	}
	String affilatedTo;
	Education education;
	int paperID;
	public int getPaperID() {
		return paperID;
	}
	public void setPaperID(int paperID) {
		this.paperID = paperID;
	}
	
	
	
}
