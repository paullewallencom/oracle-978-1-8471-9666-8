<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.1" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:fo="http://www.w3.org/1999/XSL/Format" exclude-result-prefixes="fo">
  <xsl:output method="xml" version="1.0" omit-xml-declaration="no" indent="yes"/>
  <!-- ========================= -->
  <!-- root element: catalog -->
  <!-- ========================= -->
  <xsl:template match="/catalog">
    <fo:root xmlns:fo="http://www.w3.org/1999/XSL/Format">
      <fo:layout-master-set>
        <fo:simple-page-master master-name="simpleA4" page-height="29.7cm"
		    page-width="21cm" margin-top="2cm" margin-bottom="2cm" 
         margin-left="2cm" margin-right="2cm">
          <fo:region-body/>
        </fo:simple-page-master>
      </fo:layout-master-set>
      <fo:page-sequence master-reference="simpleA4">
        <fo:flow flow-name="xsl-region-body">
          <fo:block font-size="16pt" font-weight="bold" space-after="5mm">
             Catalog:  <xsl:value-of select="@title"/>
          </fo:block>
 <fo:block font-size="16pt" font-weight="bold" space-after="5mm">
             Publisher:  <xsl:value-of select="@publisher"/>
          </fo:block>
          <fo:block font-size="10pt">
           

 <fo:table table-layout="fixed">
              <fo:table-column column-width="4cm"/>
              <fo:table-column column-width="4cm"/>
              <fo:table-column column-width="5cm"/>
            <fo:table-header>
<fo:table-row font-weight="bold"><fo:table-cell>
        <fo:block>
          <xsl:text>Edition</xsl:text>
        </fo:block>
      </fo:table-cell>
 <fo:table-cell>
        <fo:block>
          <xsl:text>Title</xsl:text>
        </fo:block>
      </fo:table-cell>
<fo:table-cell>
        <fo:block>
          <xsl:text>Author</xsl:text>
        </fo:block>
      </fo:table-cell>
 </fo:table-row>

</fo:table-header>

  <fo:table-body>
               <xsl:apply-templates select="journal"/>
              </fo:table-body>
            </fo:table>
          </fo:block>
        </fo:flow>
      </fo:page-sequence>
    </fo:root>
  </xsl:template>
 
 <xsl:template match="journal">   

<xsl:for-each select="article">
 <fo:table-row>
         <fo:table-cell>
        <fo:block>
          <xsl:value-of select="../@edition"/>
        </fo:block>
      </fo:table-cell>
      <fo:table-cell>
        <fo:block>
          <xsl:value-of select="title"/>
        </fo:block>
      </fo:table-cell>
      <fo:table-cell>
        <fo:block>
          <xsl:value-of select="author"/>
        </fo:block>
      </fo:table-cell>
    </fo:table-row>
  </xsl:for-each>


  </xsl:template>
</xsl:stylesheet>

