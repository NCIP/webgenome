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
			<br>
		</logic:iterate>
	</p>
</center>

<%-- Plots --%>
<h2 align="center">Plots</h2>
<center>
	<p>
		<logic:iterate name="shopping.cart" property="plots"
			id="plot">
			<bean:write name="plot" property="name"/>
			<br>
		</logic:iterate>
	</p>
</center>

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