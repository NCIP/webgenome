<%--L
   Copyright RTI International

   Distributed under the OSI-approved BSD 3-Clause License.
   See http://ncip.github.com/webgenome/LICENSE.txt for details.
L--%>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>

<h1 align="center">Select Plot Type</h1>

<center>

<%--
	Form to select plot type.  The value of each radio
	button input must be equivalent to the name of
	some plot type defined in org.rti.webgenome.core.PlotType.
--%>
	<html:form action="/cart/plotParameters">
	
		<%-- Pass parameters to downstream JSP and action --%>
		<logic:present parameter="id">
		
			<%-- ID of existing plot containing data for new plot --%>
			<input type="hidden" name="id"
				value="<%= request.getParameter("id") %>">
				
			<%-- Instruction to action class that this is a different
			     plot of the data from an existing plot --%>
			<input type="hidden" name="diff.plot.type" value="true">
		</logic:present>

		<table cellpadding="5" cellspacing="0" border="0">

		<%-- Scatter plot --%>
			<tr class="cellOut" onMouseOver="this.className='cellOver'" onMouseOut="this.className='cellOut'" onClick="document.forms[0].plotType[0].checked=true;">
				<td>
					<html:radio property="plotType" value="GENOME_SNAPSHOT"/>
				</td>
				<td>
					<html:img page="/images/icon-scatterPlot.gif" border="1"/>
				</td>
				<td>
					Whole Genome Snapshot
				</td>
			</tr>
		
			<tr class="cellOut" onMouseOver="this.className='cellOver'" onMouseOut="this.className='cellOut'" onClick="document.forms[0].plotType[1].checked=true;">
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
			<tr class="cellOut" onMouseOver="this.className='cellOver'" onMouseOut="this.className='cellOut'" onClick="document.forms[0].plotType[2].checked=true;">
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
			<tr class="cellOut" onMouseOver="this.className='cellOver'" onMouseOut="this.className='cellOut'" onClick="document.forms[0].plotType[3].checked=true;">
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
			
		<%-- Bar plot --%>
			<tr class="cellOut" onMouseOver="this.className='cellOver'" onMouseOut="this.className='cellOut'" onClick="document.forms[0].plotType[4].checked=true;">
				<td>
					<html:radio property="plotType" value="ANNOTATION"/>
				</td>
				<td>
					<html:img page="/images/icon-annotationPlot.gif" border="1"/>
				</td>
				<td>
					Annotation Plot
				</td>
			</tr>

		</table>

		<p>
			<html:submit value="OK"/>
		</p>
	</html:form>
</center>
