<%@ taglib uri="/WEB-INF/webcgh.tld" prefix="webcgh" %>
<%@ taglib uri="/WEB-INF/webGenome.tld" prefix="webGenome" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>

<center>
	<html:link action="/cart/changeParameters" paramId="id"
		paramName="plot" paramProperty="id">
		View/Change Plot Parameters
	</html:link>
</center>

<h1 align="center"><bean:write name="plot" property="plotParameters.plotName"/></h1>

<p align="center">
	<webGenome:plotInteractivity plotAttributeName="plot"/>
</p>