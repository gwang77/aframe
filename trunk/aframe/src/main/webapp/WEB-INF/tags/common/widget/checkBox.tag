<%@ tag pageEncoding="UTF-8" %>
<%@ tag trimDirectiveWhitespaces="true" %>
<%@ attribute name="name" type="java.lang.String" required="true" %>
<%@ attribute name="id" type="java.lang.String" required="false" %>
<%@ attribute name="onclick" type="java.lang.String" required="false" %>
<%@ attribute name="value" type="java.lang.String" required="false" %>
<%@ attribute name="checkedVal" type="java.lang.String" required="false" %>

<label class="checkbox" onclick="${onclick}">
    <input id="${id}" name="${name}" type="checkbox" onclick="chickCheckBox(this)" value="${value}" ${value==checkedVal?"checked":""} style="margin-top: -4px;"/><input type="hidden" name="_${name}" value="on" />
</label>
<script type="text/javascript">
    function chickCheckBox(chk) {
        chk.checked = !chk.checked;
    }
    $(function () {
        if (document.getElementById("${id}").checked) {
            $('.checkbox').toggleClass('on');
        } else {
            $('.checkbox').removeClass('on');
        }
    });
</script>
