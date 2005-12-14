<%@ page errorPage="/WEB-INF/jsp/exception/jspException.jsp" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>

<p><br></p>
<p><br></p>

<center>
<span class="exceptionMsg">
	<p><b>The following experiments contain data from multiple experiment types:</b></p>
</span>

<ul>
	<logic:iterate id="expName" name="splits">
		<li><bean:write name="expName"/></li>
	</logic:iterate>
</ul>

<p>The bioassays of each of these experiments have been partitioned
into new experiments by experiment type</p>

<p>
	<input type="button" value="OK" onclick="window.location='<html:rewrite page="/selectProbeSets.do"/>'">
</p>
</center>