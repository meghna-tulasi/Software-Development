package Model;
// Book Title needs discussion

import java.util.List;

public class Proceeding {
	int proceedingID;
	Editor editor;
	String Title;
	String year;
	int volume;
	String ISBN;
	String url;
	String bookTitle;
	String publisher;
	String series;
	List<Person> editors;
	int conferenceID;
	
	public int getConferenceID() {
		return conferenceID;
	}
	public void setConferenceID(int conferenceID) {
		this.conferenceID = conferenceID;
	}
	public int getProceedingID() {
		return proceedingID;
	}
	public void setProceedingID(int proceedingID) {
		this.proceedingID = proceedingID;
	}
	public Editor getEditor() {
		return editor;
	}
	public void setEditor(Editor editor) {
		this.editor = editor;
	}
	public String getTitle() {
		return Title;
	}
	public void setTitle(String title) {
		Title = title;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public int getVolume() {
		return volume;
	}
	public void setVolume(int volume) {
		this.volume = volume;
	}
	public String getISBN() {
		return ISBN;
	}
	public void setISBN(String iSBN) {
		ISBN = iSBN;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getBookTitle() {
		return bookTitle;
	}
	public void setBookTitle(String bookTitle) {
		this.bookTitle = bookTitle;
	}
	public String getPublisher() {
		return publisher;
	}
	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}
	public String getSeries() {
		return series;
	}
	public void setSeries(String series) {
		this.series = series;
	}
	public List<Person> getEditors() {
		return editors;
	}
	public void setEditors(List<Person> editors) {
		this.editors = editors;
	}
	
	

}
