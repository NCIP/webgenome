<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>

<%-- Style sheet --%>
	<link href="<html:rewrite page="/webcgh.css"/>"
		rel="stylesheet" type="text/css" />


<script type="text/javascript" language="JavaScript">
	function validate() {
		var form = document.forms["name.change.form"];
		var field = document.forms["name.change.form"].elements["name"];

		if((field.value.indexOf('&') >= 0) ||
			(field.value.indexOf('=') >= 0) ||
			(field.value.indexOf('#') >= 0) ||
			(field.value.indexOf('\'') >= 0))
			alert('Please use only valid characters for the name.\nInvalid characters are:\n& = # \'');
		else if(field.value.length <= 0)
			alert('Please do not leave the name field blank.');
		else {
			form.submit();
			window.close();
		}
	}
</script>

<br>

<center>
<html:form action="/cart/nameChange" target="mainwindow"
	onsubmit="window.close();return true;">

	<html:hidden property="id" value="<%= request.getParameter("id") %>"/>
	<html:hidden property="type" value="<%= request.getParameter("type") %>"/>
	
	New Name: &nbsp;&nbsp;
	<html:text property="name"/>

	<p>
		<input type="button" value="OK" onclick="validate()"/>
		&nbsp;&nbsp;&nbsp;
		<input type="button" value="Cancel" onclick="window.close()">
	</p>
</html:form>
</center>

<script type="text/javascript" language="JavaScript">
	var focusControl = document.forms["name.change.form"].elements["name"];
	if (focusControl.type != "hidden") {
		focusControl.focus();
	}
</script>