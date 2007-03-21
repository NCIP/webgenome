<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>

<h1 align="center">File Upload</h1>

<center>
	<html:form action="/cart/upload" method="POST" enctype="multipart/form-data">
		File:
		<html:file property="uploadFile"/>
		<html:submit value="Upload"/>
	</html:form>
</center>