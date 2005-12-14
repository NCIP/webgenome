<%@ taglib uri="/WEB-INF/webcgh.tld" prefix="webcgh" %>

<%@ page errorPage="/WEB-INF/jsp/exception/jspException.jsp" %>

<center>
	<p>
		<font color="red">
			<b>You do not have sufficient credentials to perform this action</b>
		</font>
	</p>
		
	<p>
		<webcgh:exceptionMessage/>
	</p>
</center>
