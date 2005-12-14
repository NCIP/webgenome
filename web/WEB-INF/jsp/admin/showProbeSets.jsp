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
	
	<logic:iterate name="genomeAssemblyProbeSetPairs" id="pair">
		<tr>
			<td>
				<bean:write name="pair" property="genomeAssembly.organism.displayName"/>
			</td>
			<td>
				<bean:write name="pair" property="probeSet.displayName"/>
			</td>
			<td>
				<bean:write name="pair" property="genomeAssembly.name"/>
			</td>
			<td>
				<html:link action="/deleteProbeSet" paramId="id" paramName="pair" paramProperty="probeSet.id">
					Delete
				</html:link>
			</td>
		</tr>
	</logic:iterate>
</table>

<p>
	[<html:link action="/loadProbeSetForm">Load Probe Set</html:link>]&nbsp;&nbsp;
	<% request.setAttribute("all", "yes"); %>
	[<html:link action="/deleteProbeSet" paramId="all" paramName="all">Delete All</html:link>]
</p>