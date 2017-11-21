<%@ tag pageEncoding="UTF-8" %>
<%@ attribute name="name" type="java.lang.String" required="true" %>
<%@ attribute name="datePickerID" type="java.lang.String" required="true" %>
<%@ attribute name="value" type="java.lang.String" required="false" %>
<%@ attribute name="onchange" type="java.lang.String" required="false" %>
<%@ attribute name="style" type="java.lang.String" required="false" %>
<div id="${datePickerID}" class="input-group date input-control" data-date-format="yyyy-mm-dd">
    <input class="form-control" type="text" name="${name}" id="${name}" value="${value}" onchange="${onchange}" style="${style}"
    readonly>
   	<div class="input-group-addon icon-time"><span class="glyphicon glyphicon-th"></span></div>
</div>

<script type="text/javascript">
    $('#${datePickerID}').datepicker();
    $('#${datePickerID}').on("changeDate", function () {
        $('#${name}').val($('#${datePickerID}').datepicker("getFormattedDate"));
        $('#${datePickerID}').datepicker("hide");
    });

 	$('#${name}').focus(function cls(){    	
    	$('#${name}').val("");
    });
</script>
