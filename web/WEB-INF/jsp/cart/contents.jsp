<%@ page errorPage="/WEB-INF/jsp/exception/jspException.jsp" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>

<%@ page import="org.rti.webcgh.array.Experiment" %>
<%@ page import="org.rti.webcgh.webui.util.DataIdEncoder" %>

<%
String[] style = {"contentTD", "contentTD-odd"};
int styleIdx = 1;
%>

<center>

	<p>
		<logic:present name="genomeAssemblies">
			<span class="fieldName">Genome Assemblies:</span>
			<logic:iterate id="genomeAssembly" name="genomeAssemblies">
				&nbsp;
				<span class="fieldValue">
					<bean:write name="genomeAssembly" property="name"/>
				</span>
			</logic:iterate>
		</logic:present>
	</p>

	<html:form action="/cart/removeExperiments">
		<table cellpadding="0" cellspacing="0" class="tbl">
		
			<!-- Table header -->
			<tr class="dataHeadTD">
	    		<td><b>Experiment</b></td>
	    		<td><b>Bioassay</b></td>
	    		<td><b>Type</b></td>
	    		<td><b>Organism</b></td>
	    		<td><b>Array</b></td>
	    	</tr>
	    	
	    	<!-- Table body -->
	    	<logic:iterate id="experiment" name="experiments">
	    		<% styleIdx++; %>
	    		<% int count = 0; %>
	    		<logic:iterate id="bioAssay" name="experiment" property="bioAssays">
	    			<tr class="<%= style[styleIdx % 2] %>">
	    			
	    				<!-- Experiment attributes -->
	    				<% if (count++ == 0) { %>
	    					<% Experiment exp = (Experiment)pageContext.findAttribute("experiment"); %>
	    					<% String expKey = DataIdEncoder.encode(exp.getDatabaseName(), exp.getName(), null); %>
	    					<% int numRows = exp.numBioAssays(); %>
	    					<td rowspan="<%= numRows %>">
	    						<input type="checkbox" name="<%= expKey %>">
	    						<bean:write name="experiment" property="name"/>
	    					</td>
	    				<% } %>
	    				
	    				<!-- Bioassay attributes -->
	    				<td><bean:write name="bioAssay" property="name"/></td>
	    				<td><bean:write name="bioAssay" property="bioAssayType.name"/></td>
	    				<td><bean:write name="bioAssay" property="organism.displayName"/></td>
	    				<td><bean:write name="bioAssay" property="array.displayName"/></td>
	    			</tr>
	    		</logic:iterate>
	    	</logic:iterate>
	    </table>
	    
	    <p>
	    	<%--
	    	<input type="button" onclick="window.location='<html:rewrite page="/cart/selectExperiments.do"/>'" 
	    		value="Select More Experiments">
	    	&nbsp;&nbsp;
	    	--%>
	    	<html:submit value="Remove"/>
	    </p>
	</html:form>
</center>