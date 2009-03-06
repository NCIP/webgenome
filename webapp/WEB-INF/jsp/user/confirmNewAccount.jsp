<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>

<h1 align="center">Account Creation Successful</h1>

<p align="center">
	An account has been created for <strong><bean:write name="account.email"/></strong><br/>
	You may now <a href="<html:rewrite page="/user/login.do"/>">Login</a>.
</p>