<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<%@ page errorPage="/WEB-INF/jsp/exception/jspException.jsp" %>

<table border="0" cellspacing="0" cellpadding="0" align="center">
	<tr align="center">
	
		<!-- Manage Experiments -->
		<td>
			<logic:equal name="selectedSubMenuItem" value="manage">
				<html:link action="/virtual/list" styleClass="submenu-selected">
				Manage Experiments</html:link>
			</logic:equal>	
			<logic:notEqual name="selectedSubMenuItem" value="manage">
				<html:link action="/virtual/list" styleClass="submenu">
				Manage Experiments</html:link>
			</logic:notEqual>
		</td>
		
		<td width="30"></td>
		
		<!-- New Experiment -->
		<td>
			<logic:equal name="selectedSubMenuItem" value="new">
				<html:link action="/virtual/selectExperiments" styleClass="submenu-selected">
				New Experiment</html:link>
			</logic:equal>	
			<logic:notEqual name="selectedSubMenuItem" value="new">
				<html:link action="/virtual/selectExperiments" styleClass="submenu">
				New Experiment</html:link>
			</logic:notEqual>
		</td>
		
	</tr>
</table>
