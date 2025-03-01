﻿<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<jsp:include page="/WEB-INF/views/common/header.jsp">
	<jsp:param value="Dev 목록" name="title"/>
</jsp:include>
<table class="table w-75 mx-auto">
	<thead>
		<tr>
		  <th scope="col">번호</th>
		  <th scope="col">이름</th>
		  <th scope="col">경력</th>
		  <th scope="col">이메일</th>
		  <th scope="col">성별</th>
		  <th scope="col">개발가능언어</th>
		  <th scope="col">등록일시</th>
		  <th scope="col">수정 | 삭제</th>
		</tr>
	</thead>
	<tbody>
		<c:if test="${empty list}">
			<tr>
				<td colspan="7">조회된 회원이 없습니다.</td>
			</tr>
		</c:if>
		
		<c:if test="${!empty list}">
			<c:forEach items="${list}" var="dev" varStatus="vs">
				<tr>
					<td>${dev.no}</td>
					<td>${dev.name}</td>
					<td>${dev.career}년</td>
					<td>${dev.email}</td>
					<td>${empty dev.gender ? '' : (dev.gender eq 'M' ? '남' : '여')}</td>
					<td>
						<c:forEach items="${dev.lang}" var="lang" varStatus="vs">
							${lang}${not vs.last ? ',' : ''}
						</c:forEach>
					</td>
					<td>
						<fmt:parseDate value="${dev.createdAt}" pattern="yyyy-MM-dd'T'HH:mm" var="createdAt"/>
						<fmt:formatDate value="${createdAt}" pattern="yyyy년 MM월 dd일 HH:mm"/>
					</td>
					<td>
						<button class="btn-update btn btn-sm btn-outline-secondary" value="${dev.no}">수정</button>
						<button class="btn-delete btn btn-sm btn-outline-danger" data-dev-no="${dev.no}">삭제</button>
					</td>
				</tr>
			</c:forEach>
		</c:if>
	</tbody>
</table>
<form action="${pageContext.request.contextPath}/demo/deleteDev.do" name="devDelFrm" method="POST">
	<input type="hidden" name="no" />
</form>
<script>
/**
 *  @실습문제 - Dev삭제 (dataSet 사용)
 *   - 삭제 후에는 목록페이지로 redirect 처리
 */
document.querySelectorAll(".btn-delete").forEach((btn) => {
	btn.addEventListener('click', (e) => {
		console.log(e.target);
		// console.log(e.target.dataset.devNo);
		const frm = document.devDelFrm;
		frm.no.value = e.target.dataset.devNo;
		console.log(frm.no.value);
		if(confirm('해당 정보를 삭제하시겠습니까?')){
			frm.submit();
		}
	});
});

/**
 *  @실습문제 - Dev수정
 *	 - GET /demo/updateDev.do page수정 폼 요청
 *	 - POST /demo/updateDev.do db수정 요청
 */
document.querySelectorAll(".btn-update").forEach((btn) => {
	btn.addEventListener('click', (e) => {
		location.href = `${pageContext.request.contextPath}/demo/updateDev.do?no=\${e.target.value}`;
	});
});
</script>
<jsp:include page="/WEB-INF/views/common/footer.jsp"></jsp:include>
