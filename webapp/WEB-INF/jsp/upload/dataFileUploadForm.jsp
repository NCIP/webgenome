<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<center>
<h2>Attach Data File</h2>
<html:form action="/upload/uploadDataFile" method="POST" enctype="multipart/form-data">

<p>
	<html:errors property="global"/>
</p>
<p>
	File format:
	<html:radio property="fileFormat" value="CSV">CSV</html:radio>
	<html:radio property="fileFormat" value="TAB_DELIMITED">Tab delimited</html:radio>
</p>

<p>
	<html:errors property="uploadFile"/>
	File <html:file property="uploadFile"/>
</p>

<p>
	<html:submit value="Attach File"/>
	<input type="button" value="Cancel" onClick="window.location.href='<html:rewrite page="/upload/initializeUploadForm.do"/>'"/>
</p>
</html:form>
</center>