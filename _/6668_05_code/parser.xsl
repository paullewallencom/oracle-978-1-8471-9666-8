<?xml version="1.0" encoding="windows-1252" ?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 

xmlns:parser="http://www.oracle.com/XSL/Transform/java/oracle.xml.parser.v2.DOMParser"
xmlns:document="http://www.oracle.com/XSL/Transform/java/oracle.xml.parser.v2.XMLDocument"
xmlns:element="http://www.oracle.com/XSL/Transform/java/org.w3c.dom.Element"
xmlns:node="http://www.oracle.com/XSL/Transform/java/org.w3c.dom.Node"
xmlns:nodelist="http://www.oracle.com/XSL/Transform/java/org.w3c.dom.NodeList"
xmlns:nodeConvUtil="http://www.oracle.com/XSL/Transform/java/xsltextensions.NodeConversionUtil"
xmlns:file="http://www.oracle.com/XSL/Transform/java/java.io.File"
xmlns:fileReader="http://www.oracle.com/XSL/Transform/java/java.io.FileReader"
xmlns:outputStream="http://www.oracle.com/XSL/Transform/java/java.io.FileOutputStream"
xmlns:printDriver="http://www.oracle.com/XSL/Transform/java/oracle.xml.parser.v2.XMLPrintDriver"
>
 <xsl:output  method="text" />

 <xsl:template match="/">
   <xsl:variable name="parser" select="parser:new()"/>
   <xsl:variable name="xmlDocument" select="file:new('catalog.xml')"/>
   <xsl:variable name="file-reader" select="fileReader:new($xmlDocument)"/>
   <xsl:value-of select="parser:parse($parser, $file-reader)"/>
   <xsl:variable name="parsedDocument" select="parser:getDocument($parser)"/>
   <xsl:variable name="catalogElement" select="document:getDocumentElement($parsedDocument)"/>

   <xsl:variable name="journalNodeList" select="element:getElementsByTagName($catalogElement, 'journal')"/>
   <xsl:variable name="journalNode" select="nodelist:item($journalNodeList, 0)"/>
   <xsl:variable name="journalElement"  select="nodeConvUtil:nodeToElement($journalNode)"/>
   <xsl:value-of select="element:removeAttribute($journalElement, 'date')"/>
   <xsl:value-of select="element:setAttribute($journalElement, 'date', 'March-April 2008')"/>

   <xsl:variable name="articleNodeList" select="element:getElementsByTagName($journalElement, 'article')"/>
   <xsl:variable name="articleNode" select="nodelist:item($articleNodeList, 0)"/>
   <xsl:variable name="articleElement" select="nodeConvUtil:nodeToElement($articleNode)"/>
   <xsl:value-of select="element:removeAttribute($articleElement, 'section')"/>
   <xsl:value-of select="element:setAttribute($articleElement, 'section', 'TECHNOLOGY')"/>

   <xsl:variable name="titleNodeList" select="element:getElementsByTagName($articleElement, 'title')"/>
   <xsl:variable name="titleNode" select="nodelist:item($titleNodeList, 0)"/>
   <xsl:variable name="titleTextNode" select="node:getFirstChild($titleNode)"/>
   <xsl:value-of select="node:setNodeValue($titleTextNode, 'Oracle Database 11g Redux')"/>


   <xsl:variable name="authorNodeList" select="element:getElementsByTagName($articleElement, 'author')"/>
   <xsl:variable name="authorNode" select="nodelist:item($authorNodeList, 0)"/>
   <xsl:variable name="authorTextNode" select="node:getFirstChild($authorNode)"/>
   <xsl:value-of select="node:setNodeValue($authorTextNode, 'Tom Kyte')"/>


   <xsl:variable name="outputFile" select="file:new('catalog-modified.xml')"/>
   <xsl:variable name="fileOutputStream" select="outputStream:new($outputFile)"/>
   <xsl:variable name="xmlPrintDriver" select="printDriver:new($fileOutputStream)"/>
   <xsl:value-of select="printDriver:setEncoding($xmlPrintDriver, 'utf-8')"/>
   <xsl:value-of select="printDriver:printDocument($xmlPrintDriver, $parsedDocument)"/>
   <xsl:value-of select="printDriver:close($xmlPrintDriver)"/>

 </xsl:template>

</xsl:stylesheet>

