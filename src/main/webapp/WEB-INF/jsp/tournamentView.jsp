<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html><head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  	<title>${tournament.getName()}</title>
  	<link rel="stylesheet" type="text/css" href="theme.css">
</head><body>
 
<h1>${tournament.getName()}</h1>

<c:if test="${not empty message}">
	<br/><c:out value="${message}" /><br/>
</c:if>	

Informationen...

Stand: <c:out value="<%=new java.util.Date()%>" />

</body></html>
