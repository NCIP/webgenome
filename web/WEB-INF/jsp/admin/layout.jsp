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
			<html:link action="/showSky">[Sky/M-FISH&CGH]</html:link>&nbsp;&nbsp;
			<html:link action="/showAnnotation">[Annotation]</html:link>&nbsp;&nbsp;
			<html:link action="/showCytobands">[Cytobands]</html:link>&nbsp;&nbsp;
			<html:link action="/showProbeSets">[Probe Sets]</html:link>&nbsp;&nbsp;
			<html:link action="/profile/logout">[Logout]</html:link>
		</p>
		
		<!-- Page content -->
		<tiles:get name="content"/>
	</body>
</html>