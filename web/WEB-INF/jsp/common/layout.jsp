<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>

<%@ page import="org.rti.webcgh.webui.util.AttributeManager" %>
<%@ page errorPage="/WEB-INF/jsp/exception/jspException.jsp" %>

<tiles:importAttribute name="selectedMenuItem" scope="request"/>
<tiles:importAttribute name="selectedSubMenuItem" scope="request"/>
<tiles:importAttribute name="navigationItem" scope="request"/>
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
		<tr><td height="84">
			<table width="100%" height="84" cellpadding="0" cellspacing="0" border="0">
			<tr>
				<td width="834" height="84"><html:img width="834" height="84" page="/images/title.jpg"/></td><td background="<html:rewrite page="/images/title-tile.jpg"/>">&nbsp;</td>
			</tr>
			</table>
		</td></tr>

		
		<!-- Help and Login -->
		<tr><td height="27">
			<table width="100%" height="27" cellpadding="0" cellspacing="0" border="0">
			<tr>
				<logic:present name="<%= AttributeManager.USER_PROFILE %>">
					<td valign="top" align="right" background="<html:rewrite page="/images/titlebar-tile.jpg"/>" height="27">
						<span class="smallInfoMsg">
							<bean:write name="<%= AttributeManager.USER_PROFILE %>" property="name"/>
							logged in
						</span>
					</td>
					<td valign="top" align="right" background="<html:rewrite page="/images/titlebar-tile.jpg"/>" width="35"><html:img page="/images/titlebar-split.jpg" width="35" height="27"/></td>
					<td valign="top" align="center" background="<html:rewrite page="/images/titlebar-tile.jpg"/>" width="50"
						><html:link action="/profile/logout" styleClass="menu">
							Logout
						</html:link></td
					>
				</logic:present>
				<logic:notPresent name="<%= AttributeManager.USER_PROFILE %>">
					<td valign="top" align="right" background="<html:rewrite page="/images/titlebar-tile.jpg"/>" height="27"
						><html:link action="/profile/loginPage" styleClass="menu">
							Login
						</html:link></td
					>
				</logic:notPresent>

				<!--
				<td valign="top" align="right" background="<html:rewrite page="/images/titlebar-tile.jpg"/>" width="35"><html:img page="/images/titlebar-split.jpg" width="35" height="27"/></td>
				<td valign="top" align="left" background="<html:rewrite page="/images/titlebar-tile.jpg"/>" width="100">
					<html:link styleClass="menu" href="http://caarraydb.nci.nih.gov">
						Submit Data
					</html:link>
				</td>
				-->

				<td valign="top" align="right" background="<html:rewrite page="/images/titlebar-tile.jpg"/>" width="35"><html:img page="/images/titlebar-split.jpg" width="35" height="27"/></td>
				<td valign="top" align="left" background="<html:rewrite page="/images/titlebar-tile.jpg"/>" width="50">
					<a href="#" class="menu" 
						onclick="window.open('<html:rewrite page="/html/help.htm"/>#<tiles:getAsString name="helpTopic"/>', '_blank', 'width=400, height=300, menubar=no, status=no, scrollbars=yes, resizable=yes, toolbar=yes, location=no, directories=no');">
						Help
					</a>
				</td>
			</tr>
			</table>
		</td></tr>

		
		
		<!-- Menu -->
<tr><td height="100%">
	<table width="100%" height="100%" cellpadding="0" cellspacing="0" border="0">
	<tr>
		<td valign="top" background="<html:rewrite page="/images/leftbar-tile.jpg"/>" width="36"><html:img page="/images/leftbar.jpg" width="36" height="688"/></td>
		<td valign="top">
			<table width="100%" height="100%" cellpadding="0" cellspacing="0" border="0">
			<tr>
				<td height="58" background="<html:rewrite page="/images/menu1-tile.jpg"/>"><table cellpadding="0" cellspacing="0" border="0"><tr>

				<!-- Overview -->
					<td><html:link styleClass="menu" action="/home"><img border="0"
						<logic:equal name="selectedMenuItem" value="overview">
							src="<html:rewrite page="/images/menu-overview-on.jpg"/>"
						</logic:equal>
						<logic:notEqual name="selectedMenuItem" value="overview">
							src="<html:rewrite page="/images/menu-overview-off.jpg"/>"
						</logic:notEqual>
					></html:link></td>

				<!-- Plot Data -->
					<td><html:link styleClass="menu" action="/cart/contents"><img border="0"
						<logic:equal name="selectedMenuItem" value="plot">
							src="<html:rewrite page="/images/menu-plotdata-on.jpg"/>"
						</logic:equal>
						<logic:notEqual name="selectedMenuItem" value="plot">
							src="<html:rewrite page="/images/menu-plotdata-off.jpg"/>"
						</logic:notEqual>
					></html:link></td>

				<!-- Virtual Experiments -->
					<td><logic:present name="<%= AttributeManager.USER_PROFILE %>"><html:link styleClass="menu" action="/virtual/list"><img border="0"
							<logic:equal name="selectedMenuItem" value="virtual">
								src="<html:rewrite page="/images/menu-virtualexperiments-on.jpg"/>"
							</logic:equal>
							<logic:notEqual name="selectedMenuItem" value="virtual">
								src="<html:rewrite page="/images/menu-virtualexperiments-off.jpg"/>"
							</logic:notEqual>
					></html:link></logic:present></td>

				<!-- Analytic Pipelines -->
					<td><html:link styleClass="menu" action="/pipeline/show"><img border="0"
						<logic:equal name="selectedMenuItem" value="pipelines">
							src="<html:rewrite page="/images/menu-analyticpipelines-on.jpg"/>"
						</logic:equal>
						<logic:notEqual name="selectedMenuItem" value="pipelines">
							src="<html:rewrite page="/images/menu-analyticpipelines-off.jpg"/>"
						</logic:notEqual>
					></html:link></td>

				</tr></table></td>
			</tr>
			<tr>
				<td height="36" valign="center" align="center" background="<html:rewrite page="/images/menu2-tile.jpg"/>">
					<font face="arial"><b>
	<!-- Sub-menu -->			<tiles:get name="submenu"/>
					</b></font>
				</td>
			</tr>
			<tr>
				<td height="21" background="<html:rewrite page="/images/menushadow-tile.jpg"/>"><html:img page="/images/spacer.gif" width="1" height="1"/></td>
			</tr>
			<tr>
				<td valign="top" align="center">
	<!-- Navigation map -->		<tiles:get name="navigationMap"/>
	<!-- Page content -->		<div class="contentItem"><tiles:get name="content"/><br></div>
				</td>
			</tr>
			<tr>
				<td height="20" valign="bottom" align="center">
				<small><i>Copyright &copy; RTI International, 2005-2006</i></small>
				</td>
			</tr>
			</table>
		</td>
		<td valign="top" background="<html:rewrite page="/images/rightbar-tile.jpg"/>" width="36"><html:img page="/images/rightbar.jpg" width="36" height="688"/></td>
	</tr>
	</table>
</td></tr>

</td></tr></table>

</body>
</html>