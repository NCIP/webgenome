<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>

<%@ page errorPage="/WEB-INF/jsp/exception/jspException.jsp" %>

<p><br></p>
<p><br></p>

<center>
	<p>
		<html:errors property="global"/>
	</p>
	
	<html:form action="/profile/login" focus="name">
		<table border="0" cellpadding="10">
			<tr>
				<td>
					User name
					<html:errors property="name"/>
				</td>
				<td>
					<html:text property="name"/>
				</td>
			</tr>
			<tr>
				<td>
					Password
					<html:errors property="password"/>
				</td>
				<td>
					<html:password property="password"/>
				</td>
			</tr>
		</table>
		
		<p>
			<html:submit value="Login"/>
		</p>
	</html:form>
</center>
			