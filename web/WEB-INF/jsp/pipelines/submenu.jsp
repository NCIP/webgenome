<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<%@ page errorPage="/WEB-INF/jsp/exception/jspException.jsp" %>
<%@ page import="org.rti.webcgh.webui.util.AttributeManager" %>

<table border="0" cellspacing="0" cellpadding="0" align="center">
	<tr align="center">
	
		<logic:present name="<%= AttributeManager.USER_PROFILE %>">
		
			<!-- Manage pipelines -->
			<td>
				<logic:equal name="selectedSubMenuItem" value="manage">
					<html:link action="/pipeline/show" styleClass="submenu-selected">
					Manage Pipelines</html:link>
				</logic:equal>	
				<logic:notEqual name="selectedSubMenuItem" value="manage">
					<html:link action="/pipeline/show" styleClass="submenu">
					Manage Pipelines</html:link>
				</logic:notEqual>
			</td>
			
			
			
			<td width="30"></td>
			
				<!-- New pipeline -->
				<td>
					<logic:equal name="selectedSubMenuItem" value="new">
						<html:link action="/pipeline/new" styleClass="submenu-selected">
						New Pipeline</html:link>
					</logic:equal>	
					<logic:notEqual name="selectedSubMenuItem" value="new">
						<html:link action="/pipeline/new" styleClass="submenu">
						New Pipeline</html:link>
					</logic:notEqual>
				</td>
		
		</logic:present>
		
		
	</tr>
</table>
