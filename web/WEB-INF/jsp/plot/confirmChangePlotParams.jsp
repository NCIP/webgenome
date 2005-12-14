<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>

<%@ page errorPage="/WEB-INF/jsp/exception/jspException.jsp" %>

<script>
	function ok() {
		window.location = "<html:rewrite page="/plot/params.do"/>";
	}
</script>

<p><br></p>
<p><br></p>
<p><br></p>
<p><br></p>
<p><br></p>

<p align="center">
	<b>Plot parameters changed</b>
</p>

<p><br></p>
<p align="center">
	<input type="button" value="OK" onclick="ok()">
</p>