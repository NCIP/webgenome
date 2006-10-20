<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>

<h1 align="center">Analytic Operation Parameters</h1>

<logic:iterate name="props" id="prop">
	<bean:write name="prop" property="displayName"/>
</logic:iterate>