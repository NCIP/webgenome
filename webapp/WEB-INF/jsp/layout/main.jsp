<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/webcgh.tld" prefix="webcgh" %>

<tiles:importAttribute name="selectedMenuItem" scope="request"/>
<tiles:importAttribute name="helpTopic" scope="request"/>

<html>

	<head>
	
	<%-- Page title --%>
		<title><tiles:getAsString name="title"/></title>
		
	<%-- Style sheet --%>
		<link href="<html:rewrite page="/webcgh.css"/>"
			rel="stylesheet" type="text/css" />
			
	<%-- Show help Javascript function --%>
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

	<body>
	
	<%-- Menu --%>
		<p align="center">
		
		<%-- Home --%>
			[
				<html:link action="/home">
					Home
				</html:link>
			]&nbsp;&nbsp;
			
		<%-- Admin --%>
			
			<webcgh:onlyIfAdmin>
				[
					<html:link action="/admin/home">
					Admin
					</html:link>
				]
			</webcgh:onlyIfAdmin>
			
		<%-- Shopping cart --%>
			<webcgh:onlyIfShoppingCartExists>
				[
					<html:link action="/cart/showCart">
						Shopping Cart
					</html:link>
				]&nbsp;&nbsp;
			</webcgh:onlyIfShoppingCartExists>
			
		<%-- Jobs table --%>
			<webcgh:onlyIfLoggedInAndStandAloneMode>
				[
					<html:link action="/cart/showJobsTable">
						Jobs Table
					</html:link>
				]&nbsp;&nbsp;
			</webcgh:onlyIfLoggedInAndStandAloneMode>
			
		<%-- Login --%>
			<webcgh:onlyIfUserLoggedOut>
				[
					<html:link action="/user/login">
						Login
					</html:link>
				]&nbsp;&nbsp;
			</webcgh:onlyIfUserLoggedOut>
			
		<%-- Profile --%>
			<webcgh:onlyIfUserLoggedIn>
				[
					My Profile
				]&nbsp;&nbsp;
			</webcgh:onlyIfUserLoggedIn>
			
		<%-- Logout --%>
			<webcgh:onlyIfUserLoggedIn>
				[
					<html:link action="/user/logout">
						Logout
					</html:link>
				]&nbsp;&nbsp;
			</webcgh:onlyIfUserLoggedIn>
			
		<%-- Help --%>
			[Help]
		</p>
		<hr>
	
	<%-- Page content --%>
		<div class="contentItem">
			<tiles:get name="content"/><br>
		</div>

	</body>
</html>