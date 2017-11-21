<%@include file="/WEB-INF/jsp/common/htmlHead.jsp"%>
<html>
<script type="text/javascript">
    function doSubmit() {
    	clearError();
    	if(codeTypeCheck()){
    	document.forms[0].action = "${actionPath}";
        document.forms[0].submit();
        }   	
    }
    function doBack() { 
    	document.forms[0].action = "${pageContext.request.contextPath}/common/codeint/codeIntSearch";
        document.forms[0].submit();        	
    }
    function codeTypeCheck(){
    	var codetype=document.getElementById("codetype_id").value; 
		if(codetype.match(/^\w+$/)) 
			return true;   
		else showError("<spring:message code='err.codeType.codetype_id'/>");		
    }
</script>
<body style="height:600px">
	<div class="wrapper">
		<div class="table-title">	
				<h4>
					<spring:message code="codeType.create" />
				</h4>						
	</div>
	</div>
	<tags:errorInfo commandName="codetype" />
	<form:form action="${actionPath}" commandName="codetype" cssClass="form-horizontal">		 		
			<div class="form-group">
				<label class="control-label"><spring:message code="codeType.codetype_id" />:</label>            
				<div class="input-control cl">
					<form:input path="codetype_id" maxlength="20" class="form-control"/>
				</div>
				<tags:essential />								
			</div>		
			<div class="form-group">
				<label class="control-label" style="text-align: right; margin-top: 6px;"><spring:message code="codeType.codetype_desc" />:</label>
				<div class="input-control cl">
					<form:input path="codetype_desc" maxlength="200" class="form-control" />
				</div>
			</div>	
	<div class="row">
		<div class="btn-box form-btn-box">
			<button type="button" name="submitFrm" class="btn btn-default"  onclick="doSubmit()">
				<spring:message code="button.submit" />
			</button>
			<button type="button" name="back" class="btn btn-default btn-back"  onclick="doBack()">
					<spring:message code="button.back" />
			</button>
		</div>
	</div>
	</form:form>
	<br>
</body>
</html>

