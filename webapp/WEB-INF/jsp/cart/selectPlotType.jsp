<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>

<h1 align="center">Select Plot Type</h1>

<center>

<%--
	Form to select plot type.  The value of each radio
	button input must be equivalent to the name of
	some plot type defined in org.rti.webcgh.core.PlotType.
--%>
	<html:form action="/cart/routeToPlotParametersPage">

		<table cellpadding="5" cellspacing="0" border="0">

		<%-- Scatter plot --%>
			<tr class="cellOut" onMouseOver="this.className='cellOver'" onMouseOut="this.className='cellOut'" onClick="document.forms[0].plotType[0].checked=true;">
				<td>
					<html:radio property="plotType" value="SCATTER"/>
				</td>
				<td>
					<html:img page="/images/icon-scatterPlot.gif" border="1"/>
				</td>
				<td>
					Scatter Plot
				</td>
			</tr>
		
		<%-- Ideogram plot --%>
			<tr class="cellOut" onMouseOver="this.className='cellOver'" onMouseOut="this.className='cellOut'" onClick="document.forms[0].plotType[1].checked=true;">
				<td>
					<html:radio property="plotType" value="IDEOGRAM"/>
				</td>
				<td>
					<html:img page="/images/icon-ideogramPlot.gif" border="1"/>
				</td>
				<td>
					Ideogram Plot
				</td>
			</tr>
			
		<%-- Bar plot --%>
			<tr class="cellOut" onMouseOver="this.className='cellOver'" onMouseOut="this.className='cellOut'" onClick="document.forms[0].plotType[2].checked=true;">
				<td>
					<html:radio property="plotType" value="BAR"/>
				</td>
				<td>
					<html:img page="/images/icon-barPlot.gif" border="1"/>
				</td>
				<td>
					Bar Plot
				</td>
			</tr>

		</table>

		<p>
			<html:submit value="OK"/>
		</p>
	</html:form>
</center>
