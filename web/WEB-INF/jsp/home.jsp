<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>

<%@ page errorPage="/WEB-INF/jsp/exception/jspException.jsp" %>

<p><br></p>

<table width="750" align="center" border="0">
<tr>
<td width="45%" valign="top">

<h3 class="documentationHeader">Purpose</h3>
<p class="documentationBody">
<span class="logo">webGenome</span> is a tool for plotting and visualizing
microarray-based gene expression and comparative genome hybridization data.
The tool enables users to:
</p>

<ul>
	<li class="documentationBody">Retrieve array experiments from a database</li>
	<li class="documentationBody">Create and manage <it>virtual experiments</it></li>
	<li class="documentationBody">Create and manage <it>analytic pipelines</it></li>
	<li class="documentationBody">Create scatter plots</li>
	<li class="documentationBody">Create annotation plots showing the location of
	annotated genome features in relation to measured DNA copy number</li>
	<li class="documentationBody">Create plots showing regions of loss and gain
	in relation to chromosome ideograms</li>
	<li class="documentationBody">Create plots of specific probes (reporters)</li>
</ul>

<p align="center">
&copy; RTI International, 2005.
</p>

</td>

<td width="10%"></td>

<td width="45%" valign="top">
<h3 class="documentationHeader">Web Browser Requirements</h3>
<ul>
	<li class="documentationBody">
		<b>Recommended Browser:</b> Internet Explorer version 6.0 or higher.  Future
		version of <span class="logo">webGenome</span> will provide better support
		for Mozilla (e.g. Firefox) and other common browsers.
	</li>
	<li class="documentationBody">
		<b>Scalable Vector Graphics
		(SVG) support</b>. Click 
		<html:link action="/svgTest">
			here 
		</html:link>
		to check if your browser supports SVG.  If not, Adobe offers
		a free SVG browser plugin.  
		[<html:link href="http://www.adobe.com/svg/viewer/install/main.html">Download
		</html:link>]
	</li>
</ul>

<h3 class="documentationHeader">Getting Started</h3>
<p class="documentationBody">
Click on the help link (top right) to access a users manual.  The help
system is context sensitive; clicking on the help link displays guidance on system
functions represented on the current screen.
</p>

</td>

</tr>
</table>
