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
				<logic:equal name="navigationItem" value="select.bioassays">
					<b>Select Bioassays</b>
				</logic:equal>
				<logic:notEqual name="navigationItem" value="select.bioassays">
					Select Bioassays
				</logic:notEqual>
			</td>
			
			<td>
				<html:img width="25" height="25" 
					page="/images/right_arrow.gif" align="middle"/>
			</td>
			
			<td>
				<logic:equal name="navigationItem" value="enter.annotation">
					<b>Enter Annotation</b>
				</logic:equal>
				<logic:notEqual name="navigationItem" value="enter.annotation">
					Enter Annotation
				</logic:notEqual>
			</td>
			
			<td>
				<html:img width="25" height="25" 
					page="/images/right_arrow.gif" align="middle"/>
			</td>
		
			<td>
				<logic:equal name="navigationItem" value="create.experiment">
					<b>Create Experiment</b>
				</logic:equal>
				<logic:notEqual name="navigationItem" value="create.experiment">
					Create Experiment
				</logic:notEqual>
			</td>
		</tr>
	</table>
</p>
