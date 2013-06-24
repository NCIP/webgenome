<%--L
   Copyright RTI International

   Distributed under the OSI-approved BSD 3-Clause License.
   See http://ncip.github.com/webgenome/LICENSE.txt for details.
L--%>

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
		<bean:define id="opId" name="op" property="key"/>
		<img styleClass="pointer"
			src="<html:rewrite page="/images/Inform.gif"/>"
			align="absmiddle"
			onclick="help('op-<bean:write name="op" property="key"/>')"
			title="Information" border="0"
			width="15" height="15">
		<bean:write name="op" property="value"/>
	</td></tr>
</logic:iterate>
</table>

<p>
	<html:submit value="OK" onclick="javascript:return validateSelection(this.form)"/>
</p>
<script language="JavaScript">
/* Make sure at least one analytic operation is selected */
function validateSelection( form )
{
	var ok=false ;
	for ( var idx = 0 ; idx < form.operationKey.length; idx++ ) {
		if ( form.operationKey[idx].checked ) {
			ok=true;
		}
	}
	if ( ! ok ) {
		alert ( "Please select an Analytic Operation to perform." ) ;
	}
    return ok;
}
</script>

</center>
</html:form>
