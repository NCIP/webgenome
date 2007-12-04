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

<h3 align="center">ZIP File: <bean:write name="zip" property="remoteFileName"/></h3>

<center>
<form name="colForm" action="<html:rewrite page="/upload/attachZipDataFile.do"/>">
<p>
	<html:errors property="global"/>
</p>

<p>
	<input type="submit" value="OK" />
</p>

<logic:iterate name="zip" property="zipEntryMetaData" id="meta">

	<h2><bean:write name="meta" property="remoteFileName"/></h2>
	<bean:define id="fname" name="meta" property="localFile.name"/>
	<p>
		Reporter name column heading: &nbsp;&nbsp;
		<select name="<%= fname%>_sb">
			<logic:iterate name="meta" property="columnHeadings" id="col">
				<option>
					<bean:write name="col"/>
				</option>
			</logic:iterate>
		</select>
	</p>
	<table class="table">
		<tr>
			<th>Contains Data</th>
			<th>Column Heading</th>
			<th>Bioassay Name</th>
		</tr>
		<logic:iterate name="meta" property="columnHeadings" id="col">
		<bean:define id="col" name="col"/>
		<tr>
			<td>
				<input type="checkbox" id="<%= fname%>_<%= col%>_cb"
				name="<%= fname%>_<%= col%>_cb"
				onclick="setBioassayName('<%= fname%>_<%= col%>')"/>
			</td>
			<td id="<%= fname%>_<%= col%>_col"><%= col%></td>
			<td>
				<input type="text"
					id="<%= fname%>_<%= col%>_bioassay"
					name="<%= fname%>_<%= col%>_bioassay"/>
			</td>
		</tr>
		</logic:iterate>
	</table>

</logic:iterate>

<p>
	<input type="submit" value="OK" />
</p>

</form>
</center>
