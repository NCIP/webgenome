<%--L
   Copyright RTI International

   Distributed under the OSI-approved BSD 3-Clause License.
   See http://ncip.github.com/webgenome/LICENSE.txt for details.
L--%>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<h1 align="center">Import Data</h1>

<p>&nbsp;</p>

<p align="center">
	<html:link action="/upload/initializeUploadForm">
		Upload SMD or MAGE-TAB data
	</html:link>
</p>

<p align="center">
	<logic:iterate name="data.sources.index" id="dataSource">
		<html:link action="/upload/initRemoteSession"
			paramId="dataSourceKey" paramName="dataSource"
			paramProperty="key">
			Import data from <bean:write name="dataSource"
				property="value.displayName"/>
		</html:link>
	</logic:iterate>
</p>