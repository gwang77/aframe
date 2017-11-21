<%@include file="/WEB-INF/jsp/common/htmlHead.jsp"%>

<html>
<head>
</head>
<body>
	<div class="row">
		<div class="col-md-12">
			<h4>View Menu</h4>
		</div>
	</div>
	<div class="row">
		<div class="form-group">
			<label class="col-md-2 col-lg-2" style="text-align: right"><spring:message code="caption" />:</label>
			<div class="col-md-4 col-lg-4">${menuTO.caption}</div>
		</div>
	</div>
	<div class="row">
		<label class="col-md-2 col-lg-2" style="text-align: right"><spring:message code="url" />:</label>
		<div class="col-md-4 col-lg-4">${menuTO.url}</div>
	</div>
	<div class="row">
		<label class="col-md-2 col-lg-2" style="text-align: right"><spring:message code="parent_id" />:</label>
		<div class="col-md-4 col-lg-4">${menuTO.parent_id}</div>
	</div>
	<label class="col-md-2 col-lg-1 control-label"><button type="button" name="back" class="btn btn-default" onclick="window.location='${pageContext.request.contextPath}/menu/menuSearch'">
			<spring:message code="button.back" />
		</button></label>
</body>
</html>

