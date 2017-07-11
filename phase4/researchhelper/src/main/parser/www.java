package parser;
import javax.xml.bind.annotation.XmlAttribute;  
import javax.xml.bind.annotation.XmlElement;  
import javax.xml.bind.annotation.XmlRootElement;  
  
@XmlRootElement  
public class www {  
    
    private String author; 
    private String title;  
  
    private String mdate;
    private String key;
    
  
public www() {}  
public www(String mdate, String key,String author, String title) {  
    super();  
    this.mdate=mdate;
    this.key=key;
    this.author=author;
    this.title=title;
    
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
}