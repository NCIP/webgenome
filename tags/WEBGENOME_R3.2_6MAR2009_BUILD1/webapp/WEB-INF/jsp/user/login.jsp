<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>

<h1 align="center">Login</h1>

<center>
	<html:errors property="global"/>
	<html:form action="/user/validateLogin" focus="name">

	<table cellpadding="5" cellspacing="0" border="0" style="margin-top: 10px;">

	<%-- User name --%>
		<tr>
			<td class="label">
				<html:errors property="name"/>
				Email Address:
			</td>
			<td class="value">
				<html:text property="name" size="30" maxlength="200"/>
			</td>
		</tr>

	<%-- Password --%>
		<tr>
			<td class="label">
				<html:errors property="password"/>
				Password:
			</td>
			<td class="value">
				<html:password property="password" size="30" maxlength="200"/>
			</td>
		</tr>

	<%-- Submit button --%>
		<tr>
			<td class="label">&nbsp;</td>
			<td class="value">
				<html:submit value="Login"/>
			</td>
		</tr>

	</table>
	</html:form>
	
	<p>
		<html:link action="/user/newAccount">Register</html:link>
	</p>
	<p>
		<html:link action="/user/showForgotPassword">Forgot Password</html:link>
	</p>
	
</center>