package xmlexcel;

import org.apache.poi.hssf.usermodel.*;
import javax.xml.xpath.*;
import java.io.*;
import org.w3c.dom.*;
import org.xml.sax.InputSource;



public class XMLToExcel {


    public void generateExcel(File xmlDocument) {
        try {


            HSSFWorkbook wb = new HSSFWorkbook();
            HSSFSheet spreadSheet = wb.createSheet("spreadSheet");
            HSSFCellStyle cellStyle = wb.createCellStyle();
            cellStyle.setBorderRight(HSSFCellStyle.BORDER_MEDIUM);
            cellStyle.setBorderTop(HSSFCellStyle.BORDER_MEDIUM);
            /*cellStyle.setFillForegroundColor(org.apache.poi.hssf.util.HSSFColor.BLUE.index
);
cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);*/
            spreadSheet.setColumnWidth(0, (256 * 25));
            spreadSheet.setColumnWidth(1, (256 * 25));
            spreadSheet.setColumnWidth(2, (256 * 25));
            spreadSheet.setColumnWidth(3, (256 * 25));
            spreadSheet.setColumnWidth(4, (256 * 75));
            spreadSheet.setColumnWidth(5, (256 * 25));



            InputSource inputSource = 
                new InputSource(new FileInputStream(xmlDocument));
            XPathFactory factory = XPathFactory.newInstance();
            XPath xPath = factory.newXPath();
            String expression = "/catalog/journal";
            com.sun.org.apache.xml.internal.dtm.ref.DTMNodeList nodeList = 
               (com.sun.org.apache.xml.internal.dtm.ref.DTMNodeList)(xPath.evaluate(expression,inputSource, XPathConstants.NODESET));
            HSSFRow row=spreadSheet.createRow(0);
            row.createCell(0).setCellValue("Journal");
            row.createCell(1).setCellValue("Publisher");
            row.createCell(2).setCellValue("Section");
            row.createCell(3).setCellValue("Edition");
            row.createCell(4).setCellValue("Title");
            row.createCell(5).setCellValue("Author");
            for (int i = 0; i < nodeList.getLength(); i++) {
                 row = spreadSheet.createRow(i+1);


                HSSFCell cell = row.createCell(0);
                cell.setCellValue(new HSSFRichTextString (((Element)(nodeList.item(i))).getElementsByTagName("journal-title").item(0).getFirstChild().getNodeValue()));
                cell.setCellStyle(cellStyle);
                cell = row.createCell(1);


                cell.setCellValue(new HSSFRichTextString (((Element)(nodeList.item(i))).getElementsByTagName("publisher").item(0).getFirstChild().getNodeValue()));

                cell.setCellStyle(cellStyle);
                cell = row.createCell(2);
              cell.setCellValue(new HSSFRichTextString (((Element)(nodeList.item(i))).getElementsByTagName("section").item(0).getFirstChild().getNodeValue());)
                cell.setCellStyle(cellStyle);
                cell = row.createCell(3);
              cell.setCellValue(new HSSFRichTextString (((Element)(nodeList.item(i))).getElementsByTagName("edition").item(0).getFirstChild().getNodeValue()));
                cell.setCellStyle(cellStyle);
                cell = row.createCell(4);
                cell.setCellValue(new HSSFRichTextString ((Element)(nodeList.item(i))).getElementsByTagName("title").item(0).getFirstChild().getNodeValue()));
                cell.setCellStyle(cellStyle);
                cell = row.createCell(5);
                cell.setCellValue(new HSSFRichTextString (((Element)(nodeList.item(i))).getElementsByTagName("author").item(0).getFirstChild().getNodeValue()));
                cell.setCellStyle(cellStyle);
            }


            FileOutputStream output = 
                new FileOutputStream(new File("catalog.xls"));
            wb.write(output);
            output.flush();
            output.close();

        } catch (IOException e) { System.err.println(e.getMessage());
        } catch (XPathExpressionException e) { System.err.println(e.getMessage());
        }
    }


    public static void main(String[] argv) {
        File xmlDocument = new File("catalog.xml");
        XMLToExcel excel = new XMLToExcel();
        excel.generateExcel(xmlDocument);
    }
}
