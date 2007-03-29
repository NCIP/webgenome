<%@ taglib uri="/WEB-INF/webgenome.tld" prefix="webgenome" %>

<%@ page errorPage="/WEB-INF/jsp/exception/jspException.jsp" %>

<center>
	<p>
		<font color="red">
			<b>webCGH was unable to complete this request for the following reason:</b>
		</font>
	</p>
	
	<p>
		<webgenome:exceptionMessage/>
	</p>
	

	<webgenome:errorEmail/>		
	
</center>
