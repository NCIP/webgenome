<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="org.rti.webgenome.client.QuantitationTypes" %>

<%

	// Parameters for copy number plot
	Map copyNumParams = new HashMap();
	copyNumParams.put("exptIDs", "Experiment 1");
	copyNumParams.put("intervals", "1:1-200000000");
	copyNumParams.put("qType", QuantitationTypes.COPY_NUMBER_LOG2_RATION);
	copyNumParams.put("clientID", "1");
	copyNumParams.put("test", "true");
	request.setAttribute("copyNumParamsMap", copyNumParams);
	
	// Parameters for LOH plot
	Map lohParams = new HashMap();
	lohParams.put("exptIDs", "Experiment 1");
	lohParams.put("intervals", "1:1-200000000");
	lohParams.put("qType", QuantitationTypes.LOH);
	lohParams.put("clientID", "1");
	lohParams.put("test", "true");
	request.setAttribute("lohParamsMap", lohParams);
%>

<h1 align="center">webGenome Home</h1>

<%-- Messages --%>
<p align="center">
	<span class="message-stationary">
		<html:messages id="message" message="true">
			<bean:write name="message"/>
		</html:messages>
	</span>
</p>

<br>

<table align="center" width="760" cellspacing="0" cellpadding="10" border="0">
	<tr valign="top">
		<td width="52%">
			<!--    START:    L E F T    P A N E L    T E X T    -->
			<p><span style="color:#336699; font-weight: bold;"><i>web</i>Genome</span> is an application for
			creating genomics plots.</p>
			<p>The system is designed to operate as both
			a <strong>stand-alone</strong> application and as a <strong>plotting client</strong> for
			other applications.
			</p>
			<p>To create plots, you must
			either <html:link action="/user/login">log in</html:link> and upload data or select a data set
			in another affiliated application, such as <a target="_blank" href="http://caintegrator-info.nci.nih.gov/rembrandt">REMBRANDT</a>, and
			then elect to plot data in <i>web</i>Genome</strong>.</p>
			<p>
			To get a flavor for the system without going through another
			application, you can plot randomly generated
			<html:link action="/client/testPlot"
			name="copyNumParamsMap">copy number</html:link>
			or
			<html:link action="/client/testPlot"
			name="lohParamsMap">LOH</html:link> data.
			</p>
			<p style="padding-top: 70px; color: #888888;font-size:98%;">REMBRANDT is available at:<br/>
			<a href="https://caintegrator.nci.nih.gov/rembrandt/" target="_blank">https://caintegrator.nci.nih.gov/rembrandt/</a></p>
			</div> 
			<!--      END:    L E F T    P A N E L    T E X T    -->
		</td>
		<td width="48%">
			<!--    START:    R I G H T    P A N E L    T E X T    -->
			<p>
			<span style="color:#336699; font-weight: bold;"><i>web</i>Genome</span> supports the creation
			of five types of plots:</p>
			<table cellspacing="10" cellpadding="0" border="0">
				<tr>
					<td align="center">
						<html:img page="/images/icon-scatterPlot.gif"/><br>
						<small>Genome Snapshot Plot</small>
					</td>
					<td align="center">
						<html:img page="/images/icon-scatterPlot.gif"/><br>
						<small>Scatter Plot</small>
					</td>
					<td rowspan="3"><html:img page="/images/spacer.gif" width="10" height="1" border="0"/></td>
					<td align="center">
						<html:img page="/images/icon-ideogramPlot.gif"/><br>
						<small>Ideogram Plot</small>
					</td>
				</tr>
				<tr>
					<td align="center">
						<html:img page="/images/icon-annotationPlot.gif"/><br>
						<small>Annotation Plot</small>
					</td>
					<td align="center">
						<html:img page="/images/icon-barPlot.gif"/><br>
						<small>Bar Plot</small>
					</td>
					<td>&nbsp;</td>
					<%--
					<td rowspan="3"><html:img page="/images/spacer.gif" width="10" height="1" border="0"/></td>
					--%>
				</tr>
			</table>
			<p>Supported data types include
			<em>copy number (generated from array-CGH or SNP-array)</em>,
			<em>fold change</em>, <em>loss of heterozygosity</em>, and <em>gene expression</em>.
			<p>
			Additionally, a number of basic
			statistical operations are available to process data prior to plotting.
			</p>
			<!--      END:    R I G H T    P A N E L    T E X T    -->
		</td>
	</tr>
</table>