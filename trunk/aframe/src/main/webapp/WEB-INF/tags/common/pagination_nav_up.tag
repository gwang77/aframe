<%@ tag pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ attribute name="title" type="java.lang.String" required="false"%>

<div class="wrapper">
	<div class="table-title">
		<h4>
			<spring:message code="${title}" />
		</h4>
		<p>(${pageTO.totalRecords} Records)</p>
	</div>
</div>