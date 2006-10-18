<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.HashMap" %>

<p align="center">
	WebGenome Home
</p>

<p align="center">
	<%
		Map<String, String> paramsMap = new HashMap<String, String>();
		paramsMap.put("exptIDs", "Experiment 1,Experiment 2, Experiment 3");
		paramsMap.put("intervals", "1:1-100000000");
		paramsMap.put("qType", "LOG2Ratio");
		paramsMap.put("clientID", "1");
		request.setAttribute("params", paramsMap);
	%>
	<html:link action="/client/plot" name="params">
		Client Plot Test
	</html:link>
</p>