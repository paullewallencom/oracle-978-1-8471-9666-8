<?xml version="1.0" encoding="windows-1252" ?><xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:output encoding="ISO-8859-1"  method="text/html" />
<xsl:template match="/catalog">
<html>
  <head>
    <title>Oracle Catalog</title>
  </head>
  <body>
    <table border="1" cellspacing="0">
        <tr>
         <th>Journal</th>
         <th>Publisher</th>
         <th>Edition</th>
         <th>Section</th>
         <th>Title</th>
         <th>Author</th>
        </tr>
      <xsl:for-each select="journal">
        <tr>
         <td><xsl:value-of select="@title"/></td>
         <td><xsl:value-of select="@publisher"/></td>
         <td><xsl:value-of select="@edition"/></td>
          <td><xsl:value-of select="article/@section"/></td>
         <td><xsl:value-of select="article/title"/></td>
         <td><xsl:value-of select="article/author"/></td>
        </tr>
      </xsl:for-each>
    </table>
  </body>
</html>
</xsl:template>
</xsl:stylesheet>
