<%--L
   Copyright RTI International

   Distributed under the OSI-approved BSD 3-Clause License.
   See http://ncip.github.com/webgenome/LICENSE.txt for details.
L--%>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>

<%@ page errorPage="/WEB-INF/jsp/exception/jspException.jsp" %>

<script language="JavaScript">

	function ok() {
		window.location = "<html:rewrite page="/pipeline/show.do"/>";
	}
	
</script>

<p><br></p>
<p><br></p>

<p align="center">
	<span class="infoMsg">
		Pipelines successfully modified
	</span>
</p>

<p><br></p>

<p align="center">
	<input type="button" value="OK" onclick="ok()">
</p>