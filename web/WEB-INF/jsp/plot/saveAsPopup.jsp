<%--L
   Copyright RTI International

   Distributed under the OSI-approved BSD 3-Clause License.
   See http://ncip.github.com/webgenome/LICENSE.txt for details.
L--%>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/webcgh.tld" prefix="webcgh" %>
<%--

	This popup presents options for allowing the user to save their current plot.
	Javascript functions exist to toggle the various states.
	When the user clicks Submit, the window submits and then sets a timeout to
	actually close itself; it closes itself to avoid usability confusion about
	which Save As dialogue this particular page is for etc.
	
--%>
<%@ page errorPage="/WEB-INF/jsp/exception/jspException.jsp" %>
<%@ page import="org.rti.webcgh.webui.plot.SaveAsForm" %>
<style type="text/css">
<!--
table td.label { text-align: right; padding-right: 10px;}
-->
</style>

<script language="JavaScript">

	// toggle the height
	function heightChange (  value ) {
		var height = window.document.plotSaveAsForm.height.value ;
		var width  = window.document.plotSaveAsForm.width.value ;
		if ( height == "<%=SaveAsForm.RELATIVE_STRING%>" && width == "<%=SaveAsForm.RELATIVE_STRING%>" ) {
			window.document.plotSaveAsForm.width.value = "<%=SaveAsForm.ASIS_STRING%>" ;
			window.document.plotSaveAsForm.height.value = "<%=SaveAsForm.ASIS_STRING%>" ;
		}
		else if ( height != "<%=SaveAsForm.RELATIVE_STRING%>" && height != "<%=SaveAsForm.ASIS_STRING%>" )
			window.document.plotSaveAsForm.width.value = "<%=SaveAsForm.RELATIVE_STRING%>" ;
		else if ( height == "<%=SaveAsForm.ASIS_STRING%>" )
			window.document.plotSaveAsForm.width.value = "<%=SaveAsForm.ASIS_STRING%>" ;
	}

    // toggle the width
	function widthChange ( value ) {
		var height = window.document.plotSaveAsForm.height.value ;
		var width  = window.document.plotSaveAsForm.width.value ;
		if ( height == "<%=SaveAsForm.RELATIVE_STRING%>" && width == "<%=SaveAsForm.RELATIVE_STRING%>" ) {
			window.document.plotSaveAsForm.width.value = "<%=SaveAsForm.ASIS_STRING%>" ;
			window.document.plotSaveAsForm.height.value = "<%=SaveAsForm.ASIS_STRING%>" ;
		}
		else if ( width != "<%=SaveAsForm.RELATIVE_STRING%>" && width != "<%=SaveAsForm.ASIS_STRING%>" )
			window.document.plotSaveAsForm.height.value = "<%=SaveAsForm.RELATIVE_STRING%>" ;
		else if ( width == "<%=SaveAsForm.ASIS_STRING%>" )
			window.document.plotSaveAsForm.height.value = "<%=SaveAsForm.ASIS_STRING%>" ;
	}
	
	// control toggling for the image type, for PNG images, quality can't be set
	function imgTypeChange ( value ) {
	
		var imgType = window.document.plotSaveAsForm.imgType.value ;
		var qualityPulldown = window.document.plotSaveAsForm.quality ;
		var commentDiv = document.getElementById('qualityCommentDiv'); 

		if ( imgType.indexOf ( "png" ) != -1 ) {
			commentDiv.style.visibility = "visible" ;
			qualityPulldown.disabled = true ;
		}
		else {
			commentDiv.style.visibility = "hidden" ;
			qualityPulldown.disabled = false ;
		}
	
	}
	
	// Use a timer to re-attempt closing - this is because the File Save As dialogue
	// hogs the page until the user saves the graphic. So we have to periodically attempt
	// to close the window.
	function setupClosing () {
		setInterval('self.close()',10000 );
	}
	
</script>
<html:form action="/plot/saveAs" focus="imgType" onsubmit="setupClosing();">
	<html:hidden property="svgDOM"/>
	<table border="0" cellpadding="6">
		<tr>
			<td class="label">&nbsp;</td>
			<td>Specify how to save your current plot:</td>
		</tr>
		<tr>
			<td class="label">Image Type/Format:</td>
			<td>
				<html:select property="imgType" onchange="imgTypeChange(this);">
					<html:optionsCollection property="imgTypes"/>
				</html:select>
		</td>
		</tr>
		<tr>
			<td class="label">Image Quality:</td>
			<td>
				<html:select property="quality">
					<html:optionsCollection property="qualities"/>
				</html:select>
				<div id="qualityCommentDiv" style="visibility: hidden; display: inline; font-size:65%;padding-left:10px; color: gray;">
					(not applicable for PNG)
				</div>
			</td>
		</tr>
		<tr>
			<td class="label">Height:</td>
			<td>
				<html:select property="height" onchange="heightChange(this);">
					<html:optionsCollection property="heights"/>
				</html:select>
			</td>
		</tr>
		<tr>
			<td class="label">Width:</td>
			<td>
				<html:select property="width" onchange="widthChange(this);">
					<html:optionsCollection property="widths"/>
				</html:select>
			</td>
		</tr>
		<tr>
			<td class="label">&nbsp;</td>
			<td>
				<input type="submit" value="Save">
				<span style="width:20px;">&nbsp;</span>
				<html:reset value="Reset"/>
				<span style="width:20px;">&nbsp;</span>
				<input type="submit" value="Cancel / Close" onClick="window.close();">
			</td>
		</tr>
	</table>		
</html:form>