<%--L
   Copyright RTI International

   Distributed under the OSI-approved BSD 3-Clause License.
   See http://ncip.github.com/webgenome/LICENSE.txt for details.
L--%>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>

<h3>Load SKY/M-FISH&CGH Data</h3>

<p>(Data must be in tab-delimited text file)</p>

<p>
	<html:errors property="global"/>
</p>
<html:form action="/admin/loadSkyData" method="POST" enctype="multipart/form-data">

	<table>
		<tr>
			<td>Genus</td>
			<td>
				<html:errors property="genus"/>
				<html:text property="genus"/>
			</td>
		</tr>
		<tr>
			<td>Species</td>
			<td>
				<html:errors property="species"/>
				<html:text property="species"/>
			</td>
		</tr>
		<tr>
			<td>Contact</td>
			<td>
				<html:errors property="contact"/>
				<html:text property="contact"/>
			</td>
		</tr>
		<tr>
			<td>ESI File</td>
			<td>
				<html:errors property="formFile"/>
				<html:file property="formFile" size="50"/>
			</td>
		</tr>
	</table>
	
	<p><br></p>
	
	<p>
		<html:submit value="Load"/>
	</p>

</html:form>