<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>

<%-- Style sheet --%>
	<link href="<html:rewrite page="/webcgh.css"/>"
		rel="stylesheet" type="text/css" />

<script language="Javascript">
	// dec 2 hex conversion
	var hD="0123456789ABCDEF";
	function d2h(d) {
		var h = hD.substr(d&15, 1);
		while(d > 15) {
			d >>= 4;
			h = hD.substr(d&15, 1) + h;
		}
		return h;
	}
	function padZero(h) {
		if(h.length < 2) return '0' + h;
		else return h;
	}

	// hex 2 dec conversion
	function h2d(h) {
		return parseInt(h,16);
	}

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

		// check if color is in rgb format and convert to hex if so
		if(color.substring(0,3) == 'rgb') {
			var rgbString = color.substring(4, color.length-1);
			var rgb = rgbString.split(',');
			color = '#' + padZero(d2h(rgb[0])) + padZero(d2h(rgb[1])) + padZero(d2h(rgb[2]));
		}

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