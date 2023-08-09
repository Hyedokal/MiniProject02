<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>	
<!DOCTYPE html>
<html>
<head>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-4bw+/aepP/YC94hEpVNVgiZdgIC5+VKNBQNGCHeKRQN+PtmoHDEXuppvnDJzQIu9" crossorigin="anonymous">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/js/bootstrap.bundle.min.js" integrity="sha384-HwwvtgBNo3bZJJLYd8oVXjrBZt8cqVSpeBNS5n7C8IVInixGAoxmnlMuBnhbgrkm" crossorigin="anonymous"></script>
<meta charset="UTF-8">
<title>방명록 목록</title>
</head>
<body>
	<div class="container w-75  mt-5 mx-auto">
		<h1>방명록</h1>
		<hr>
		<ul class="list-group">
			<c:forEach var="gb" items="${guestList }" varStatus="status">
				<li class="list-group-item list-group-item-action d-flex justify-content-between-align-items-center">
					<a href="guestBook?action=getGuestBook&cid=${gb.cid }" class="text-decoration-none">
						[${status.count }] ${gb.nickname} / ${gb.contents } / ${gb.date }  </a>
					<a href="guestBook?action=deleteGuestBook&cid=${gb.cid }">
						<span class="badge bg-secondary">&times;</span></a>
				</li>
			</c:forEach>
		</ul>
		<c:if test="${error != null }">
			<div class="alert alert-danger alert-dismissible fade show mt-3">에러 발생: ${error }
			<button type="button" class="btn-close" data-bs-dismiss="alert"></button>
			</div>
		</c:if>
		<button class="btn btn-outline-info mb-3" type="button" data-bs-toggle="collapse" data-bs-target="#addForm"
	 aria-expanded="false" aria-controls="addForm">뉴스 등록</button>
		<div id="addForm">
		<div class="card card-body">
			<form method="post" action="/GuestBoard/guestBook?action=addGuestBook" enctype="application/x-www-form-urlencoded">
				<label class="form-label">이름</label>
				<input type="text" name="nickname" class="form-control"></input>
				<label class="form-label">내용</label>
				<textarea cols="50" rows="5" name="contents" class="form-control"></textarea>
				<button type="submit" class="btn btn-success mt-3">저장</button>
			</form>
		</div>
	</div>
	</div>
</body>
</html>