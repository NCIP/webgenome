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
				
		</script>
		
	</head>

	<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
	
		<!-- Header -->
		<table width="100%" height="54" border="0" cellpadding="0" cellspacing="0" background="<html:rewrite page="/images/title-bg.gif"/>">
  			<tr>
    			<td>
    				<html:img page="/images/title.gif" width="185" height="54" />
    			</td>
  			</tr>
		</table>
		
		<!-- Help and Login -->
		<table width="95%" height="10" border="0" cellpadding="0" 
			cellspacing="0">
			<tr class="menu">
				<td align="right">
					<logic:present name="<%= AttributeManager.USER_PROFILE %>">
						<span class="smallInfoMsg">
							<bean:write name="<%= AttributeManager.USER_PROFILE %>" property="name"/>
							logged in
						</span>
						&nbsp;&nbsp;&nbsp;
						<html:link action="/profile/logout" styleClass="menu">
							Logout
						</html:link>
					</logic:present>
					<logic:notPresent name="<%= AttributeManager.USER_PROFILE %>">
						<html:link action="/profile/loginPage" styleClass="menu">
							Login
						</html:link>
					</logic:notPresent>
					<!--
					&nbsp;&nbsp;&nbsp;
					<html:link styleClass="menu" href="http://caarraydb.nci.nih.gov">
						Submit Data
					</html:link>
					-->
					&nbsp;&nbsp;&nbsp;
					<a href="#" class="menu" 
						onclick="window.open('<html:rewrite page="/html/help.htm"/>#<tiles:getAsString name="helpTopic"/>', '_blank', 'width=400, height=300, menubar=no, status=no, scrollbars=yes, resizable=yes, toolbar=yes, location=no, directories=no');">
						Help
					</a>
				</td>
			</tr>
		</table>
		
		<table width="100%" height="2" border="0" cellpadding="0" 
			cellspacing="0" bgcolor="#eeeedd"">
			<tr><td></td></tr>
		</table>
		
		<p></p>
		
		<!-- Menu -->
		<table height="20" border="0" cellpadding="0" cellspacing="0" bgcolor="white" align="center">
  			<tr height="100%"> 
    			<td align="center">
    			
    				<table align="middle" valign="bottom" border="0" cellpadding="0" cellspacing="0" height="100%">
    					<tr>
  
    						<!-- Overview -->
    						<td align="center" valign="middle" width="100"
    							<logic:equal name="selectedMenuItem" value="overview">
    								class="folderTab-selected"
    							</logic:equal>
    							<logic:notEqual name="selectedMenuItem" value="overview">
    								class="folderTab"
    							</logic:notEqual>
    						>
      							<html:link styleClass="menu" 
      								action="/home">Overview</html:link>
      						</td>
      						
      						<td width="25"></td>
      						      						
      						<!-- Plot Data -->
    						<td align="center" valign="middle" width="100"
    							<logic:equal name="selectedMenuItem" value="plot">
    								class="folderTab-selected"
    							</logic:equal>
    							<logic:notEqual name="selectedMenuItem" value="plot">
    								class="folderTab"
    							</logic:notEqual>
    						>
      							<html:link styleClass="menu" 
      								action="/cart/contents">Plot Data</html:link>
      						</td>
      						
      						<logic:present name="<%= AttributeManager.USER_PROFILE %>">
	      						<td width="25"></td>
	      						
	      						<!-- Virtual Experiments -->
	    						<td align="center" valign="middle" width="150"
	    							<logic:equal name="selectedMenuItem" value="virtual">
	    								class="folderTab-selected"
	    							</logic:equal>
	    							<logic:notEqual name="selectedMenuItem" value="virtual">
	    								class="folderTab"
	    							</logic:notEqual>
	    						>
	      							<html:link styleClass="menu" 
	      								action="/virtual/list">
	      								Virtual Experiments
	      							</html:link>
	      						</td>
      						</logic:present>
      						
      						<td width="25"></td>
      						
      						<!-- Analytic Pipelines -->
    						<td align="center" valign="middle" width="150"
    							<logic:equal name="selectedMenuItem" value="pipelines">
    								class="folderTab-selected"
    							</logic:equal>
    							<logic:notEqual name="selectedMenuItem" value="pipelines">
    								class="folderTab"
    							</logic:notEqual>
    						>
      							<html:link styleClass="menu" 
      								action="/showPipelines">
      								Analytic Pipelines
      							</html:link>
      						</td>
						</tr>
					</table>
      			</td>
  			</tr>
		</table>
		
		<!-- Sub-menu -->
		<table width="750" border="0" cellpadding="0" cellspacing="0" 
			align="center" height="30">
			<tr>
				<td class="folderTab-selected" >
					<tiles:get name="submenu"/>
				</td>
			</tr>
		</table>
		
		<table width="100%" height="10"><tr><td></td></tr></table>
		
		<center>
			<tiles:get name="navigationMap"/>
		</center>
		
		<!-- Content area -->
		<table border="0" cellpadding="0" cellspacing="0" width="100%">
			<tr height="100%">
				<td width="20" height="100%"></td>
				
				<td height="100%" valign="top">

					<!-- Page content -->
					<tiles:get name="content"/>
				</td>
			</tr>
		</table>

	</body>
</html>