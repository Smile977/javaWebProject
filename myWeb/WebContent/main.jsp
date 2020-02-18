<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link href="css.css" rel="stylesheet">
<title>Main</title>
<script type="text/javascript">
	function toggle(box) {		
		if(${fn:length(tasks) > 1}) {
			for (var i = 0; i < document.tasks.idTask.length; i++) {
				document.tasks.idTask[i].checked = box.checked;
			}
		} else {
			document.tasks.idTask.checked = box.checked;
		}		
	}

	function doOperation(operationName) {
		var err = document.getElementById("errorText");
		var noTaskSelected = true;
		if(${fn:length(tasks) > 1}) {
			for (var i = 0; i < document.tasks.idTask.length; i++) {
				if (document.tasks.idTask[i].checked) {
					noTaskSelected = false;
				}
			}
		} else {
			if (document.tasks.idTask.checked) {
				noTaskSelected = false;
			}
		}		
		if (noTaskSelected) {
			err.innerHTML = "No task is selected";
			return;
		}
		document.tasks.operation.value = operationName;
		document.tasks.submit();
	}

	function emptyBin() {
		if(${fn:length(tasks) > 1}) {
			for (var i = 0; i < document.tasks.idTask.length; i++) {
				document.tasks.idTask[i].checked = true;
			}
		} else {
			document.tasks.idTask.checked = true;
		}			
		document.tasks.operation.value = 'delete';
		document.tasks.submit();		
	}
	
	function deleteFileById(id) {		
		document.deleteFile.idTask.value = id;
		document.deleteFile.submit();		
	}
</script>
</head>
<body>
	<header>
		<jsp:include page="header.jsp"/>
	</header>
	<main>
		<form name="deleteFile" action="<c:url value = '/deleteFile'/>"	method="POST">
			<input name="idTask" type="hidden"> 
			<input name="view" type="hidden" value="${view}">
		</form>		
			
			<c:forEach var="viewLink" items="${viewLinks}">
			<a class="${viewLink.linkClass}" href="<c:url value = '${viewLink.url}'/>">${viewLink.text}</a> 
			</c:forEach>		
		
			<h3>${viewTitle}</h3>		
		
		<c:if test="${not empty tasks}">
			<form action="<c:url value = '/main'/>" method="POST" name="tasks">
				<input type="hidden" name="operation" value="no"> 
				<input type="hidden" name="view" value="${view}">
				<table>
					<tr>
						<th>
							<input type="checkbox" onClick="toggle(this)">
						</th>
						<th>Task</th>
						<c:if test="${view == 'someday' or view == 'fixed' or view == 'bin'}">
							<th>Date</th>
						</c:if>
						<th>File</th>
					</tr>
					<c:forEach var="task" items="${tasks}">
						<tr>
							<td>
								<input type="checkbox" name="idTask" value="${task.id}">
							</td>
							<td>${task.text}</td>
							<c:if test="${view == 'someday' or view == 'fixed' or view == 'bin'}">
								<td>${task.date}</td>
							</c:if>
							<td class="tdFile">
								<c:if test="${task.fileStatus}">
									${task.file}
									<a href="<c:url value = '/download?idTask=${task.id}'/>">Download</a>
									<a href="javascript:deleteFileById(${task.id})">Delete</a>
								</c:if> 
								<c:if test="${not task.fileStatus}">
									<a href="<c:url value = '/upload?idTask=${task.id}&view=${view}'/>">
										Upload
									</a>
								</c:if>
							</td>
						</tr>
					</c:forEach>
				</table>
			</form>			
			<p id="errorText" style="color: red;"></p>
			
			<c:forEach var="operationLink" items="${operationLinks[view]}">
			<a class="operationLink" href="<c:url value = '${operationLink.url}'/>">${operationLink.text}</a> 
			</c:forEach>	
			
		</c:if>		
		<c:if test="${empty tasks}">
			<p>No tasks in ${view} view</p>
			<c:if test="${view == 'today' or view == 'tomorrow' or view == 'someday'}">
				<a href="<c:url value = '/add.jsp?view=${view}'/>">Add Task</a>
			</c:if>
		</c:if>		
	</main>
	<footer> 
		<jsp:include page="footer.jsp"/>
	</footer>
</body>
</html>