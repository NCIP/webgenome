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


<html:form action="/recordPlotParams">

<p align="center">
    <html:submit value="Save Changes"/>
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
		<td colspan="2" rowspan="2" align="center" valign="middle">Parameter</td>
		<td colspan="5" align="center">Plot Type Requirements</td>
	</tr>
	<tr class="dataHeadTD">
		<td>Scatter</td>
		<td>Annotation</td>
		<td align="center">Annotation<br>Report</td>
		<td>Ideogram</td>
		<td align="center">Probe<br>Specific</td>
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
		<td align="center"><span class="required-param">R</span></td>
		<td align="center"><span class="required-param">R</span></td>
		<td align="center"><span class="optional-param">O</span></td>
		<td align="center"><span class="ignored-param">I</span></td>
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
		<td align="center"><span class="required-param">R</span></td>
		<td align="center"><span class="optional-param">O</span></td>
		<td align="center"><span class="required-param">R</span></td>
		<td align="center"><span class="required-param">R</span></td>
	</tr>
	
	
	<!-- ================================ -->
	<!--     Analytical Parameters        -->
	<!-- ================================ -->
	
	<!-- Analytic pipeline and common first column -->
	<tr class="contentTD">
		<td>Analytic Pipeline</td>
		<td>
			<table>
				<tr class="contentTD">
					<td rowspan="2">
						<html:img styleClass="pointer" page="/images/helpicon.gif" align="absmiddle" onclick="help('param-pipeline')"/>
					</td>
					<td width="150">Pipeline</td>
		    		<td>
		    			<html:select property="pipelineName">
							<html:options collection="pipelines" property="name" labelProperty="name"/>
		    			</html:select>
		    		</td>
					
		    	</tr>
		    </table>
		</td>
		<td align="center"><span class="optional-param">O</span></td>
		<td align="center"><span class="optional-param">O</span></td>
		<td align="center"><span class="ignored-param">I</span></td>
		<td align="center"><span class="optional-param">O</span></td>
		<td align="center"><span class="optional-param">O</span></td>
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
		<td align="center"><span class="required-param">R</span></td>
		<td align="center"><span class="ignored-param">I</span></td>
		<td align="center"><span class="ignored-param">I</span></td>
		<td align="center"><span class="ignored-param">I</span></td>
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
		<td align="center"><span class="ignored-param">I</span></td>
		<td align="center"><span class="ignored-param">I</span></td>
		<td align="center"><span class="ignored-param">I</span></td>
		<td align="center"><span class="ignored-param">I</span></td>
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
		<td align="center"><span class="optional-param">O</span></td>
		<td align="center"><span class="ignored-param">I</span></td>
		<td align="center"><span class="optional-param">O</span></td>
		<td align="center"><span class="ignored-param">I</span></td>
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
		<td align="center"><span class="optional-param">O</span></td>
		<td align="center"><span class="ignored-param">I</span></td>
		<td align="center"><span class="optional-param">O</span></td>
		<td align="center"><span class="ignored-param">I</span></td>
	</tr>
	
	<!-- Fold change threshold -->
	<tr class="contentTD">
		<td>
			<table>
				<tr class="contentTD">
					<td>
						<html:img styleClass="pointer" page="/images/helpicon.gif" align="absmiddle" onclick="help('param-threshold')"/>
					</td>
					<td width="150">Data mask
					<html:errors property="lowerFoldChangeThresh"/>
					</td>
					<td>
						Lower value
						<html:text size="3" name="plotParamsForm" property="lowerFoldChangeThresh"/>
						<br>Upper value
						<html:text size="3" name="plotParamsForm" property="upperFoldChangeThresh"/>
					</td>
				</tr>
			</table>
		</td>
		<td align="center"><span class="ignored-param">I</span></td>
		<td align="center"><span class="optional-param">O</span></td>
		<td align="center"><span class="ignored-param">I</span></td>
		<td align="center"><span class="optional-param">O</span></td>
		<td align="center"><span class="ignored-param">I</span></td>
	</tr>
	
	<!-- Show annotation feature labels in plot? -->
	<tr class="contentTD">
		<td>
			<table>
				<tr class="contentTD">
					<td>
						<html:img styleClass="pointer" page="/images/helpicon.gif" align="absmiddle" onclick="help('param-showLabels')"/>
					</td>
					<td width="150">Show annotated feature labels in plot</td>
					<td><html:checkbox property="showLabels"/></td>
				</tr>
			</table>
		</td>
		<td align="center"><span class="ignored-param">I</span></td>
		<td align="center"><span class="optional-param">O</span></td>
		<td align="center"><span class="ignored-param">I</span></td>
		<td align="center"><span class="ignored-param">I</span></td>
		<td align="center"><span class="ignored-param">I</span></td>
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
		<td align="center"><span class="optional-param">O</span></td>
		<td align="center"><span class="ignored-param">I</span></td>
		<td align="center"><span class="ignored-param">I</span></td>
		<td align="center"><span class="ignored-param">I</span></td>
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
		<td align="center"><span class="ignored-param">I</span></td>
		<td align="center"><span class="ignored-param">I</span></td>
		<td align="center"><span class="required-param">R</span></td>
		<td align="center"><span class="ignored-param">I</span></td>
	</tr>
	
	<!-- Chromosome ideogram size -->
	<tr class="contentTD">
		<td>
			<table>
				<tr class="contentTD">
					<td>
						<html:img styleClass="pointer" page="/images/helpicon.gif" align="absmiddle" onclick="help('param-ideogramSize')"/>
					</td>
					<td width="150">Chromosome ideogram size</td>
					<td>
						<html:select property="chromIdeogramSize">
							<webcgh:chromosomeIdeogramSizeOptions name="plotParamsForm" property="chromIdeogramSize"/>
						</html:select>
					</td>
				</tr>
			</table>
		</td>
		<td align="center"><span class="ignored-param">I</span></td>
		<td align="center"><span class="ignored-param">I</span></td>
		<td align="center"><span class="ignored-param">I</span></td>
		<td align="center"><span class="required-param">R</span></td>
		<td align="center"><span class="ignored-param">I</span></td>
	</tr>
	
	<!-- Annotation feature types -->
	<tr class="contentTD">
		<td>
			<table>
				<tr class="contentTD">
					<td rowspan="2">
						<html:img styleClass="pointer" page="/images/helpicon.gif" align="absmiddle" onclick="help('param-selectedFeatureTypes')"/>
					</td>
					<td>
						Annotated genomic feature types
						<ul>
							<logic:iterate id="featType" name="plotParamsForm" property="selectedFeatureTypesAsCollection">
								<li><bean:write name="featType"/></li>
							</logic:iterate>
						</ul>
					</td>
				</tr>
				<tr class="contentTD">
					<td>
						<html:link action="/updateSelectedFeatureTypes">Add/remove feature types</html:link>
					</td>
				</tr>
			</table>
		</td>
		<td align="center"><span class="ignored-param">I</span></td>
		<td align="center"><span class="required-param">R</span></td>
		<td align="center"><span class="required-param">R</span></td>
		<td align="center"><span class="ignored-param">I</span></td>
		<td align="center"><span class="ignored-param">I</span></td>
	</tr>
	
	
	<!-- Genome assembly -->
	<tr class="contentTD">
		<td>
			<table>
				<tr class="contentTD">
					<td>
						<html:img styleClass="pointer" page="/images/helpicon.gif" align="absmiddle" onclick="help('param-referenceAssembly')"/>
					</td>
					<td width="150">Reference Genome Assembly</td>
					<td>
						<html:errors property="assemblyId"/>
						<html:select property="assemblyId">
							<html:option value="none">Select</html:option>
							<html:options collection="assemblies" property="id" labelProperty="displayName"/>
						</html:select>
					</td>
				</tr>
			</table>
		</td>
		<td align="center"><span class="required-param">R</span></td>
		<td align="center"><span class="required-param">R</span></td>
		<td align="center"><span class="ignored-param">I</span></td>
		<td align="center"><span class="required-param">R</span></td>
		<td align="center"><span class="ignored-param">I</span></td>
	</tr>
	
	<!-- Probe names -->
	<tr class="contentTD">
		<td>
			<table>
				<tr class="contentTD">
					<td>
						<html:img styleClass="pointer" page="/images/helpicon.gif" align="absmiddle" onclick="help('param-probes')"/>
					</td>
					<td width="150" align="center">Specific Probes<br>
						(Comma-separated list)</td>
					<td>
						<html:errors property="selectedProbes"/>
						<html:textarea rows="3" cols="20" property="selectedProbes"/>
					</td>
				</tr>
			</table>
		</td>
		<td align="center"><span class="ignored-param">I</span></td>
		<td align="center"><span class="ignored-param">I</span></td>
		<td align="center"><span class="ignored-param">I</span></td>
		<td align="center"><span class="ignored-param">I</span></td>
		<td align="center"><span class="required-param">R</span></td>
	</tr>
	
</table>

<p align="center">
	<span class="required-param">R</span> = Required,
	<span class="optional-param">O</span> = Optional,
	<span class="ignored-param">I</span> = Ignored
</p>

	
<p align="center">
    <html:submit value="Save Changes"/>
</p>
 
</html:form>

