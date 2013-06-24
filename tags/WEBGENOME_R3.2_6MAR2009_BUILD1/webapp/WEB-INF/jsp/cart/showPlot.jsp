<%--L
   Copyright RTI International

   Distributed under the OSI-approved BSD 3-Clause License.
   See http://ncip.github.com/webgenome/LICENSE.txt for details.
L--%>

<%@ taglib uri="/WEB-INF/webgenome.tld" prefix="webGenome" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<script language="JavaScript">
	function openPopUpWindow(url) {
		window.open(
			url + "&makePopUp=true",
			"popupwindow", 
			"width=500, height=400, menubar=no, status=no, scrollbars=yes, resizable=yes, toolbar=no, location=no, directories=no"
		);
	}
</script>

<center>
	<a href="javascript:openPopUpWindow('<html:rewrite page="/cart/changeParameters.do" paramId="id"
		paramName="plot" paramProperty="id"/>');">
		Change Plot Parameters
	</a>
	<logic:present name="analysis.params">
	&nbsp;&nbsp;
	<a href="javascript:openPopUpWindow('<html:rewrite page="/cart/adjustPlotAnalysisParamsSetup.do" paramId="id"
		paramName="plot" paramProperty="id"/>');">
		Change Analytic Parameters
	</a>
	</logic:present>
	&nbsp;&nbsp;
	<html:link action="/cart/derivedPlot" paramId="id" paramName="plot"
		paramProperty="id">
		New Plot of these Data
	</html:link>
</center>

<h1 align="center"><bean:write name="plot" property="plotParameters.plotName"/></h1>

<center>
	<webGenome:plotInteractivity plotAttributeName="plot"/>
</center>