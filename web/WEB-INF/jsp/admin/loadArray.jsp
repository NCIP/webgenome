<%--L
   Copyright RTI International

   Distributed under the OSI-approved BSD 3-Clause License.
   See http://ncip.github.com/webgenome/LICENSE.txt for details.
L--%>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>

<p><br></p>

<h3>Load Probe Set</h3>

<html:form action="/admin/loadArray" method="POST" enctype="multipart/form-data">

	<table>
		<tr>
			<td>Array Vendor</td>
			<td>
				<html:errors property="arrayVendor"/>
				<html:text property="arrayVendor"/>
			</td>
		</tr>
		<tr>
			<td>Array Name</td>
			<td>
				<html:errors property="arrayName"/>
				<html:text property="arrayName"/>
			</td>
		</tr>
		
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
			<td>Assembly</td>
			<td>
				<html:errors property="assembly"/>
				<html:text property="assembly"/>
			</td>
		</tr>
		<tr>
			<td>Probe Name Column</td>
			<td>
				<html:errors property="nameCol"/>
				<html:text property="nameCol" size="2"/>
			</td>
		</tr>
		<tr>
			<td>Chromosome Column</td>
			<td>
				<html:errors property="chromCol"/>
				<html:text property="chromCol" size="2"/>
			</td>
		</tr>
		<tr>
			<td>Chromosome Start Column</td>
			<td>
				<html:errors property="startCol"/>
				<html:text property="startCol" size="2"/>
			</td>
		</tr>
		<tr>
			<td>Chromosome End Column</td>
			<td>
				<html:errors property="endCol"/>
				<html:text property="endCol" size="2"/>
			</td>
		</tr>
		<tr>
			<td>File</td>
			<td>
				<html:errors property="formFile"/>
				<html:file property="formFile" size="50"/>
			</td>
	</table>

	<html:submit value="Load"/>

</html:form>
