package xsltextensions;

import org.w3c.dom.*;

public class NodeConversionUtil{
   
    public static Element nodeToElement(Node node){
       Element element=null;
        if(node instanceof Element)
          element=(Element)node;
       return element;

    }
}