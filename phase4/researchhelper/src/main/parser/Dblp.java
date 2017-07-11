package parser;
import java.util.List;  

import javax.xml.bind.annotation.XmlAttribute;  
import javax.xml.bind.annotation.XmlElement;  
import javax.xml.bind.annotation.XmlRootElement;  

@XmlRootElement  
public class Dblp {  

	private List<Article> article; 
	private List<Incollection> incollection; 
	private List<Inproceedings> inproceeding; 
	private List<www> www; 
	private List<Phdthesis> thesis;
	private List<Proceedings> proceeding;
	private List<Book> book;
	public Dblp() {}  

	public Dblp(List<Article> article, List<Incollection> incollection,List<Inproceedings> inproceeding,
			List<www> www,List<Phdthesis> thesis, List<Proceedings> proceeding,List<Book> book) {  
		super();  

		this.article = article;  
		this.incollection = incollection;
		this.inproceeding = inproceeding;
		this.www=www;
		this.thesis=thesis;
		this.proceeding = proceeding;
		this.book = book;
	}  

	@XmlElement  
	public List<Article> getArticle() {  
		return article;  
	}  
	public void setArticle(List<Article> article) {  
		this.article = article;  
	}

	public List<Incollection> getIncollection() {
		// TODO Auto-generated method stub
		return incollection;
	}  
	public void setIncollection(List<Incollection> incollection) {
		// TODO Auto-generated method stub
		this.incollection = incollection;  
	}  
	public List<Inproceedings> getInproceedings() {
		// TODO Auto-generated method stub
		return inproceeding;
	}  
	public  void setInproceedings(List<Inproceedings> inproceeding) {
		// TODO Auto-generated method stub
		this.inproceeding = inproceeding;
	}  
	public List<www> getwww() {
		// TODO Auto-generated method stub
		return www;
	}  
	public void setwww(List<www> www) {
		// TODO Auto-generated method stub
		this.www = www;
	} 

	@XmlElement
	public List<Phdthesis> getPhdthesis() {
		// TODO Auto-generated method stub
		return thesis;
	}  
	public void setPhdthesis(List<Phdthesis> thesis) {
		// TODO Auto-generated method stub
		this.thesis = thesis;
	}

	public List<Proceedings> getProceedings() {
		return proceeding;
	}

	public void setProceedings(List<Proceedings> proceeding) {
		this.proceeding = proceeding;
	}

	public List<Book> getBook() {
		return book;
	}

	public void setBook(List<Book> book) {
		this.book = book;
	} 
}
