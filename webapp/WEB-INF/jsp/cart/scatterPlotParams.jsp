<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>

<h1 align="center">Scatter Plot Parameters</h1>

<center>
<p>
	<html:errors property="global"/>
</p>
<html:form action="/cart/newPlot">

	<logic:present parameter="id">
		<html:hidden property="id" value="<%= request.getParameter("id") %>"/>
	</logic:present>
	
	<table class="noBorder">
	
	<%-- Plot name --%>
		<tr>
			<td>
				Plot Name
				<html:errors property="name"/>
			</td>
			<td>
				<html:text property="name"/>
			</td>
		</tr>
	
	<%-- Genome intervals --%>
		<tr>
			<td>
				Genome Intervals
				<html:errors property="genomeIntervals"/>
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
				<html:errors property="numPlotsPerRow"/>
			</td>
			<td>
				<html:text property="numPlotsPerRow"/>
			</td>
		</tr>
		
	<%-- Minimum Y-axis value --%>
		<tr>
			<td>
				Minimum Y-axis value
				<html:errors property="minY"/>
			</td>
			<td>
				<html:text property="minY"/>
			</td>
		</tr>
		
	<%-- Maximum Y-axis value --%>
		<tr>
			<td>
				Maximum Y-axis value
				<html:errors property="maxY"/>
			</td>
			<td>
				<html:text property="maxY"/>
			</td>
		</tr>
		
	<%-- Plot width --%>
		<tr>
			<td>
				Plot width in pixels
				<html:errors property="width"/>
			</td>
			<td>
				<html:text property="width"/>
			</td>
		</tr>
		
	<%-- Plot height --%>
		<tr>
			<td>
				Plot height in pixels
				<html:errors property="height"/>
			</td>
			<td>
				<html:text property="height"/>
			</td>
		</tr>
		
	<%-- Draw horizontal grid lines --%>
		<tr>
			<td>
				Draw horizontal grid lines?
			</td>
			<td>
				<html:checkbox property="drawHorizGridLines"/>
			</td>
		</tr>
		
	<%-- Draw vertical grid lines --%>
		<tr>
			<td>
				Draw vertical grid lines?
			</td>
			<td>
				<html:checkbox property="drawVertGridLines"/>
			</td>
		</tr>
		
	</table>
	
	<p>
		<html:submit value="OK"/>
	</p>
</html:form>
</center>