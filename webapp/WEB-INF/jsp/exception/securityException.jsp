<%@ taglib uri="/WEB-INF/webgenome.tld" prefix="webgenome" %>

<%@ page errorPage="/WEB-INF/jsp/exception/jspException.jsp" %>

<center>
	<p>
		<font color="red">
			<b>You do not have sufficient credentials to perform this action</b>
		</font>
	</p>
		
	<p>
		<webgenome:exceptionMessage/>
	</p>
</center>
