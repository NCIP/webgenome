<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<script language="JavaScript">

	function deleteCytobands(assemblyId) {
		if (confirm('Really delete cytobands?'))
			window.location = "<html:rewrite page="/deleteCytobands.do"/>?assemblyId=" + assemblyId;
	}

</script>

<p><br></p>

<h3>Cytoband Data</h3>

<table border="1" cellpadding="5" cellspacing="5">
	<tr>
		<td><b>Genus</b></td>
		<td><b>Species</b></td>
		<td><b>Assembly</b></td>
		<td>&nbsp;</td>
	</tr>
	<logic:iterate name="genomeAssemblies" id="genomeAssembly">
		<tr>
			<td><bean:write name="genomeAssembly" property="organism.genus"/></td>
			<td><bean:write name="genomeAssembly" property="organism.species"/></td>
			<td><bean:write name="genomeAssembly" property="name"/></td>
			<td>
				<a href="#" onclick="deleteCytobands('<bean:write name="genomeAssembly" property="id"/>')">
					Delete
				</a>
			</td>
		</tr>
	</logic:iterate>
	
</table>

<p>
	[<html:link action="/loadCytobandsForm">Load Cytobands</html:link>]&nbsp&nbsp;
	<% request.setAttribute("all", "true"); %>
	[<html:link action="/deleteCytobands" paramId="all" paramName="all">
		Delete All Cytobands
	</html:link>]
</p>