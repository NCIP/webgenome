<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/webcgh.tld" prefix="webcgh" %>

<%@ page import="org.rti.webcgh.webui.util.WebUtils" %>

<%@ page errorPage="/WEB-INF/jsp/exception/jspException.jsp" %>

<script language="JavaScript">
	function resizeImage(width, height) {
		var image = document.getElementById("plotImg");
		image.setAttribute("width", width);
		image.setAttribute("height", height);
	}
</script>

<table border="0" cellpadding="0" cellspacing="0" align="center">
	<tr>
		<td>
			<html:form action="/plot/setup">
				<webcgh:beanPropsToHiddenFields name="plotParamsForm"
					exclusions="genomeIntervals,units,plotType"/>
				<html:hidden property="plotType" value="ideogram"/>
				<html:img styleClass="pointer" 
					page="/images/helpicon.gif" align="absmiddle" 
					onclick="help('param-genomeIntervals')"/>
				Genome Interval &nbsp;&nbsp;
				<html:text property="genomeIntervals"/>
				<html:select name="plotParamsForm" property="units">
   						<webcgh:unitOptions name="plotParamsForm" property="units"/>
   				</html:select>
				&nbsp;&nbsp;<html:submit value="Go"/>
   			</html:form>
   		</td>
	</tr>
</table>

<center>
	<embed
		id="plotImg"
		src="<html:rewrite page="/plot/svg.do"/><%= WebUtils.paramListFromProps(pageContext.findAttribute("plotParamsForm")) %>"
		width="<bean:write name="plotParamsForm" property="width"/>"
		height="<bean:write name="plotParamsForm" property="height"/>"
		type="image/svg-xml"
		pluginspage="http://www.adobe.com/svg/viewer/install">
</center>

<br>

<table border="0" cellpadding="0" cellspacing="0" align="center">
	<tr>
		<td>
			<html:form action="/plot/setup">
				<webcgh:beanPropsToHiddenFields name="plotParamsForm"
					exclusions="genomeIntervals,units,plotType"/>
				<html:hidden property="plotType" value="ideogram"/>
				<html:img styleClass="pointer" 
					page="/images/helpicon.gif" align="absmiddle" 
					onclick="help('param-genomeIntervals')"/>
				Genome Interval &nbsp;&nbsp;
				<html:text property="genomeIntervals"/>
				<html:select name="plotParamsForm" property="units">
   						<webcgh:unitOptions name="plotParamsForm" property="units"/>
   				</html:select>
				&nbsp;&nbsp;<html:submit value="Go"/>
   			</html:form>
   		</td>
	</tr>
</table>