<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>

<%@ page errorPage="/WEB-INF/jsp/exception/jspException.jsp" %>

<html>
	<head>
		<title><tiles:getAsString name="title"/></title>
	</head>

	<body>
	
		<p><br></p>
		<p><br></p>
		<p><br></p>
		
		<table width="40%" align="center" border="3" bgcolor="navy"/>
			<tr>
				<td>
					<font color="#FFFFFF">
						<center>
							<b>
								<tiles:getAsString name="errorHeader"/>
							</b>
						</center>
					</font>
				</td>
			</tr>
			<tr>
				<td>
					<table border="0" bgcolor="silver" width="100%">
						<tr>
							<td>
								<tiles:get name="content"/>
							</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td align="center">
					<input type="button" value="OK" onclick="window.location='<html:rewrite page="/home.do"/>'"></input>
				</td>
			</tr>
		</table>
	</body>
</html>