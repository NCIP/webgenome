<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/webcgh.tld" prefix="webcgh" %>

<script language="Javascript">

	/**
	 * This function bulk-modifies the values of all
	 * text fields that correspond to output experiment and bioassay
	 * names.
	 * @param augmentation The text augmentation that will be
	 * made to the current value of some source string.
	 * @param location The relative location where the augmentation
	 * will be made in the source string--i.e., prefix or suffix
	 * @param source Indicates the source text field that will
	 * be augmented.  This may be either 'input' or 'output.'
	 * If 'input,' the actual source text will be the names of
	 * input experiment or bioassay names.  If 'output,' the actual
	 * source text will be the names of output experiment or
	 * bioassay names.
	 */
	function augmentNames(augmentation, location, source) {
	
		// Get form input fields
		var inputs = document.forms[0].elements;
		
		// Iterate over input fields
		for (var i = 0; i < inputs.length; i++) {
			var input = inputs[i];
			var name = input.getAttribute("name");
			
			// Input field is experiment or bioassay output name
			if (name.indexOf("eo_") == 0 || name.indexOf("bo_") == 0) {
			
				// Get source text
				var sourceText = null;
				if ("input" == source) {
					expOrBioAssayId = name.substring(3);
					var sourceInputName = null;
					if (name.indexOf("eo_") == 0) {
						sourceInputId = "ei_" + expOrBioAssayId;
					} else if (name.indexOf("bo_") == 0) {
						sourceInputId = "bi_" + expOrBioAssayId;
					}
					var sourceElmt = document.getElementById(sourceInputId);
					sourceText = sourceElmt.value;
				} else if ("output" == source) {
					sourceText = input.value;
				}
				
				// Create augmented text and set input value
				var augmentedText = null;
				if ("prefix" == location) {
					augmentedText = augmentation + sourceText;
				} else if ("suffix" == location) {
					augmentedText = sourceText + augmentation;	
				}
				input.value = augmentedText;
			}
		}
	}
	
	/**
	 * This function bulk-modifies the values of all
	 * text fields that correspond to output experiment and bioassay
	 * names.  It obtains parameters for making these bulk
	 * modifications from the form.
	 */
	function augmentOutputNames() {
	
		// Get augmentation text value from form
		var augmentation = document.forms[0].augmentation.value;
		
		// Get relative location of augmentation--i.e., prefix or suffix
		var p = document.forms[0].location.selectedIndex;
		var location = document.forms[0].location.options[p].value;
		
		// Get source of base of augmented name.  This may be
		// either the name of the input experiment or bioassay,
		// or the current name of the output experiment or bioassay--
		// i.e., the value of a text field.
		p = document.forms[0].source.selectedIndex;
		var source = document.forms[0].source.options[p].value;
		
		// Augment names
		augmentNames(augmentation, location, source);
	}
</script>

<html:form action="/cart/analysis">
<h1 align="center">Analytic Operation Parameters</h1>

<%-- Analytic operation configurable properties --%>
<center>
<logic:iterate name="props" id="prop">
	<p>
		<bean:write name="prop" property="displayName"/>
		&nbsp;&nbsp;
		<webcgh:userConfigurablePropertyInput name="prop"
			prefix="prop_"/>
	</p>
</logic:iterate>
</center>

<center>

<logic:present name="experiments">

<h3 align="center">Specify names of output experiments and bioassays</h3>

	<%--
	===========================================================
	Form elements to bulk-change names of experiments
	and bioassays in text boxes below.
	This form is not displayed if the selected experiment is of
	type MultiExperimentToNonArrayDataAnalyticOperation;
	the upstream Action will not have attached the necessary
	beans to the request.
	============================================================
	--%>
	
	<p>
	Add
	<select name="location">
		<option value="prefix">prefix</option>
		<option value="suffix">suffix</option>
	</select>
	<input type="text" name="augmentation">
	to
	<select name="source">
		<option value="input">input name</option>
		<option value="output">output name</option>
	</select>
	<input type="button" value="OK" onclick="augmentOutputNames()">
	</p>


	<%--
	============================================================
	Form elements that enable the user to specify
	names of output experiments and bioassays.
	A coding system is used so that the names of form
	fields corresponding to input and output experiments
	and bioassays can be deduced.  These fields are named
	with a prefix followed by the experiment or bioassay name.
	The prefix may be ei, eo, bi, bo which corresponds to
	experiment-input (readonly field), experiment-output,
	bioassay-input (readonly field), bioassay-output, respectively.
	This form is not displayed if the selected experiment is of
	type MultiExperimentToNonArrayDataAnalyticOperation;
	the upstream Action will not have attached the necessary
	beans to the request.
	=============================================================
	--%>
	
	<table class="noBorder">
		<tr>
			<th>Input Name</th>
			<th>Output Name</th>
		</tr>
		<logic:iterate name="experiments" id="experiment">
			<tr>
			
			<%-- Experiment input name field (readonly) --%>
				<td>
					<input type="text"
						name="ei_<bean:write name="experiment" property="id"/>"
						id="ei_<bean:write name="experiment" property="id"/>"
						value="<bean:write name="experiment" property="name"/>"
						readonly>
				</td>
				
			<%-- Experiment output name field --%>
				<td>
					<input type="text" name="eo_<bean:write name="experiment" property="id"/>">
				</td>
			</tr>
			
			<logic:notPresent name="singleBioAssay">
			<logic:iterate name="experiment" property="bioAssays"
				id="bioAssay">
				<tr>
				
				<%-- Bioassay input name field (readonly) --%>
					<td>
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="text"
							name="bi_<bean:write name="bioAssay" property="id"/>"
							id="bi_<bean:write name="bioAssay" property="id"/>"
							value="<bean:write name="bioAssay" property="name"/>"
							readonly>
					</td>
					
				<%-- Bioassay output name field --%>
					<td>
						<input type="text" name="bo_<bean:write name="bioAssay" property="id"/>">
					</td>
				</tr>
			</logic:iterate>
			</logic:notPresent>
		</logic:iterate>
	</table>
	
	</logic:present>
	
	<p>
		<html:submit value="Perform Analysis"/>
	</p>
</center>

</html:form>