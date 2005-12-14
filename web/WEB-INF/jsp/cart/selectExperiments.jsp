<%@ page errorPage="/WEB-INF/jsp/exception/jspException.jsp" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>

<%@ page import="org.rti.webcgh.webui.util.DataIdEncoder" %>
<%@ page import="org.rti.webcgh.array.Experiment" %>

<%
String[] style = {"contentTD", "contentTD-odd"};
int styleIdx = 0;
%>


<p><br></p>
<center><html:errors property="global"/></center>
<p><br></p>

<center>

<html:form action="/cart/recordSelectedExperiments">

	<table cellpadding="0" cellspacing="0" class="tbl">
	
		<!-- Experiment table header -->
		<tr class="dataHeadTD">
    		<td><b>Select</b></td>
    		<td><b>Experiment Name</b></td>
    		<td><b>Database</b></td>
    		<td><b>Experiment Description</b></td>
    	</tr>

		<!-- Experiment table body -->
		<logic:iterate id="experiment" name="experiments">
			<% Experiment exp = 
				(Experiment)pageContext.findAttribute("experiment");
			   String idEncoding = DataIdEncoder.encode(exp.getDatabaseName(), exp.getName(), null);
			   request.setAttribute("idEncoding", idEncoding);
			%>
        	<tr class="<%=style[styleIdx++ % 2]%>">
        		<td><input type="checkbox" name="<%= idEncoding %>"></td>
        		<td><bean:write name="experiment" property="name"/></td>
        		<td><bean:write name="experiment" property="databaseName"/></td>
         		<td><bean:write name="experiment" property="description"/></td>
        	</tr>
        </logic:iterate>
    </table><p>
    
	<html:submit value="Next"/>
	<input type="button" value="Cancel" onclick="window.location='<html:rewrite page="/cart/contents.do"/>'">
</html:form>

</center>