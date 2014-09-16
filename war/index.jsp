<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
	
<%@ page import="com.coryhogan.smashogl.persistence.entities.Chat" %>
<%@ page import="com.coryhogan.smashogl.persistence.daos.ChatDAO" %>
	
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>

	<%
		Chat chat = new Chat();
		ChatDAO.getInstance().add(chat);
		Chat chatReturned = ChatDAO.getInstance().get(chat.getId());
		String entries = chatReturned.getUsers().toString();
	%>

	<div id="setMe"><%= entries %></div>
	
	<script src="//ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
	<script src="//ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>
	
	<script>
//  		$.get("/enterQueue", function(data) {
// 			$("#setMe").html(data);
// 			alert( "Load was performed." );
// 		});
	</script>
	
</body>
</html>