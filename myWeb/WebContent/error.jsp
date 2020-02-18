<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Error page</title>
<link href="css.css" rel="stylesheet">
<script type="text/javascript">
	function sendForm() {
		document.uploadFile.submit();
	}
</script>
</head>
<body>
	<header>
		<jsp:include page="header.jsp"/>
	</header>
	<main>
		<a href="<c:url value = '/main?view=today'/>">Today ${util.todayDate}</a> 
		<a href="<c:url value = '/main?view=tomorrow'/>">Tomorrow ${util.tomorrowDate}</a> 
		<a href="<c:url value = '/main?view=someday'/>">Someday</a>
		<a href="<c:url value = '/main?view=fixed'/>">Fixed</a> 
		<a href="<c:url value = '/main?view=bin'/>">Recycle Bin</a>
		<div id="errorMessage">
			<h3>Something went wrong</h3>
			<h5>${errorMessage}</h5>
		</div>
		
		
	</main>
	<footer> 
		<jsp:include page="footer.jsp"/>
	</footer>
</body>
</html>