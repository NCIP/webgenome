<%--L
   Copyright RTI International

   Distributed under the OSI-approved BSD 3-Clause License.
   See http://ncip.github.com/webgenome/LICENSE.txt for details.
L--%>

<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>

<%@ page errorPage="/WEB-INF/jsp/exception/jspException.jsp" %>

<html>

	<head>
	
	<%-- Page title --%>
		<title><tiles:getAsString name="title"/></title>

	<%-- Style sheet --%>
		<link href="<html:rewrite page="/webcgh.css"/>"
			rel="stylesheet" type="text/css" />

	<%-- Show help and onLeave Javascript functions --%>
		<script language="JavaScript">
			var helpPage = "<html:rewrite page="/help/help.htm"/>";

			function help(topic) {
				window.open(
					helpPage + "#" + topic,
					"_blank", 
					"width=400, height=300, menubar=no, status=no, scrollbars=yes, resizable=yes, toolbar=yes, location=no, directories=no"
				)
			}

			<% if((request.getParameter("makePopUp") == null) || (request.getParameter("makePopUp") == "")) { %>

				function onLeave() {}
				window.self.name = "mainwindow";

			<% } else { %>

				function onLeave() {
					if(self.window.name != "mainwindow") {
						window.close();
					}
				}
				window.self.name = "popupwindow";

			<% } %>
		</script>
	</head>

	<body onUnload="onLeave();"><table width="100%" height="100%" cellpadding="0" cellspacing="0" border="0">


	<%-- Left portion of UI and header --%>
	<% if((request.getParameter("makePopUp") == null) || (request.getParameter("makePopUp") == "")) { %>
		<tr>
			<td rowspan="4" align="right" valign="top" background="<html:rewrite page="/images/ui-body-side-tile.gif"/>"><table height="100%" cellpadding="0" cellspacing="0" border="0" align="right"><tr><td background="<html:rewrite page="/images/ui-body-left.gif"/>"><img src="<html:rewrite page="/images/spacer.gif"/>" width="9" height="1" border="0"></td></tr></table></td>
			<td height="71" align="left" valign="top" background="<html:rewrite page="/images/ui-title-tile.gif"/>"><img src="<html:rewrite page="/images/ui-title.jpg"/>" width="444" height="71" border="0"></td>
			<td rowspan="4" align="left" valign="top" background="<html:rewrite page="/images/ui-body-side-tile.gif"/>"><table height="100%" cellpadding="0" cellspacing="0" border="0" align="left"><tr><td background="<html:rewrite page="/images/ui-body-right.gif"/>"><img src="<html:rewrite page="/images/spacer.gif"/>" width="9" height="1" border="0"></td></tr></table></td>
		</tr>

	
	<%-- Menu --%>
		<tr>
			<td height="26" align="right" valign="top" background="<html:rewrite page="/images/ui-menu-tile.gif"/>"><div class="menu">

			<%-- Home --%>
				<a class="menuItem" href="<html:rewrite page="/home.do"/>">
					Home
				</a>
				|

			<%-- Help --%>
				<a class="menuItem" href="javascript:void(0);">
					Help
				</a>
			</div></td>
		</tr>
	<% } else {%>
		<tr>
			<td height="26" align="right" valign="top" background="<html:rewrite page="/images/ui-menu-tile.gif"/>"><div class="menu">

			<%-- Help --%>
				<a class="menuItem" href="javascript:void(0);">
					Help
				</a>
				|
			<%-- Close --%>
				<a class="menuItem" href="javascript:window.close();">
					Close
				</a>

			</div></td>
		</tr>
	<% } %>


	<%-- Page content --%>
		<tr>
			<td class="content" align="left" valign="top" background="<html:rewrite page="/images/ui-body-tile.gif"/>" width="800">

				<h1 class="h1icon"><tiles:getAsString name="errorHeader"/></h1>

				<div class="exceptionItem">
					<tiles:get name="content"/><br>
				</div>

				<p><a href="<html:rewrite page="/home.do"/>">Click to continue</a></p>

			</td>
		</tr>


	<%-- Right portion of UI and footer --%>
	<% if((request.getParameter("makePopUp") == null) || (request.getParameter("makePopUp") == "")) { %>
		<tr>
			<td height="39" align="center" valign="center" background="<html:rewrite page="/images/ui-footer-tile.gif"/>" border="0">
				<span class="footer">
					Copyright &copy; RTI International, 2005-2006. All rights reserved.
				</span>
			</td>
		</tr>
	<% } %>


	</table></body>

</html>