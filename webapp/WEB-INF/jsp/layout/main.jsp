<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/webgenome.tld" prefix="webgenome" %>

<tiles:importAttribute name="selectedMenuItem" scope="request"/>
<tiles:importAttribute name="helpTopic" scope="request"/>

<html>

	<head>
	
	<%-- Page title --%>
		<title><tiles:getAsString name="title"/></title>
		
	<%-- Style sheet --%>
		<link href="<html:rewrite page="/webgenome.css"/>"
			rel="stylesheet" type="text/css" />
			
	<%-- Show help and onLeave Javascript functions --%>
		<script language="JavaScript">
			var helpPage = "<html:rewrite page="/help/help.htm"/>";

			function help(topic) {
				window.open(
					helpPage + "#" + topic,
					"_blank", 
					"width=450, height=300, menubar=no, status=no, "
					+ "scrollbars=yes, resizable=yes, toolbar=yes, location=no, "
					+ "directories=no"
				)
			}

			<% if((request.getParameter("makePopUp") == null) || (request.getParameter("makePopUp") == "")) { %>

				function onLeave() {}
				window.self.name = "mainwindow";

			<% } else { %>

				function onLeave() {
					if(self.window.name != "mainwindow") {
						setTimeout("window.close()",500);
					}
				}
				window.self.name = "popupwindow";

			<% } %>
		</script>
		
		<%--
		==================================================
		  The following scipts use AJAX to check for
		  recently completed compute jobs and notify
		  the user.
		==================================================
		--%>
		<script language="Javascript">
		
			<%--
			If user logged in, continually poll for recently
			completed jobs.
			--%>
			<webgenome:onlyIfLoggedInAndStandAloneMode>
				window.setInterval("updateJobStatus()", 5000);
			</webgenome:onlyIfLoggedInAndStandAloneMode>
			
			var messageLeftPix;
			var intervalId;
			
			// Get XMLHttp object for AJAX calls
			function getXmlHttpObject() {
				var xmlHttp;
			  	try {
			    	xmlHttp = new XMLHttpRequest();
			    } catch (e) {
			    	try {
			    		xmlHttp = new ActiveXObject("Msxml2.XMLHTTP");
			    	} catch (e) {
			    		try {
			    			xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
			    		} catch (e) {
			    			// Do nothing
			    		}
			    	}
			    }
			    return xmlHttp;
			}
			
			// Update job statuses in jobs table JSP using AJAX.
			// If the user is not on this page, nothing happens
			// client-side.
			function updateJobStatus() {
				var xmlHttp = getXmlHttpObject();
				if (xmlHttp != null) {
				    xmlHttp.onreadystatechange=function() {
				    	if(xmlHttp.readyState == 4) {
				    		var xmlDoc = xmlHttp.responseXML.documentElement;
				    		if (xmlDoc != null) {
					    		updateTable(xmlDoc);
					    		showJobCompletionMessage(xmlDoc);
					    	}
				    	}
				    }
				    xmlHttp.open("GET",
				    	"<html:rewrite page="/ajax/newlyCompletedJobs.do"/>",
				    	true);
				    xmlHttp.send(null);
				}
				return false;
			}
		
			// Parse server response and update HTML table on the
			// jobs JSP, if it is showing.
			function updateTable(doc) {
				var elements = doc.getElementsByTagName("update");
				for (var i = 0; i < elements.length; i++) {
					var element = elements.item(i);
					var id = element.getAttribute("elementId");
					var value = element.getAttribute("elementValue");
					var td = document.getElementById(id);
					if (td != null) {
						var text = td.firstChild;
						text.nodeValue = value;
						td.className = "finishedJob";
					}
				}
			}

			// Show message that jobs have completed, if any
			// jobs have in fact been newly completed.  This will
			// be displayed on all screens.
			function showJobCompletionMessage(doc) {
				if (doc.getElementsByTagName("update").length > 0) {
					closeMessage();
					intervalId = setInterval("moveMessageRight()", 3);
				}
			}
	
			// Move job complation message in from left side of the
			// screen a pixel at a time.
			function moveMessageRight() {
				messageLeftPix++;
				if (messageLeftPix >= 0) {
					clearInterval(intervalId);
				} else {
					var msg = document.getElementById("jobCompletionMessage");
					msg.style.left = messageLeftPix;
				}
			}
	
			// Close job completion message.
			function closeMessage() {
				messageLeftPix = -400;
				var msg = document.getElementById("jobCompletionMessage");
				msg.style.left = messageLeftPix;
			}
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

			<%-- Admin --%>

				<webgenome:onlyIfAdmin>
					<a class="menuItem" href="<html:rewrite page="/admin/home.do"/>">
						Admin
					</a>
					|
				</webgenome:onlyIfAdmin>

			<%-- Workspace --%>
				<webgenome:onlyIfShoppingCartExists>
					<a class="menuItem" href="<html:rewrite page="/cart/showCart.do"/>">
						Workspace
					</a>
					|
				</webgenome:onlyIfShoppingCartExists>

			<%-- Jobs table --%>
				<webgenome:onlyIfLoggedInAndStandAloneMode>
					<a class="menuItem" href="<html:rewrite page="/cart/showJobs.do"/>">
						Jobs Table
					</a>
					|
				</webgenome:onlyIfLoggedInAndStandAloneMode>
				
			<%-- File upload --%>
				<webgenome:onlyIfLoggedInAndStandAloneMode>
					<html:link action="/cart/uploadForm" styleClass="menuItem">
						Upload File
					</html:link>
					|
				</webgenome:onlyIfLoggedInAndStandAloneMode>
			<%-- Login --%>
				<webgenome:onlyIfUserLoggedOut>
					<a class="menuItem" href="<html:rewrite page="/user/login.do"/>">
						Login
					</a>
					|
				</webgenome:onlyIfUserLoggedOut>

			<%-- Profile --%>
				<webgenome:onlyIfUserLoggedIn>
					<a class="menuItem" href="javascript:void(0);">
						My Profile
					</a>
					|
				</webgenome:onlyIfUserLoggedIn>

			<%-- Logout --%>
				<webgenome:onlyIfUserLoggedIn>
					<a class="menuItem" href="<html:rewrite page="/user/logout.do"/>">
						Logout
					</a>
					|
				</webgenome:onlyIfUserLoggedIn>

			<%-- Help --%>
				<a href="#" class="menuItem" 
						onclick="help('<tiles:getAsString name="helpTopic"/>')">
						Help
				</a>
			</div></td>
		</tr>
	<% } else {%>
		<tr>
			<td height="26" align="right" valign="top" background="<html:rewrite page="/images/ui-menu-tile.gif"/>"><div class="menu">

			<%-- Help --%>
				<a href="#" class="menuItem" 
						onclick="help('<tiles:getAsString name="helpTopic"/>')">
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

				<%-- Job completion message, which is initially off the screen --%>
				<table id="jobCompletionMessage" class="message">
					<tr>
						<td>One or more jobs have completed
						&nbsp;
						<a href="#" onclick="closeMessage()" class="transientLink">[ok]</a></td>
					</tr>
				</table>
		
				<%-- Content tile --%>
				<div class="contentItem">
					<tiles:get name="content"/><br>
				</div>

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