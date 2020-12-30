package apachefop;

import org.apache.fop.apps.Driver;
import java.io.*;
import org.apache.avalon.framework.logger.ConsoleLogger;
import org.apache.avalon.framework.logger.Logger;
import org.apache.fop.apps.FOPException;
import org.xml.sax.InputSource;
import javax.xml.transform.*;
import javax.xml.transform.stream.StreamSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.dom.DOMSource;

import org.xml.sax.SAXException;
import org.w3c.dom.Document;



public class XMLToPDF 
{
  public XMLToPDF()
  {
  }
  

  public void xmlToFO(){
  try{
     DocumentBuilderFactory factory =
            DocumentBuilderFactory.newInstance();

            File stylesheet = new File("catalog.xsl");
            File xmlFile   = new File("catalog.xml");
 
            DocumentBuilder builder = factory.newDocumentBuilder();
         Document   document = builder.parse(xmlFile);

     TransformerFactory transformerFactory = TransformerFactory.newInstance();
    
     Transformer transformer = transformerFactory.newTransformer(new StreamSource(stylesheet));
    
    

     DOMSource source = new DOMSource(document);
     StreamResult  result = new StreamResult(new File("catalog.fo"));
     transformer.transform(source, result);
  
  }catch(TransformerConfigurationException e){System.err.println("TransformerConfigurationException: "+e.getMessage());}
   catch(TransformerException e){System.err.println("TransformerException: "+e.getMessage());}
    catch(ParserConfigurationException e){System.err.println("TransformerConfigurationException: "+e.getMessage());}
       catch(IOException e){System.err.println("TransformerException: "+e.getMessage());}
    catch(SAXException e){System.err.println("TransformerException: "+e.getMessage());}
    
  }
  


  public void foToPDF(){
  try{
     Driver driver=new Driver();
     Logger logger=new ConsoleLogger(ConsoleLogger.LEVEL_INFO);
     driver.setLogger(logger);
     org.apache.fop.messaging.MessageHandler.setScreenLogger(logger);
     driver.setRenderer(Driver.RENDER_PDF);
     File xslFOFile=new File("catalog.fo");
     File pdfFile=new File("catalog.pdf");
     InputStream input=new FileInputStream(xslFOFile);
     driver.setInputSource(new InputSource(input));
     OutputStream output=new FileOutputStream(pdfFile);
     driver.setOutputStream(output);
 
     driver.run();
     output.flush();
     output.close();
  }catch(IOException e){System.err.println("IOException: "+e.getMessage());}
   catch(FOPException e){System.err.println("FOPException: "+e.getMessage());}
   
 
  }


  public static void main(String[] argv){ 
     XMLToPDF  fop=new XMLToPDF();
     fop.xmlToFO();
     fop.foToPDF();
  
  }
}
