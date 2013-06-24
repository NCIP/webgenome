<%--L
   Copyright RTI International

   Distributed under the OSI-approved BSD 3-Clause License.
   See http://ncip.github.com/webgenome/LICENSE.txt for details.
L--%>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>

<h1 align="center">Create Account</h1>

<center>
	<html:errors property="global"/>
	<html:form action="/user/createAccount" focus="email">

	<table cellpadding="5" cellspacing="0" border="0" style="margin-top: 10px;">
	
	<%-- Email --%>
		<tr>
			<td class="label">
				<html:errors property="email"/>				
				<strong>Email address:</strong>				
			</td>
			<td valign="middle" align="left">
				<html:text property="email" size="30" maxlength="200"/>
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
			<td class="label">
				<html:errors property="password"/>
				<strong>Password:</strong>
			</td>
			<td valign="middle" align="left">
				<html:password property="password" size="30" maxlength="200"/>
			</td>
		</tr>
		
	<%-- Password confirm --%>
		<tr>
			<td class="label">
				<html:errors property="confirmedPassword"/>
				<b>Confirm password:</b>
			</td>
			<td valign="middle" align="left">
				<html:password property="confirmedPassword"  size="30" maxlength="200"/>
			</td>
		</tr>
			
	<%-- First name --%>
		<tr>
			<td class="label">				
				First name:
			</td>
			<td valign="middle" align="left">
				<html:text property="firstName" size="30" maxlength="200"/>
			</td>
		</tr>
	
	<%-- Last name --%>
		<tr>
			<td class="label">				
				Last name:
			</td>
			<td valign="middle" align="left">
				<html:text property="lastName" size="30" maxlength="200"/>
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
			<td class="label">				
				Institution:
			</td>
			<td valign="middle" align="left">
				<html:text property="institution" size="30" maxlength="200"/>
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
			<td class="label">&nbsp;</td>
			<td valign="middle" align="left">
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
			<html:checkbox property="feedbacks"/>
				<strong>I'd like to provide my ideas and feedback for WebGenome</strong>
				<div style="font-size:10px;color:#888888;"> We are seeking comments from users on how we might improve WebGenome. 
				If you'd be interested in being contacted by us periodically (initially by email),
				please check this box. You will be able to opt-out of this arrangement at any time, for any or no reason.
				Your involvement would probably take as little as 5-10 minutes of your time.</div>								
		</p>		
	</html:form>
</center>