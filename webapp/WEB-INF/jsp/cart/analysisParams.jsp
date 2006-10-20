<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/webcgh.tld" prefix="webcgh" %>

<h1 align="center">Analytic Operation Parameters</h1>

<center>
<logic:iterate name="props" id="prop">
	<p>
		<bean:write name="prop" property="displayName"/>
		&nbsp;&nbsp;
		<webcgh:userConfigurablePropertyInput name="prop"/>
	</p>
</logic:iterate>
</center>