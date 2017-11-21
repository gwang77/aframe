<%@include file="/WEB-INF/jsp/common/htmlHead.jsp"%>

<html>
<head>
</head>
<body>
	<script type="text/javascript">
		function doback(ctId) {
			document.forms[0].codetype_id.value=ctId;
			document.forms[0].action = "${pageContext.request.contextPath}/common/codeint/codeIntSearch";
			document.forms[0].submit();
		}
	</script>
	<div class="row">
		<div class="col-md-12">
			<h4>
				<spring:message code="CodeInt" />
			</h4>
		</div>
	</div>
	<tags:errorInfo commandName="codeint" />
	<form:form commandName="codeint" cssClass="form-horizontal">
		<input type="hidden" name="codetype_id">
		<div class="row">
			<div class="form-group">
				<label class="col-md-2 col-lg-2 control-label"><spring:message code="codeInt.code_id" />:</label>
				<div class="col-md-4 col-lg-4">
					<c:out value="${codeint.code_id}" />
				</div>
			</div>
		</div>
		<div class="row">
			<div class="form-group">
				<label class="col-md-2 col-lg-2 control-label"><spring:message code="codeInt.code_desc" />:</label>

				<div class="col-md-4 col-lg-4">
					<c:out value="${codeint.code_desc}" />
				</div>
			</div>
		</div>
		<div class="row">
			<div class="form-group">
				<label class="col-md-2 col-lg-2 control-label"><spring:message code="codeInt.codetype_id" />:</label>

				<div class="col-md-4 col-lg-4">
					<c:out value="${codeint.codetype_id}" />
				</div>
			</div>
		</div>
		<div class="row">
			<div class="form-group">
				<label class="col-md-2 col-lg-2 control-label"><spring:message code="codeInt.status" />:</label>
				<div class="col-md-4 col-lg-4">
					<c:out value="${codeint.status}" />
				</div>
			</div>
		</div>
		<div class="row">
			<div class="form-group">
				<label class="col-md-2 col-lg-2 control-label"><spring:message code="codeInt.locale" />:</label>

				<div class="col-md-4 col-lg-4">
					<c:out value="${codeint.locales}" />
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-md-12">
				<button type="button" name="back" class="btn btn-default" onclick="doback('${codeint.codetype_id}')">
					<spring:message code="button.back" />
				</button>
			</div>
		</div>
	</form:form>
</body>
</html>

