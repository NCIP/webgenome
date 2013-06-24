<%--L
   Copyright RTI International

   Distributed under the OSI-approved BSD 3-Clause License.
   See http://ncip.github.com/webgenome/LICENSE.txt for details.
L--%>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>

<html>
	<head>
		<link href="webcgh.css" rel="stylesheet" type="text/css"/>
		<title>webCGH Administration</title>
	</head>
	
	<body>
	
		<hr>
		
		<h2>webCGH Administration</h2>
		
		<hr>
		
		<!-- Menu bar -->
		<p>
			<html:link action="/admin/showSky">[Sky/M-FISH&CGH]</html:link>&nbsp;&nbsp;
			<html:link action="/admin/showAnnotation">[Annotation]</html:link>&nbsp;&nbsp;
			<html:link action="/admin/showCytobands">[Cytobands]</html:link>&nbsp;&nbsp;
			<html:link action="/admin/showArrays">[Probe Sets]</html:link>&nbsp;&nbsp;
			<html:link action="/profile/logout">[Logout]</html:link>
		</p>
		
		<!-- Page content -->
		<tiles:get name="content"/>
	</body>
</html>