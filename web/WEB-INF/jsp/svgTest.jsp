<%--L
   Copyright RTI International

   Distributed under the OSI-approved BSD 3-Clause License.
   See http://ncip.github.com/webgenome/LICENSE.txt for details.
L--%>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>

<script language="JavaScript">
	function back() {
		history.go(-1);
	}
</script>

<p><br></p>
<p><br></p>

<center>
	<embed 
		src="<html:rewrite page="/images/svgTest.svg"/>"
		width="250"
		height="50"
		type="image/svg-xml" 
		pluginspage="http://www.adobe.com/svg/viewer/install/">
</center>

<table width="750" border="0" align="center">
	<tr>
		<td class="documentationBody">
			If you cannot see the message
			<span class="red">You have an SVG viewer</span>
			in the above image your browser does not have SVG support.
			A plugin is available from Adobe. 
			[<html:link href="http://www.adobe.com/svg/viewer/install/main.html">
				Download
			</html:link>]
		</td>
	</tr>
</table>


<p align="center">
	<input type="button" value="Back" onclick="back()">
</p>