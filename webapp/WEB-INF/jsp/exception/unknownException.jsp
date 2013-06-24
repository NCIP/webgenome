<%@ taglib uri="/WEB-INF/webgenome.tld" prefix="webgenome" %>

<%@ page errorPage="/WEB-INF/jsp/exception/jspException.jsp" %>

<p>
	<font color="red">
		<b>
			An unexpected error has occurred that has prevented
			WebGenome from completing this request.
		</b>
	</font>
</p>
<webgenome:errorEmail hideMessage="true"/>		
<p>
	The system administrator has been sent an error report.  We apologize
	for the inconvenience.
</p>
