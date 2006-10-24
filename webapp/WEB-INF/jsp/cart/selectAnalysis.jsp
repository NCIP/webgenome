<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>

<h1 align="center">Select Analytic Operation</h1>

<html:form action="/cart/analysisParams">
<center>
<table class="noBorder">
<logic:iterate name="opIndex" id="op">
	<tr><td>
		<html:radio property="operationKey" idName="op" value="key"/>
		<bean:write name="op" property="value"/>
	</td></tr>
</logic:iterate>
</table>

<p>
	<html:submit value="OK"/>
</p>

</center>
</html:form>
