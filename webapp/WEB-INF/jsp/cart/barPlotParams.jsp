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
		
	<%-- Plot height --%>
		<tr>
			<td>
				<html:img styleClass="pointer"
					page="/images/Inform.gif" align="absmiddle"
					onclick="help('param-height')"
					title="Information" border="0"
					width="15" height="15"/>
				Height of row of plots in pixels
				<html:errors property="height"/>
			</td>
			<td>
				<html:text property="height"/>
			</td>
		</tr>
		
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
	</table>
		
	<p>
		<html:submit value="OK" onclick="onLeave();"/>
	</p>
</html:form>
</center>