<%--L
   Copyright RTI International

   Distributed under the OSI-approved BSD 3-Clause License.
   See http://ncip.github.com/webgenome/LICENSE.txt for details.
L--%>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>

<%@ page import="org.rti.webcgh.webui.util.DataIdEncoder" %>
<%@ page import="org.rti.webcgh.array.Experiment" %>

<%@ page errorPage="/WEB-INF/jsp/exception/jspException.jsp" %>

<script language="JavaScript">

	function cancel() {
		window.location = "<html:rewrite page="/virtual/list.do"/>";
	}
	
</script>

<p><br></p>
<center><html:errors property="global"/></center>
<p><br></p>

<%
String[] style = {"contentTD", "contentTD-odd"};
int styleIdx = 0;
%>

<center>
<html:form action="/virtual/recordSelectedExperiments">

	<h3>CGH Experiments</h3>
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
			<% 
				Experiment exp = 
					(Experiment)pageContext.findAttribute("experiment");
			   	String idEncoding = DataIdEncoder.encode(exp.getDatabaseName(), exp.getName(), null);
			%>
        	<tr class="<%=style[styleIdx++ % 2]%>">
        		<td><input type="checkbox" name="<%= idEncoding %>"></td>
        		<td><bean:write name="experiment" property="name"/></td>
        		<td><bean:write name="experiment" property="databaseName"/></td>
         		<td><bean:write name="experiment" property="description"/></td>
        	</tr>
        </logic:iterate>
    </table><p>
    
    <p>
		<html:submit value="Next"/>
		&nbsp;&nbsp;
		<input type="button" value="Cancel" onclick="cancel()">
	</p>
</html:form>

</center>