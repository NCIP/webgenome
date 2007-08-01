<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>

<h1 align="center">Jobs Table</h1>

<center>
<table class="table">
	<tr>
		<th>Job ID</th>
		<th>Submission Date</th>
		<th>Start Date</th>
		<th>End Date</th>
		<th>Outcome</tr>
	</tr>
	<logic:iterate name="jobs" id="job">
	<tr>
		<td><bean:write name="job" property="id"/>&nbsp;</td>
		<td><bean:write name="job" property="instantiationDate"/>&nbsp;</td>
		<td><bean:write name="job" property="startDate"/>&nbsp;</td>
		<td><bean:write name="job" property="endDate"/>&nbsp;</td>
		<td><bean:write name="job" property="terminationMessage"/>&nbsp;</td>
	</tr>
	</logic:iterate>
</table>

<p>
	<html:link action="/cart/purgeJobs">Purge Completed Jobs</html:link>
</p>
</center>