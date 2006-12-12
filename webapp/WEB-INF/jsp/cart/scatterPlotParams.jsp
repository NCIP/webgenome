<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

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
				<html:img styleClass="pointer"
					page="/images/Inform.gif" align="absmiddle"
					onclick="help('param-plotName')"
					title="Information" border="0"
					width="15" height="15"/>
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
				<html:img styleClass="pointer"
					page="/images/Inform.gif" align="absmiddle"
					onclick="help('param-genomeIntervals')"
					title="Information" border="0"
					width="15" height="15"/>
				Genome Intervals
				<html:errors property="genomeIntervals"/>
			</td>
			<td>
				<html:text property="genomeIntervals"/>
				&nbsp;
				<html:select property="units">
					<html:option value="BP">BP</html:option>
					<html:option value="KB">KB</html:option>
					<html:option value="MB">MB</html:option>
				</html:select>
			</td>
		</tr>
		
	<%-- Number of plots per row --%>
		<tr>
			<td>
				<html:img styleClass="pointer"
					page="/images/Inform.gif" align="absmiddle"
					onclick="help('param-numPlotPerRow')"
					title="Information" border="0"
					width="15" height="15"/>
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
				<html:img styleClass="pointer"
					page="/images/Inform.gif" align="absmiddle"
					onclick="help('param-lohThreshold')"
					title="Information" border="0"
					width="15" height="15"/>
				LOH probability threshold
			</td>
			<td>
				<html:text property="lohThreshold"/>
			</td>
		</tr>
		
	<%-- Interpolate LOH endpoints --%>
		<tr>
			<td>
				<html:img styleClass="pointer"
					page="/images/Inform.gif" align="absmiddle"
					onclick="help('param-interpolateLohEndpoints')"
					title="Information" border="0"
					width="15" height="15"/>
				Interpolate LOH endpoints?
			</td>
			<td>
				<html:checkbox property="interpolateLohEndpoints"/>
			</td>
		</tr>
		
	<%-- Draw raw LOH probabilities --%>
		<tr>
			<td>
				<html:img styleClass="pointer"
					page="/images/Inform.gif" align="absmiddle"
					onclick="help('param-drawRawLohProbabilities')"
					title="Information" border="0"
					width="15" height="15"/>
				Include raw LOH probabilities?
			</td>
			<td>
				<html:checkbox property="drawRawLohProbabilities"/>
			</td>
		</tr>
		
	<%-- Minimum Y-axis value --%>
		<tr>
			<td>
				<html:img styleClass="pointer"
					page="/images/Inform.gif" align="absmiddle"
					onclick="help('param-minY')"
					title="Information" border="0"
					width="15" height="15"/>
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
				<html:img styleClass="pointer"
					page="/images/Inform.gif" align="absmiddle"
					onclick="help('param-maxY')"
					title="Information" border="0"
					width="15" height="15"/>
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
				<html:img styleClass="pointer"
					page="/images/Inform.gif" align="absmiddle"
					onclick="help('param-width')"
					title="Information" border="0"
					width="15" height="15"/>
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
				<html:img styleClass="pointer"
					page="/images/Inform.gif" align="absmiddle"
					onclick="help('param-height')"
					title="Information" border="0"
					width="15" height="15"/>
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
				<html:img styleClass="pointer"
					page="/images/Inform.gif" align="absmiddle"
					onclick="help('param-drawHorizGridLines')"
					title="Information" border="0"
					width="15" height="15"/>
				Draw horizontal grid lines?
			</td>
			<td>
				<html:checkbox property="drawHorizGridLines"/>
			</td>
		</tr>
		
	<%-- Draw vertical grid lines --%>
		<tr>
			<td>
				<html:img styleClass="pointer"
					page="/images/Inform.gif" align="absmiddle"
					onclick="help('param-drawVertGridLines')"
					title="Information" border="0"
					width="15" height="15"/>
				Draw vertical grid lines?
			</td>
			<td>
				<html:checkbox property="drawVertGridLines"/>
			</td>
		</tr>
		
	<%-- Draw data points --%>
		<tr>
			<td>
				<html:img styleClass="pointer"
					page="/images/Inform.gif" align="absmiddle"
					onclick="help('param-drawPoints')"
					title="Information" border="0"
					width="15" height="15"/>
				Draw data points?
			</td>
			<td>
				<html:checkbox property="drawPoints"/>
			</td>
		</tr>
		
	<%-- Show annotations in mouseover --%>
		<tr>
			<td>
				<html:img styleClass="pointer"
					page="/images/Inform.gif" align="absmiddle"
					onclick="help('param-showAnnotation')"
					title="Information" border="0"
					width="15" height="15"/>
				Display reporter annotations<br>
				in mouseover text?
			</td>
			<td>
				<html:checkbox property="showAnnotation"/>
			</td>
		</tr>
		
	<%-- Show gene names in mouseover --%>
		<tr>
			<td>
				<html:img styleClass="pointer"
					page="/images/Inform.gif" align="absmiddle"
					onclick="help('param-showGenes')"
					title="Information" border="0"
					width="15" height="15"/>
				Display names of nearby genes<br>
				in mouseover text?
			</td>
			<td>
				<html:checkbox property="showGenes"/>
			</td>
		</tr>
		
	<%-- Interpolation type --%>
		<tr>
			<td valign="top">
				<html:img styleClass="pointer"
					page="/images/Inform.gif" align="absmiddle"
					onclick="help('param-interpolationType')"
					title="Information" border="0"
					width="15" height="15"/>
				Type of interpolation between data points
			</td>
			<td valign="top">
				<table border="0" cellpadding="5">
				<tr><td valign="middle">
				<html:radio property="interpolationType" value="NONE"/>
				<html:img page="/images/no-interpolation.png" border="1" align="middle"/>
				None
				</td></tr>
				<tr><td>
				<html:radio property="interpolationType" value="STRAIGHT_LINE"/>
				<html:img page="/images/straight-line-interpolation.png" border="1" align="middle"/>
				Straight line
				</td></tr>
				<tr><td>
				<html:radio property="interpolationType" value="STEP"/>
				<html:img page="/images/step-interpolation.png" border="1" align="middle"/>
				Step
				</td></tr>
				<tr><td>
				<html:radio property="interpolationType" value="SPLINE"/>
				<html:img page="/images/spline-interpolation.png" border="1" align="middle"/>
				Spline
				</td></tr>
				</table>
			</td>
		</tr>
		
	<%-- Draw error bars --%>
		<tr>
			<td>
				<html:img styleClass="pointer"
					page="/images/Inform.gif" align="absmiddle"
					onclick="help('param-drawErrorBars')"
					title="Information" border="0"
					width="15" height="15"/>
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