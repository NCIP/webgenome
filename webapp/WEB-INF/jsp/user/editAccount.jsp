<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>

<h1 align="center">Edit Account</h1>

<center>
	<html:errors property="global"/>
	<html:form action="/user/editAccount" focus="email">

	<table cellpadding="5" cellspacing="0" border="0" style="margin-top: 10px;">
	
	<%-- Email --%>
		<tr>
			<td class="label">
				<html:errors property="email"/>				
				<strong>Email address:</strong>				
			</td>
			<td valign="middle" align="left">
				<html:text name="account" property="email" size="30" maxlength="200"/>
			</td>
		</tr>
		
	<%-- First name --%>
		<tr>
			<td class="label">				
				First name:
			</td>
			<td valign="middle" align="left">
				<html:text name="account" property="firstName" size="30" maxlength="200"/>
			</td>
		</tr>
	
	<%-- Last name --%>
		<tr>
			<td class="label">				
				Last name:
			</td>
			<td valign="middle" align="left">
				<html:text name="account" property="lastName" size="30" maxlength="200"/>
			</td>
		</tr>
	
	<%-- Institution --%>
		<tr>
			<td class="label">				
				Institution:
			</td>
			<td valign="middle" align="left">
				<html:text name="account" property="institution" size="30" maxlength="200"/>
			</td>
		</tr>
		
	<%-- Feedback Setting  --%>
		<tr>
			<td class="label" valign="top">
				Feedback Preference:
			</td>
			<td valign="middle" align="left">
				<html:checkbox name="account" property="feedbacks"/>
				<div style="font-size:10px;color:#888888; margin-left: 8px; vertical-align: middle; display:inline; padding-bottom: 8px;">
				check, if you'd be willing to provide feedback to us<sup>*</sup>
				</div>								
			</td>
		</tr>
	
	<%-- Password --%>
		<tr>
			<td class="label" valign="top">
						<html:errors property="password"/>
						<strong>Current Password:</strong>
			</td>
			<td valign="top" align="left">
				<html:password name="account" property="password" size="30" maxlength="200"/><br/>
			</td>
		</tr>
		<tr style="margin-top:0px; padding-top:0px;">
			<td class="label">&nbsp;</td>
			<td valign="top" style="padding-top:0px; margin-top:0px;">
				<span style="font-size: 95%">You need to enter your current password to confirm any changes.<br/>
				</span>
			</td>
		</tr>
		
	<%-- Submit button --%>
		<tr>
			<td class="label">&nbsp;</td>
			<td valign="middle" align="left">
				<html:submit value="Update Account"/>
				<span style="margin-left: 20px;">
				<html:reset/>
				</span>
			</td>
		</tr>

	<%-- Cancel Option --%>
		<tr>
			<td class="label">&nbsp;</td>
			<td class="value">
				<p style="margin-top: 20px;">
				<html:link action="/user/account">Cancel any updates</html:link> and return to main <html:link action="/user/account">Account Settings</html:link>.<br/>
				<html:link action="/user/showChangePassword">Change your Password</html:link> (any changes made above will discarded).
				</p>
			</td>
		</tr>


		<tr>
			<td class="label">&nbsp;</td>
			<td valign="middle" align="left" style="padding-top: 20px;">				
				<b>Bolded</b> fields are required.<br/>
				<span style="color:#888888;">
					<sup>*</sup> Feedback would typically involve responding to a short<br/>
					annual questionnaire via email, which would take a few minutes of your time.
				</span> 
			</td>			
		</tr>
	</table>

	</html:form>
</center>