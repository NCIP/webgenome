<%--L
   Copyright RTI International

   Distributed under the OSI-approved BSD 3-Clause License.
   See http://ncip.github.com/webgenome/LICENSE.txt for details.
L--%>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/webcgh.tld" prefix="webcgh" %>

<%@ page errorPage="/WEB-INF/jsp/exception/jspException.jsp" %>

<p><br></p>

<table border="0" cellpadding="4" cellspacing="0">
	<tr>
		<td width="10"></td>
		
		<% 
			String history = ""; 
			int count = 0;
		%>
		<logic:iterate name="plotParamsForm" property="historyAsList" id="ancestor">
			
			<%
				count++;
				if (count > 1)
					history += ";";
				history += (String)pageContext.findAttribute("ancestor");
				request.setAttribute("history", history);
			%>
			
			<% if (count > 1) { %>
				<td>
					<html:img width="15" height="15" 
						page="/images/right_arrow.gif" align="middle"/>
				</td>
			<% } %>
			
			<td>
				<webcgh:endOfHistory name="plotParamsForm" history="history">
					<b>
						<webcgh:formatNumber sigDigits="1">
							<bean:write name="ancestor"/>
						</webcgh:formatNumber>
					</b>
				</webcgh:endOfHistory>
				<webcgh:notEndOfHistory name="plotParamsForm" history="history">
					<webcgh:historyLink name="plotParamsForm" history="history">
						<webcgh:formatNumber sigDigits="1">
							<bean:write name="ancestor"/>
						</webcgh:formatNumber>
					</webcgh:historyLink>
				</webcgh:notEndOfHistory>
			</td>
		</logic:iterate>
	</tr>
</table>
			