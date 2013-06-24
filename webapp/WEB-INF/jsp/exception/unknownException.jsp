<%--L
   Copyright RTI International

   Distributed under the OSI-approved BSD 3-Clause License.
   See http://ncip.github.com/webgenome/LICENSE.txt for details.
L--%>

<%@ taglib uri="/WEB-INF/webcgh.tld" prefix="webcgh" %>

<%@ page errorPage="/WEB-INF/jsp/exception/jspException.jsp" %>

<p>
	<font color="red">
		<b>
			An unknown exception has occurred that has prevented
			webCGH from completing this request.
		</b>
	</font>
</p>
<webcgh:errorEmail hideMessage="true"/>		
<p>
	The system administrator has been sent an error report.  We apologize
	for the inconvenience.
</p>
