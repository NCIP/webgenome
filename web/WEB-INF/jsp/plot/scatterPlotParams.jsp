<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/webcgh.tld" prefix="webcgh" %>

<%@ page import="org.rti.webcgh.webui.util.Attribute" %>

<%@ page errorPage="/WEB-INF/jsp/exception/jspException.jsp" %>


<p><br></p>

<center>
	<html:errors property="global"/>
</center>


<html:form action="/configPlotParams">

	<html:hidden property="plotType" value="scatter"/>

<script language="JavaScript">
<!--
function load(file,target) {
    if (target != '')
        target.window.location.href = file;
    else
        window.location.href = file;
}
//--></script>

<script language="JavaScript">
<!--
function closeAndForwardToPlot() {
	load('<html:rewrite page="/plot/setup.do?plotType=scatter"/>', top.opener);
	window.opener=self;
	window.close();
}
//--></script>


<p align="center">
	<html:submit value="OK" onclick="javascript:closeAndForwardToPlot();" />
</p>

<input type="hidden" name="update" value="yes">

<p align="center">
	<span class="required-param">R</span> = Required,
	<span class="optional-param">O</span> = Optional,
	<span class="ignored-param">I</span> = Ignored
</p>

<table border="1" cellspacing="0" cellpadding="4" class="tbl" align="center">

	<!-- ================================= -->
	<!--             Header                -->
	<!-- ================================= -->
	
	<tr class="dataHeadTD">
		<td colspan="2" align="center" valign="middle">Parameter</td>
		<td align="center">Scatter Plot Requirements</td>
	</tr>
	
	<!-- =================================== -->
	<!--           Genome Segment            -->
	<!-- =================================== -->
	
	<!-- Genome interval -->
	<tr class="contentTD">
		<td rowspan="1">Genome Segment</td>
		<td>
			<table><tr class="contentTD">
				<td>
					<html:img styleClass="pointer" page="/images/helpicon.gif" align="absmiddle" onclick="help('param-genomeIntervals')"/>
				</td>
				<td width="100">Genome Intervals:</td>
				<td><html:errors property="genomeIntervals"/>
            		<html:text size="15" name="plotParamsForm" maxlength="100" property="genomeIntervals"/>
            		<html:select name="plotParamsForm" property="units">
   						<webcgh:unitOptions name="plotParamsForm" property="units"/>
   					</html:select>
            	</td>
            </tr></table>
		</td>
		<td align="center"><span class="optional-param">O</span></td>
	</tr>
	
	
	<!-- =================================== -->
	<!--         Quantitation type           -->
	<!-- =================================== -->
	
	<!-- Quantitation type -->
	<tr class="contentTD-odd">
		<td rowspan="1">Quantitation Type</td>
		<td>
			<table><tr class="contentTD-odd">
				<td>
					<html:img styleClass="pointer" page="/images/helpicon.gif" align="absmiddle" onclick="help('param-quantitationType')"/>
				</td>
				<td width="100">Quantitation Type:</td>
				<td>
            		<html:select name="plotParamsForm" property="quantitationTypeId">
   						<html:options collection="quantitationTypes" property="name" labelProperty="name"/>
   					</html:select>
            	</td>
            </tr></table>
		</td>
		<td align="center"><span class="required-param">R</span></td>
	</tr>

	
	<!-- =========================================== -->
	<!--       Plot dimensions                       -->
	<!-- =========================================== -->
	
	<!-- Plot width -->
	<tr class="contentTD-odd">
		<td rowspan="2">Plot Dimensions</td>
		<td>
			<table>
				<tr class="contentTD-odd">
					<td>
						<html:img styleClass="pointer" page="/images/helpicon.gif" align="absmiddle" onclick="help('param-width')"/>
					</td>
					<td width="100">Width</td>
					<td>
						<html:errors property="width"/>
						<html:text size="3" maxlength="4" name="plotParamsForm" property="width"/>
					</td>
				</tr>
			</table>
		</td>
		<td align="center"><span class="required-param">R</span></td>
	</tr>
	
	<!-- Plot height -->
	<tr class="contentTD-odd">
		<td>
			<table>
				<tr class="contentTD-odd">
					<td>
						<html:img styleClass="pointer" page="/images/helpicon.gif" align="absmiddle" onclick="help('param-height')"/>
					</td>
					<td width="100">Height</td>
					<td>
						<html:errors property="height"/>
						<html:text size="3" maxlength="4" name="plotParamsForm" property="height"/>
					</td>
				</tr>
			</table>
		</td>
		<td align="center"><span class="required-param">R</span></td>
	</tr>
	
	<!-- =============================== -->
	<!--         Plot Properties         -->
	<!-- =============================== -->
	
	<!-- Minimum copy number fold change and common first column -->
	<tr class="contentTD">
		<td rowspan="10">Plot Properties</td>
		<td>
			<table>
				<tr class="contentTD">
					<td>
						<html:img styleClass="pointer" page="/images/helpicon.gif" align="absmiddle" onclick="help('param-minY')"/>
					</td>
					<td width="150">Minimum plot value</td>
					<td>
						<html:errors property="minY"/>
						<html:text size="6" name="plotParamsForm" property="minY"/>
					</td>
				</tr>
			</table>
		</td>
		<td align="center"><span class="optional-param">O</span></td>
	</tr>
	
	<!-- Maximum copy number fold change -->
	<tr class="contentTD">
		<td>
			<table>
				<tr class="contentTD">
					<td>
						<html:img styleClass="pointer" page="/images/helpicon.gif" align="absmiddle" onclick="help('param-maxY')"/>
					</td>
					<td width="150">Maximum plot value</td>
					<td>
						<html:errors property="maxY"/>
						<html:text size="6" name="plotParamsForm" property="maxY"/>
					</td>
				</tr>
			</table>
		</td>
		<td align="center"><span class="optional-param">O</span></td>
	</tr>
	
	<!-- Show ideograms? -->
	<tr class="contentTD">
		<td>
			<table>
				<tr class="contentTD">
					<td>
						<html:img styleClass="pointer" page="/images/helpicon.gif" align="absmiddle" onclick="help('param-showIdeograms')"/>
					</td>
					<td width="150">Show ideogram in plot</td>
					<td><html:checkbox property="showIdeogram"/></td>
				</tr>
			</table>
		</td>
		<td align="center"><span class="optional-param">O</span></td>
	</tr>
	
	<!-- Chromosome ideograms per row -->
	<tr class="contentTD">
		<td>
			<table>
				<tr class="contentTD">
					<td>
						<html:img styleClass="pointer" page="/images/helpicon.gif" align="absmiddle" onclick="help('param-ideogramsPerRow')"/>
					</td>
					<td width="150">Chromosome ideograms per row</td>
					<td>
						<html:errors property="plotsPerRow"/>
						<html:text size="4" name="plotParamsForm" property="plotsPerRow"/>
					</td>
				</tr>
			</table>
		</td>
		<td align="center"><span class="ignored-param">I</span></td>
	</tr>
	
</table>

<p align="center">
	<span class="required-param">R</span> = Required,
	<span class="optional-param">O</span> = Optional,
	<span class="ignored-param">I</span> = Ignored
</p>

	
<p align="center">
	<html:submit value="OK" onclick="javascript:closeAndForwardToPlot();" />
</p>
 
</html:form>