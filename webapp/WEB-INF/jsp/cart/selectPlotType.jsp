<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>

<h1 align="center">Select Plot Type</h1>

<p align="center">
	This page should display a list of plot types
	enabling the user to select one.
</p>

<p align="center">
	<html:link action="/cart/scatterPlotParams">
		Scatter Plot
	</html:link>
	<br>
	<html:link action="/cart/ideogramPlotParams">
		Ideogram Plot
	</html:link>
	<br>
	<html:link action="/cart/barPlotParams">
		Bar Plot
	</html:link>
	<br>
	<html:link action="/cart/annotationPlotParams">
		Annotation Plot
	</html:link>
	<br>
	<html:link action="/cart/frequencyPlotParams">
		Frequency Plot
	</html:link>
</p>