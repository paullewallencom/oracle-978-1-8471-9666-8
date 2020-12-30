<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/xml" prefix="x"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>transformXML</title>
  </head>
  <body>
    <c:import var="xml" url="catalog.xml"/>
    <c:import var="xslt"    url="catalog.xsl"/>
    <x:transform doc="${xml}"   xslt="${xslt}"/>
  </body>
</html>
