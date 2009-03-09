<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>

<h1 align="center">Forgot Password</h1>

<center>
	<html:errors property="global"/>
	<html:form action="/user/forgotPassword" focus="email">
	
	<table cellpadding="5" cellspacing="0" border="0" style="margin-top: 10px;">
	
	<%-- Instructions --%>		
		<tr>
			<td class="label">&nbsp;</td>
			<td>
				<p style="padding-top: 10px; padding-bottom: 10px;">
				Please enter the email address that you registered with.</br>
				We will send a password reminder to that address.
				</p>
			</td>
		</tr>
	
	<%-- Email --%>
		<tr>
			<td class="label">				
				Email address:				
			</td>
			<td valign="middle" align="left">
				<html:text property="email" size="30" maxlength="200"/>
			</td>
		</tr>
		
		
	<%-- Submit button --%>
		<tr>
			<td class="label">&nbsp;</td>
			<td valign="middle" align="left">
				<html:submit value="Email Password"/>
			</td>
		</tr>


	</table>


	</html:form>
</center>