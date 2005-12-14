<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<%@ page errorPage="/WEB-INF/jsp/exception/jspException.jsp" %>

<table border="0" cellspacing="0" cellpadding="0" align="center">
	<tr align="center">
		
		<!-- CGH Data -->
		<td>
			<logic:equal name="selectedSubMenuItem" value="cgh">
				<html:link page="/cart/contents.do" styleClass="submenu-selected">
				Data</html:link>
			</logic:equal>	
			<logic:notEqual name="selectedSubMenuItem" value="cgh">
				<html:link page="/cart/contents.do" styleClass="submenu">
				Data</html:link>
			</logic:notEqual>
		</td>
				
		<td width="20"></td>
		
		<!-- Plot Parameters -->
		<td>
			<logic:equal name="selectedSubMenuItem" value="parameters">
				<html:link action="/plot/params" styleClass="submenu-selected">
				Plot Parameters</html:link>
			</logic:equal>	
			<logic:notEqual name="selectedSubMenuItem" value="parameters">
				<html:link action="/plot/params" styleClass="submenu">
				Plot Parameters</html:link>
			</logic:notEqual>
		</td>
				
		<td width="20"></td>
		
		<!-- Scatter Plot -->
		<td>
			<logic:equal name="selectedSubMenuItem" value="scatter">
				<html:link page="/plot/setup.do?plotType=scatter" styleClass="plot-submenu-selected">
				Scatter Plot</html:link>
			</logic:equal>	
			<logic:notEqual name="selectedSubMenuItem" value="scatter">
				<html:link page="/plot/setup.do?plotType=scatter" styleClass="plot-submenu">
				Scatter Plot</html:link>
			</logic:notEqual>
		</td>
		
		<td width="20"></td>
		
		<!-- Annotation Plot
		<td>
			<logic:equal name="selectedSubMenuItem" value="annotation">
				<html:link page="/plot.do?plotType=annotation" styleClass="plot-submenu-selected">
				Annotation Plot</html:link>
			</logic:equal>	
			<logic:notEqual name="selectedSubMenuItem" value="annotation">
				<html:link page="/plot.do?plotType=annotation" styleClass="plot-submenu">
				Annotation Plot</html:link>
			</logic:notEqual>
		</td>
		
		<td width="20"></td>
		
		-->
		
		<!-- Annotation Report
		<td>
			<logic:equal name="selectedSubMenuItem" value="annotation.report">
				<html:link action="/annotationReport" styleClass="plot-submenu-selected">
				Annotation Report</html:link>
			</logic:equal>	
			<logic:notEqual name="selectedSubMenuItem" value="annotation.report">
				<html:link action="/annotationReport" styleClass="plot-submenu">
				Annotation Report</html:link>
			</logic:notEqual>
		</td>
		
		<td width="20"></td>
		
		-->
		
		<!-- Ideogram Plot -->
		<td>
			<logic:equal name="selectedSubMenuItem" value="ideogram">
				<html:link page="/plot/setup.do?plotType=ideogram" styleClass="plot-submenu-selected">
				Ideogram Plot</html:link>
			</logic:equal>	
			<logic:notEqual name="selectedSubMenuItem" value="ideogram">
				<html:link page="/plot/setup.do?plotType=ideogram" styleClass="plot-submenu">
				Ideogram Plot</html:link>
			</logic:notEqual>
		</td>
		
		<td width="20"></td>
		
		<!-- Bar Plot
		<td>
			<logic:equal name="selectedSubMenuItem" value="bar">
				<html:link page="/plot/setup.do?plotType=bar" styleClass="plot-submenu-selected">
				Probe Plot</html:link>
			</logic:equal>	
			<logic:notEqual name="selectedSubMenuItem" value="bar">
				<html:link page="/plot/setup.do?plotType=bar" styleClass="plot-submenu">
				Probe Plot</html:link>
			</logic:notEqual>
		</td>
		-->
		
	</tr>
</table>
