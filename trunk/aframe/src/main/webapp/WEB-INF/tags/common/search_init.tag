<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ tag pageEncoding="UTF-8" %>
<%@ attribute name="search_url" type="java.lang.String" required="false" %>

<form:hidden path="sortBy"/>
<form:hidden path="sortType"/>
<input type="hidden" name="currentPage" id="currentPage" value="${pageTO.currentPage}">
<input type="hidden" name="pageSize" id="pageSize" value="${pageTO.pageSize}">
<input type="hidden" name="totalPages" id="totalPages" value="${pageTO.totalPages}">

<input type="hidden" name="search_url" id="search_url" value="${search_url}">

<script type="text/javascript">
    function doSort(sortBy) {
        var sortField = document.getElementById("sortBy").value;
        var sortType = document.getElementById("sortType").value;
        if (sortField == sortBy) {
            if (sortType == 'ASC') {
                sortType = 'DESC';
            } else if (sortType == 'DESC') {
                sortType = 'ASC';
            }
        }
        if (sortType == '') {
            sortType = 'ASC';
        }
        document.getElementById("sortBy").value = sortBy;
        document.getElementById("sortType").value = sortType;

        document.forms[0].submit();
    }
</script>