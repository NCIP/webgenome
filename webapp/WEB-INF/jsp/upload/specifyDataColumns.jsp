<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>

<script language="Javascript">
	function setBioassayName(field) {
		var checkBoxId = field + "_cb";
		var checkBox = document.getElementById(checkBoxId);
		var colName = null;
		if (checkBox.checked) {
			var colTdId = field + "_col";
			colName = document.getElementById(colTdId).firstChild.nodeValue;
		} else {
			colName = "";
		}
		var textInputId = field + "_bioassay";
		var textInput = document.getElementById(textInputId);
		textInput.value = colName;
	}
</script>

<p>
	Select column containing reporter names
	and columns that contain experiment data to be loaded.
	Each experiment data column will constitute a single
	bioassay once the data are loaded.  Also, specify a
	name for each bioassay.  These may be changed later if
	desired.
</p>

<h3 align="center">File: <bean:write name="data" property="remoteFileName"/></h3>

<center>
<html:form action="/upload/attachDataFile">
<p>
	<html:errors property="global"/>
</p>

<p>
	Column containing reporter names
	<html:errors property="reporterColumnName"/>
	<html:select property="reporterColumnName">
		<html:options name="columnHeadings"/>
	</html:select>
</p>

<table>
	<tr>
		<th>Contains Data</th>
		<th>Column Name</th>
		<th>Bioassay Name</th>
	</tr>
	<logic:iterate name="columnHeadings" id="col">
	<bean:define id="col" name="col"/>
	<tr>
		<td align="center"><input type="checkbox" id="<%= col%>_cb"
			name="<%= col%>_cb"
			onclick="setBioassayName('<%= col%>')"/></td>
		<td id="<%= col%>_col"><%= col%></td>
		<td><input type="text" id="<%= col%>_bioassay" name="<%= col%>_bioassay"/></td>
	</tr>
	</logic:iterate>
</table>

<p>
	<input type="submit" value="OK" />
	<input type="button" value="Cancel" />
</p>
</html:form>
</center>