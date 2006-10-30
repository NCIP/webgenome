<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="org.rti.webgenome.client.QuantitationTypes" %>

<%
	Map<String, String> paramsMap = new HashMap<String, String>();
	paramsMap.put("exptIDs", "Experiment 1");
	paramsMap.put("intervals", "1:1-200000000");
	paramsMap.put("qType", QuantitationTypes.COPY_NUMBER_LOG2_RATION);
	paramsMap.put("clientID", "1");
	request.setAttribute("params", paramsMap);
%>

<h1 align="center">WebGenome Home</h1>

<table border="0" cellpadding="10"><tr valign="top"><td>
<p>
WebGenome is an application for creating genomics plots.  This version
of the system is designed to operate as a plotting client for
other applications.  To create plots, you must first select a data set
in another affiliated application, such as Rembrandt.  You will then
be directed back into webGenome.
</p>

<p>
To get a flavor for the system without going through another
application, you can click
<html:link action="/client/plot" name="params">here</html:link>.
The system will randomly generate artifical test data.
</p>

</td><td>

<p>
This version of webGenome supports the creation of two types of plots:
<table>
	<tr>
		<td><html:img page="/images/icon-scatterPlot.gif"/></td>
		<td>Scatter Plot</td>
	</tr>
	<tr>
		<td><html:img page="/images/icon-ideogramPlot.gif"/></td>
		<td>Ideogram Plot</td>
	</tr>
</table>
Supported data types include <em>copy number</em>, <em>fold change</em>,
and <em>loss of heterozygosity</em>.  Additionally, a number of basic
statistical operations are available to process data prior to plotting.
</p>
</td></tr></table>