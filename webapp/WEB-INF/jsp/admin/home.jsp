<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>

<h1 align="center">Admin Home</h1>

<center>
<p>
	<html:link action="/admin/loadCytobandsForm">
		Load cytobands
	</html:link>
	&nbsp;&nbsp;|&nbsp;&nbsp;
	<html:link action="/admin/loadGenesForm">
		Load genes
	</html:link>
</p>
</center>