<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>

<center>
<html:form action="/cart/import">

	Select data type &nbsp;&nbsp;
	<html:select property="quantitationTypeId">
		<html:optionsCollection name="quantitationTypes"
			label="name" value="id"/>
	</html:select>
	
	<html:submit value="OK"/>
</html:form>
</center>