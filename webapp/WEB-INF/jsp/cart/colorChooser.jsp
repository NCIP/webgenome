<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>

<%-- Style sheet --%>
	<link href="<html:rewrite page="/webgenome.css"/>"
		rel="stylesheet" type="text/css" />

<script language="Javascript">
	function setColor(color) {
		document.getElementById("selectedColor").bgColor = color;
	}
	
	function onOk() {
		var bioAssayId = "<%= pageContext.findAttribute("id") %>";
		var color = document.getElementById("selectedColor").bgColor;
		color = color.substring(1, color.length);
		var url = "<html:rewrite page="/cart/changeBioAssayColor.do"/>?id="
			+ bioAssayId + "&color=" + color;
		opener.location.href = url;
		window.close();
	}
</script>

<br>

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
				<td width="25" height="25" bgcolor="<%= palette[i][j] %>"
					onclick="setColor('<%= palette[i][j] %>')">
					<img src="<html:rewrite page="/images/spacer.gif"/>" width="1" height="1" border="0">
				</td>
			<%
			}
			%>
		</tr>
	<%
	}
	%>

</table>

<br>
<table id="selectedColor" border="0" cellpadding="0" cellspacing="0" bgcolor="<%= palette[0][0] %>">
	<tr><td width="50" height="25"><img src="<html:rewrite page="/images/spacer.gif"/>" width="1" height="1" border="0"></td></tr>
</table>
<br>

<p>
	<input type="button" value="OK" onclick="onOk()">
	&nbsp;&nbsp;
	<input type="button" value="Cancel" onclick="window.close()">
</p>
</center>