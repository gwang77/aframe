<%@include file="/WEB-INF/jsp/common/htmlHead.jsp"%>
<html>
<head>
</head>
<script type="text/javascript">
	var bool = true;
	function doSubmit(id) {
		document.forms[0].action = "${pageContext.request.contextPath}/menu/menuUpdate";
		document.forms[0].submit();
	}

	function menuSeqNoCheck() {
		var seq_no = document.getElementById("seq_no").value;
		if (!seq_no.match(/^[0-9]*[1-9][0-9]*$/) || seq_no == null
				|| seq_no == '') {
			showError("<spring:message code='err.Seq_no'/>");
			bool = false;
		}
	}
	function checkBeforeSubmit() {
		clearError();
		bool = true;
		menuSeqNoCheck();
		return bool;
	}
</script>
<body>
<body style="height: 600px">
	<div class="wrapper">
		<div class="table-title">
			<h4>
				<spring:message code="menu.menuUpdate" />
			</h4>
		</div>
	</div>
	<tags:errorInfo commandName="menuTO" />

	<form:form action="menuUpdate" commandName="menuTO" cssClass="form-horizontal">
		<form:hidden path="id" value="${menuTO.id}" />
		<form:hidden path="parent_id" value="${menuTO.parent_id}" />
		<input type="hidden" name="id">
		<div class="row">
			<div class="form-group">
				<div class="col-md-4 col-lg-2 control-label">
					<label class="control-label"><spring:message code="Caption" />:</label>
				</div>
				<div class="col-md-4 col-lg-3">
					<form:input path="caption" size="50" maxlength="200" class="form-control col-md-2 col-lg-2" />
				</div>
				<tags:essential />
			</div>
		</div>
		<div class="row">
			<div class="form-group">
				<div class="col-md-4 col-lg-2 control-label">
					<label class="control-label"><spring:message code="Style" />:</label>
				</div>
				<div class="col-md-4 col-lg-3">
					<form:input path="style" size="50" maxlength="200" class="form-control col-md-2 col-lg-2" />
				</div>
			</div>
		</div>
		<div class="row">
			<div class="form-group">
				<div class="col-md-4 col-lg-2 control-label">
					<label class="control-label"><spring:message code="Url" />:</label>
				</div>
				<div class="col-md-4 col-lg-3">
					<form:input path="url" size="50" maxlength="200" class="form-control col-md-2 col-lg-2" />
				</div>
			</div>
		</div>
		<div class="row">
			<div class="form-group">
				<div class="col-md-4 col-lg-2 control-label">
					<label class="control-label"><spring:message code="Permission" />:</label>
				</div>
				<div class="col-md-4 col-lg-3">
					<form:input path="permission" size="50" maxlength="200" class="form-control col-md-2 col-lg-2" />
				</div>
			</div>
		</div>

		<div class="row">
			<div class="form-group">
				<div class="col-md-4 col-lg-2 control-label">
				<label class="control-label"><spring:message code="Available" />:</label>
				</div>
				<div class="col-md-4 col-lg-3" style="margin-top: 8px;">
					<div class="input-control cl">
						<code:code_radio codeType="<%=CodeConstant.CODE_TYPE_YES_NO%>" name="available" value="${menuTO.available}" />
					</div>
				</div>
				<tags:essential />
			</div>
		</div>
		<div class="row">
			<div class="form-group">
				<div class="col-md-4 col-lg-2 control-label">
					<label class="control-label"><spring:message code="SeqNo" />:</label>
				</div>
				<div class="col-md-4 col-lg-3">
					<form:input path="seq_no" size="50" maxlength="200" class="form-control col-md-2 col-lg-2" />
				</div>
				<tags:essential />
			</div>
		</div>
		<div class="btn-box form-btn-box">
	     <button type="button" name="submitFrm" class="btn btn-default btn-default" style="margin-right: 8px" onclick="checkBeforeSubmit() ? doSubmit() : {}">
			     <spring:message code="button.submit" />
		</button>
			
			<button type="button" name="back" class="btn btn-default btn-back" style="margin-right: 8px" onclick="window.location='${pageContext.request.contextPath}/menu/menuSearch'">
				<spring:message code="button.back" />
			</button>
		</div>
	</form:form>
	<br />
	<br />
	<br />
	<br />
	<br />
	<br />
</body>
</html>