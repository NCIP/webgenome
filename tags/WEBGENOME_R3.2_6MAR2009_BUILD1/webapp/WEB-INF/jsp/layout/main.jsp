<%--L
   Copyright RTI International

   Distributed under the OSI-approved BSD 3-Clause License.
   See http://ncip.github.com/webgenome/LICENSE.txt for details.
L--%>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/webgenome.tld" prefix="webgenome" %>

<%@ page import="org.rti.webgenome.util.SystemUtils" %>
<%@ page import="java.util.GregorianCalendar" %>

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
					"width=620, height=400, menubar=no, status=no, "
					+ "scrollbars=yes, resizable=yes, toolbar=no, location=no, "
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
				window.setInterval("updateJobStatus()",
				<%= SystemUtils.getApplicationProperty("job.polling.interval") %>);
			</webgenome:onlyIfLoggedInAndStandAloneMode>
			
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
					    		if (anyJobsCompleted(xmlDoc)) {
					    			showJobCompletionMessage(xmlDoc);
					    		}
					    	}
				    	}
				    }
				    xmlHttp.open("GET",
				    	"<html:rewrite page="/ajax/newlyChangedJobs.do"/>",
				    	true);
				    xmlHttp.send(null);
				}
				return false;
			}
			
			// Determines if any jobs have completed
			function anyJobsCompleted(doc) {
				var completed = false;
				var elements = doc.getElementsByTagName("update");
				for (var i = 0; i < elements.length && !completed; i++) {
					var element = elements.item(i);
					var id = element.getAttribute("elementId");
					if (id.indexOf("_endDate") >= 0) {
						completed = true;
					}	
				}
				return completed;
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
					var msg = document.getElementById("jobCompletionMessage");
					msg.style.display = "inline";
				}
			}
	
			// Close job completion message.
			function closeMessage() {
				var msg = document.getElementById("jobCompletionMessage");
				msg.style.display = "none";
			}
		</script>
		
	</head>


	<body onUnload="onLeave();" style="background-color: #FFFFFF;">
	<table width="100%" height="100%" cellpadding="0" cellspacing="0" border="0">

	<%-- Left portion of UI and header --%>
	<% if((request.getParameter("makePopUp") == null) || (request.getParameter("makePopUp") == "")) { %>
		<tr>
			<td rowspan="4" align="right" valign="top" background="<html:rewrite page="/images/ui-body-side-tile.gif"/>"><table height="100%" cellpadding="0" cellspacing="0" border="0" align="right"><tr><td background="<html:rewrite page="/images/ui-body-left.gif"/>"><img src="<html:rewrite page="/images/spacer.gif"/>" width="9" height="1" border="0"></td></tr></table></td>
			<td height="71" align="left" valign="top" background="<html:rewrite page="/images/ui-title-tile.gif"/>">
				<a class="headerLink" href="<html:rewrite page="/home.do"/>"><img src="<html:rewrite page="/images/ui-title.jpg"/>" width="444" height="71" border="" alt="webGenome"></a>
			</td>
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
					<a class="menuItem" href="<html:rewrite page="/upload/initializeUploadForm.do"/>">
						Import Data
					</a>
					|
				</webgenome:onlyIfLoggedInAndStandAloneMode>
				
			<%-- caArray upload --%>
				<webgenome:onlyIfLoggedInAndStandAloneMode>
					<a class="menuItem" href="<html:rewrite page="/upload/initcaArrayUpload.do"/>">
						Import caArray Data
					</a>
					|
				</webgenome:onlyIfLoggedInAndStandAloneMode>
					
			<%-- Login --%>
				<webgenome:onlyIfUserLoggedOut>
					<a class="menuItem" href="<html:rewrite page="/user/login.do"/>">
						Login
					</a>
					|
					<a class="menuItem" href="<html:rewrite page="/user/newAccount.do"/>">
						Register
					</a>
				</webgenome:onlyIfUserLoggedOut>

			<%-- Logout --%>
				<webgenome:onlyIfUserLoggedIn>
					<a class="menuItem" href="<html:rewrite page="/user/account.do"/>">
						Edit Account
					</a>
					|
					<a class="menuItem" href="<html:rewrite page="/user/logout.do"/>">
						Logout
					</a>
					|
				</webgenome:onlyIfUserLoggedIn>

			<%-- Help --%>
				<a href="#" class="menuItem" onclick="help('<tiles:getAsString name="helpTopic"/>')">
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
			<td height="69" align="center" valign="middle" background="<html:rewrite page="/images/ui-footer-tile.gif"/>" border="0">
				<table width="100%">
					<tr>
						<td nowrap="nowrap" align="center" valign="middle">
				<span class="footer">				
					Copyright &copy; RTI International, 2003-<% GregorianCalendar currentYear = new GregorianCalendar(); %><%= currentYear.get(GregorianCalendar.YEAR) %>. All rights reserved.
				</span>
						</td>
						<td nowrap="nowrap" align="right" valign="middle">
							<span class="footer" style="padding-left: 10px;">
									<a class="headerLink" href="http://ncicb.nci.nih.gov/NCICB/about/contact_us">Contact Us</a><br/>
									<a class="headerLink" href="http://ncicb.nci.nih.gov/NCICB/support">Application Support</a>
							</span>
						</td>
						<td nowrap="nowrap" align="right" valign="middle" style="padding-right: 20px;">
							<a class="headerLink" href="http://www.cancer.gov/"><img src="<html:rewrite page="/images/logos/footer_nci.gif"/>" width="63" height="31" alt="National Cancer Institute" border="0"></a>
							<a class="headerLink" href="http://www.dhhs.gov/"><img src="<html:rewrite page="/images/logos/footer_hhs.gif"/>" width="39" height="31" alt="Department of Health and Human Services" border="0"></a>
							<a class="headerLink" href="http://www.nih.gov/"><img src="<html:rewrite page="/images/logos/footer_nih.gif"/>" width="46" height="31" alt="National Institutes of Health" border="0"></a>
							<a class="headerLink" href="http://www.firstgov.gov/"><img src="<html:rewrite page="/images/logos/footer_firstgov.gif"/>" width="91" height="31" alt="FirstGov.gov" border="0"></a>
						</td>
					</tr>
				</table>
			</td>
		</tr>
	<% } %>


	</table></body>

</html>