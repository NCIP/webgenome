<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>

<h1 align="center">Shopping Cart</h1>

<p align="center">
	This page should show the contents of the
	shopping cart, including uploaded experiments,
	statistically processed experiments, and
	generated plots.
</p>

<h2 align="center">Experiments</h2>
<center>
	<p>
		<ul>
			<logic:iterate name="shopping.cart" property="experiments"
				id="experiment">
				<li>
					<bean:write name="experiment" property="name"/>
				</li>
			</logic:iterate>
		</ul>
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