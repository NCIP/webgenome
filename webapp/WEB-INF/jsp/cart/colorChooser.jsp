<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>

<script language="Javascript">
	function setColor(color) {
		var td = document.getElementById("selectedColor");
		td.style.backgroundColor = color;
	}
	
	function onOk() {
		var bioAssayId = "<%= pageContext.findAttribute("id") %>";
		var td = document.getElementById("selectedColor");
		var color = td.style.backgroundColor;
		if (color == null || color.length < 1) {
			color = td.getAttribute("bgcolor");
		}
		color = color.substring(1, color.length);
		var url = "<html:rewrite page="/cart/changeBioAssayColor.do"/>?id="
			+ bioAssayId + "&color=" + color;
		opener.location.href = url;
		window.close();
	}
</script>

<center>
<table border="0" cellpadding="0" cellspacing="0">
	<%
	String[][] palette = (String[][]) pageContext.findAttribute("palette");
	for (int i = 0; i < palette.length; i++) {
	%>
		<tr>
			<%
			for (int j = 0; j < palette[i].length; j++) {
			%>
				<td bgcolor="<%= palette[i][j] %>"
					onclick="setColor('<%= palette[i][j] %>')">
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				</td>
			<%
			}
			%>
		</tr>
	<%
	}
	%>

</table>

<p>
<table width="50" id="selectedColor" border="0" bgcolor="<%= palette[0][0] %>">
	<tr><td>&nbsp;</td></tr>
	<tr><td>&nbsp;</td></tr>
</table>
</p>

<p>
	<input type="button" value="OK" onclick="onOk()">
	&nbsp;&nbsp;
	<input type="button" value="Cancel" onclick="window.close()">
</p>
</center>