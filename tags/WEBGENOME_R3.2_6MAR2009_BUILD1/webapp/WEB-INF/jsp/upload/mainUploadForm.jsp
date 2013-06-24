<%--L
   Copyright RTI International

   Distributed under the OSI-approved BSD 3-Clause License.
   See http://ncip.github.com/webgenome/LICENSE.txt for details.
L--%>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>

<h1 align="center">Import Data</h1>

WebGenome needs data about reporters (probes) as well as
actual data values in order to construct plots.  All data for upload must
be in "rectangular" text files in either CSV (comma-separated values) or
tab delimited format.  With the exception of the first row, each row should
correspond to a single reporter.  The first row should contain column
headings.  Reporter data and experimental data may
reside in the same file or separate files.  Files containing reporter data
must include columns containing reporter names, chromosome numbers, and
physical locations.  Experimental data files may
include data from one or more bioassays.  The column headings must be
unique across all experiment files and are intended to provide an
identifier for the corresponding bioassay data.  All data uploaded
in a single batch will be associated with the same experiment.  If
reporter data are uploaded in a separate file, all experiment data files
must be based on the same array design as the reporter data file, i.e. the
number of rows must be the same and the order of reporters the same.

<p>
	<html:errors property="global"/>
</p>

<html:form action="/upload/uploadData">
<h3>Step 1: Attach one or more data files</h3>
<p>
	<logic:iterate name="upload" property="dataFileMetaData" id="meta">
	<p>
		<b>File</b>&nbsp;<bean:write name="meta" property="remoteFileName"/>
		&nbsp; [<html:link action="/upload/removeDataFile" paramId="file" paramName="meta" paramProperty="localFileName">
			Remove</html:link>]<br />
		<b>Format</b>&nbsp;<bean:write name="meta" property="format"/><br />
		<b>Reporter Column</b>&nbsp;<bean:write name="meta" property="reporterNameColumnName"/><br />
		<b>Data Columns</b>
		<ul>
		<logic:iterate name="meta" property="dataColumnMetaData" id="colMeta">
			<li><bean:write name="colMeta" property="columnName"/> (bioassay name: '<bean:write name="colMeta" property="bioAssayName"/>')</li>
		</logic:iterate>
		</ul>
	</p>
	</logic:iterate>
</p>
<html:link action="/upload/dataFileUploadForm">
	Attach individual data file
</html:link>
&nbsp;&nbsp;
<html:link action="/upload/zipFileUploadForm">
	Attach ZIP file containing multiple data files
</html:link>

<h3>Step 2 (optional): Attach a file containing reporter annotations</h3>

<logic:present name="upload" property="reporterLocalFileName">
	<b>File</b>&nbsp;<bean:write name="upload" property="reporterRemoteFileName"/>
	&nbsp; [<html:link action="/upload/removeReporterFile">
		Remove</html:link>]<br />
	<b>Format</b>&nbsp;<bean:write name="upload" property="reporterFileFormat"/><br />
	<b>Reporter Column</b>&nbsp;<bean:write name="upload" property="reporterFileReporterNameColumnName"/><br />
</logic:present>

<logic:notPresent name="upload" property="reporterLocalFileName">
	<html:link action="/upload/reporterFileUploadForm">
		Attach file
	</html:link>
</logic:notPresent>

<h3>Step 3: Specify reporter data column names</h3>

<p>
Chromosome
<html:select property="chromosomeColumnName">
	<html:options name="allCols"/>
</html:select>
</p>

<p>
Position
<html:select property="positionColumnName">
    <option value=""/>
	<html:options name="allCols"/>
</html:select>&nbsp;

 <b>OR</b>
 
 &nbsp;
 Start Position
<html:select property="startPositionColumnName">
    <option value=""/>
	<html:options name="allCols"/>
</html:select>&nbsp;
End Position
<html:select property="endPositionColumnName">
    <option value=""/>
	<html:options name="allCols"/>
</html:select>&nbsp;
 
 
 
 </p>
 <p>
 
Units
<html:select property="units">
	<html:options name="units"/>
</html:select>
</p>

<h3>Step 4: Specify experiment metadata</h3>
<p>
<html:errors property="experimentName"/>
Experiment name <html:text property="experimentName"/><br />
</p>

<p>
Organism
<html:select property="organismId">
	<html:options collection="organisms" property="id"
		labelProperty="displayName"/>
</html:select>
</p>

<p>
Quantitation type
<html:select property="quantitationTypeId">
	<html:options collection="qTypes" property="id"
		labelProperty="name"/>
</html:select>

<%--Other:
<% if (true){%>
	<html:text property="quantitationTypeOther" disabled="true" />    
<%}else{%>
    <html:text property="quantitationTypeOther"/>
<%}%>--%>
	
</p>



<p>
	<html:submit property="OK"/>
	<input type="button" value="Cancel" onClick="window.location.href='<html:rewrite page="/upload/mainImport.do"/>'"/>
</p>
</html:form>

<script type="text/javascript" language="JavaScript">
function onQuantitaionType(){
      
      var elOtherQuantiationTypeId = document.getElementById("quantitationTypeId");
      var quantitationTypeOther = document.getElementById("quantitationTypeOther");
      
      alert(elOtherQuantiationTypeId.value);
      
      if ( elOtherQuantiationTypeId.value == "Other"){      	
      	quantitationTypeOther.disabled = false;
      }
    }
    
</script>    