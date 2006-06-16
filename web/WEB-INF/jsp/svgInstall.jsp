<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>

<script language="JavaScript">
	function back() {
		history.go(-1);
	}
</script>

<p><br></p>

<h3 class="documentationHeader">SVG Plug-in Installation Instructions</h3>

<table width="750" border="0" align="center">
	<tr>
		<td class="documentationBody">
			<h3 class="documentationHeader">Installing SVG Viewer in Firefox on a <b>Windows</b> platform:</h3>
			<ol type="1">
				<li>Download and install Adobe SVG Viewer from <html:link href="http://www.adobe.com/svg/viewer/install/main.html">http://www.adobe.com/svg/viewer/install/main.html</html:link></li>
				<li>Using Windows Explorer browse to the <b>C:\Program Files\Common Files\Adobe</b> folder.</li>
				<li>Here you will find an <b>SVG Viewer</b> folder and inside it a <b>Plugins</b> folder.</li>
				<li>Inside the <b>Plugins</b> folder you will find 2 files: <b>NPSVG6.dll</b> and <b>NPSVG6.zip</b>.</li>
				<li>Copy both files into your Firefox plugins folder (ex: <b>C:\Program Files\Mozilla Firefox\plugins</b>)</li>
				<li>Close all Firefox windows and restart Firefox.</li>
			</ol>
		</td>
	</tr>
	<tr>
		<td class="documentationBody">
			<h3 class="documentationHeader">Installing SVG Viewer on a <b>Mac OS X</b> platform:</h3>
			<ol type="1">
				<li>Download and install Adobe SVG Viewer from <html:link href="http://www.adobe.com/svg/viewer/install/main.html">http://www.adobe.com/svg/viewer/install/main.html</html:link></li>
				<li>All browsers that support the plug-in will automatically work with the plug-in. The following browsers are confirmed to work with the plug-in: Mozilla, Netscape, Firefox, Internet Explorer.
			</ol>
		</td>
	</tr>
</table>


<p align="center">
	<input type="button" value="Back" onclick="back()">
</p>