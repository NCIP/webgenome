<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/webgenome.tld" prefix="webgenome" %>

<form action="<html:rewrite page="/cart/adjustPlotAnalysisParams.do"/>"
	target="mainwindow">
<bean:define id="plotId" name="plot" property="id"/>
<input type="hidden" name="plotId" value="<%= plotId %>">
<table>
	<tr>
		<td>Experiment</td>
		<td>Analytic Operation</td>
		<td>Parameters</td>
	</tr>
	<logic:iterate name="derived.experiments" id="experiment">
		<tr>
			<td>
				<bean:write name="experiment" property="key.name"/>
			</td>
			<td>
				<bean:write name="experiment"
					property="key.sourceAnalyticOperation.name"/>
			</td>
			<td>
				<bean:define id="expId" name="experiment" property="key.id"/>
				<% String prefix = "prop_exp_" + expId + "_"; %>
				<logic:iterate name="experiment" property="value" id="prop">
					<bean:write name="prop" property="displayName"/>
					<webgenome:userConfigurablePropertyInput name="prop"
						prefix="<%= prefix %>"/><br>
				</logic:iterate>
			</td>
		<tr>
	</logic:iterate>
</table>

<p align="center">
	<input type="submit" value="OK" onclick="onLeave();"/>
</p>
</form>