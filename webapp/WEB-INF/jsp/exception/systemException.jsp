<%@ taglib uri="/WEB-INF/webgenome.tld" prefix="webgenome" %>

<%@ page errorPage="/WEB-INF/jsp/exception/jspException.jsp" %>
				
<p>
	<font color="red">
		<b>
			webCGH was unable to complete this request due to the
			failure of a system component.
		</b>
	</font>
</p>
		
<p>
	A message has been sent to the administrator detailing this
	exception.  We apologize for the inconvenience.
</p>
		
<webgenome:errorEmail/>		
<h3>Error Log</h3>
<p>
	<webgenome:exceptionStackTrace/>
</p>
