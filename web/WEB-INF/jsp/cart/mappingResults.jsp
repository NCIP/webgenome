<%--L
   Copyright RTI International

   Distributed under the OSI-approved BSD 3-Clause License.
   See http://ncip.github.com/webgenome/LICENSE.txt for details.
L--%>

<%@ page errorPage="/WEB-INF/jsp/exception/jspException.jsp" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>

<%@ page import="org.rti.webcgh.array.ExperimentProbeMappingResults" %>

<p><br></p>
<p><br></p>

<%
	String[] style = {"contentTD", "contentTD-odd"};
	int styleIdx = 1;
%>

<table cellpadding="0" cellspacing="0" class="tbl" align="center">
	
	<!-- Table header -->
	<tr class="dataHeadTD">
		<td><b>Experiment</b></td>
		<td><b>Bioassays</b></td>
		<td><b>Number of Probes</b></td>
		<td><b>Mapped Probes</b></td>
		<td><b>Percent Probes Mapped</b></td>
	</tr>

	<!-- Table body -->
	<logic:iterate id="experimentProbeMappingResults" name="probeMappingResults">
		<%
			styleIdx++;
			int count = 0;
		%>
		<logic:iterate id="bioAssayProbeMappingResults" 
			name="experimentProbeMappingResults" property="bioAssayProbeMappingResults">
   			<tr class="<%=style[styleIdx % 2]%>">
   			
	   			<% 
	   				if (count++ == 0) {
	   					ExperimentProbeMappingResults results = (ExperimentProbeMappingResults)
	   						pageContext.findAttribute("experimentProbeMappingResults");
	   					int numArrays = results.getBioAssayProbeMappingResults().size();
	   			%>
						<td rowspan="<%= numArrays %>">
							<bean:write name="experimentProbeMappingResults"
								property="experiment.name"/>
						</td>
	    		<% } %>
    	
    			<td>
    				<bean:write name="bioAssayProbeMappingResults"
    					property="bioAssay.name"/>
    			</td>
    			<td>
    				<bean:write name="bioAssayProbeMappingResults" property="numReporters"/>
    			</td>
    			<td>
    				<bean:write name="bioAssayProbeMappingResults" 
    					property="numMappedReporters"/>
    			</td>
				<td>
    				<bean:write name="bioAssayProbeMappingResults" 
    					property="formattedPercentReportersMapped"/>
    			</td>
    		</tr>
    	</logic:iterate>
    </logic:iterate>
</table>

<p><br></p>

<p align="center">
	<input type="button" value="OK" onclick="window.location='<html:rewrite page="/cart/contents.do"/>'">
</p>