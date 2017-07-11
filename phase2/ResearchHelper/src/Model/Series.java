package Model;

import java.util.List;

public class Series implements Publication{
	
	String name;
	int seriesID;
	List<Book> books;
	List<Incollection> incollections;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getSeriesID() {
		return seriesID;
	}
	public void setSeriesID(int seriesID) {
		this.seriesID = seriesID;
	}
	public List<Book> getBooks() {
		return books;
	}
	public void setBooks(List<Book> books) {
		this.books = books;
	}
	public List<Incollection> getIncollections() {
		return incollections;
	}
	public void setIncollections(List<Incollection> incollections) {
		this.incollections = incollections;
	}
	

}
