<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/xml" prefix="x"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>parseXML</title>
  </head>
  <body>
    <c:import var="xml" url="catalog.xml"/>
    <x:parse var="xmlDocument" doc="${xml}"/>
    <x:set var="catalog" select="$xmlDocument/catalog"/>
    <x:forEach select="$catalog/journal">
      <b>Catalog Title: 
        <x:out select="../@title"/></b>
      <br/>
      Catalog Publisher:
      <x:out select="../@publisher"/>
      <br/>
      Catalog Edition:
      <x:out select="../@edition"/>
      <br/>
      Journal Section:
      <x:out select="@section"/>
      <br/>
      Article Title:
      <x:out select="article/title"/>
      <br/>
      Article Author:
      <x:out select="article/author"/>
      <br/>
      <br/>
    </x:forEach>
    <x:if select="$catalog/@edition='March-April 2008'">Edition: March-April 2008</x:if>
    <br/>
    <x:choose>
      <x:when select="$catalog/journal[2]/@section='Technology'">Title: Oracle Database 11g Redux</x:when>
      <x:otherwise>Title: Declarative Data Filtering</x:otherwise>
    </x:choose>
  </body>
</html>
