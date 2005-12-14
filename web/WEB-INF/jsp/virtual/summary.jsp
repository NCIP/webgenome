<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<%@ page errorPage="/WEB-INF/jsp/exception/jspException.jsp" %>

<script language="JavaScript">

	function cancel() {
		window.location = "<html:rewrite page="/virtual/list.do"/>";
	}
	
</script>

<center>

<html:form action="/virtual/save">

	<p>
		<b>Experiment Name:</b>
		<bean:write name="virtualExperimentForm" property="experimentName"/>
	</p>
	
	<p>
		<b>Description:</b>
		<bean:write name="virtualExperimentForm" property="description"/>
	</p>

	<p>
		<b>BioAssays:</b>
		<ul>
			<logic:iterate id="bioassay" name="dataSelectionForm" property="bioassaysAsCollection">
				<li>
					<bean:write name="bioassay"/>
				</li>
			</logic:iterate>
		</ul>
	</p>
	
	<p>
		<html:submit value="Create Experiment"/>
		&nbsp;&nbsp;
		<input type="button" value="Cancel" onclick="cancel()">
	</p>
	
	
</html:form>
</center>
