package parser;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;  
import javax.xml.bind.annotation.XmlElement;  
import javax.xml.bind.annotation.XmlRootElement;  
  
@XmlRootElement  
public class Proceedings {  
    
    private List<String> editor; 
    private String title; 
    private String year;
    private String volume;
    private String series;
    private String booktitle; 
    private String ee;  
    private String url;
    private String publisher;
    private String isbn;
    private String key;
    
    public Proceedings(){}
    
	public Proceedings(List<String> editor, String title, String year, String volume, String series, String booktitle,
			String ee, String url, String isbn, String key) {
		super();
		this.editor = editor;
		this.title = title;
		this.year = year;
		this.volume = volume;
		this.series = series;
		this.booktitle = booktitle;
		this.ee = ee;
		this.url = url;
		this.isbn = isbn;
		this.key = key;
	}
	
	public List<String> getEditor() {
		return editor;
	}
	public void setEditor(List<String> editor) {
		this.editor = editor;
	}
	
	public String gettitle() {
		return title;
	}
	public void settitle(String title) {
		this.title = title;
	}
	
	public String getyear() {
		return year;
	}
	public void setyear(String year) {
		this.year = year;
	}
	
	public String getvolume() {
		return volume;
	}
	public void setvolume(String volume) {
		this.volume = volume;
	}
	
	public String getseries() {
		return series;
	}
	public void setseries(String series) {
		this.series = series;
	}
	
	public String getbooktitle() {
		return booktitle;
	}
	public void setbooktitle(String booktitle) {
		this.booktitle = booktitle;
	}
	
	public String getee() {
		return ee;
	}
	public void setee(String ee) {
		this.ee = ee;
	}

	public String geturl() {
		return url;
	}
	public void seturl(String url) {
		this.url = url;
	}
	
	public String getisbn() {
		return isbn;
	}
	public void setisbn(String isbn) {
		this.isbn = isbn;
	}
	
	@XmlAttribute
	public String getkey() {
		return key;
	}
	public void setkey(String key) {
		this.key = key;
	}

	public String getpublisher() {
		return publisher;
	}

	public void setpublisher(String publisher) {
		this.publisher = publisher;
	}
    
}