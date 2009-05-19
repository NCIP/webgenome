<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>

<h1 align="center">Login to caArray</h1>
<p>You need to login to caArray server in order to retrieve experiment data from caArray and
upload bioassays into WebGenome.</p>

<center>
	
	<html:form action="/user/caarrayLogin" focus="name">

	<table cellpadding="5" cellspacing="0" border="0" style="margin-top: 10px;">

	<%-- User name --%>
		<tr>
			<td class="label">
				User name:
			</td>
			<td class="value">
				<html:text property="name" size="30" maxlength="200"/>
			</td>
		</tr>

	<%-- Password --%>
		<tr>
			<td class="label">
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
</center>