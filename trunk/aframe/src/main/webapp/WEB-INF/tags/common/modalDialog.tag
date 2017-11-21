<%@ tag pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ attribute name="dialog_id" type="java.lang.String" required="true" %>

<div class="modal fade" id="${dialog_id}">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="myModalLable"><spring:message code="label.confirm"/></h4>
            </div>
            <div class="modal-body"><span class="glyphicon glyphicon-exclamation-sign" style="float:left"></span><div id="dialogMsgContent" ></div></div>
            <div class="modal-footer">
                <input type="hidden" id="idHidden"/>
                <button type="button" name="okButton" id="okButton" class="btn btn-primary" onclick=""><spring:message code="button.ok"/></button>
                <button type="button" class="btn btn-default" data-dismiss="modal"><spring:message code="button.cancel"/></button>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">
    function showConfirmModalDialog(msg, okEvent, param1, param2, param3) {
        document.getElementById("dialogMsgContent").innerHTML = msg;
        document.getElementById("okButton").onclick = function () {
            okEvent(param1, param2, param3);
        };
        $("#${dialog_id}").modal("show");
    }

</script>

<style>
    #${dialog_id}{
        top:160px;
    }
</style>
