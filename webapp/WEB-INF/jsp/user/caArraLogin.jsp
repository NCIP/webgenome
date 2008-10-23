<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>

<h1 align="center">Login to caArray</h1>
You need to login to caArray server in order to retrieve experiment data from caArray and
upload bioassays into WebGenome.

<center>
	
	<html:form action="/user/caarrayLogin" focus="name">

	<table cellpadding="5" cellspacing="0" border="0">

	<%-- User name --%>
		<tr>
			<td valign="middle" align="left">
	
				User name:
			</td>
			<td valign="middle" align="left">
				<html:text property="name"/>
			</td>
		</tr>

	<%-- Password --%>
		<tr>
			<td valign="middle" align="left">
	
				Password:
			</td>
			<td valign="middle" align="left">
				<html:password property="password"/>
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