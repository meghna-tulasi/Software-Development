package parser;
import javax.xml.bind.annotation.XmlAttribute;  
import javax.xml.bind.annotation.XmlElement;  
import javax.xml.bind.annotation.XmlRootElement;  

@XmlRootElement  
public class Phdthesis {  

	private String author; 
	private String title;  
	private String year;  

	private String school;  
	private String ee;  
	private String note;
	private String key;


	public Phdthesis() {}  
	public Phdthesis(String key,String author, String title,
			String year ,String school,String ee,String note, String note_type) {  
		super();
		this.key=key;
		this.author=author;
		this.title=title;

		this.year=year;
		this.school=school;

		this.ee=ee;
		this.note=note;

	}

	@XmlAttribute  
	public String getkey() {  
		return key;  
	}  
	public void setkey(String key) {  
		this. key =  key;  
	}  

	@XmlElement  
	public String getAuthor() {  
		return author;  
	}  
	public void setAuthor(String author) {  
		this.author = author;  
	}  

	@XmlElement  
	public String gettitle() {  
		return title;  
	}  
	public void settitle(String title) {  
		this.title=  title;  
	}  

	public String getyear() {  
		return year;  
	}  
	public void setyear(String year) {  
		this.year = year;  
	}  

	public String getschool() {  
		return school;  
	}  
	public void setschool(String school) {  
		this.school = school;  
	}  

	public String getee() {   
		return ee;  
	}  
	public void setee(String ee) {  
		this.ee = ee;  
	}  
	public String getnote() {  
		return note;  
	}  
	public void setnote(String note) {  
		this.note = note;  
	} }