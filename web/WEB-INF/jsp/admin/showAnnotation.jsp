<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<%@ page import="java.util.Map" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="org.rti.webcgh.etl.AnnotationTypeAssemblyPair" %>

<p><br></p>

<h3>Annotated Feature Types</h3>

<table border="1" cellpadding="5">
	<tr>
		<td><b>Organism</b></td>
		<td><b>Assembly</b></td>
		<td><b>Feature Type</b></td>
		<td></td>
	</tr>
	
	<logic:iterate name="annotationTypeAssemblyPairs" id="pair">
		<tr>
			<td><bean:write name="pair" property="genomeAssembly.organism.displayName"/></td>
			<td><bean:write name="pair" property="genomeAssembly.name"/></td>
			<td><bean:write name="pair" property="annotationType"/></td>
			<td>
				<% 
					AnnotationTypeAssemblyPair annotationTypeAssemblyPair = (AnnotationTypeAssemblyPair)
						pageContext.findAttribute("pair");
					Map paramMap = new HashMap();
					paramMap.put("assemblyId", new Long(annotationTypeAssemblyPair.getGenomeAssembly().getId()));
					paramMap.put("featureType", annotationTypeAssemblyPair.getAnnotationType());
					request.setAttribute("paramMap", paramMap);
				%>
				<html:link action="/admin/deleteAnnotation" name="paramMap">
					Delete
				</html:link>
			</td>
		</tr>
	</logic:iterate>
</table>

<p>
	<html:link action="/admin/loadAnnotationForm">Load Annotation Data</html:link>&nbsp;&nbsp;&nbsp;
	<%
		String all = "true";
		request.setAttribute("all", all);
	%>
	<html:link action="/deleteAnnotation" paramId="all" paramName="all">Delete All</html:link>
</p>