<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>

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

<h3>Step 1: Attach one or more data files</h3>
<p>
	<logic:iterate name="upload" property="dataFileMetaData" id="meta">
		<bean:write name="meta" property="remoteFileName"/><br>
	</logic:iterate>
</p>
<html:link action="/upload/dataFileUploadForm">
	Attach file
</html:link>

<h3>Step 2 (optional): Attach a file containing reporter annotations</h3>
<html:link action="/upload/reporterFileUploadForm">
	Attach file
</html:link>

<h3>Step 3: Specify reporter data column names</h3>
Chromosome <input type="text" /><br />
Position <input type="text" /><br />
Units
<select size="3">
	<option>BP</option>
	<option>KB</option>
	<option>MB</option>
</select>

<h3>Step 4: Specify experiment metadata</h3>
Experiment name <input type="text" /><br />
Organism
<select>
	<option>Homo sapiens</option>
	<option>Mus musculus</option>
</select>

<p>
	<input type="submit" value="Upload" />
	<input type="button" value="Cancel" />
</p>