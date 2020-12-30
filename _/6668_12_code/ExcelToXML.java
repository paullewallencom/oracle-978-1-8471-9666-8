package xmlexcel;
import org.apache.poi.hssf.usermodel.*;
import org.w3c.dom.*;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;

import javax.xml.transform.stream.StreamResult;


import java.io.*;


public class ExcelToXML {


    public void generateXML(File excelFile) {


        try {


            DocumentBuilderFactory factory = 
                DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.newDocument();
            Element catalogElement = document.createElement("catalog");
            document.appendChild(catalogElement);


            InputStream input = new FileInputStream(excelFile);
            HSSFWorkbook workbook = new HSSFWorkbook(input);
            HSSFSheet spreadsheet = workbook.getSheetAt(0);

            for (int i = 1; i <= spreadsheet.getLastRowNum(); i++)

            {
                HSSFRow row = spreadsheet.getRow(i);

                Element journalElement = document.createElement("journal");
                catalogElement.appendChild(journalElement);

                Element journalTitleElement = document.createElement("journal-title");
                journalElement.appendChild(journalTitleElement);
                journalTitleElement.appendChild(document.createTextNode(row.getCell(0).getRichStringCellValue().getString()));

                Element publisherElement = document.createElement("publisher");
                journalElement.appendChild(publisherElement);
                publisherElement.appendChild(document.createTextNode(row.getCell(1).getRichStringCellValue().getString()));

                Element sectionElement = document.createElement("section");
                journalElement.appendChild(sectionElement);
                sectionElement.appendChild(document.createTextNode(row.getCell(2).getRichStringCellValue().getString()));

                Element editionElement = document.createElement("edition");
                journalElement.appendChild(editionElement);
                editionElement.appendChild(document.createTextNode(row.getCell(3).getRichStringCellValue().getString()));


                Element titleElement = document.createElement("title");
                journalElement.appendChild(titleElement);
                titleElement.appendChild(document.createTextNode(row.getCell(4).getRichStringCellValue().getString()));

                Element authorElement = document.createElement("author");
                journalElement.appendChild(authorElement);
                authorElement.appendChild(document.createTextNode(row.getCell(5).getRichStringCellValue().getString()));

            }

            TransformerFactory tFactory = TransformerFactory.newInstance();

            Transformer transformer = tFactory.newTransformer();

            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(new File("catalog.xml"));
            transformer.transform(source, result);

        } catch (IOException e) {System.err.println(e.getMessage());
        } catch (ParserConfigurationException e) { System.err.println(e.getMessage());

        } catch (TransformerConfigurationException e) { System.err.println(e.getMessage());

        } catch (TransformerException e) { System.err.println(e.getMessage());

        }
    }


    public static void main(String[] argv) {

        ExcelToXML excel = new ExcelToXML();
        File input = new File("catalog.xls");
        excel.generateXML(input);
    }

}
