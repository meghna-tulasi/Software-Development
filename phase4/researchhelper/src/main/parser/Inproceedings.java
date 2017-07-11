package parser;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;  
import javax.xml.bind.annotation.XmlElement;  
import javax.xml.bind.annotation.XmlRootElement;  
  
@XmlRootElement  
public class Inproceedings {  
    
    private List<String> author; 
    private String title;  
    private String pages;  
    private String year;  
    private String booktitle; 
    private String ee;  
    private String crossref;  
    private String url;  
    private String mdate;
    private String key;
    
  
public Inproceedings() {}  
public Inproceedings(String mdate, String key,List<String> author, String title,String pages,
		String year ,String booktitle ,String ee,String crossref,String url) {  
    super();  
    this.mdate=mdate;
    this.key=key;
    this.author=author;
    this.title=title;
    this.pages=pages;
    this.year=year;
    this.booktitle=booktitle;
    this.ee=ee;
    this.crossref=crossref;
    this.url=url;
    
    
    
}  
@XmlAttribute  
public String getmdate() {  
    return mdate;  
}  
public void setmdate(String mdate) {  
    this. mdate =  mdate;  
}  

@XmlAttribute  
public String getkey() {  
    return key;  
}  
public void setkey(String key) {  
    this. key =  key;  
}  

@XmlElement  
public List<String> getAuthor() {  
    return author;  
}  
public void setAuthor(List<String> author) {  
    this.author = author;  
}  

@XmlElement  
public String gettitle() {  
    return title;  
}  
public void settitle(String title) {  
    this.title=  title;  
}  
public String getpages() {  
    return pages;  
}  
public void setpages(String pages) {  
    this.pages = pages;  
}  
public String getyear() {  
    return year;  
}  
public void setyear(String year) {  
    this.year = year;  
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

public String getcrossref() {  
    return crossref;  
}  
public void setcrossre(String crossref) {  
    this.crossref = crossref;  
}  
public String geturl() {  
    return url;  
}  
public void seturl(String url) {  
    this.url = url;  
}  
}
