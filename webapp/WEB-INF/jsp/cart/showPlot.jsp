<%@ taglib uri="/WEB-INF/webcgh.tld" prefix="webcgh" %>
<%@ taglib uri="/WEB-INF/webGenome.tld" prefix="webGenome" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>

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
		View/Change Plot Parameters
	</a>
</center>

<h1 align="center"><bean:write name="plot" property="plotParameters.plotName"/></h1>

<center>
	<webGenome:plotInteractivity plotAttributeName="plot"/>
</center>