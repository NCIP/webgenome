<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>

<%@ page errorPage="/WEB-INF/jsp/exception/jspException.jsp" %>

<script language="JavaScript">

	function deleteExp(expName) {
		if (confirm('Really delete experiment?'))
			window.location = "<html:rewrite page="/virtual/delete.do"/>?experimentName=" + expName;
	}

</script>

<p><br></p>
<p><br></p>

<center>
<logic:notPresent name="experiments">
	<p><br></p>
	<p><br></p>
		<h3>No virtual experiments are defined</h3>
</logic:notPresent>

<!-- Print group names with delete buttons -->
<%
	String[] style = {"contentTD", "contentTD-odd"};
    int styleIdx = 0;
%>

<logic:present name="experiments">
    <table cellpadding="0" cellspacing="0" class="tbl">
    	<tr class="dataHeadTD">
    	<td><b>Experiment Name</b></td>
    	<td><b>Description</b></td>
    	<td><b>Bioassays</b></td>
    	<td><b>Delete Experiment</b></td>
    	</tr>

		<logic:iterate id="virtExp" name="experiments">
            <tr class="<%=style[styleIdx++ %2]%>">
            	<td><bean:write name="virtExp" property="name"/></td>
            	<td><bean:write name="virtExp" property="description"/></td>
            	<td>
            		<logic:iterate id="bioassay" name="virtExp" property="bioAssays">
            			<bean:write name="bioassay" property="name"/><br>
            		</logic:iterate>
            	</td>
         		<td>
         			<a href="#" 
         				onclick="deleteExp('<bean:write name="virtExp" property="id"/>')">
         				Delete
         			</a>
         		</td>
            </tr>
     	</logic:iterate>
    </table>
</logic:present>

</center>