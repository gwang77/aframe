<%@include file="/WEB-INF/jsp/common/htmlHead.jsp"%>
<html>
<head>
</head>
<script type="text/javascript">
    function doSubmit() {      	
    	document.forms[0].action = "${actionPath}";
        document.forms[0].submit(); 	
    }
    function doBack() { 
    	document.forms[0].action = "${pageContext.request.contextPath}/common/codetype/codeTypeSearch";
        document.forms[0].submit();        	
    }   
</script>
<body >
	<div class="wrapper">
		<div class="table-title">	
				<h4>
					<spring:message code="codeType.update" />
				</h4>		
		</div>
	</div>
	<tags:errorInfo commandName="codetype" />
	<form:form action="${actionPath}" commandName="codetype" cssClass="form-horizontal">		
		 <input type="hidden" name="codetype_id" value="${codetype.codetype_id}">		
			<div class="form-group">
				<label class="control-label"><spring:message code="codeType.codetype_id" />:</label>             
				<div class="input-control cl">
					<c:out value="${codetype.codetype_id}"/>
				</div>
			</div>		
			<div class="form-group">
				<label class="control-label" ><spring:message code="codeType.codetype_desc" />:</label>
				<div class="input-control cl">
					<form:input path="codetype_desc" maxlength="200" class="form-control " />
				</div>
			</div>	
	<div class="row">
		<div class="btn-box form-btn-box">
			<button type="button" name="submitFrm" class="btn btn-default " style="margin-right: 8px" onclick="doSubmit()">
				<spring:message code="button.submit" />
			</button>
			<button type="button" name="back" class="btn btn-default btn-back" style="margin-right: 8px" onclick="doBack()">
					<spring:message code="button.back" />
				</button>
		</div>
	</div>
	</form:form>
	<br>
</body>
</html>

