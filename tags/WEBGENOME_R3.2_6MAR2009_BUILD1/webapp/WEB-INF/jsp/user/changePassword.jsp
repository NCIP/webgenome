<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>

<h1 align="center">Change Password</h1>

<center>
	<html:errors property="global"/>
	<html:form action="/user/changePassword" focus="password">

	<table cellpadding="5" cellspacing="0" border="0" style="margin-top: 10px;">
	
	<%-- Email (display only) --%>
		<tr>
			<td class="label">
				Email Address:
			</td>
			<td class="value">
				<bean:write name="password" property="email"/>
			</td>
		</tr>

	<%-- Current Password --%>
		<tr>
			<td class="label">
				<html:errors property="password"/>
				Current Password:
			</td>
			<td class="value">
				<html:password name="password" property="password" size="30" maxlength="200"/>
			</td>
		</tr>

	<%-- New Password --%>
		<tr>
			<td class="label">
				<html:errors property="newPassword"/>
				New Password:
			</td>
			<td class="value">
				<html:password name="password" property="newPassword" size="30" maxlength="200"/>
			</td>
		</tr>

	<%-- Confirm New Password --%>
		<tr>
			<td class="label">
				<html:errors property="confirmNewPassword"/>
				Confirm New Password:
			</td>
			<td class="value">
				<html:password property="confirmNewPassword" size="30" maxlength="200"/>
			</td>
		</tr>

	<%-- Submit button --%>
		<tr>
			<td class="label">&nbsp;</td>
			<td class="value">
				<html:submit value="Change Password"/>
				<html:reset/>
			</td>
		</tr>
		
	<%-- Cancel Option --%>
		<tr>
			<td class="label">&nbsp;</td>
			<td class="value">
				<p style="margin-top: 20px;">
				<html:link action="/user/account">Cancel change</html:link> and return to main <html:link action="/user/account">Account Settings</html:link>.
				</p>
			</td>
		</tr>
		
		<tr>
			<td class="label">&nbsp;</td>
			<td class="value">
				<p style="margin-top: 20px;color:#888888; ">
				Passwords are case-sensitive.
				</p>
			</td>
		</tr>

	</table>
	</html:form>
	
</center>