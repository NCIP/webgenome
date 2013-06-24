<%--L
   Copyright RTI International

   Distributed under the OSI-approved BSD 3-Clause License.
   See http://ncip.github.com/webgenome/LICENSE.txt for details.
L--%>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>

<%@ page errorPage="/WEB-INF/jsp/exception/jspException.jsp" %>

<script language="JavaScript">

	function cancel() {
		window.location = "<html:rewrite page="/virtual/list.do"/>";
	}
	
</script>

<p><br></p>
<center><html:errors property="global"/></center>
<p><br></p>

<center>
<html:form action="/virtualRecordAnnotation">

	<table border="0" cellpadding="5" cellspacing="0">
		<tr>
			<td><b>Experiment name:</b></td>
			<td>
				<html:errors property="experimentName"/>
				<html:text maxlength="30" size="30" property="experimentName"/>
			</td>
		</tr>
		<tr>
			<td><b>Description:</b></td>
			<td>
				<html:textarea property="description" rows="5" cols="30"/>
			</td>
		</tr>
	</table>
	
	<p>
	<html:submit value="Next"/>
	&nbsp;&nbsp;
	<input type="button" value="Cancel" onclick="cancel()">
	
</html:form>
</center>