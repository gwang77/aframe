<%@include file="/WEB-INF/jsp/common/htmlHead.jsp"%>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/common/style_common.css" />
<html>
<body style="height:600px">
	<script type="text/javascript">
		function doDelete(id) {
			window.location = id + "/delete"
		}
		function doBack(ctID){
			document.forms[0].action = "${pageContext.request.contextPath}/common/codeint/codeIntSearch";
			document.forms[0].submit();
		}
	</script>
	<tags:errorInfo commandName="codetype" />
	<form:form action="codeTypeSearch" commandName="codetype" class="form-inline">
		<input type="hidden" name="codetype_id">
		<div class="row search-box">
	       <i class="arrow-left"></i> <i class="arrow-right"></i>
		<h4>
			<spring:message code="codeType.codetypeSearch" />
		</h4>
	   </div>
		<div class="table-responsive">
			<table class="maintable table-codetype">
					<thead>
						<tr>
							<td width="20%"><spring:message code="codeType.codetype_id" /></td>
							<td width="20%"><spring:message code="codeType.codetype_desc" /></td>
							<td width="30%"><spring:message code="codeType.Operation" /></td>
						</tr>
					</thead>
					<c:forEach items="${pageTO}" var="CodeTO">
						<tr>
							<td><a href="${pageContext.request.contextPath}/common/codetype/${CodeTO.codetype_id}/edit"><c:out value="${CodeTO.codetype_id}" /></a></td>
							<td><c:out value="${CodeTO.codetype_desc}"/></td>
							<td>	
								<button type="button" name="delete" class="btn btn-default btn-xs btn-delete" onclick="showConfirmModalDialog('<spring:message code="label.confirmDel" />', doDelete,'${CodeTO.codetype_id}')"data-toggle="tooltip" title="<spring:message code="button.delete"/>">
								<span class="glyphicon glyphicon-remove" ></span>
								<spring:message code="button.delete" />
								</button>
						</tr>
					</c:forEach>
				</table>
			</div>
			<br>
		<div class="wrapper cl">
			<div class="btn-box">
				<button type="button" name="create" class="btn btn-default" onclick="doBack()">
					<spring:message code="button.back" />
				</button>
			</div>
		</div>
		<br/>
	</form:form>
</body>
</html>

