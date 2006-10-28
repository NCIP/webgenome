<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>

<h1 align="center">Ideogram Plot Parameters</h1>

<center>
<p>
	<html:errors property="global"/>
</p>
<html:form action="/cart/newPlot" target="mainwindow">
	
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
		
	<%--
	Ideogram size.  Note that values must correspond to
	ChromosomeIdeogramSize.
	--%>
		<tr>
			<td>
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
				Ideogram width
				<html:errors property="ideogramThickness"/>
			</td>
			<td>
				<html:text property="ideogramThickness"/>
			</td>
		</tr>
		
	<%-- Minimum data mask --%>
		<tr>
			<td>
				Minimum data mask
				<html:errors property="minMask"/>
			</td>
			<td>
				<html:text property="minMask"/>
			</td>
		</tr>
		
	<%-- Maximum data mask --%>
		<tr>
			<td>
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
				Minimum saturation
				<html:errors property="minSaturation"/>
			</td>
			<td>
				<html:text property="minSaturation"/>
			</td>
		</tr>
		
	<%-- Maximum saturation --%>
		<tr>
			<td>
				Maximum saturation
				<html:errors property="maxSaturation"/>
			</td>
			<td>
				<html:text property="maxSaturation"/>
			</td>
		</tr>
		
	<%-- Data track width --%>
		<tr>
			<td>
				Data track width
				<html:errors property="trackWidth"/>
			</td>
			<td>
				<html:text property="trackWidth"/>
			</td>
		</tr>
		
	</table>
	
	<p>
		<html:submit value="OK" onclick="onLeave();"/>
	</p>
</html:form>
</center>