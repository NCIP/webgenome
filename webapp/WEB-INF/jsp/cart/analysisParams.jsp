<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/webcgh.tld" prefix="webcgh" %>

<script language="Javascript">
	function augmentNames(augmentation, location, target) {
		var inputs = document.forms[0].elements;
		for (var i = 0; i < inputs.length; i++) {
			var input = inputs[i];
			var name = input.getAttribute("name");
			if (name.indexOf("output_") == 0) {
				var base = null;
				if ("input" == target) {
					base = name.substring(7);
				} else if ("output" == target) {
					base = input.value;
				}
				var newValue = null;
				if ("prefix" == location) {
					newValue = augmentation + base;
				} else if ("suffix" == location) {
					newValue = base + augmentation;	
				}
				input.value = newValue;
			}
		}
	}
	
	function augmentOutputNames() {
		var augmentation = document.forms[0].augmentation.value;
		var p = document.forms[0].location.selectedIndex;
		var location = document.forms[0].location.options[p].value;
		p = document.forms[0].target.selectedIndex;
		var target = document.forms[0].target.options[p].value;
		augmentNames(augmentation, location, target);
	}
</script>

<form>
<h1 align="center">Analytic Operation Parameters</h1>

<center>
<logic:iterate name="props" id="prop">
	<p>
		<bean:write name="prop" property="displayName"/>
		&nbsp;&nbsp;
		<webcgh:userConfigurablePropertyInput name="prop"/>
	</p>
</logic:iterate>
</center>

<h3 align="center">Specify names of output experiments and bioassays</h3>

<center>

	<p>
	Add
	<select name="location">
		<option value="prefix">prefix</option>
		<option value="suffix">suffix</option>
	</select>
	<input type="text" name="augmentation">
	to
	<select name="target">
		<option value="input">input name</option>
		<option value="output">output name</option>
	</select>
	<input type="button" value="OK" onclick="augmentOutputNames()">
	</p>

	<table border="1">
		<tr>
			<td>Input Name</td>
			<td>Output Name</td>
		</tr>
		<logic:iterate name="experiments" id="experiment">
			<tr>
				<td><bean:write name="experiment" property="name"/></td>
				<td>
					<input type="text" name="output_<bean:write name="experiment" property="name"/>">
				</td>
			</tr>
			<logic:iterate name="experiment" property="bioAssays"
				id="bioAssay">
				<tr>
					<td>
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<bean:write name="bioAssay" property="name"/>
					</td>
					<td>
						<input type="text" name="output_<bean:write name="bioAssay" property="name"/>">
					</td>
				</tr>
			</logic:iterate>
		</logic:iterate>
	</table>
</center>
</form>