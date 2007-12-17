<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>

<script language="Javascript">
	function toggle(name, value) {
		var yes = document.getElementById(name + "_yes");
		var no = document.getElementById(name + "_no");
		var cell = document.getElementById(name + "_td");
		if (value == "yes") {
			yes.className = "circled";
			no.className = "uncircled";
			var textBox = document.createElement("input");
			textBox.setAttribute("type", "text");
			textBox.setAttribute("id", name + "_bioassay");
			textBox.setAttribute("name", name + "_bioassay");
			cell.appendChild(textBox);
		} else {
			yes.className = "uncircled";
			no.className = "circled";
			var textBox = document.getElementById(name + "_bioassay");
			cell.removeChild(textBox);
		}
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

<table class="table">
	<tr>
		<th>Contains Data</th>
		<th>Column Name</th>
		<th>Bioassay Name</th>
	</tr>
	<logic:iterate name="columnHeadings" id="col">
	<bean:define id="col" name="col"/>
	<tr>
		<td align="center">
			<span id="<%= col %>_yes" class="uncircled">
				<a href="#" onclick="toggle('<%= col %>', 'yes')">
					YES
				</a>
			</span>
			&nbsp;|&nbsp;
			<span id="<%= col %>_no" class="circled">
				<a href="#" onclick="toggle('<%= col %>', 'no')">
					NO
				</a>
			</span>
		</td>
		<td>
			<%= col%>
		</td>
		<td id="<%= col %>_td">
			&nbsp;
		</td>
	</tr>
	</logic:iterate>
</table>

<p>
	<input type="submit" value="OK" />
	<input type="button" value="Cancel" />
</p>
</html:form>
</center>