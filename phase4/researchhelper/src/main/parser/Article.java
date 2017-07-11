package parser;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;  
import javax.xml.bind.annotation.XmlElement;  
import javax.xml.bind.annotation.XmlRootElement;  
  
@XmlRootElement  
public class Article {  
    
    private List<String> author; 
    private String title;  
    private String pages;  
    private String year;  
    private String volume;  
    private String journal;  
    private String url;  
    private String ee;  
    private String mdate;
    private String key;
    
  
public Article() {}  
public Article(String mdate, String key,List<String> author, String title,String pages,
		String year ,String volume ,String journal,String url,String ee) {  
    super();  
    this.mdate=mdate;
    this.key=key;
    this.author=author;
    this.title=title;
    this.pages=pages;
    this.year=year;
    this.volume=volume;
    this.journal=journal;
    this.url=url;
    this.ee=ee;
    
    
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
public String getvolume() {  
    return volume;  
}  
public void setvolume(String volume) {  
    this.volume = volume;  
}  
public String getjournal() {  
    return journal;  
}  
public void setjournal(String journal) {  
    this.journal = journal;  
}  
public String geturl() {  
    return url;  
}  
public void seturl(String url) {  
    this.url = url;  
}  
public String getee() {  
    return ee;  
}  
public void setee(String ee) {  
    this.ee = ee;  
}  }