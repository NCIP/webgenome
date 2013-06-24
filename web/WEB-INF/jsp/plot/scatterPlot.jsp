<%--L
   Copyright RTI International

   Distributed under the OSI-approved BSD 3-Clause License.
   See http://ncip.github.com/webgenome/LICENSE.txt for details.
L--%>

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

<script language="JavaScript">
<!--
function newWindow(file,window) {
    msgWindow=open(file,window,'resizable=yes,width=800,height=500,status=1,scrollbars=1');
    if (msgWindow.opener == null) msgWindow.opener = self;
}
//-->
</script>

<%-- BEGIN: BLOCK NEEDED FOR SAVE AS FUNCTION --%>
<script language="JavaScript1.2" src="<html:rewrite page="/js/plot/svgSaveAsImage.js"/>" type="text/javascript"></script>
<%-- THIS FORM MUST EXIST TO ALLOW THE PLOT TO BE SAVED AS A GRAPHIC --%>
<html:form action="/plot/saveAs" target="plotSaveAs">
	<html:hidden property="svgDOM" value=""/>
</html:form>
<%-- END: BLOCK NEEDED FOR SAVE AS FUNCTION --%>

<center>

	<!-- Links -->
	<%-- BEGIN: SAVE AS LINK --%>
   	<div style="margin-top:0px;padding-top:0px;">
   	<script language="JavaScript">
   	renderSaveAsLink( );
   	</script>
   	</div>&nbsp;&nbsp;
   	<%-- END: SAVE AS LINK --%>
	<a class="actionLink" href="javascript:newWindow('<html:rewrite page="/configPlotParams.do?plotType=scatter"/>','scatterParams')">Plot Parameters</a>&nbsp;&nbsp;

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

</center>
