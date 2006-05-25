<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/webcgh.tld" prefix="webcgh" %>

<%@ page import="org.rti.webcgh.webui.util.Attribute" %>

<script language="JavaScript">

	function back() {
		history.go(-1);
	}

	function cancel() {
		window.location = "<html:rewrite page="/pipeline/show.do"/>";
	}
	
	function addOp(opCategory) {
		var selectElement = null;
		if (opCategory == 'dataFilter')
			selectElement = document.pipelineForm.dataFilterOp.value;
		else if (opCategory == 'normalization')
			selectElement = document.pipelineForm.normalizationOp.value;
		else if (opCategory == 'summaryStatistic')
			selectElement = document.pipelineForm.summaryStatisticOp.value;
		var url = 
			"<html:rewrite page="/pipeline/addOperation.do"/>?opCategory=" + 
			opCategory + "&" + allParams();
		window.location = url;
	}
	
	function removeOp(index) {
		var url = "<html:rewrite page="/pipeline/removeOperation.do"/>?index=" + 
			index + "&" + allParams();
		window.location = url;
	}
	
	function allParams() {
		var paramStr = "";
		var params = document.pipelineForm.elements;
		for (var i = 0; i < params.length; i++) {
			var param = params[i];
			if (i > 0)
				paramStr += "&";
			paramStr += param.name + "=" + param.value;
		}
		return paramStr;
	}

</script>

<p align="center">
	<html:errors property="global"/><br>
</p>

<html:form action="/pipeline/save">

	<table border="0" cellpadding="20" align="center" class="tblLite">
		<tr>
			<td align="center">
			
				<!-- Pipeline name -->
				<p>
					<span class="fieldName">Pipeline Name</span><span class="fieldValue">
					<html:errors property="name"/>
					<logic:equal name="<%= Attribute.NEW_PIPELINE %>" 
						property="readOnly" value="false">
						<html:text name="<%= Attribute.NEW_PIPELINE %>" property="name"/>
					</logic:equal>
					<logic:equal name="<%= Attribute.NEW_PIPELINE %>" 
						property="readOnly" value="true">
						<b><bean:write name="<%= Attribute.NEW_PIPELINE %>" property="name"/></b>
					</logic:equal></span>
				</p>
				
				<% 	int counter = -1; %>
				
				<!-- Pipeline operation sequence -->
				<table border="0" cellpadding="10" class="tbl">
					<tr>
						<td align="center">
							<p class="paragraphHeading">
								Sequence of Operations
							</p>
							
							<!-- Table header -->
							<table border="1" cellpadding="5" class="data">
								<tr>
									<th class="fieldName" align="center">Step</td>
									<th class="fieldName" align="center">Type</td>
									<th class="fieldName" align="center">Operation</td>
									<th class="fieldName" align="center">Parameters</td>
									<logic:equal name="<%= Attribute.NEW_PIPELINE %>"
										property="readOnly" value="false">
										<th class="fieldName" align="center">&nbsp</td>
									</logic:equal>
								</tr>
				
								<!-- Operations -->
								<webcgh:iterateOverPipeline name="<%= Attribute.NEW_PIPELINE %>">
									<tr>
										<td><webcgh:operationNumber/></td>
										<td class="opCategory">
											<webcgh:operationCategory/>
										</td>
										
										
										<!-- Basic information about operation -->
										<td>
											<table border="0" class="dataInner">
												<tr>
												
													<!-- Help icon -->
													<td>
														<img 
															class="pointer" 
															src="<html:rewrite page="/images/helpicon.gif"/>"
															align="absmiddle" 
															onclick="help('<webcgh:operationName/>')"
														>
													</td>
																										
													<!-- Operation name -->
													<td class="opType">
														<webcgh:operationName/>
													</td>
												</tr>
											</table>
										</td>
										
										<!-- Configurable parameters -->
										<td>
											<webcgh:iterateOverOperationProperties>
												
												<%-- Field name --%>
												<span class="fieldName">
													<webcgh:propertyDisplayName/>
												</span>:
												
												<%-- Error message, if upstream action added any --%>
												<webcgh:operationPropertyToBean id="pName"/>
												<% String pName = (String)pageContext.findAttribute("pName"); %>
												<html:errors property="<%= pName %>"/>
												
												<%-- Field input --%>
												<webcgh:propertyInputField size="5"/>
											</webcgh:iterateOverOperationProperties>
											&nbsp;
										</td>
							
										<logic:equal name="<%= Attribute.NEW_PIPELINE %>"
											property="readOnly" value="false">
											<td>
												<a href="#" onclick="removeOp(<webcgh:operationNumber/>)">
													Remove
												</a>
											</td>
										</logic:equal>
							
									</tr>
								</webcgh:iterateOverPipeline>
							</table>
					
							<!-- Add operation embedded form -->
							<logic:equal name="<%= Attribute.NEW_PIPELINE %>" 
								property="readOnly" value="false">
								<p><table>
								
									<!-- Add filter operation -->
									<tr>
									<td align="right">
									<input type="button" value="Add Data Filter" onclick="addOp('dataFilter')">
									</td>
									<td>
									<html:select property="dataFilterOp">
										<html:options collection="dataFilterOperations" property="beanName"
											labelProperty="displayName"/>					
									</html:select>
									</td>
									</tr>
									
									<!-- Add normalization operation -->
									<tr>
									<td align="right">
									<input type="button" value="Add Normalization" onclick="addOp('normalization')">
									</td>
									<td>
									<html:select property="normalizationOp">
										<html:options collection="normalizationOperations" property="beanName"
											labelProperty="displayName"/>					
									</html:select>
									</td>
									</tr>
									
									<!-- Add summary statistics operation -->
									<tr>
									<td align="right">
									<input type="button" value="Add Summary Statistic" onclick="addOp('summaryStatistic')">
									</td>
									<td>
									<html:select property="summaryStatisticOp">
										<html:options collection="summaryStatisticOperations" property="beanName"
											labelProperty="displayName"/>					
									</html:select>
									</td>
									</tr>
									
								</table></p>
							</logic:equal>
						</td>
					</tr>
				</table>
				
				
				<!-- Save and Cancel buttons -->
				<logic:equal name="<%= Attribute.NEW_PIPELINE %>" 
					property="readOnly" value="false">
					<p>
						<html:submit value="Save"/>
						&nbsp;&nbsp;
						<input type="button" value="Cancel" onclick="cancel()">
					</p>
				</logic:equal>
				<logic:equal name="<%= Attribute.NEW_PIPELINE %>" 
					property="readOnly" value="true">
					<p>
						<input type="button" value="Back" onclick="back()">
					</p>
				</logic:equal>
			</tr>
		</td>
	</table>
</html:form>
								