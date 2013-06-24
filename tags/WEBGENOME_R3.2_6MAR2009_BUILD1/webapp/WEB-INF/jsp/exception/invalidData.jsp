<%--L
   Copyright RTI International

   Distributed under the OSI-approved BSD 3-Clause License.
   See http://ncip.github.com/webgenome/LICENSE.txt for details.
L--%>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>

<%@ page errorPage="/WEB-INF/jsp/exception/jspException.jsp" %>

<%@ page import="org.rti.webgenome.webui.util.Attribute" %>

<p><br></p>

<table class="tbl" border="0" cellpadding="10" align="center">
	<tr>
		<td>
			<h3 class="exceptionMsg" align="center">Data Cannot Be Processed</h3>
			<ul>
				<logic:iterate name="<%= Attribute.EXCEPTION %>" 
					property="invalidations.invalidations" id="invalidation">
					<li><bean:write name="invalidation" property="message"/></li>
				</logic:iterate>
			</ul>
		</td>
	</tr>
</table>
	