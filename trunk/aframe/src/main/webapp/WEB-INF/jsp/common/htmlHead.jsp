<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="sz.internal.common.component.code.CodeConstant" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags/common" %>
<%@ taglib prefix="code" tagdir="/WEB-INF/tags/common/code" %>
<%@ taglib prefix="widget" tagdir="/WEB-INF/tags/common/widget" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="common" uri="/WEB-INF/common.tld" %>
<!DOCTYPE html>
<meta http-equiv="Pragma" content="no-cache"/>
<meta http-equiv="Cache-Control" content="no-cache"/>
<meta http-equiv="Expires" content="0"/>
<meta http-equiv="X-UA-Compatible" content="IE=edge,Chrome=1" />
<meta http-equiv="Content-Type" content="text/html" charset="UTF-8"/>
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/assets/global/plugins/bootstrap/3.3.6/css/bootstrap.min.css"/>
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/assets/global/plugins/bootstrap/3.3.6/css/bootstrap-theme.min.css"/>
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/assets/global/plugins/bootstrap-datepicker/css/bootstrap-datepicker.css"/>
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/assets/global/plugins/bootstrap-datetimepicker/css/datetimepicker.css"/>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" />
<script src="${pageContext.request.contextPath}/public/assets/global/plugins/jquery-1.11.3.min.js"></script>
<script src="${pageContext.request.contextPath}/public/assets/global/plugins/bootstrap/3.3.6/js/bootstrap.min.js"></script>
<script src="${pageContext.request.contextPath}/public/assets/global/plugins/bootstrap-datepicker/js/bootstrap-datepicker.js"></script>
<script src="${pageContext.request.contextPath}/public/assets/global/plugins/bootstrap-datetimepicker/js/bootstrap-datetimepicker.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/common/common.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/common/date_util.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/common/common2.js"></script>

<tags:modalDialog dialog_id="model_dialog"/>

<script type="text/javascript">
  $(function(){
	$("[data-toggle='tooltip']").tooltip();
    });
</script>