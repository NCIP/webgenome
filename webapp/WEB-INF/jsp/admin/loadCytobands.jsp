<%--L
   Copyright RTI International

   Distributed under the OSI-approved BSD 3-Clause License.
   See http://ncip.github.com/webgenome/LICENSE.txt for details.
L--%>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>

<h1 align="center">Load Cytobands</h1>

<h2 align="center">Uploaded Cytobands</h2>
<center>
<table class="table">
<tr>
<th colspan="2">Organism</th>
</tr>
<logic:iterate name="organismsWithMap" id="org">
	<tr>
	<td>
		<bean:write name="org" property="displayName"/>
	</td>
	<td>
		<html:link action="/admin/deleteCytobands" paramId = "organismId"
			paramName="org" paramProperty="id">
			Delete
		</html:link>
	</td>
	</tr>
</logic:iterate>
</table>
</center>

<center>
<p>
<html:errors property="global"/>
</p>
<html:form action="/admin/loadCytobands" enctype="multipart/form-data"
	method="POST">
	<p>
		<html:select property="organismId">
			<html:options collection="organisms" property="id"
				labelProperty="displayName"/>
		</html:select>
	</p>
	
	<p>
		<html:errors property="formFile"/>
	    <html:file property="formFile"/>
    </p>
    
    <p>
		<html:submit value="Upload"/>
	</p>
</html:form>
</center>