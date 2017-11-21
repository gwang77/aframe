<%@ tag pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<script type="text/javascript">
    function doPagination(page) {
        document.getElementById("currentPage").value = page;
        prepareSearchURL();
        document.forms[0].submit();
    }
    function prepareSearchURL() {
        var sUrl = document.getElementById("search_url").value;
        if (sUrl != "") {
            document.forms[0].action = sUrl;
        }
    }
</script>
<div class="page-box">
	<ul class="pagination pagination-center cl">
		<li><a href="#" onclick="doPagination(1)">&laquo;</a></li>
		<li><a href="#" onclick="doPagination(${pageTO.currentPage}-1)">&lt;</a></li>

		<c:choose>
			<c:when test="${pageTO.totalPages<6}">
				<c:forEach var="pageIdx" begin="1" end="${pageTO.totalPages}">
					<li ${pageIdx==pageTO.currentPage?"class='active'":""}><a href="#" onclick="doPagination(${pageIdx})">${pageIdx}</a></li>
				</c:forEach>
			</c:when>

			<c:when test="${pageTO.totalPages>5&&pageTO.currentPage<4}">
				<c:forEach var="pageIdx" begin="1" end="5">
					<li ${pageIdx==pageTO.currentPage?"class='active'":""}><a href="#" onclick="doPagination(${pageIdx})">${pageIdx}</a></li>
				</c:forEach>
				<li><a href="#" onclick="doPagination(${pageTO.currentPage}+2)">...</a></li>
			</c:when>

			<c:when test="${pageTO.currentPage<pageTO.totalPages-4}">
				<li><a href="#" onclick="doPagination(${pageTO.currentPage}-2)">...</a></li>
				<c:forEach var="pageIdx" begin="${pageTO.currentPage-2}" end="${pageTO.currentPage+2}">
					<li ${pageIdx==pageTO.currentPage?"class='active'":""}><a href="#" onclick="doPagination(${pageIdx})">${pageIdx}</a></li>
				</c:forEach>
				<li><a href="#" onclick="doPagination(${pageTO.currentPage}+2)">...</a></li>
			</c:when>

			<c:otherwise>
				<li><a href="#" onclick="doPagination(${pageTO.currentPage}-2)">...</a></li>
				<c:forEach var="pageIdx" begin="${pageTO.totalPages-4}" end="${pageTO.totalPages}">
					<li ${pageIdx==pageTO.currentPage?"class='active'":""}><a href="#" onclick="doPagination(${pageIdx})">${pageIdx}</a></li>
				</c:forEach>
			</c:otherwise>
		</c:choose>

		<c:choose>
			<c:when test="${pageTO.currentPage<pageTO.totalPages}">
				<li><a href="#" onclick="doPagination(${pageTO.currentPage}+1)">&gt;</a></li>
			</c:when>
			<c:otherwise>
				<li><a href="#" onclick="doPagination(${pageTO.totalPages})">&gt;</a></li>
			</c:otherwise>
		</c:choose>

		<li><a href="#" onclick="doPagination(${pageTO.totalPages})">&raquo;</a></li>
	</ul>

	<p>
		<spring:message code="label.totalSize" />:${pageTO.totalRecords}
	</p>
</div>
