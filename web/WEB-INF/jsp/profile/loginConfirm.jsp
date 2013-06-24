<%--L
   Copyright RTI International

   Distributed under the OSI-approved BSD 3-Clause License.
   See http://ncip.github.com/webgenome/LICENSE.txt for details.
L--%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>

<%@ page import="org.rti.webcgh.webui.util.AttributeManager" %>

<%@ page errorPage="/WEB-INF/jsp/exception/jspException.jsp" %>

<p><br></p>
<p><br></p>

<p align="center">
	<span class="largeInfoMsg">
		Welcome
		<bean:write name="<%= AttributeManager.USER_PROFILE %>" property="name"/>
	</span>
</p>

<p align="center">
	<span class="infoMsg">
		You have been logged into the system
	</span>
</p>