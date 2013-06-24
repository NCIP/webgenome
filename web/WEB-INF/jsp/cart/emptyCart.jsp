<%--L
   Copyright RTI International

   Distributed under the OSI-approved BSD 3-Clause License.
   See http://ncip.github.com/webgenome/LICENSE.txt for details.
L--%>

<%@ page errorPage="/WEB-INF/jsp/exception/jspException.jsp" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
	
<p><br></p>
<p><br></p>

<center>
	<p>
		<span class="infoMsg">Data shopping cart is empty.  Press button below to shop for data.</span>
	</p>
	
	<p><br></p>
	
	<p>
		[<html:link action="/cart/selectExperiments" styleClass="actionLink">Load Experiments from Database</html:link>]&nbsp;&nbsp;
		[<html:link action="/cart/uploadExperiment?methodToCall=view" styleClass="actionLink">Upload File</html:link>]
	</p>
	
</center>