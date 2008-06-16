<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>

<h1 align="center">Create Account</h1>

<center>
	<html:errors property="global"/>
	<html:form action="/user/createAccount" focus="email">

	<table cellpadding="5" cellspacing="0" border="0">
	
		<%--<tr>
			<td colspan="2" valign="middle" align="center">				
				<font color="red">*</font> Invalid or missing fields. Please correct.  
			</td>			
		</tr>--%>
	
	<%-- Email --%>
		<tr>
			<td valign="middle" align="left">				
				<b></font>Email address:</b><br>				
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
				<b>Password:</b>
			</td>
			<td valign="middle" align="left">
				<html:password property="password"/>
			</td>
		</tr>
		
	<%-- Password confirm --%>
		<tr>
			<td valign="middle" align="left">
				<html:errors property="confirmedPassword"/>
				<b>Confirm password:</b>
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
		
	
		
	
		
		
	<%-- Submit button --%>
		<tr>
			<td colspan="2" valign="middle" align="center">
				<html:submit value="Register"/>
			</td>
		</tr>

	<tr>
			<td colspan="2" valign="middle" align="center">				
				<b>Bolded</b> fields above are required.  
			</td>			
		</tr>
	</table>

		<p>	
			<html:checkbox property="feedbacks"/> <b>I'd like to provide my ideas and feedbacks for WebGenome</b>
				<div style="font-size:10px;color:#888888;"> We are seeking comments from users on how we might improve WebGenome. 
				If you'd be interested in being contacted by us periodically (initially by email),
				please check this box. You will be able to opt-out of this agreement at the time, at your discretion, with no reason
				for opting out required. We anticipate that your involvement could take as little as 5-10 minutes of your time to provide feedbacks to us.</div>								
		</p>		
	</html:form>
</center>