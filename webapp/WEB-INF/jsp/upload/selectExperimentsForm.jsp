<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<h1 align="center">Select Experiments</h1>

<p align="center">
	<html:errors property="global"/>
</p>

<center>
<html:form action="/upload/fetchExperiments">
	<logic:iterate name="ids.and.names" id="exp">
		<input type="checkbox"
			name="value(<%= org.rti.webgenome.webui.util.PageContext.EXPERIMENT_ID_PREFIX %><bean:write name="exp" property="key"/>)">
		<bean:write name="exp" property="value"/><br>
	</logic:iterate>
	<html:submit value="Fetch Experiments"/>
</html:form>
</center>
