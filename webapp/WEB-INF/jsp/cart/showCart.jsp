<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/webcgh.tld" prefix="webcgh" %>

<%@ page import="org.rti.webcgh.domain.Experiment" %>

<script language="Javascript">

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

<h1 align="center">Shopping Cart</h1>

<center>

	<html:img page="/images/icon-show.gif"
		title="Select color"
		 border="0"
		 width="15" height="15"/> = Show plot &nbsp;&nbsp;
	<html:img page="/images/icon-nameChooser.gif"
		title="New name" border="0"
		width="15" height="15"/> = New name &nbsp;&nbsp;
							
	<html:img page="/images/icon-remove.gif"
		title="Remove" border="0"
		width="15" height="15"/> = Delete &nbsp;&nbsp;
		
	<html:img page="/images/icon-colorChooser.gif"
		title="Select color"
		 border="0"
		 width="15" height="15"/> = Select color

<table cellpadding="10"><tr valign="top"><td>

<%-- Experiments --%>
<html:form action="/cart/routeToOperationPage">
	<html:errors property="global"/>
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
							<bean:write name="experiment" property="name"/>
						</td>
						<td valign="middle" width="1">
							<html:img page="/images/spacer.gif"
								border="0"
								width="1" height="1"
						/></td>
						<td valign="middle" align="right" width="48">
							<span style="font-size:16px;">
								<a href="#"
									onclick="nameChange('<bean:write name="experiment" property="id"/>', 'experiment')"
										><html:img page="/images/icon-nameChooser.gif"
											title="New experiment name" border="0"
											width="15" height="15"
								/></a>
								<a href="javascript:confirmRemove('<bean:write name="experiment" property="name"/>', '<html:rewrite page="/cart/removeExperiment.do"
									paramName="experiment" paramProperty="id"
									paramId="id"/>');">
									<html:img page="/images/icon-remove.gif"
										title="Remove experiment" border="0"
										width="15" height="15"/>
								</a>
							</span>
						</td>
					</tr></table>
				</td>
				<td>
					<logic:iterate name="experiment" property="bioAssays"
						id="bioAssay">
						<table class="noBorder" width="100%"><tr>
							<td valign="middle" width="15" bgcolor="<webcgh:bioAssayColor name="bioAssay"/>">
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
							<td valign="middle" align="right" width="48">
								<span style="font-size:16px;">
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
		Perform Analytic Operation
		
		&nbsp;&nbsp;
		
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
									paramName="plot" paramProperty="id"/>');">
									<html:img page="/images/icon-remove.gif" title="Remove plot"
										 border="0" width="15" height="15"/>
								</a>
							</span>
						</td>
					</tr></table>
				</td>
			</tr>
		</logic:iterate>
	</table>
</td></tr></table>

</center>