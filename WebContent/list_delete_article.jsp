<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ page import="java.io.IOException" %>
<%@ page import="java.io.InputStreamReader" %>
<%@ page import="java.io.InputStream" %>

<%@ page import="org.apache.http.HttpResponse" %>
<%@ page import="org.apache.http.client.ClientProtocolException" %>
<%@ page import="org.apache.http.client.methods.HttpGet" %>
<%@ page import="org.apache.http.impl.client.HttpClientBuilder" %>
<%@ page import="org.apache.http.client.HttpClient" %>

<%@ page import="javax.xml.parsers.DocumentBuilderFactory" %>
<%@ page import="javax.xml.parsers.DocumentBuilder" %>
<%@ page import="org.w3c.dom.Document" %>
<%@ page import="org.w3c.dom.NodeList" %>
<%@ page import="org.w3c.dom.Node" %>
<%@ page import="org.w3c.dom.Element" %>

<%@ page import="javax.ws.rs.core.UriBuilder" %>
<%@ page import="javax.ws.rs.client.Client" %>
<%@ page import="javax.ws.rs.client.ClientBuilder" %>
<%@ page import="javax.ws.rs.client.WebTarget" %>
<%@ page import="java.net.URI" %>
<%@ page import="org.glassfish.jersey.client.ClientConfig" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>JSP to list and delete a article from database</title>
</head>
<body>
List of Articles
<br>
<br>

<%

if(request.getParameter("id") != null){
	
	URI uri = UriBuilder.fromUri("http://localhost:8080/com.crossover.article/").build();
	ClientConfig cConfig = new ClientConfig();
	Client client = ClientBuilder.newClient(cConfig);
	WebTarget service = client.target(uri);
	
	service.path("rest").path("article/" + request.getParameter("id")).request().delete();
}

try {
    
	// create HTTP Client
	HttpClient httpClient = HttpClientBuilder.create().build();

	// Create new getRequest with below mentioned URL
	HttpGet getRequest = new HttpGet("http://localhost:8080/com.crossover.article/rest/article");

	// Add additional header to getRequest which accepts application/xml data
	getRequest.addHeader("accept", "application/xml");

	// Execute your request and catch response
	HttpResponse rest_response = httpClient.execute(getRequest);
	
	// Check for HTTP response code: 200 = success
	if (rest_response.getStatusLine().getStatusCode() != 200) {
	        throw new RuntimeException("Failed : HTTP error code : " + rest_response.getStatusLine().getStatusCode());
	}

	// Get-Capture Complete application/xml body response
	InputStream is = rest_response.getEntity().getContent();

	//parsing the XML
	Document document = null;
    DocumentBuilderFactory dbf  = DocumentBuilderFactory.newInstance();
     
    dbf.setValidating(false);
    dbf.setIgnoringComments(false);
    dbf.setIgnoringElementContentWhitespace(true);
    dbf.setNamespaceAware(true);
     
    DocumentBuilder builder = dbf.newDocumentBuilder();

    document = builder.parse(is);
	
	document.getDocumentElement().normalize();
	
	NodeList nList = document.getElementsByTagName("articles");

	Node nNode = nList.item(0);

	Element eElement = (Element) nNode;

	for(int i = 0; i < eElement.getElementsByTagName("id").getLength(); i++){
		
		%>
		---------------------<br>
		Article Id : <%= eElement.getElementsByTagName("id").item(i).getTextContent() %> <br>
		Article Description : <%= eElement.getElementsByTagName("description").item(i).getTextContent() %> <br>
		Article File : <%= eElement.getElementsByTagName("file").item(i).getTextContent() %> <br>
		<a href="http://localhost:8080/com.crossover.article/list_delete_article.jsp?id=<%= eElement.getElementsByTagName("id").item(i).getTextContent() %>">delete</a><br>
		---------------------<br>
		<%
	
	}
	
} catch (ClientProtocolException e) {
	e.printStackTrace();

} catch (IOException e) {
	e.printStackTrace();
}

%>
</body>
</html>