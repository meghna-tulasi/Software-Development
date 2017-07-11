package Model;

public class Article {

	public int getArticleID() {
		return articleID;
	}
	public void setArticleID(int articleID) {
		this.articleID = articleID;
	}
	public int getIssueNo() {
		return issueNo;
	}
	public void setIssueNo(int issueNo) {
		this.issueNo = issueNo;
	}
	int articleID;
	int issueNo;
	int journalID;
	public int getJournalID() {
		return journalID;
	}
	public void setJournalID(int journalID) {
		this.journalID = journalID;
	}
}
