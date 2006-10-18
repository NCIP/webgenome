<%@ taglib uri="/WEB-INF/webcgh.tld" prefix="webcgh" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>

<h1 align="center"><bean:write name="plot" property="name"/></h1>

<p align="center">
	<webcgh:showPlot name="plot"/>
</p>