<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>

<%@ page errorPage="/WEB-INF/jsp/exception/jspException.jsp" %>

<script language="JavaScript">

	function ok() {
		window.location = "<html:rewrite page="/virtual/list.do"/>";
	}
	
</script>

<p><br></br>
<p><br></br>

<center>
<p>
	Virtual experiments successfully modified
</p>
<p><br></br>
<p>
	<input type="button" value="OK" onclick="ok()">
</p>
</center>