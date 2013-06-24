<%--L
   Copyright RTI International

   Distributed under the OSI-approved BSD 3-Clause License.
   See http://ncip.github.com/webgenome/LICENSE.txt for details.
L--%>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<%@ page import="org.rti.webcgh.domain.ArrayExperimentMetaData" %>

<script language="JavaScript">

	function deleteData(id) {
		if (confirm('Really delete data?'))
			window.location = "<html:rewrite page="/admin/deleteSkyData.do"/>?id=" + id;
	}

</script>

<p><br></p>
<h3>SKY/M-FISH&CGH Data Sets</h3>

<table border="1" cellpadding="5" cellspacing="5"
	<tr>
		<td><b>Investigator</b></td>
		<td><b>Experiment</b></td>
		<td><b>Bioassay Count</b></td>
		<td>&nbsp;</td>
	</tr>
	
	<logic:iterate name="arrayExperimentMetaData" id="metaDatum">
		<tr>
			<td>
				<bean:write name="metaDatum" property="contact"/>
			</td>
			<td>
				<bean:write name="metaDatum" property="name"/>
			</td>
			<td>
				<%
					ArrayExperimentMetaData aemd = (ArrayExperimentMetaData)pageContext.findAttribute("metaDatum");
					int numBioAssays = aemd.getGenomeArrayDataNames().size();
				%>
				<%= numBioAssays %>
			</td>
			<td>
				<a href="#" onclick="deleteData('<bean:write name="metaDatum" property="id"/>')">
					Delete
				</a>
			</td>
		</tr>
	</logic:iterate>
</table>

<p>
	<% request.setAttribute("all", "true"); %>
	[<html:link action="/admin/loadSkyForm">Load SKY/M-FISH&CGH Data</html:link>]&nbsp;&nbsp;
	[<html:link action="/admin/deleteSkyData" paramId="all" paramName="all">Delete All</html:link>]
</p>

