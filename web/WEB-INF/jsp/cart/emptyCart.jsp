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
		<input type="button" onclick="window.location='<html:rewrite page="/cart/selectExperiments.do"/>'" 
			value="Select Experiments">
	</p>
	
	<p>
		<input type="button" onclick="window.location='<html:rewrite page="/cart/uploadExperiment.do?methodToCall=view"/>'" 
			value="Upload Experiments">
	</p>
</center>