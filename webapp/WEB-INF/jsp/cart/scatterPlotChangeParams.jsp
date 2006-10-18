<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>

<h1 align="center">Scatter Plot Parameters</h1>

<center>
<html:form action="/cart/newPlot">

	<input type="hidden" name="id" value="<%= request.getParameter("id") %>">
	
	<table border="1">
	
	<%-- Plot name --%>
		<tr>
			<td>
				Plot Name
			</td>
			<td>
				<html:text property="name"/>
			</td>
		</tr>
	
	<%-- Genome intervals --%>
		<tr>
			<td>
				Genome Intervals
			</td>
			<td>
				<html:text property="genomeIntervals"/>
			</td>
		</tr>
		
	<%--
	Genome interval units.  Note that option values
	must correspond to units defined in org.rti.webcgh.units.BpUnits.
	--%>
		<tr>
			<td>
				Genome Interval Units
			</td>
			<td>
				<html:select property="units">
					<html:option value="BP">BP</html:option>
					<html:option value="KB">KB</html:option>
					<html:option value="MB">MB</html:option>
				</html:select>
			</td>
		</tr>
		
	<%--
	Quantitation type.  Note that option values
	must correspond to typs defined in org.rti.webcgh.domain.QuantitationType.
	--%>
		<tr>
			<td>
				Quantitation Type
			</td>
			<td>
				<html:select property="quantitationType">
					<html:option value="log2ratio">Log2 Ratio</html:option>
					<html:option value="loh">LOH</html:option>
				</html:select>
			</td>
		</tr>
		
	<%-- Number of plots per row --%>
		<tr>
			<td>
				Number of plots per row
			</td>
			<td>
				<html:text property="numPlotsPerRow"/>
			</td>
		</tr>
		
	<%-- Minimum Y-axis value --%>
		<tr>
			<td>
				Minimum Y-axis value
			</td>
			<td>
				<html:text property="minY"/>
			</td>
		</tr>
		
	<%-- Maximum Y-axis value --%>
		<tr>
			<td>
				Maximum Y-axis value
			</td>
			<td>
				<html:text property="maxY"/>
			</td>
		</tr>
		
	<%-- Plot width --%>
		<tr>
			<td>
				Plot width in pixels
			</td>
			<td>
				<html:text property="width"/>
			</td>
		</tr>
		
	<%-- Plot height --%>
		<tr>
			<td>
				Plot height in pixels
			</td>
			<td>
				<html:text property="height"/>
			</td>
		</tr>
		
	</table>
	
	<p>
		<html:submit value="OK"/>
	</p>
</html:form>
</center>