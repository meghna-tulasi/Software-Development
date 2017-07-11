package parser;

import java.io.File;  
import java.util.List;  
  
import javax.xml.bind.JAXBContext;  
import javax.xml.bind.JAXBException;  
import javax.xml.bind.Unmarshaller;  


public class parsing {  
    public static void main(String[] args) {  
   
     try {  
    	
    	 
        File file = new File("E:/Bansal/NEU/SEM-3/MSD/DBLP Data/dblp.xml");  
        JAXBContext jaxbContext = JAXBContext.newInstance(Dblp.class);  
   
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();  
        //jaxbUnmarshaller.setProperty(javax.xml.XMLConstants.ACCESS_EXTERNAL_DTD, true);
        System.setProperty("javax.xml.accessExternalDTD", "all"); 
        System.setProperty("jdk.xml.maxGeneralEntitySizeLimit","0");
        System.setProperty("jdk.xml.entityExpansionLimit","0");
        Dblp que= (Dblp) jaxbUnmarshaller.unmarshal(file);  
          
       
        /*List<Article> list=que.getArticle();  
        for(Article e:list)  
        	System.out.println(e.getAuthor()+" "+e.gettitle()+" "+e.getpages()+" "+e.getyear()+" "+e.getvolume()+" "+e.getjournal()+" "+e.geturl()+" "+e.getee());*/  
       
        List<Incollection> list1=que.getIncollection();  
        for(Incollection e:list1)  
        	System.out.println(e.getAuthor());//+" "+e.gettitle()+" "+e.getpages()+" "+e.getyear()+" "+e.getbooktitle()+" "+e.getee()+" "+e.getcrossref()+" "+e.geturl());  
       
        /*List<Inproceedings> list2=que.getInproceedings();  
        for(Inproceedings e:list2)  
        	System.out.println(e.getAuthor()+" "+e.gettitle()+" "+e.getpages()+" "+e.getyear()+" "+e.getbooktitle()+" "+e.getee()+" "+e.getcrossref()+" "+e.geturl());  
       
        List<www> list3=que.getwww();  
        for(www e:list3)  
        	System.out.println(e.getAuthor()+" "+e.gettitle());
        
        List<Thesis> list4=que.getthesis();  
        for(Thesis e:list4)  
        	System.out.println(e.getAuthor()+" "+e.gettitle()+" "+e.getyear()+" "+e.getschool()+" "+e.getee()+" "+e.getnote());  */
       
    
      } catch (JAXBException e) {  
    	 
        e.printStackTrace();  
      }  
   
    }  
}  