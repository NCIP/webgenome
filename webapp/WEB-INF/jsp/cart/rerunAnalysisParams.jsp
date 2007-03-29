<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/webgenome.tld" prefix="webgenome" %>

<%-- Analytic operation configurable properties --%>
<p align="center">
	<html:errors property="global"/>
</p>

<html:form action="/cart/rerunAnalysis">
<input type="hidden" name="experimentId"
value="<%= pageContext.findAttribute("experimentId") %>">
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