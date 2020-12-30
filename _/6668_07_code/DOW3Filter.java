package dom3ls;

import org.w3c.dom.*;
import org.w3c.dom.ls.*;
import oracle.xml.parser.v2.*;
import org.w3c.dom.traversal.*;
import java.io.*;


public class DOM3Filter {


    public void filter() {
        try {


            DOMImplementationLS impl = new XMLDOMImplementation();
            LSParser parser =
                impl.createLSParser(DOMImplementationLS.MODE_SYNCHRONOUS,
                                    null);
            InputFilter inputFilter = new InputFilter();
            parser.setFilter(inputFilter);
            Document document =
                parser.parseURI("file://c:/DOM3.0/catalog.xml");


            LSSerializer domWriter = impl.createLSSerializer();
            OutputFilter outputFilter = new OutputFilter();
            domWriter.setFilter(outputFilter);
            LSOutput lsOutput = impl.createLSOutput();
            OutputStream outputStream =
                new FileOutputStream(new File("filter-output.xml"));
            lsOutput.setByteStream(outputStream);
            domWriter.write(document, lsOutput);

        } catch (IOException e) {
            System.out.println("IOException " + e.getMessage());
        }

        catch (DOMException e) {
            System.out.println("DOMException " + e.getMessage());
        }

    }


    public static void main(String[] args) {

        DOM3Filter dom3Filter = new DOM3Filter();
        dom3Filter.filter();
    }


    private class InputFilter implements LSParserFilter {
        public short acceptNode(Node node) {
            return NodeFilter.FILTER_ACCEPT;
        }

        public int getWhatToShow() {
            return NodeFilter.SHOW_ALL;
        }

        public short startElement(Element element) {
            System.out.println("Element Parsed " + element.getTagName());
            return NodeFilter.FILTER_ACCEPT;
        }
    }


    private class OutputFilter implements LSSerializerFilter {
        public short acceptNode(Node node) {
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element)node;
                if (element.getTagName().equals("journal"))
                    if (element.getAttribute("date").equals("May-June 2008"))
                        return NodeFilter.FILTER_REJECT;
            }
            return NodeFilter.FILTER_ACCEPT;
        }

        public int getWhatToShow() {
            return NodeFilter.SHOW_ALL;
        }
    }
}
