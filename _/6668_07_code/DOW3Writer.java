package dom3ls;

import org.w3c.dom.ls.*;
import oracle.xml.parser.v2.*;
import org.w3c.dom.*;
import java.io.*;


public class DOM3Writer {


    public void saveDocument() {
        try {
            XMLDOMImplementation impl = new XMLDOMImplementation();
            Document document = impl.createDocument(null, null, null);
            Element catalog = document.createElement("catalog");
            catalog.setAttribute("title", "Oracle Magazine");
            document.appendChild(catalog);
            Element journal = document.createElement("journal");
            journal.setAttribute("date", "Sept-Oct 2008");
            journal.setAttribute("section", "Oracle Developer");

            catalog.appendChild(journal);
            


            LSSerializer domWriter = impl.createLSSerializer();
            LSOutput output = impl.createLSOutput();
            OutputStream outputStream =
                new FileOutputStream(new File("output.xml"));
            output.setByteStream(outputStream);
            output.setEncoding("UTF-8");
            domWriter.write(document, output);
            Node journalNode=catalog;
            outputStream = new FileOutputStream(new File("nodeOutput.xml"));
            output.setByteStream(outputStream);
            domWriter.write(journalNode, output);
            String nodeString = domWriter.writeToString(journalNode);
            System.out.println(nodeString);
        } catch (IOException e) {
            System.err.println("IOException " + e.getMessage());
        } catch (DOMException e) {
            System.err.println("DOMException " + e.getMessage());
        }
    }


    public static void main(String[] argv) {
        DOM3Writer writer = new DOM3Writer();
        writer.saveDocument();
    }
}
