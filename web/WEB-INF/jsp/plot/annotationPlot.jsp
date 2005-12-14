<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/webcgh.tld" prefix="webcgh" %>

<%@ page import="org.rti.webcgh.webui.util.WebUtils" %>

<%@ page errorPage="/WEB-INF/jsp/exception/jspException.jsp" %>


		
<script language="JavaScript">
	self.focus();
	
	function resizeImage(height) {
		var image = document.getElementById("plotImg");
		image.setAttribute("height", height);
	}
</script>

<center>
	<html:form action="/plot/setup">
		<webcgh:beanPropsToHiddenFields name="plotParamsForm" 
			exclusions="chromosome,startPoint,endPoint,startUnits,endUnits"/>
		<table cellpadding="3" cellspacing="3" border="0">
			<tr>
    			<td>
        			<html:submit value="Go"/>
    			</td>
    			<td>
    				Chromosome: <html:text size="2" maxlength="2" property="chromosome"/>
    			</td>
        		<td>
    				Start:
    				<html:text property="startPoint" size="6"
    						onblur="setSelectedRegion()"/>
    				<html:select property="startUnits">
   						<html:option value="mb"/>
   						<html:option value="kb"/>
   						<html:option value="bp"/>
   					</html:select>
   				</td>
   				<td>
   					End:
   					<html:text property="endPoint" size="6" property="endPoint" size="6"
    					onblur="setSelectedRegion()"/>
    				<html:select property="endUnits">
    					<html:option value="mb"/>
    					<html:option value="kb"/>
   						<html:option value="bp"/>
   					</html:select>
   				</td>
        		<td>
            		<html:submit value="Go"/>
        		</td>
    		</tr>
		</table>
	</html:form>
		
	<br>
		
	<webcgh:genomeNavigation action="/plot.do" name="plotParamsForm"/>

</center>
		
<p>

<center>
	<embed
		id="plotImg"
		src="<html:rewrite page="/showAnnotationPlotSvg.do"/><%= WebUtils.paramListFromProps(pageContext.findAttribute("plotParamsForm")) %>"
		width="<bean:write name="plotParamsForm" property="width"/>"
		height="<bean:write name="plotParamsForm" property="height"/>"
		type="image/svg-xml"
		pluginspage="http://www.adobe.com/svg/viewer/install">
</center>
		