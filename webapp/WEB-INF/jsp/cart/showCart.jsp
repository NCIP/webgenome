<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/webcgh.tld" prefix="webcgh" %>

<h1 align="center">Shopping Cart</h1>

<p align="center">
	This page should show the contents of the
	shopping cart, including uploaded experiments,
	statistically processed experiments, and
	generated plots.
</p>

<%-- Experiments --%>
<h2 align="center">Experiments</h2>
<center>
	<p>
		<logic:iterate name="shopping.cart" property="experiments"
			id="experiment">
			<webcgh:experimentCheckBox name="experiment"/>
			<bean:write name="experiment" property="name"/>
			<logic:iterate name="experiment" property="bioAssays"
				id="bioAssay">
				<table><tr>
					<td bgcolor="<webcgh:bioAssayColor name="bioAssay"/>">
						&nbsp;&nbsp;&nbsp;
					</td>
					<td>
						<bean:write name="bioAssay" property="name"/>
					</td>
				</table>
			</logic:iterate>
			<br>
		</logic:iterate>
	</p>
</center>

<hr>

<%-- Plots --%>
<h2 align="center">Plots</h2>
<center>
	<p>
		<logic:iterate name="shopping.cart" property="plots"
			id="plot">
			<html:link action="/cart/showPlot" paramId="plotId"
				paramName="plot" paramProperty="id">
				<bean:write name="plot" property="name"/>
			</html:link>
			<br>
		</logic:iterate>
	</p>
</center>

<hr>

<p align="center">
	<html:link action="/cart/selectPlotType">
		New Plot
	</html:link>
	<br>
	<html:link action="/cart/selectOperation">
		Run Analytic Operation
	</html:link>
	<br>
	<html:link action="/cart/setDisplayNames">
		Set Names
	</html:link>
	<br>
	<html:link action="/cart/setColors">
		Set Colors
	</html:link>
	<br>
	<html:link action="/cart/fileUpload">
		Upload Data File
	</html:link>
</p>