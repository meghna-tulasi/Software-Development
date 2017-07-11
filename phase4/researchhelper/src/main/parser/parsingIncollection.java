package parser;
import java.io.File;  
import java.util.List;  
  
import javax.xml.bind.JAXBContext;  
import javax.xml.bind.JAXBException;  
import javax.xml.bind.Unmarshaller;  

public class parsingIncollection {
 
    public static void main(String[] args) {  
   
     try {  
    	
    	 
        File file = new File("/Users/meghnatulasi/Documents/IR/Dblp-parser-master/dblp.xml");  
        JAXBContext jaxbContext = JAXBContext.newInstance(Dblp.class);  
   
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();  
        //jaxbUnmarshaller.setProperty(javax.xml.XMLConstants.ACCESS_EXTERNAL_DTD, true);
        System.setProperty("javax.xml.accessExternalDTD", "all"); 
        System.setProperty("jdk.xml.maxGeneralEntitySizeLimit","0");
        System.setProperty("jdk.xml.entityExpansionLimit","0");
        Dblp que= (Dblp) jaxbUnmarshaller.unmarshal(file);  
          
       
        List<Incollection> list=que.getIncollection();  
        for(Incollection e:list)  
        	System.out.println(e.getAuthor()+" "+e.gettitle()+" "+e.getpages()+" "+e.getyear()+" "+e.getbooktitle()+" "+e.getee()+" "+e.getcrossref()+" "+e.geturl());  
       
   
      } catch (JAXBException e) {  
    	 
        e.printStackTrace();  
      }  
   
    }  
}  