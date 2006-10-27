<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/webcgh.tld" prefix="webcgh" %>

<h1 align="center">Scatter Plot Parameters</h1>

<center>
<p>
	<html:errors property="global"/>
</p>
<html:form action="/cart/newPlot" target="mainwindow">

	<logic:present parameter="id">
		<input type="hidden" name="id" value="<%= request.getParameter("id") %>">
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
		
	<%-- Quantitation type. --%>
		<tr>
			<td>
				Quantitation Type
			</td>
			<td>
				<html:select property="quantitationType">
					<webcgh:quantitationTypeOptions/>
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
		
	<%-- LOH threshold --%>
		<tr>
			<td>
				LOH probability threshold
			</td>
			<td>
				<html:text property="lohThreshold"/>
			</td>
		</tr>
		
	<%-- Interpolate LOH endpoints --%>
		<tr>
			<td>
				Interpolate LOH endpoints?
			</td>
			<td>
				<html:checkbox property="interpolateLohEndpoints"/>
			</td>
		</tr>
		
	<%-- Draw raw LOH probabilities --%>
		<tr>
			<td>
				Include raw LOH probabilities?
			</td>
			<td>
				<html:checkbox property="drawRawLohProbabilities"/>
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
		
	<%-- Draw data points --%>
		<tr>
			<td>
				Draw data points?
			</td>
			<td>
				<html:checkbox property="drawPoints"/>
			</td>
		</tr>
		
	<%-- Draw regression lines --%>
		<tr>
			<td>
				Draw regression lines?
			</td>
			<td>
				<html:checkbox property="drawLines"/>
			</td>
		</tr>
		
	<%-- Draw error bars --%>
		<tr>
			<td>
				Draw error bars if plotting means?
			</td>
			<td>
				<html:checkbox property="drawErrorBars"/>
			</td>
		</tr>
		
	</table>
	
	<p>
		<html:submit value="OK" onclick="onLeave();"/>
	</p>
</html:form>
</center>