<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>

<p align="center">
	<html:messages id="message" message="true">
		<bean:write name="message"/>
	</html:messages>
</p>

<h1 align="center">Jobs Table</h1>

<center>
<table class="table">
	<tr>
		<th>Job ID</th>
		<th>Description</th>
		<th>Submission Date</th>
		<th>Start Date</th>
		<th>End Date</th>
		<th>Outcome</tr>
	</tr>
	<logic:iterate name="jobs" id="job">
	<tr>
		<td><bean:write name="job" property="id"/>&nbsp;</td>
		<td><bean:write name="job" property="description"/>&nbsp;</td>
		<td><bean:write name="job" property="instantiationDate"/>&nbsp;</td>
		<td id="<bean:write name="job" property="id"/>_startDate">
			<bean:write name="job" property="startDate"/>&nbsp;
		</td>
		<td id="<bean:write name="job" property="id"/>_endDate">
			<bean:write name="job" property="endDate"/>&nbsp;
		</td>
		<td id="<bean:write name="job" property="id"/>_terminationMessage">
			<bean:write name="job" property="terminationMessage"/>&nbsp;
		</td>
	</tr>
	</logic:iterate>
</table>

<p>
	<html:link action="/cart/purgeJobs">Purge Completed Jobs</html:link>
</p>
</center>