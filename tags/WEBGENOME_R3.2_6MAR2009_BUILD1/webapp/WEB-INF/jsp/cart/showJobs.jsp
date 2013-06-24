<%--L
   Copyright RTI International

   Distributed under the OSI-approved BSD 3-Clause License.
   See http://ncip.github.com/webgenome/LICENSE.txt for details.
L--%>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@page import="java.util.Map" %>
<%@page import="org.rti.webgenome.service.job.DownloadDataJob" %>
<%@page import="org.rti.webgenome.domain.BioAssay" %>


 <META HTTP-EQUIV="refresh" CONTENT="5">

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
		<th>Submission Time</th>
		<th>Start Time</th>
		<th>End Time</th>
		<th>Outcome</tr>
	</tr>
	<logic:iterate name="jobs" id="job" type="org.rti.webgenome.service.job.Job">
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
			<logic:match name="job" property="description" value="Downloading">
			<% 			  
			   DownloadDataJob ddj = (DownloadDataJob)job;
			   BioAssay bioAssay = ddj.getBioAssay();
			   String downloadFileName = bioAssay.getId() + ".csv";			  
			%>   
			   
			  <br>You can download generated data file from <br>
			  <a  target="_blank" href="<%= request.getContextPath()%>/download/<%=downloadFileName%>">File to download.</a>
			</logic:match>
		</td>
	</tr>
	</logic:iterate>
</table>

<p>
	<html:link action="/cart/purgeJobs">Purge Completed Jobs</html:link>
</p>
</center>