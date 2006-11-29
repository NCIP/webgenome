<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="org.rti.webgenome.client.QuantitationTypes" %>

<%

	// Parameters for copy number plot
	Map<String, String> copyNumParams = new HashMap<String, String>();
	copyNumParams.put("exptIDs", "Experiment 1");
	copyNumParams.put("intervals", "1:1-200000000");
	copyNumParams.put("qType", QuantitationTypes.COPY_NUMBER_LOG2_RATION);
	copyNumParams.put("clientID", "1");
	request.setAttribute("copyNumParamsMap", copyNumParams);
	
	// Parameters for LOH plot
	Map<String, String> lohParams = new HashMap<String, String>();
	lohParams.put("exptIDs", "Experiment 1");
	lohParams.put("intervals", "1:1-200000000");
	lohParams.put("qType", QuantitationTypes.LOH);
	lohParams.put("clientID", "1");
	request.setAttribute("lohParamsMap", lohParams);
%>

<h1 align="center">webGenome Home</h1>

<br>

<table align="center" width="760" cellspacing="0" cellpadding="10" border="0"><tr valign="top"><td width="52%">

<p>
<font color="#336699"><b><i>web</i>Genome</b></font> is an application for creating genomics plots.  This version
of the system is designed to operate as a plotting client for
other applications. To create plots, you must first select a data set
in another affiliated application, such as <b>Rembrandt</b>.  You will then
be directed back into <font color="#336699"><b><i>web</i>Genome</b></font>.
</p>

<p>
To get a flavor for the system without going through another
application, you can plot randomly generated
<html:link action="/client/testPlot"
name="copyNumParamsMap">copy number</html:link>
or
<html:link action="/client/testPlot"
name="lohParamsMap">LOH</html:link> data.
</p>

</td><td width="48%">

<p>
This version of <font color="#336699"><b><i>web</i>Genome</b></font> supports the creation of two types of plots:
<table cellspacing="10" cellpadding="0" border="0">
	<tr>
		<td align="center">
			<html:img page="/images/icon-scatterPlot.gif"/><br>
			<small>Scatter Plot</small>
		</td>
		<td rowspan="2"><html:img page="/images/spacer.gif" width="10" height="1" border="0"/></td>
		<td align="center">
			<html:img page="/images/icon-ideogramPlot.gif"/><br>
			<small>Ideogram Plot</small>
		</td>
	</tr>
</table>
Supported data types include <em>copy number</em>, <em>fold change</em>,
and <em>loss of heterozygosity</em>.  Additionally, a number of basic
statistical operations are available to process data prior to plotting.
</p>

</td></tr></table>