<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
  <xsl:output encoding="utf-8"/>
  <!--Select all nodes-->
  <xsl:template match="node()|@*">
    <xsl:copy>
      <xsl:apply-templates select="node()|@*"/>
    </xsl:copy>
  </xsl:template>
  <xsl:template match="/catalog[1]/journal[1]/@title"/>
  <xsl:template match="/catalog[1]/journal[1]/@publisher"/>
  <xsl:template match="/catalog[1]/journal[1]/@edition">
    <xsl:attribute name="edition">March-April 2008</xsl:attribute>
  </xsl:template>
  <xsl:template match="/catalog[1]/journal[1]/article[1]/@section">
    <xsl:attribute name="section">Technology</xsl:attribute>
  </xsl:template>
  <xsl:template match="/catalog[1]/journal[1]/article[1]/title[1]">
    <xsl:element name="{name()}">
      <xsl:apply-templates select="@*"/>
      Oracle Database 11g Redux
    </xsl:element>
  </xsl:template>
  <xsl:template match="/catalog[1]/journal[1]/article[1]/author[1]">
    <xsl:element name="{name()}">
      <xsl:apply-templates select="@*"/>
      Tom Kyte
    </xsl:element>
  </xsl:template>
  <xsl:template match="/catalog[1]/journal[2]/@title"/>
  <xsl:template match="/catalog[1]/journal[2]/@publisher"/>
  <xsl:template match="/catalog[1]/journal[2]/@edition">
    <xsl:attribute name="edition">March-April 2008</xsl:attribute>
  </xsl:template>
  <xsl:template match="/catalog[1]/journal[2]/article[1]/title[1]">
    <xsl:element name="{name()}">
      <xsl:apply-templates select="@*"/>
      Declarative Data Filtering
    </xsl:element>
  </xsl:template>
  <xsl:template match="/catalog[1]/journal[2]">
    <xsl:copy>
      <xsl:apply-templates select="node()|@*"/>
    </xsl:copy>
    <journal></journal>
  </xsl:template>
</xsl:stylesheet>
