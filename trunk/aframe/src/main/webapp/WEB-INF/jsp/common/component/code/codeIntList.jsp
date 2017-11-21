<%@include file="/WEB-INF/jsp/common/htmlHead.jsp"%>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/common/style_common.css" />
<html>
<body style="height: 600px">
	<tags:local_init />
	<tags:errorInfo commandName="codeTO" />
	<form:form action="codeIntSearch" commandName="codeTO" class="form-inline" style="height: 1500px">
		<input type="hidden" name="code_id">
		<div class="row search-box">
			<i class="arrow-left"></i> <i class="arrow-right"></i>
			<h4>
				<spring:message code="codeInt.codedetail" />
			</h4>
			<div class="input-group searchdiv">
				<span class="input-group-addon"><spring:message code="codeType.codetype_id" /></span>
				<code:code_comboBox items="${codetypelist}" name="codetype_id" onchange="doSearch();" value="${codeTO.codetype_id}" />
			</div>
			<div class="input-group searchdiv" style="margin-left:60px">
				<div class="input-control cl">
					<code:code_radio codeType="<%=CodeConstant.CODE_TYPE_LOCAL%>" name="locales" onclick="doSift()" value="${codeTO.locales}" />
				</div>
			</div>
			<div class="input-group searchdiv">
				<button type="submit" name="submitFrm" class="btn btn-default" style="width: 220px;" onclick="doCreateCodeType()">
					<spring:message code="codeType.create" />
				</button>
			</div>
			<div class="input-group searchdiv">
				<button type="submit" name="submitFrm" class="btn btn-default" style="width: 220px;margin-left:-60px" onclick="doReview()">
					<spring:message code="codeType.view" />
				</button>
			</div>
		</div>
		<div class="table-responsive">
			<table class="maintable table-code">
				<thead>
					<tr>
						<td width="30%"><spring:message code="codeInt.code_id" /></td>
						<td width="30%"><spring:message code="codeInt.code_desc" /></td>
						<td width="10%"><spring:message code="codeInt.status" /></td>
						<td width="5%"><spring:message code="codeInt.locale" /></td>
						<td width="5%"><spring:message code="codeInt.code_seq" /></td>
						<td width="20%"><spring:message code="codeInt.operation" /></td>
					</tr>
				</thead>
				<c:forEach items="${pageTO}" var="CodeTO">
					<tr>
						<td><c:if test="${CodeTO.editable=='N'}">
								<c:out value="${CodeTO.code_id}" />
							</c:if> <c:if test="${CodeTO.editable!='N'}">
								<a href="${pageContext.request.contextPath}/common/codeint/${CodeTO.codetype_id}/${CodeTO.code_id}/${CodeTO.locales}/edit"><c:out value="${CodeTO.code_id}" /></a>
							</c:if></td>
						<td><c:out value="${CodeTO.code_desc}" /></td>
						<td><code:code_desc codeType="<%=CodeConstant.CODE_TYPE_STATUS %>" value="${CodeTO.status}" /></td>
						<td><code:code_desc codeType="<%=CodeConstant.CODE_TYPE_LOCAL %>" value="${CodeTO.locales}" /></td>
						<td><c:out value="${CodeTO.code_seq}" /></td>
						<td><c:if test="${CodeTO.editable!='N'}">
								<button type="button" name="delete" class="btn btn-default btn-xs btn-delete" onclick="showConfirmModalDialog('<spring:message code="label.confirmDel" />', doDelete, '${CodeTO.codetype_id}','${CodeTO.code_id}','${CodeTO.locales}')" data-toggle="tooltip" title="<spring:message code="button.delete"/>">
									<span class="glyphicon glyphicon-remove"></span>
									<spring:message code="button.delete" />
								</button>
							</c:if>
					</tr>
				</c:forEach>
			</table>
		</div>
		<br>
		<div class="wrapper cl">
			<div class="btn-box">
				<button type="button" name="create" class="btn btn-default" style="margin-right: 8px" onclick="doCreateCodeInt('${codeTO.codetype_id}')">
					<spring:message code="button.create" />
				</button>
			</div>
		</div>
		<br>
	</form:form>
</body>
</html>
<script type="text/javascript">
    function doDelete(ctID, cID, locales) {
	document.forms[0].action = "${pageContext.request.contextPath}/common/codeint/"
		+ ctID + "/" + cID + "/" + locales + "/delete";
	document.forms[0].submit();
    }
    function doEdit(cID, ctID) {
	document.forms[0].code_id.value = cID;
	document.forms[0].codetype_id.value = ctID;
	document.forms[0].action = "${pageContext.request.contextPath}/common/codeint/edit";
	document.forms[0].submit();
    }
    function doCreateCodeInt(ctID) {
	document.forms[0].codetype_id.value = ctID;
	document.forms[0].action = "${pageContext.request.contextPath}/common/codeint/codeIntCreateInit";
	document.forms[0].submit();
    }
    function doCreateCodeType() {
	document.forms[0].action = "${pageContext.request.contextPath}/common/codetype/codeTypeCreateInit";
	document.forms[0].submit();
    }
    function doSearch() {
	document.forms[0].action = "${pageContext.request.contextPath}/common/codeint/codeIntSearch";
	document.forms[0].submit();
    }
    function doSift() {
	document.forms[0].action = "${pageContext.request.contextPath}/common/codeint/codeIntSearch";
	document.forms[0].submit();
    }
    function doReview() {
	document.forms[0].action = "${pageContext.request.contextPath}/common/codetype/codeTypeSearch";
	document.forms[0].submit();
    }
</script>
