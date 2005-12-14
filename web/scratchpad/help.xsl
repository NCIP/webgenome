<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	
	<xsl:output method="html"/>
	
	<!-- Top level template -->
	<xsl:template match="/help-doc">
	  <html>
	  	<link href="webcgh-help.css" rel="stylesheet" type="text/css"/>
	  	<body>
			<h1 class="docTitle" align="center"><xsl:value-of select="@title"/></h1>
			<h2 class="heading1">Table of Contents</h2>
			
			<!-- TOC -->
			<xsl:for-each select="section">
				<xsl:call-template name="toc"/>
			</xsl:for-each>
			
			<p><br/></p>
			<p><br/></p>
			
			<xsl:for-each select="section">
				<xsl:call-template name="section"/>
			</xsl:for-each>
		</body>
	  </html>
	</xsl:template>
	
	<!-- TOC template -->
	<xsl:template name="toc">
		<xsl:param name="level" select="0"/>
		<xsl:call-template name="indent">
			<xsl:with-param name="level" select="$level"/>
		</xsl:call-template>
		<a href="#{@id}"><xsl:number level="multiple"/> - <xsl:value-of select="@title"/></a><br/>
		<xsl:for-each select="section">
			<xsl:call-template name="toc">
				<xsl:with-param name="level" select="$level + 1"/>
			</xsl:call-template>
		</xsl:for-each>
	</xsl:template>
	
	<xsl:template name="indent">
		<xsl:param name="level" select="0"/>
		<xsl:if test="$level > 0">
			<xsl:text>&#160;&#160;&#160;&#160;&#160;</xsl:text>
			<xsl:call-template name="indent">
				<xsl:with-param name="level" select="$level - 1"/>
			</xsl:call-template>
		</xsl:if>
	</xsl:template>
	
	<!-- Section template -->
	<xsl:template name="section">
		<xsl:param name="level" select="1"/>
		<a name="{@id}"/>
		<span class="heading{$level}"><xsl:number level="multiple"/> - <xsl:value-of select="@title"/></span><br/>
		<p>
			<xsl:apply-templates select="text()|ref|link|prop-list|em|b|p|br"/>
		</p>
		<xsl:for-each select="section">
			<xsl:call-template name="section">
				<xsl:with-param name="level" select="$level+1"/>
			</xsl:call-template>
		</xsl:for-each>
	</xsl:template>
	
	<!-- Reference template -->
	<xsl:template match="ref">
		<a href="#{@id}"><xsl:value-of select="text()"/></a>
	</xsl:template>
	
	<!-- Link template -->
	<xsl:template match="link">
		<a href="{@href}"><xsl:value-of select="@href"/></a>
	</xsl:template>
	
	<!-- Prop list template -->
	<xsl:template match="prop-list">
		<ul>
			<xsl:for-each select="prop">
				<a name="{@id}"/>
				<li>
					<xsl:if test="@refid">
						<a href="#{@refid}">
							<xsl:value-of select="@name"/>
						</a> -
					</xsl:if>
					<xsl:if test="not(@refid)">
						<span class="propName"><xsl:value-of select="@name"/></span> -
					</xsl:if>
					<xsl:apply-templates select="text()|ref|link|prop-list|em|b|p|br"/>
				</li>
			</xsl:for-each>
		</ul>
	</xsl:template>
	
	<!-- HTML stuff -->
	<xsl:template match="em|b|p|br">
		<xsl:element name="{name()}">
			<xsl:apply-templates/>
		</xsl:element>
	</xsl:template>

</xsl:stylesheet>