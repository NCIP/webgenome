<%--L
   Copyright RTI International

   Distributed under the OSI-approved BSD 3-Clause License.
   See http://ncip.github.com/webgenome/LICENSE.txt for details.
L--%>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<p><br></p>
<p><br></p>

<table border="1" cellpadding="5" cellspacing="1">
	<tr>
		<td><b>Organism</b></td>
		<td><b>Probe Set</b></td>
		<td><b>Genome Assembly</b></td>
		<td>&nbsp;</td>
	</tr>
	
	<logic:iterate name="arrayMappings" id="arrayMapping">
		<tr>
			<td>
				<bean:write name="arrayMapping" property="genomeAssembly.organism.displayName"/>
			</td>
			<td>
				<bean:write name="arrayMapping" property="array.displayName"/>
			</td>
			<td>
				<bean:write name="arrayMapping" property="genomeAssembly.name"/>
			</td>
			<td>
				<html:link action="/admin/deleteArray" paramId="id" paramName="arrayMapping" paramProperty="array.id">
					Delete
				</html:link>
			</td>
		</tr>
	</logic:iterate>
</table>

<p>
	[<html:link action="/admin/loadArrayForm">Load Probe Set</html:link>]&nbsp;&nbsp;
	<% request.setAttribute("all", "yes"); %>
	[<html:link action="/admin/deleteArray" paramId="all" paramName="all">Delete All</html:link>]
</p>