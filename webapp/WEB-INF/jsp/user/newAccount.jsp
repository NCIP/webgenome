<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>

<h1 align="center">Create Account</h1>

<center>
	<html:errors property="global"/>
	<html:form action="/user/createAccount" focus="email">

	<table cellpadding="5" cellspacing="0" border="0">
	
		<tr>
			<td colspan="2" valign="middle" align="center">				
				<font color="red">*</font> Required Information  
			</td>			
		</tr>
	
	<%-- Email --%>
		<tr>
			<td valign="middle" align="left">				
				<font color="red">*</font>Email address:<br>				
			</td>
			<td valign="middle" align="left">
				<html:text property="email"/>
			</td>
		</tr>
		
	<%-- Login name --%>
		<%-- Login name is user e-mail<tr>
			<td valign="middle" align="left">
				<html:errors property="name"/>
				<font color="red">*</font>Login name:
			</td>
			<td valign="middle" align="left">
				<html:text property="name"/>
			</td>
		</tr>--%>
		
	<%-- Password --%>
		<tr>
			<td valign="middle" align="left">
				<html:errors property="password"/>
				<font color="red">*</font>Password:
			</td>
			<td valign="middle" align="left">
				<html:password property="password"/>
			</td>
		</tr>
		
	<%-- Password confirm --%>
		<tr>
			<td valign="middle" align="left">
				<html:errors property="confirmedPassword"/>
				<font color="red">*</font>Confirm password:
			</td>
			<td valign="middle" align="left">
				<html:password property="confirmedPassword"/>
			</td>
		</tr>
		
	
		
		
			
			
	<%-- First name --%>
		<tr>
			<td valign="middle" align="left">				
				First name:
			</td>
			<td valign="middle" align="left">
				<html:text property="firstName"/>
			</td>
		</tr>
	
	<%-- Last name --%>
		<tr>
			<td valign="middle" align="left">				
				Last name:
			</td>
			<td valign="middle" align="left">
				<html:text property="lastName"/>
			</td>
		</tr>
	
	<%-- Degree --%>
	<%--	<tr>
			<td valign="middle" align="left">				
				Degree:
			</td>
			<td valign="middle" align="left">
				<html:text property="degree"/>
			</td>
		</tr>--%>
	
	<%-- Institution --%>
		<tr>
			<td valign="middle" align="left">				
				Institution:
			</td>
			<td valign="middle" align="left">
				<html:text property="institution"/>
			</td>
		</tr>
	
	<%-- Department --%>
		<%--<tr>
			<td valign="middle" align="left">				
				Department:
			</td>
			<td valign="middle" align="left">
				<html:text property="department"/>
			</td>
		</tr>--%>
	
	<%-- Position --%>
		<%--<tr>
			<td valign="middle" align="left">				
				Position:
			</td>
			<td valign="middle" align="left">
				<html:text property="position"/>
			</td>
		</tr>--%>
		
	
		
	
		<tr>			
			<td valign="middle" align="left" colspan="2">
				<html:checkbox property="feedbacks"/>Would you like to contribute your feedbacks?
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