<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>

<%@ page errorPage="/WEB-INF/jsp/exception/jspException.jsp" %>


<p><br></p>

<p>
	&nbsp;&nbsp;&nbsp;
	
	<table border="0" cellpadding="4" cellspacing="0">
		<tr>
			<td width="20"></td>
			
			<td>
				<logic:equal name="navigationItem" value="select.experiments">
					<b>Select Experiments</b>
				</logic:equal>
				<logic:notEqual name="navigationItem" value="select.experiments">
      				Select Experiments
				</logic:notEqual>
			</td>
			
			<td>
				<html:img width="25" height="25" 
					page="/images/right_arrow.gif" align="middle"/>
			</td>
			
			<td>
				<logic:equal name="navigationItem" value="select.experiment.types">
					<b>Select Experiment Type</b>
				</logic:equal>
				<logic:notEqual name="navigationItem" value="select.experiment.types">
					Select Experiment Type
				</logic:notEqual>
			</td>
			
			<td>
				<html:img width="25" height="25" 
					page="/images/right_arrow.gif" align="middle"/>
			</td>
			
			<td>
				<logic:equal name="navigationItem" value="select.probe.sets">
					<b>Select Probe Sets</b>
				</logic:equal>
				<logic:notEqual name="navigationItem" value="select.probe.sets">
					Select Probe Sets
				</logic:notEqual>
			</td>
			
			<td>
				<html:img width="25" height="25" 
					page="/images/right_arrow.gif" align="middle"/>
			</td>
			
			<td>
				<logic:equal name="navigationItem" value="select.assembly">
					<b>Select Assembly</b>
				</logic:equal>
				<logic:notEqual name="navigationItem" value="select.assembly">
					Select Assembly
				</logic:notEqual>
			</td>
			
			<td>
				<html:img width="25" height="25" 
					page="/images/right_arrow.gif" align="middle"/>
			</td>
			
			<td>
				<logic:equal name="navigationItem" value="mapping.results">
					<b>Mapping Completed</b>
				</logic:equal>
				<logic:notEqual name="navigationItem" value="mapping.results">
					Mapping Completed
				</logic:notEqual>
			</td>
			
		</tr>
	</table>
</p>
