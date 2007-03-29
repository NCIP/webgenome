<%@ taglib uri="/WEB-INF/webgenome.tld" prefix="webgenome" %>

<%@ page errorPage="/WEB-INF/jsp/exception/jspException.jsp" %>

<p>
	<font color="red">
		<b>
			An unknown exception has occurred that has prevented
			webCGH from completing this request.
		</b>
	</font>
</p>
<webgenome:errorEmail hideMessage="true"/>		
<p>
	The system administrator has been sent an error report.  We apologize
	for the inconvenience.
</p>
