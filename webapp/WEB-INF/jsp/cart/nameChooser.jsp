<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>

<%-- Style sheet --%>
	<link href="<html:rewrite page="/webcgh.css"/>"
		rel="stylesheet" type="text/css" />

<br>

<center>
<html:form action="/cart/nameChange" target="mainwindow"
	onsubmit="window.close();return true;">

	<html:hidden property="id" value="<%= request.getParameter("id") %>"/>
	<html:hidden property="type" value="<%= request.getParameter("type") %>"/>
	
	New Name: &nbsp;&nbsp;
	<html:text property="name"/>

	<p>
		<html:submit value="OK"/>
		&nbsp;&nbsp;&nbsp;
		<input type="button" value="Cancel" onclick="window.close()">
	</p>
</html:form>
</center>