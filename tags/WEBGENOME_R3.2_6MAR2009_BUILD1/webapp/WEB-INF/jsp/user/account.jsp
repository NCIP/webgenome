<%--L
   Copyright RTI International

   Distributed under the OSI-approved BSD 3-Clause License.
   See http://ncip.github.com/webgenome/LICENSE.txt for details.
L--%>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>

<h1 align="center">Edit Account Settings</h1>

<center>
<p>
	<html:messages id="message" message="true">
		<bean:write name="message"/>
	</html:messages>
</p>
<p><html:link action="/user/showEditAccount">Edit Account</html:link></p>
<p><html:link action="/user/showChangePassword">Change Password</html:link></p>
</center>