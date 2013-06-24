<%--L
   Copyright RTI International

   Distributed under the OSI-approved BSD 3-Clause License.
   See http://ncip.github.com/webgenome/LICENSE.txt for details.
L--%>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/webgenome.tld" prefix="webgenome" %>

<%@ page import="org.rti.webgenome.domain.Experiment,org.rti.webgenome.domain.QuantitationType" %>

<script language="Javascript">

	
	// Open window to choose bioassay color
	function downloadRawData(bioAssayId) {
		var url = "<html:rewrite page="/cart/downloadRawData.do"/>"
			+ "?id=" + bioAssayId;
		window.open(
			url,
			"_blank", 
			"width=200, height=250, menubar=no, status=no, scrollbars=no, "
			+ "resizable=no, toolbar=no, location=no, directories=no"
		);
	}

	// Open window to choose bioassay color
	function colorChooser(bioAssayId) {
		var url = "<html:rewrite page="/cart/colorChooser.do"/>"
			+ "?id=" + bioAssayId;
		window.open(
			url,
			"_blank", 
			"width=200, height=250, menubar=no, status=no, scrollbars=no, "
			+ "resizable=no, toolbar=no, location=no, directories=no"
		);
	}
	
	// Open window to set bioassay name
	function nameChange(id, type) {
		var url = "<html:rewrite page="/cart/nameChooser.do"/>"
			+ "?id=" + id + "&type=" + type;
		window.open(
			url,
			"_blank", 
			"width=400, height=100, menubar=no, status=no, scrollbars=no, "
			+ "resizable=no, toolbar=no, location=no, directories=no"
		);
	}

	// Confirm remove
	function confirmRemove(item, url) {
		var confirmAnswer = confirm("Are you sure you wish to remove " + item + "?");
		if(confirmAnswer) {
			window.location = url;
		}
	}
</script>

<h1 align="center">Workspace</h1>


<html:messages id="msg" message="true">
	<p align="center">
		<span class="message-stationary">
			<bean:write name="msg"/><br>
		</span>	
	</p>
</html:messages>



<center>


	<html:img page="/images/icon-show.gif"
		title="Select color"
		 border="0"
		 width="15" height="15"/> = Show plot &nbsp;&nbsp;
		 
    <html:img page="/images/icon-download.gif"
		title="New name" border="0"
		width="15" height="15"/> = Download data &nbsp;&nbsp;
		
	<html:img page="/images/icon-nameChooser.gif"
		title="New name" border="0"
		width="15" height="15"/> = New name &nbsp;&nbsp;
							
	<html:img page="/images/icon-remove.gif"
		title="Remove" border="0"
		width="15" height="15"/> = Delete &nbsp;&nbsp;
		
	<html:img page="/images/icon-colorChooser.gif"
		title="Select color"
		 border="0"
		 width="15" height="15"/> = Select color &nbsp;&nbsp;
		
	<html:img page="/images/icon-undo.gif"
		title="Change parameters"
		 border="0"
		 width="15" height="15"/> = Change parameters
	
<div><html:errors property="global"/></div>


<table cellpadding="10"><tr valign="top" align="center"><td>

<%-- Experiments --%>
<html:form action="/cart/routeToOperationPage" >
	
	<table class="table">
		<tr>
			<th colspan="2">Experiments</th>
		</tr>
		<tr>
			<th>Experiment Name</th>
			<th>Nested Bioassays</th>
		</tr>
		<logic:iterate name="shopping.cart" property="experiments"
			id="experiment">
			<tr>
				<td>
					<table class="noBorder" width="100%"><tr>
						<td valign="middle" align="right" width="22">
							<%
								Experiment exp = (Experiment)
									pageContext.findAttribute("experiment");
								String propName = "value(exp_" + exp.getId() + ")";
							%>
							<html:checkbox property="<%= propName %>"/>
						</td>
						<td valign="middle" align="left">
							<bean:write name="experiment" property="name"/><br>
							(<bean:write name="experiment" property="quantitationType.name"/><%
								if ( QuantitationType.Other.getName().equals(exp.getQuantitationTypeAsString() ) && 
									 exp.getQuantitationTypeLabel() != null )
								{
									out.println ( ": <em>" + exp.getQuantitationTypeLabel() + "</em>" ) ;
								}
							%>)
						</td>
						<td valign="middle" width="1">
							<html:img page="/images/spacer.gif" border="0" width="1" height="1" alt=""/>
						</td>
						<td valign="middle" align="right" width="48">
							<span style="font-size:16px;">
								<table><tr>
								<td>
								<a href="#"
									onclick="nameChange('<bean:write name="experiment" property="id"/>', 'experiment')"
										><html:img page="/images/icon-nameChooser.gif"
											title="New experiment name" border="0"
											width="15" height="15"
								/></a>
								</td>
								<td>
								<a href="javascript:confirmRemove('<bean:write name="experiment" property="name"/>', '<html:rewrite page="/cart/removeExperiment.do"
									paramName="experiment" paramProperty="id"
									paramId="id"/>');"
									><html:img page="/images/icon-remove.gif"
										title="Remove experiment" border="0"
										width="15" height="15"
								/></a>
								</td>
								<webgenome:onlyIfParameteredDerivedExperiment name="experiment">
								<td>
								<html:link action="/cart/rerunAnalysisParams"
									paramId="experimentId" paramName="experiment"
									paramProperty="id">
									<html:img page="/images/icon-undo.gif"
										title="Change parameters"
										border="0"
										width="15" height="15"/>
								</html:link>
								</td>
								</webgenome:onlyIfParameteredDerivedExperiment>
								</tr></table>
							</span>
						</td>
					</tr></table>
				</td>
				<td>
					<logic:iterate name="experiment" property="bioAssays"
						id="bioAssay">
						<table class="noBorder" width="100%"><tr>
							<td valign="middle" width="15" bgcolor="<webgenome:bioAssayColor name="bioAssay"/>">
								<html:img page="/images/spacer.gif"
									border="0"
									width="15" height="1"
							/></td>
							<td valign="middle" align="left">
								<bean:write name="bioAssay" property="name"/>
							</td>
							<td valign="middle" width="1">
								<html:img page="/images/spacer.gif"
									border="0"
									width="1" height="1"
							/></td>
							<td valign="middle" align="right" width="68">
								<span style="font-size:16px;">
								
								 <a href="<%= request.getContextPath()%>/cart/downloadRawData.do?bioAssId=<bean:write name="bioAssay" property="id"/>&expId=<bean:write name="experiment" property="id"/>">
								     <html:img page="/images/icon-download.gif"
												title="Download Raw Data"
												 border="0"
												 width="15" height="15"
									/></a>
								 
									<a href="#"
										onclick="nameChange('<bean:write name="bioAssay" property="id"/>', 'bioassay')"
											><html:img page="/images/icon-nameChooser.gif"
												title="New bioassay name"
												 border="0"
												 width="15" height="15"
									/></a>
									<a href="#"
										onclick="colorChooser('<bean:write name="bioAssay" property="id"/>')"
											><html:img page="/images/icon-colorChooser.gif"
												title="Select bioassay color"
												 border="0"
												 width="15" height="15"
									/></a>
								</span>
							</td>
						</tr></table>
					</logic:iterate>
				</td>
			</tr>
		</logic:iterate>
	</table>
	
	<p>
		<html:radio property="operation" value="plot"/>
		New Plot
		
		&nbsp;&nbsp;
		
		<html:radio property="operation" value="analysis"/>
		Smooth/Normalize Data
		
		&nbsp;&nbsp;
		
		<webgenome:onlyIfClientMode>
			<html:radio property="operation" value="import"/>
			Add data of different type
			
			&nbsp;&nbsp;
		</webgenome:onlyIfClientMode>
		
		<html:submit value="GO"/>
	</p>
</html:form>

</td><td>

<%-- Plots --%>
	<table class="table">
		<tr>
			<th>Plots</th>
		</tr>
		<logic:iterate name="shopping.cart" property="plots"
			id="plot">
			<tr>
				<td>
					<table class="noBorder" width="100%"><tr>
						<td valign="middle" align="left">
							<bean:write name="plot" property="plotParameters.plotName"/>
						</td>
						<td valign="middle" align="right">
							<span style="font-size:16px;">
								<html:link action="/cart/showPlot" paramId="plotId"
									paramName="plot" paramProperty="id">
									<html:img page="/images/icon-show.gif" title="Show plot"
										border="0" width="15" height="15"/>
								</html:link>
								<a href="#"
									onclick="nameChange('<bean:write name="plot" property="id"/>', 'plot')"
										><html:img page="/images/icon-nameChooser.gif" title="New plot name"
											 border="0" width="15" height="15"
								/></a>
								<a href="javascript:confirmRemove('<bean:write name="plot" property="plotParameters.plotName"/>', '<html:rewrite page="/cart/removePlot.do" paramId="id"
									paramName="plot" paramProperty="id"/>');"
									><html:img page="/images/icon-remove.gif" title="Remove plot"
										 border="0" width="15" height="15"
								/></a>
							</span>
						</td>
					</tr></table>
				</td>
			</tr>
		</logic:iterate>
	</table>
</td></tr></table>

</center>
