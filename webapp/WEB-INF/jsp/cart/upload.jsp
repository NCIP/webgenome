<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>

<h1 align="center">File Upload</h1>

<center>
	<html:form action="/cart/upload" method="POST" enctype="multipart/form-data">
		<p>
			Organism:
			<html:select property="organismId">
				<html:options collection="organisms" property="id"
					labelProperty="displayName"/>
			</html:select>
			&nbsp;&nbsp;&nbsp;
			File:
			<html:file property="uploadFile"/>
		</p>
		
		<p>
			<html:submit value="Upload"/>
		</p>
	</html:form>
</center>