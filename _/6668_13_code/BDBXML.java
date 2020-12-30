package berkeleydbxml;

import com.sleepycat.dbxml.XmlContainer;
import com.sleepycat.dbxml.XmlDocument;
import com.sleepycat.dbxml.XmlException;
import com.sleepycat.dbxml.XmlInputStream;
import com.sleepycat.dbxml.XmlManager;
import com.sleepycat.dbxml.XmlModify;
import com.sleepycat.dbxml.XmlQueryContext;
import com.sleepycat.dbxml.XmlQueryExpression;
import com.sleepycat.dbxml.XmlResults;
import com.sleepycat.dbxml.XmlUpdateContext;
import com.sleepycat.dbxml.XmlValue;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;


public class BDBXML {


    XmlManager xmlManager = null;
    XmlContainer xmlContainer = null;


    public void createContainer() {
        try {
            xmlManager = new XmlManager();
            xmlManager.setDefaultContainerType(XmlContainer.NodeContainer);
            xmlContainer = xmlManager.createContainer("catalog.dbxml");
        } catch (XmlException e) {
            System.err.println("XmlException" + e.getMessage());
        } catch (java.io.FileNotFoundException e) {
            System.err.println("FileNotFoundException" + e.getMessage());
        }
    }


    public void addDocument() {
        try {
            String docString =
                "<catalog title='Oracle Magazine' publisher='Oracle Publishing'>" +
                "<journal date='November-December 2008'>" + "<article>" +
                "<title>Application Server Convergence</title>" +
                "<author>David Baum</author>" + "</article>" + "</journal>" +
                "</catalog>";

            String docName = "catalog1";

            XmlUpdateContext updateContext = xmlManager.createUpdateContext();
            xmlContainer.putDocument(docName, docString, updateContext, null);

            InputStream inputStream =
                new FileInputStream(new File("catalog.xml"));
            XmlInputStream xmlInputStream =
                xmlManager.createInputStream(inputStream);


            docName = "catalog2";
            xmlContainer.putDocument(docName, xmlInputStream, updateContext,
                                     null);


            XmlResults results = xmlContainer.getAllDocuments(null);

            while (results.hasNext()) {
                XmlValue xmlValue = results.next();
                System.out.println(xmlValue.asString());
            }
        } catch (XmlException e) {
            System.err.println("XmlException" + e.getMessage());
        } catch (FileNotFoundException e) {
            System.err.println("XmlException" + e.getMessage());
        }
    }


    public void queryDocument() {
        try {

            XmlQueryContext context = xmlManager.createQueryContext();

            String query =
                "collection('catalog.dbxml')/catalog/journal/article/title/text()";
            XmlQueryExpression qe = xmlManager.prepare(query, context);
            XmlResults results = qe.execute(context);

            while (results.hasNext()) {
                XmlValue xmlValue = results.next();
                System.out.println(xmlValue.asString());
            }
        } catch (XmlException e) {
            System.err.println("XmlException" + e.getMessage());
        }
    }


    public void modifyDocument() {
        try {
            XmlQueryContext qc = xmlManager.createQueryContext();
            XmlUpdateContext uc = xmlManager.createUpdateContext();
            XmlModify mod = xmlManager.createModify();
            XmlQueryExpression select =
                xmlManager.prepare("/catalog/journal/article[title='Application Server Convergence']",
                                   qc);
            mod.addAppendStep(select, XmlModify.Attribute, "section",
                              "COMMENT");

            String objectContent =
                "<article>" + "<title>Declarative Data Filtering</title>" +
                "<author>Steve Muench</author>" + "</article>";

            select = xmlManager.prepare("/catalog/journal", qc);
            mod.addInsertAfterStep(select, XmlModify.Element, "journal",
                                   objectContent);

            XmlDocument xmlDocument = xmlContainer.getDocument("catalog1");
            XmlValue xmlValue = new XmlValue(xmlDocument);
            mod.execute(xmlValue, qc, uc);

            System.out.println("XML Documents after modification");

            XmlResults results = xmlContainer.getAllDocuments(null);

            while (results.hasNext()) {
                xmlValue = results.next();
                System.out.println(xmlValue.asString() + "\n");
            }
        } catch (XmlException e) {
            System.err.println("XmlException" + e.getMessage());
        }
    }


    public void updateDocument() {
        try {
            XmlQueryContext qc = xmlManager.createQueryContext();
            XmlUpdateContext uc = xmlManager.createUpdateContext();
            XmlModify mod = xmlManager.createModify();

            String updateContent = "Oracle Database 11g Redux-Feature Review";
            XmlQueryExpression select =
                xmlManager.prepare("/catalog/journal/article[title='Oracle Database 11g Redux']/title/text()",
                                   qc);
            mod.addUpdateStep(select, updateContent);

            select = xmlManager.prepare("/catalog/journal", qc);
            mod.addRenameStep(select, "magazine");


            System.out.println("XML Documents after Update");

            XmlDocument xmlDocument = xmlContainer.getDocument("catalog2");
            XmlValue xmlValue = new XmlValue(xmlDocument);
            mod.execute(xmlValue, qc, uc);

            XmlResults results = xmlContainer.getAllDocuments(null);

            while (results.hasNext()) {
                xmlValue = results.next();
                System.err.println(xmlValue.asString());
            }
        } catch (XmlException e) {
            System.err.println("XmlException" + e.getMessage());
        }
    }


    public static void main(String[] argv) {
        BDBXML bdbXML = new BDBXML();

        bdbXML.createContainer();
        bdbXML.addDocument();
        bdbXML.queryDocument();
        bdbXML.modifyDocument();
        bdbXML.updateDocument();
    }
}
