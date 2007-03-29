<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/webgenome.tld" prefix="webgenome" %>

<center>
<p>
	<html:errors property="global"/>
</p>
<html:form action="/cart/newPlot" target="mainwindow">

	<logic:present parameter="id">
		<input type="hidden" name="plotId" value="<%= request.getParameter("id") %>">
	</logic:present>
	
	<table class="noBorder">
	
	<%-- ===================================== --%>
	<%--     Parameters for all plot types     --%>
	<%-- ===================================== --%>
	
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
	
	<%-- Genome intervals and units --%>
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
				<logic:present name="derivedFromAnalysis">
					<html:text property="genomeIntervals" disabled="true"/>
					<html:select property="units" disabled="true">
					<html:option value="BP">BP</html:option>
					<html:option value="KB">KB</html:option>
					<html:option value="MB">MB</html:option>
				</html:select>
				</logic:present>
				<logic:notPresent name="derivedFromAnalysis">
				<html:text property="genomeIntervals"/>
				&nbsp;
				<html:select property="units">
					<html:option value="BP">BP</html:option>
					<html:option value="KB">KB</html:option>
					<html:option value="MB">MB</html:option>
				</html:select>
				</logic:notPresent>
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
		
		<%-- =============================== --%>
		<%--   Parameters for genomic plots  --%>
		<%-- =============================== --%>
		 
		<webgenome:onlyIfGenomicPlot plotTypeBeanName="plotType">
	
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
		
	<%-- Show reporter names in mouseover --%>
		<tr>
			<td>
				<html:img styleClass="pointer"
					page="/images/Inform.gif" align="absmiddle"
					onclick="help('param-showReporterNames')"
					title="Information" border="0"
					width="15" height="15"/>
				Display reporter names<br>
				in mouseover text?
			</td>
			<td>
				<html:checkbox property="showReporterNames"/>
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
		</webgenome:onlyIfGenomicPlot>
		
		<%-- ================================== --%>
		<%--   Parameters for heat map plots    --%>
		<%-- ================================== --%>
		
		<webgenome:onlyIfHeatMapPlot plotTypeBeanName="plotType">
		
	<%-- Minimum data mask --%>
		<tr>
			<td>
				<html:img styleClass="pointer"
					page="/images/Inform.gif" align="absmiddle"
					onclick="help('param-minMask')"
					title="Information" border="0"
					width="15" height="15"/>
				Minimum data mask value
				<html:errors property="minMask"/>
			</td>
			<td>
				<html:text property="minMask"/>
			</td>
		</tr>
		
	<%-- Maximum data mask --%>
		<tr>
			<td>
				<html:img styleClass="pointer"
					page="/images/Inform.gif" align="absmiddle"
					onclick="help('param-maxMask')"
					title="Information" border="0"
					width="15" height="15"/>
				Maximum data mask
				<html:errors property="maxMask"/>
			</td>
			<td>
				<html:text property="maxMask"/>
			</td>
		</tr>
		
	<%-- Minimum saturation --%>
		<tr>
			<td>
				<html:img styleClass="pointer"
					page="/images/Inform.gif" align="absmiddle"
					onclick="help('param-minSaturation')"
					title="Information" border="0"
					width="15" height="15"/>
				Minimum saturation value
				<html:errors property="minSaturation"/>
			</td>
			<td>
				<html:text property="minSaturation"/>
			</td>
		</tr>
		
	<%-- Maximum saturation --%>
		<tr>
			<td>
				<html:img styleClass="pointer"
					page="/images/Inform.gif" align="absmiddle"
					onclick="help('param-maxSaturation')"
					title="Information" border="0"
					width="15" height="15"/>
				Maximum saturation value
				<html:errors property="maxSaturation"/>
			</td>
			<td>
				<html:text property="maxSaturation"/>
			</td>
		</tr>
		
		</webgenome:onlyIfHeatMapPlot>
		
		<%-- ================================== --%>
		<%--   Parameters for annotation plot   --%>
		<%-- ================================== --%>
		
		<webgenome:onlyIfAnnotationPlot plotTypeBeanName="plotType">
		
	<!-- Annotation types -->
		<tr>
			<td>
				<html:img styleClass="pointer"
					page="/images/Inform.gif" align="absmiddle"
					onclick="help('param-plotName')"
					title="Information" border="0"
					width="15" height="15"/>
					Annotation Types
				<html:errors property="name"/>
			</td>
			<td>
				<html:select multiple="true" property="annotationTypes" size="5">
					<html:options name="annotationTypes"/>
				</html:select>
			</td>
		</tr>
		
	<%-- Draw feature labels --%>
		<tr>
			<td>
				<html:img styleClass="pointer"
					page="/images/Inform.gif" align="absmiddle"
					onclick="help('param-plotName')"
					title="Information" border="0"
					width="15" height="15"/>
					Draw feature labels
				<html:errors property="name"/>
			</td>
			<td>
				<html:checkbox property="drawFeatureLabels"/>
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
		
		</webgenome:onlyIfAnnotationPlot>
		
		<%-- ================================== --%>
		<%--   Parameters for bar plot          --%>
		<%-- ================================== --%>
		
		<webgenome:onlyIfBarPlot plotTypeBeanName="plotType">
		
	<%-- Bar width --%>
		<tr>
			<td>
				<html:img styleClass="pointer"
					page="/images/Inform.gif" align="absmiddle"
					onclick="help('param-barWidth')"
					title="Information" border="0"
					width="15" height="15"/>
				Width of bars in pixels
				<html:errors property="barWidth"/>
			</td>
			<td>
				<html:text property="barWidth"/>
			</td>
		</tr>
		
	<%-- Plot height --%>
		<tr>
			<td>
				<html:img styleClass="pointer"
					page="/images/Inform.gif" align="absmiddle"
					onclick="help('param-rowHeight')"
					title="Information" border="0"
					width="15" height="15"/>
				Height of row of plots in pixels
				<html:errors property="height"/>
			</td>
			<td>
				<html:text property="height"/>
			</td>
		</tr>
		
		</webgenome:onlyIfBarPlot>
		
		<%-- ================================== --%>
		<%--   Parameters for ideogram plot     --%>
		<%-- ================================== --%>
		
		<webgenome:onlyIfIdeogramPlot plotTypeBeanName="plotType">
		
	<%--
	Ideogram size.  Note that values must correspond to
	ChromosomeIdeogramSize.
	--%>
		<tr>
			<td>
				<html:img styleClass="pointer"
					page="/images/Inform.gif" align="absmiddle"
					onclick="help('param-ideogramSize')"
					title="Information" border="0"
					width="15" height="15"/>
				Ideogram size
			</td>
			<td>
				<html:select property="ideogramSize">
					<html:option value="S"/>
					<html:option value="M"/>
					<html:option value="L"/>
				</html:select>
			</td>
		</tr>
		
	<%-- Ideogram width --%>
		<tr>
			<td>
				<html:img styleClass="pointer"
					page="/images/Inform.gif" align="absmiddle"
					onclick="help('param-ideogramThickness')"
					title="Information" border="0"
					width="15" height="15"/>
				Ideogram width
				<html:errors property="ideogramThickness"/>
			</td>
			<td>
				<html:text property="ideogramThickness"/>
			</td>
		</tr>
		
	<%-- Data track width --%>
		<tr>
			<td>
				<html:img styleClass="pointer"
					page="/images/Inform.gif" align="absmiddle"
					onclick="help('param-trackWidth')"
					title="Information" border="0"
					width="15" height="15"/>
				Data track width
				<html:errors property="trackWidth"/>
			</td>
			<td>
				<html:text property="trackWidth"/>
			</td>
		</tr>
		
		</webgenome:onlyIfIdeogramPlot>
		
		<%-- ================================== --%>
		<%--   Parameters for scatter plot      --%>
		<%-- ================================== --%>
		
		<webgenome:onlyIfScatterPlot plotTypeBeanName="plotType">
		
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
		
		</webgenome:onlyIfScatterPlot>
		
	</table>
	
	<p>
		<html:submit value="OK" onclick="onLeave();"/>
	</p>
</html:form>
</center>