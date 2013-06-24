<%--L
   Copyright RTI International

   Distributed under the OSI-approved BSD 3-Clause License.
   See http://ncip.github.com/webgenome/LICENSE.txt for details.
L--%>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>

<%-- Style sheet --%>
<link href="<html:rewrite page="/webgenome.css"/>" rel="stylesheet" type="text/css" />

<script type="text/javascript" language="JavaScript">
	function validate() {
		var form = document.forms["name.change.form"];
		var field = document.forms["name.change.form"].elements["name"];

		if((field.value.lastIndexOf('&') >= 0) ||
			(field.value.lastIndexOf('=') >= 0) ||
			(field.value.lastIndexOf('#') >= 0) ||
			(field.value.lastIndexOf('\'') >= 0)) {
			alert('Please use only valid characters for the name.\nInvalid characters are:\n& = # \'');
			return false;
		}
		else if(field.value.length <= 0) {
			alert('Please do not leave the name field blank.');
			return false;
		}
		else {
			form.submit();
			window.close();
			return true;
		}
	}
</script>

<br>

<center>
<html:form action="/cart/nameChange" target="mainwindow"
	onsubmit="return validate();">

	<html:hidden property="id" value="<%= request.getParameter("id") %>"/>
	<html:hidden property="type" value="<%= request.getParameter("type") %>"/>
	
	<table cellpadding="5" cellspacing="0" border="0">
		<tr>
			<td class="label">New Name:</td>
			<td class="value"><html:text property="name"/></td>
		</tr>
		<tr>
			<td class="label">&nbsp;</td>
			<td class="value">
				<input type="button" value="OK" onclick="validate()"/>
				&nbsp;&nbsp;&nbsp;
				<input type="button" value="Cancel" onclick="window.close()">
			</td>
		</tr>
	</table>
</html:form>
</center>

<script type="text/javascript" language="JavaScript">
	var focusControl = document.forms["name.change.form"].elements["name"];
	if (focusControl.type != "hidden") {
		focusControl.focus();
	}
</script>