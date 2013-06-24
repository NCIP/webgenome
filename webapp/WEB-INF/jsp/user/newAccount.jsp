<%--L
   Copyright RTI International

   Distributed under the OSI-approved BSD 3-Clause License.
   See http://ncip.github.com/webgenome/LICENSE.txt for details.
L--%>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>

<h1 align="center">Login</h1>

<center>
	<html:errors property="global"/>
	<html:form action="/user/createAccount">

	<table cellpadding="5" cellspacing="0" border="0">
	
	<%-- User name --%>
		<tr>
			<td valign="middle" align="left">
				<html:errors property="name"/>
				User name:
			</td>
			<td valign="middle" align="left">
				<html:text property="name"/>
			</td>
		</tr>
		
	<%-- Password --%>
		<tr>
			<td valign="middle" align="left">
				<html:errors property="password"/>
				Password:
			</td>
			<td valign="middle" align="left">
				<html:password property="password"/>
			</td>
		</tr>
		
	<%-- Password confirm --%>
		<tr>
			<td valign="middle" align="left">
				<html:errors property="confirmedPassword"/>
				Confirm password:
			</td>
			<td valign="middle" align="left">
				<html:password property="confirmedPassword"/>
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