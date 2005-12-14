<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<%@ page import="org.rti.webcgh.webui.util.DataIdEncoder" %>
<%@ page import="org.rti.webcgh.array.Experiment" %>
<%@ page import="org.rti.webcgh.array.BioAssay" %>

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
<html:form action="/virtual/recordSelectedBioAssays">

	<table cellpadding="0" cellspacing="0" class="tbl">
		<tr class="dataHeadTD">
    		<td><b>Experiment</b></td>
    		<td><b>Select</b></td>
    		<td><b>BioAssay</b></td>
		</tr>
		
		<logic:iterate id="experiment" name="experiments">
			<% int count = 0; %>
			<logic:iterate id="bioAssay" name="experiment" property="bioAssays">
				<tr class="<%=style[styleIdx++ % 2]%>">
					<td> <% if (count++ < 1) { %>
						<bean:write name="experiment" property="name"/>
						<% } %>
					</td>
					<td>
						<%
							Experiment exp = 
								(Experiment)pageContext.findAttribute("experiment");
							BioAssay bioA = (BioAssay)pageContext.findAttribute("bioAssay");
			   				String idEncoding = DataIdEncoder.encode(exp.getDatabaseName(), exp.getName(), 
			   					bioA.getName());
						%>
                		<input type="checkbox" name="<%= idEncoding %>">
                    </td>
                    <td>
                    	<bean:write name="bioAssay" property="name"/>
                    </td>
 				</tr>
 			</logic:iterate>
 		</logic:iterate>
	</table>
	
	<p>
		<html:submit value="Next"/>
		&nbsp;&nbsp;
		<input type="button" value="Cancel" onclick="cancel()">
	</p>
</html:form>
</center>
