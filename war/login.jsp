<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@ page import="com.coryhogan.smashogl.web.Cookies" %>
   
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<form action="/login" method="post" accept-charset="utf-8">
	<label for="username">Username:</label>
	<input type="text" name="username" id="username" size = "20" /><br>
	<label for="password">Password:</label>
	<input type="password" name="password" id="password" size = "60" /><br>
	<label for="emailAddress">Remember Me:</label>
	<input type="checkbox" name="rememberMe" id="rememberMe" /><br>
	<input type="submit" value="Submit" />
	</form>
	
	<a href="/enterQueue">TEST</a>
</body>
</html>