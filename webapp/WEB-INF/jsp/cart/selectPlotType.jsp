<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>

<h1 align="center">Select Plot Type</h1>

<center>

<%--
	Form to select plot type.  The value of each radio
	button input must be equivalent to the name of
	some plot type defined in org.rti.webcgh.core.PlotType.
--%>
	<html:form action="/cart/routeToPlotParametersPage">
	
	<%-- Scatter plot --%>
		<html:radio property="plotType" value="scatter"/>
		Scatter Plot
		
		<br>
		
	<%-- Ideogram plot --%>
		<html:radio property="plotType" value="ideogram"/>
		Ideogram Plot
		
		<br>
		
		<p>
			<html:submit value="OK"/>
		</p>
	</html:form>
</center>
