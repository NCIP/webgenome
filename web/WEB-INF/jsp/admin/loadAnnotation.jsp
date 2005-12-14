<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>

<p><br></p>

<h3>Load Annotation</h3>

<html:form action="/loadAnnotation" method="POST" enctype="multipart/form-data">

	<table>
		<tr>
			<td>Feature Type Name</td>
			<td>
				<html:errors property="featureTypeName"/>
				<html:text property="featureTypeName"/>
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
			<td>Feature Name Column</td>
			<td>
				<html:errors property="nameCol"/>
				<html:text property="nameCol" size="2"/>
			</td>
		</tr>
		<tr>
			<td>Feature Chromosome Column</td>
			<td>
				<html:errors property="chromCol"/>
				<html:text property="chromCol" size="2"/>
			</td>
		</tr>
		<tr>
			<td>Feature Start Column</td>
			<td>
				<html:errors property="startCol"/>
				<html:text property="startCol" size="2"/>
			</td>
		</tr>
		<tr>
			<td>Feature End Column</td>
			<td>
				<html:errors property="endCol"/>
				<html:text property="endCol" size="2"/>
			</td>
		</tr>
		<tr>
			<td>Features Are Genes?</td>
			<td>
				<html:checkbox property="geneSwitch"/>
			</td>
		</tr>
		<tr>
			<td>Exon Starts Column</td>
			<td>
				<html:errors property="exonStartsCol"/>
				<html:text property="exonStartsCol" size="2"/>
			</td>
		</tr>
		<tr>
			<td>Exon Ends Column</td>
			<td>
				<html:errors property="exonEndsCol"/>
				<html:text property="exonEndsCol" size="2"/>
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
