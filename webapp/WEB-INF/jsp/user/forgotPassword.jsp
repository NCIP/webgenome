<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>

<h1 align="center">Forgot Password</h1>

<center>
	<html:errors property="global"/>
	<html:form action="/user/forgotPassword" focus="email">

	<table cellpadding="5" cellspacing="0" border="0">
	
		
	
	<%-- Email --%>
		<tr>
			<td valign="middle" align="left">				
				<font color="red">*</font>Email address:<br>				
			</td>
			<td valign="middle" align="left">
				<html:text property="email"/>
			</td>
		</tr>
		
		
	<%-- Submit button --%>
		<tr>
			<td colspan="2" valign="middle" align="center">
				<html:submit value="OK"/>
			</td>
		</tr>

	</table>

	</html:form>
</center>