<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<%@ page errorPage="/WEB-INF/jsp/exception/jspException.jsp" %>

<script language="JavaScript">

	function deletePipeline(pName) {
		if (confirm('Really delete pipeline?'))
			window.location = "<html:rewrite page="/deletePipeline.do"/>?pipelineName=" + pName;
	}

</script>

<p><br></p>
<p><br></p>

<% 
	String[] styles = {"contentTD", "contentTD-odd"}; 
	int idx = 0;
%>

<table class="tbl" border="0" cellpadding="0" cellspacing="0" align="center">
	<tr>
		<td colspan="3" align="center" class="dataHeadTD">Analytic Pipelines</td>
	</tr>
	<logic:iterate name="analyticPipelines" id="pipeline">
		<tr class="<%= styles[idx++ % 2] %>">
			<td><bean:write name="pipeline" property="name"/></td>
			<td>
				<logic:equal name="pipeline" property="readOnly" value="false">
					<html:link action="/editPipeline" paramId="pipelineName"
						paramName="pipeline" paramProperty="name">
						View / Edit
					</html:link>
				</logic:equal>
				<logic:equal name="pipeline" property="readOnly" value="true">
					<html:link action="/editPipeline" paramId="pipelineName"
						paramName="pipeline" paramProperty="name">
						View
					</html:link>
				</logic:equal>
				
			</td>
			<td>
				<logic:equal name="pipeline" property="readOnly" value="false">
					<a href="#" onclick="deletePipeline('<bean:write name="pipeline" property="name"/>')">
						Delete
					</a>
				</logic:equal>
			</td>
		</tr>
	</logic:iterate>
</table>