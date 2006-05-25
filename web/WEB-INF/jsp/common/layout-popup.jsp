<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>

<%@ page import="org.rti.webcgh.webui.util.AttributeManager" %>
<%@ page errorPage="/WEB-INF/jsp/exception/jspException.jsp" %>

<tiles:importAttribute name="helpTopic" scope="request"/>

<html>
	<head>
		<title><tiles:getAsString name="title"/></title>
		<link href="<html:rewrite page="/webcgh.css"/>" rel="stylesheet" type="text/css" />
		<script language="JavaScript">

			var helpPage = "<html:rewrite page="/html/help.htm"/>";
		
			function help(topic) {
				window.open(
					helpPage + "#" + topic,
					"_blank", 
					"width=400, height=300, menubar=no, status=no, scrollbars=yes, resizable=yes, toolbar=yes, location=no, directories=no"
				)
			}
			
			window.self.name = "mainwindow";
				
		</script>
		
	</head>

	<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
	<table width="100%" height="100%" cellpadding="0" cellspacing="0" border="0">

		<!-- Header -->
		<tr><td height="50">
			<table width="100%" height="50" cellpadding="0" cellspacing="0" border="0">
			<tr>
				<td width="474" height="50"><html:img width="474" height="50" page="/images/title-popup.jpg"/></td><td bgcolor="#ffffff">&nbsp;</td>
			</tr>
			</table>
		</td></tr>

		
		<!-- Help and Login -->
		<tr><td height="14" background="<html:rewrite page="/images/titlebar-tile.jpg"/>"><html:img page="/images/spacer.gif" height="14"/></td>
		</tr>

		
		
		<!-- Menu -->
<tr><td height="100%">
	<table width="100%" height="100%" cellpadding="0" cellspacing="0" border="0">
	<tr>
		<td valign="top" align="center">
	<!-- Navigation map -->		<tiles:get name="navigationMap"/>
	<!-- Page content -->		<tiles:get name="content"/>
		</td>
	</tr>
	<tr>
		<td height="20" valign="bottom" align="center">
		<small><i>Copyright &copy; RTI International, 2005-2006</i></small>
		</td>
	</tr>
	</table>
</td></tr>

</td></tr></table>

</body>
</html>