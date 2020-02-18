<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link href="css.css" rel="stylesheet">
<title>Web project welcome page</title>
</head>
<body>
	<header class="header">
		User: guest 
		<a href="<c:url value = '/login.jsp'/>">Login</a> 
		<a href="<c:url value = '/registrate.jsp'/>">Registrate</a>
	</header>
	<main>
		<div id="welcome">
			<h3>Welcome!</h3>
			<h5>Web project for Epam Training</h5>
		</div>			
	</main>
	<footer> 
		<jsp:include page="footer.jsp"/>
	</footer>
</body>
</html>