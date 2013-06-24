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
			webCGH was unable to complete this request due to the
			failure of a system component.
		</b>
	</font>
</p>
		
<p>
	Please help us to fix this problem by
	<ul>
		<li>Selecting the error log below
		<li>Copying and pasting the selection into an email
		message to <webcgh:sysadmin/>
	</ul>
</p>
		
<p><br></p>
<p><br></p>
		
		
<h3>Error Log</h3>
		
<p>
	<webcgh:exceptionStackTrace/>
</p>
