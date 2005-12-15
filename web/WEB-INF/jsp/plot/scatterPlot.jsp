<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/webcgh.tld" prefix="webcgh"%>

<%@ page import="org.rti.webcgh.webui.util.WebUtils" %>

<%@ page errorPage="/WEB-INF/jsp/exception/jspException.jsp" %>

<script language="JavaScript">
	function resizeImage(width, height) {
		var image = document.getElementById("plotImg");
		image.setAttribute("width", width);
		image.setAttribute("height", height);
	}
</script>

<center>
			
	Show:
	
	&nbsp;&nbsp;&nbsp;
	<input type="checkbox" checked
		onclick="showLines(this.checked)"
	>
	Lines
		
	&nbsp;&nbsp;&nbsp;		
	<input type="checkbox" checked
		onclick="showPoints(this.checked)">				
	Data points
			
	<logic:equal value="yes" name="average">
		&nbsp;&nbsp;&nbsp;
		<input type="checkbox" checked
			onclick="showErrorBars(this.checked)">
		95% confidence intervals<br>
	</logic:equal>
	
	<br>
		
<embed
	id="plotImg"
	src="<html:rewrite page="/plot/svg.do"/><%= WebUtils.paramListFromProps(pageContext.findAttribute("plotParamsForm")) %>";
	width="<bean:write name="plotParamsForm" property="width"/>"
	height="<bean:write name="plotParamsForm" property="height"/>"
	type="image/svg-xml"
	pluginspage="http://www.adobe.com/svg/viewer/install/">

<br>
    	
<table border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td>
			<html:form action="/plot/setup">
				<webcgh:beanPropsToHiddenFields name="plotParamsForm"
					exclusions="genomeIntervals,units,minY,maxY"/>
				<html:img styleClass="pointer" 
					page="/images/helpicon.gif" align="absmiddle" 
					onclick="help('param-genomeIntervals')"/>
				Genome Interval &nbsp;&nbsp;
				<html:text property="genomeIntervals"/>
				<html:select property="units">
					<webcgh:unitOptions name="plotParamsForm" property="units"/>
				</html:select>&nbsp;&nbsp;&nbsp;
				Minimum Y &nbsp;
				<html:text property="minY" size="4" maxlength="20"/>&nbsp;&nbsp;&nbsp;
				Maximum Y &nbsp;
				<html:text property="maxY" size="4" maxlength="20"/>&nbsp;&nbsp;
				<html:submit value="Go"/>
   			</html:form>
   		</td>
	</tr>
</table>

</center>