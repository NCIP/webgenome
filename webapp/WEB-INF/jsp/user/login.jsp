<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>

<h1 align="center">Login</h1>

<center>
	<html:errors property="global"/>
	<html:form action="/user/validateLogin" focus="name">
	
	<%-- User name --%>
		<p>
			<html:errors property="name"/>
			User name: &nbsp;&nbsp; <html:text property="name"/>
		</p>
		
	<%-- Password --%>
		<p>
			<html:errors property="password"/>
			Password: &nbsp;&nbsp; <html:password property="password"/>
		</p>
		
	<%-- Submit button --%>
		<p>
			<html:submit value="OK"/>
		</p>
	</html:form>
	
	<p>
		<html:link action="/user/newAccount">Create Account</html:link>
	</p>
</center>