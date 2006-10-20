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
</script>

<h1 align="center">Shopping Cart</h1>

<%-- Experiments --%>
<html:form action="/cart/routeToOperationPage">
<center>
	<html:errors property="global"/>
	<table border="1">
		<tr>
			<td>Experiment</td>
			<td>Bioassays</td>
			<td>Actions</td>
		</tr>
		<logic:iterate name="shopping.cart" property="experiments"
			id="experiment">
			<tr>
				<td>
					<%
						Experiment exp = (Experiment)
							pageContext.findAttribute("experiment");
						String propName = "value(exp_" + exp.getId() + ")";
					%>
					<html:checkbox property="<%= propName %>"/>
					<bean:write name="experiment" property="name"/>
					[<a href="#"
						onclick="nameChange('<bean:write name="experiment" property="id"/>', 'experiment')">
							Change Name
					</a>]
				</td>
				<td>
					<logic:iterate name="experiment" property="bioAssays"
						id="bioAssay">
						<table><tr>
							<td bgcolor="<webcgh:bioAssayColor name="bioAssay"/>">
								&nbsp;&nbsp;&nbsp;
							</td>
							<td>
								<bean:write name="bioAssay" property="name"/>
								&nbsp;&nbsp;&nbsp;
								[<a href="#"
									onclick="colorChooser('<bean:write name="bioAssay" property="id"/>')">
										Change Color
								</a>]
								&nbsp;
								[<a href="#"
									onclick="nameChange('<bean:write name="bioAssay" property="id"/>', 'bioassay')">
										Change Name
								</a>]
							</td>
						</table>
					</logic:iterate>
				</td>
				<td>
					<html:link action="/cart/removeExperiment"
						paramName="experiment" paramProperty="id"
						paramId="id">
						Remove
					</html:link>
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
</center>
</html:form>

<hr>

<%-- Plots --%>
<center>
	<table border="1">
		<tr>
			<td>Plot Name</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
		</tr>
		<logic:iterate name="shopping.cart" property="plots"
			id="plot">
			<tr>
				<td>
					<bean:write name="plot" property="plotParameters.plotName"/>
					[<a href="#"
						onclick="nameChange('<bean:write name="plot" property="id"/>', 'plot')">
							Change Name
					</a>]
				</td>
				<td>
					<html:link action="/cart/showPlot" paramId="plotId"
						paramName="plot" paramProperty="id">
						Show
					</html:link>
				</td>
				<td>
					<html:link action="/cart/removePlot" paramId="id"
						paramName="plot" paramProperty="id">
						Remove
					</html:link>
				</td>
			</tr>
		</logic:iterate>
	</table>
</center>