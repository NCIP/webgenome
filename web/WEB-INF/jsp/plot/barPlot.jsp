<%--L
   Copyright RTI International

   Distributed under the OSI-approved BSD 3-Clause License.
   See http://ncip.github.com/webgenome/LICENSE.txt for details.
L--%>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>

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
	<embed
		id="plotImg"
		src="<html:rewrite page="/showBarPlotSvg.do"/><%= WebUtils.paramListFromProps(pageContext.findAttribute("plotParamsForm")) %>"
		width="<bean:write name="plotParamsForm" property="width"/>"
		height="<bean:write name="plotParamsForm" property="height"/>"
		type="image/svg-xml"
		pluginspage="http://www.adobe.com/svg/viewer/install">
</center>