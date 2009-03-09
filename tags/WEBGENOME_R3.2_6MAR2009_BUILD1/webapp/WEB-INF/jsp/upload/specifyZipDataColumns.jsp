<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>


<script language="Javascript">
	var xOffset = 0;
	var yOffset = 0;
	function toggle(name, value) {
		var yes = document.getElementById(name + "_yes");
		var no = document.getElementById(name + "_no");
		var cell = document.getElementById(name + "_td");
		if (value == "yes") {
			xOffset = document.body.scrollLeft || window.pageXOffset;
			yOffset = document.body.scrollTop || window.pageYOffset;
			yes.className = "circled";
			no.className = "uncircled";
			var textBox = document.createElement("input");
			textBox.setAttribute("type", "text");
			textBox.setAttribute("id", name + "_bioassay");
			textBox.setAttribute("name", name + "_bioassay");
			cell.appendChild(textBox);
			setTimeout("resetViewport()", 500);
		} else {
			yes.className = "uncircled";
			no.className = "circled";
			var textBox = document.getElementById(name + "_bioassay");
			cell.removeChild(textBox);
		}
	}
	
	function resetViewport() {
		document.body.scrollLeft = xOffset;
		document.body.scrollTop = yOffset;
		window.pageXOffset = xOffset;
		window.pageYOffset = yOffset;
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
			<td align="center">
				<span id="<%= fname%>_<%= col %>_yes" class="uncircled">
					<a href="#" onclick="toggle('<%= fname%>_<%= col %>', 'yes')">
						YES
					</a>
				</span>
				&nbsp;|&nbsp;
				<span id="<%= fname%>_<%= col %>_no" class="circled">
					<a href="#" onclick="toggle('<%= fname%>_<%= col %>', 'no')">
						NO
					</a>
				</span>
			</td>
			<td><%= col%></td>
			<td id="<%= fname%>_<%= col %>_td">
				&nbsp;
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
