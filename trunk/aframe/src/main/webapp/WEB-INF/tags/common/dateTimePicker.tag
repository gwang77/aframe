<%@ tag pageEncoding="UTF-8"%>
<%@ attribute name="name" type="java.lang.String" required="true"%>
<%@ attribute name="value" type="java.lang.String" required="true"%>


<div class="input-daterange input-group">
	<input type="text" id="${name}" name="${name}" class="input-group date form-control" data-date-format="yyyy-mm-dd hh:ii" value="${value}" style="background: #e5ffff;" readonly> 	
	<i class="icon-calendar"></i>
</div>

<script type="text/javascript">
    $('#${name}').datetimepicker({
	todayBtn : true,
    });
        
    $('#${name}').focus(function cls() {
	$('#${name}').val("");
    });
    
   
</script>
