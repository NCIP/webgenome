<%--L
   Copyright RTI International

   Distributed under the OSI-approved BSD 3-Clause License.
   See http://ncip.github.com/webgenome/LICENSE.txt for details.
L--%>

<%@ taglib uri="/WEB-INF/webcgh.tld" prefix="webcgh" %>

<%@ page errorPage="/WEB-INF/jsp/exception/jspException.jsp" %>

<center>
	<p>
		<font color="red">
			<b>webCGH was unable to complete this request for the following reason:</b>
		</font>
	</p>
	
	<p>
		<webcgh:exceptionMessage/>
	</p>
	

	<webcgh:errorEmail/>		
	
</center>
