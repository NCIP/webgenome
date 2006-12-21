<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<h1 align="center">Ideogram Plot Parameters</h1>

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
		
	</table>
	
	<p>
		<html:submit value="OK" onclick="onLeave();"/>
	</p>
</html:form>
</center>