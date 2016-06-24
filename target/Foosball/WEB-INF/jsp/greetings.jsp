<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
 
<html>
 
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  	<title>Foosball Manager</title>
</head>
 
<body>
 
<h1>Foosball Manager</h1>
 
Greeting by <b>Anonymous</b><br/>
on <c:out value="<%=new java.util.Date()%>" /><br/>
<c:out value="${greetingText}" /><br/>			
 
<p><a href="/foosball/data/addgreeting.html">Add greeting</a><br/>
<a href="/foosball/">Home</a>
 
</body>
</html>