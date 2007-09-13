<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<center>

<h2>Attach ZIP File</h2>
<html:form action="/upload/uploadZipFile" method="POST" enctype="multipart/form-data">

<p>
	<html:errors property="global"/>
</p>
<p>
	Format of enclosed data files:
	<html:radio property="fileFormat" value="CSV">CSV</html:radio>
	<html:radio property="fileFormat" value="TAB_DELIMITED">Tab delimited</html:radio>
</p>

<p>
	<html:errors property="uploadFile"/>
	File <html:file property="uploadFile"/>
</p>

<p>
	<html:submit value="Submit ZIP File"/>
	<input type="button" value="Cancel" />
</p>
</html:form>
</center>
