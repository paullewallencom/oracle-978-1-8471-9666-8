package xmlpublisher;

import com.sun.java.util.collections.Hashtable;

import oracle.apps.xdo.XDOException;
import oracle.apps.xdo.common.pdf.util.PDFDocMerger;
import oracle.apps.xdo.dataengine.DataProcessor;
import oracle.apps.xdo.template.FOProcessor;
import oracle.apps.xdo.template.fo.util.FOUtility;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;

import java.lang.reflect.Array;

import java.sql.DriverManager;
import java.sql.SQLException;


public class XMLPublisher {
    protected java.sql.Connection jdbcConnection;

    public XMLPublisher() {
    }

    public void foProcessorEngine() {
        try {
            FOProcessor processor = new FOProcessor();
            processor.setData("catalog.xml");
            processor.setTemplate("catalog.xsl");
            processor.setOutput("catalog.pdf");
            processor.setOutputFormat(FOProcessor.FORMAT_PDF);

            processor.generate();
        } catch (XDOException e) {
            System.err.println("XDOException " + e.getMessage());
        }
    }

    public void xslFoUtility() {
        try {
            InputStream[] input = new InputStream[2];
            InputStream firstFOStream =
                FOUtility.createFO("catalog2.xml", "catalog.xsl");
            InputStream secondFOStream =
                FOUtility.createFO("catalog3.xml", "catalog.xsl");
            Array.set(input, 0, firstFOStream);
            Array.set(input, 1, secondFOStream);

            InputStream mergedFOStream = FOUtility.mergeFOs(input, null);

            if (mergedFOStream == null) {
                System.err.println("Merge failed.");
            }

            FOProcessor processor = new FOProcessor();
            processor.setData(mergedFOStream);
            processor.setTemplate((String)null);
            processor.setOutput("catalog2.pdf");
            processor.setOutputFormat(FOProcessor.FORMAT_PDF);
            processor.generate();
        } catch (XDOException e) {
            System.err.println("XDOException" + e.getMessage());
        }
    }


    public void pdfDocumentMerger() {
        try {
            FileInputStream[] inputStreams = new FileInputStream[2];
            inputStreams[0] = new FileInputStream("catalog.pdf");
            inputStreams[1] = new FileInputStream("catalog2.pdf");

            FileOutputStream outputStream =
                new FileOutputStream("catalog3.pdf");
            PDFDocMerger pdfMerger =
                new PDFDocMerger(inputStreams, outputStream);

            pdfMerger.setPageNumberCoordinates(300, 20);
            pdfMerger.setPageNumberFontInfo("Courier", 10);
            pdfMerger.setPageNumberValue(1, 1);
            
            pdfMerger.setTextWatermark("Oracle XML Publisher", 50f, 450f); 
            pdfMerger.setTextWatermarkAngle(45); 
            pdfMerger.setTextWatermarkColor(0.3f, 0.3f, 1.0f); 
            
          /*  FileInputStream imgStream = new FileInputStream("C:\\XMLPublisher\\pdfwatermark.jpg");
            float[] rct = {10f, 800f, -1f, -1f}; 
            pdfMerger.setImageWatermark(imgStream, rct);*/
            
            pdfMerger.process();
            pdfMerger = null;
        } catch (XDOException e) {
            System.err.println("XDOException" + e.getMessage());
        } catch (FileNotFoundException e) {
            System.err.println("FileNotFoundException " + e.getMessage());
        }
    }


    public void dataEngine() {

        try {
            Class.forName("oracle.jdbc.OracleDriver");

            String url = "jdbc:oracle:thin:@localhost:1521:ORCL";
            jdbcConnection =
                    DriverManager.getConnection(url, "OE", "pw");

            DataProcessor dataProcessor = new DataProcessor();
            dataProcessor.setDataTemplate("catalogDataTemplate.xml");

            Hashtable parameters = new Hashtable();
            parameters.put("id", "catalog1");
            dataProcessor.setParameters(parameters);
            dataProcessor.setConnection(jdbcConnection);

            dataProcessor.setOutput("catalogData.xml");
            dataProcessor.processData();
        } catch (SQLException e) {
            System.err.println("SQLException " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.err.println("ClassNotFoundException " + e.getMessage());
        } catch (XDOException e) {
            System.err.println("XDOException" + e.getMessage());
        } finally {
            try {
                jdbcConnection.close();
            } catch (SQLException e) {
                System.err.println("SQLException in closing connection" +
                                   e.getMessage());
            }
        }
    }


    public static void main(String[] argv) {
        XMLPublisher xmlPublisher = new XMLPublisher();
        xmlPublisher.foProcessorEngine();
        xmlPublisher.xslFoUtility();
        xmlPublisher.pdfDocumentMerger();
        xmlPublisher.dataEngine();
    }
}


