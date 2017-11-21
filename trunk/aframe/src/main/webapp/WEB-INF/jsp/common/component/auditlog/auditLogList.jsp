<%@page import="com.sun.mail.imap.protocol.Item"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="sz.internal.common.component.code.to.CodeTO"%>
<%@include file="/WEB-INF/jsp/common/htmlHead.jsp"%>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/common/style_common.css" />

<script>
	function timeCheck() {
		var sDate = document.getElementById("start_date").value;
		var eDate = document.getElementById("end_date").value;
		if (sDate != 0 && eDate != 0) {
			var sArr = sDate.split('-');
			var eArr = eDate.split('-');
			var sRDate = new Date(sArr[0], sArr[1], sArr[2]);
			var eRDate = new Date(eArr[0], eArr[1], eArr[2]);
			var result = (eRDate - sRDate) / (24 * 60 * 60 * 1000);
		}
		if (result > 30) {
			showError("<spring:message code='err.auditLog.time.interval'/>");
			bool = false;
		} else if (result < 0) {
			showError("<spring:message code='err.auditLog.time.past'/>");
			bool = false;
		}
	}

	function search() {
		var sDate = document.getElementById("start_date").value;
		var eDate = document.getElementById("end_date").value;
		var class_nameLike = document.getElementById("class_nameLike").value;
		var actionLike = document.getElementById("actionLike").value;
		if (class_nameLike == '' && actionLike == '' && sDate == ''
				&& eDate == '') {
			showError("<spring:message code='err.auditLog.search'/>");
			bool = false;
		}
	}

	function checkBeforeSubmit() {
		clearError();
		bool = true;
		search();
		timeCheck();
		return bool;
	}

	function doSubmit() {
		document.forms[0].submit();
	}
</script>

<html>
<body>
	<tags:errorInfo commandName="auditLog" />
	<form:form action="auditLogSearch" commandName="auditLog" class="form-inline">

		<div class="row search-box">
			<i class="arrow-left"></i> <i class="arrow-right"></i>
			<h4>
				<spring:message code="auditLog.auditLogSearch" />
			</h4>

			<div class="input-group searchdiv">
				<span class="input-group-addon"><spring:message code="auditLog.class_name" /></span>
				<form:input path="class_nameLike" maxlength="10" class="form-control" />
			</div>

			<div class="input-group searchdiv">
				<span class="input-group-addon"><spring:message code="auditLog.action" /></span>
				<code:code_comboBox name="actionLike" id="actionLike" firstOption=" " items="${actionList}" value="${auditLog.actionLike}" />
			</div>
			
			<div class="input-group searchdiv">
				<span class="input-group-addon"><spring:message code="auditLog.audit_date.from" /></span>
				<tags:datePicker name="start_date" datePickerID="start_date1" value="${auditLogTO.start_date}" style="width:300px;background: #e5ffff;"/>

			</div>

			<div class="input-group searchdiv">
				<span class="input-group-addon"><spring:message code="auditLog.audit_date.to" /></span>
				<tags:datePicker name="end_date" datePickerID="end_date1" value="${auditLogTO.end_date}" style="width:300px;background: #e5ffff;"/>
			</div>

			<div class="input-group searchdiv">
				<button type="button" name="submitFrm" id="submitFrm1" onclick="checkBeforeSubmit() ? doSubmit() : {}" class="btn btn-default" style="margin-right: 8px">
					<i></i>
					<spring:message code="button.search" />
				</button>
			</div>
		</div>

		<tags:search_init />
		<tags:pagination_nav_up title="auditLog.auditLogList" />
		<div class="wrapper cl">
			<table class="maintable table-auditlog">
				<thead>
					<tr>
						<td><spring:message code="auditLog.class_name" /></td>
						<td><spring:message code="auditLog.action" /></td>
						<td><spring:message code="auditLog.audit_date" /></td>
						<td><spring:message code="auditLog.b_img" /></td>
						<td><spring:message code="auditLog.a_img" /></td>
					</tr>
				</thead>
				<c:if test="${pageTO.totalRecords==0}">
					<tr>
						<td colspan="5"><spring:message code="label.no.records" /></td>
					</tr>
				</c:if>
				<c:forEach items="${pageTO.list}" var="auditLog">
					<tr>
						<td><c:out value="${auditLog.class_name}" /></td>
						<td><c:out value="${auditLog.action}" /></td>
						<td><c:out value="${auditLog.audit_date}" /></td>
						<td><c:out value="${auditLog.b_img}" /></td>
						<td><c:out value="${auditLog.a_img}" /></td>
					</tr>
				</c:forEach>
			</table>
		</div>
	</form:form>
	<tags:pagination_nav />

</body>
</html>