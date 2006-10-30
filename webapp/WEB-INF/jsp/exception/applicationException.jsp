<%@ taglib uri="/WEB-INF/webcgh.tld" prefix="webcgh" %>

<%@ page errorPage="/WEB-INF/jsp/exception/jspException.jsp" %>

<center>
	<p>
		<font color="red">
			<b>webCGH was unable to complete this request for the following reason:</b>
		</font>
	</p>
	<webcgh:errorEmail/>		
	<p>
		<webcgh:exceptionMessage exceptionMsg="<%= exception.getMessage() %>"/>
	</p>
</center>
