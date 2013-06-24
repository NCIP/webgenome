<%--L
   Copyright RTI International

   Distributed under the OSI-approved BSD 3-Clause License.
   See http://ncip.github.com/webgenome/LICENSE.txt for details.
L--%>

<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %><%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %><% response.setHeader("Cache-Control","no-store"); response.setHeader("Pragma","no-cache"); response.setDateHeader("Expires", 0); %><%@ page contentType="text/xml" %>
<updates>
	<logic:iterate name="completedJobs" id="job">
	<update
		elementId="<bean:write name="job" property="id"/>_startDate"
		elementValue="<bean:write name="job" property="startDate"/>"/>
	<update
		elementId="<bean:write name="job" property="id"/>_endDate"
		elementValue="<bean:write name="job" property="endDate"/>"/>
	<update
		elementId="<bean:write name="job" property="id"/>_terminationMessage"
		elementValue="<bean:write name="job" property="terminationMessage"/>"/>
	</logic:iterate>
	<logic:iterate name="startedJobs" id="job">
	<update
		elementId="<bean:write name="job" property="id"/>_startDate"
		elementValue="<bean:write name="job" property="startDate"/>"/>
	</logic:iterate>
</updates>
