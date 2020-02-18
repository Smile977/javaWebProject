<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
User: ${user.account}
<a href="javascript:logout()">Logout</a>
<form name="logout" action="<c:url value = '/logout'/>" method="POST">
</form>
<script>
	function logout() {
		document.logout.submit();
	}
</script>