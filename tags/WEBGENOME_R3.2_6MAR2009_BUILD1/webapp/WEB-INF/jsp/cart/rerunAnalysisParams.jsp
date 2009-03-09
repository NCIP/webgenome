<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/webgenome.tld" prefix="webgenome" %>

<%-- Analytic operation configurable properties --%>
<p align="center">
	<html:errors property="global"/>
</p>

<html:form action="/cart/rerunAnalysis">
<bean:define name="experimentId" id="experimentId" type="java.lang.String"/>
<bean:define name="operationKey" id="operationKey" type="java.lang.String"/>
<html:hidden property="experimentId" value="<%= experimentId%>"/>
<html:hidden property="operationKey" value="<%= operationKey%>"/>
<center>
<table border="0">
<logic:iterate name="userConfigurableProperties" id="prop">
	<tr>
		<td align="right">
		<bean:write name="prop" property="displayName"/>
		</td>
		<td>
		<webgenome:userConfigurablePropertyInput name="prop"
			prefix="prop_"/>
		</td>
	</tr>
</logic:iterate>
</table>

<p>
	<html:submit value="OK"/>
</p>
</center>
</html:form>