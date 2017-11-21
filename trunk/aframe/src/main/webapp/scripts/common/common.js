function clearElements(form) {
    var f = form;
    var el, e = 0;
    while (el = f.elements[e++]) {
        if (el.type == 'text' || el.type == 'textarea' ||
            el.type == 'select-one' || el.type == 'select-multiple') {
            el.value = '';
        }
        if (el.type == 'select-one' || el.type == 'select-multiple') {
            el.value = el.options[0].value;
        }
    }
}

function trimElements(form) {
    var f = form;
    var el, e = 0;
    while (el = f.elements[e++]) {
        if (el.type == 'text' || el.type == 'textarea') {
            el.value = el.value.replace(/(^\s*)|(\s*$)/g, "");
        }
    }
}

//remove the space from the start and the end in the string
function trim(str) {
    var regStr = /^\s/;
    if (regStr.test(str)) {
        var _str = str.substring(1);
        return trim(_str);
    }

    var regEnd = /\s$/;
    if (regEnd.test(str)) {
        var _str = str.substring(0, str.length - 1);
        return trim(_str);
    }

    return str
}

function trimText(txtField) {
    if (txtField != null) {
        txtField.value = trim(txtField.value);
    }
}

function handleInputUpper(txtField) {
    trimText(txtField);
    txtField.value = txtField.value.toLocaleUpperCase();
}

//check whether the str is empty(null or "")
//the "" is empty but the " " is not empty
function isEmpty(str) {
    if (str == null) {
        return true;
    }
    if (str.length == 0) {
        return true;
    }
    return false;
}

//check whether the str is not empty
function isNotEmpty(str) {
    return !isEmpty(str);
}

//check whether the str is balnk
//the "" is blank ,and the " ","   ".. is blank too, this is not same isEmpty
function isBlank(str) {
    if (isEmpty(str)) {
        return true;
    }

    var trimStr = trim(str);
    if (trimStr.length == 0) {
        return true;
    }

    return false;
}
//check whether the str is not blank
function isNotBlank(str) {
    return !isBlank(str);
}

function formatFloat(element, point) {
    if (element.value.indexOf(".") == 0) {
        element.value = "0" + element.value;
    }
    if (element.value.indexOf(".") >= 0) {
        var a = "";
        for (var i = 0; i < point; i++) {
            a = a + "0";
        }
        element.value = (element.value + a).substr(0, element.value.indexOf(".") + point + 1);
    }
}

//check the value whether match the number format
//1. the val should be number(18,4) ,such as 10.1,10.11 is right and the 10.111 is not correct
function isNumber(str) {
    var numberNoDot = /^-{0,1}\d{1,16}$/
    var numberDot = /^-{0,1}\d{1,16}\.\d{1,4}$/;
    if (numberNoDot.test(str) || numberDot.test(str)) {
        return true;
    }
    return false;
}

//check whether the parameter contains any non-number
function isString(str) {
    //check wheter contains the non-number(0-9.) char
    var strReg = new RegExp("[^0-9]");
    if (strReg.exec(str) != null) {
        return true;
    }
    return false;
}

function isInteger(str, msg) {
    var regex = new RegExp(/^[-\+]?\d+$/);
    if (!regex.test(str)) {
        alert(msg + " must be a Integer!");
        return false;
    }
    return true;
}

function openWindow(url, width, height) {
    if (navigator.userAgent.indexOf("Chrome") > 0) {
        var param = "width=" + width + ", height=" + height + ",toolbar=no, menubar=no, scrollbars=no, resizable=no,location=no, status=no,alwaysRaised=yes,depended=yes";
        var win = window.open(url, "Window", param);
    } else {
        return window.showModalDialog(url, window, 'dialogHeight: ' + height + 'px; dialogWidth: ' + width + 'px;edge: Raised; center: Yes; help: Yes;scroll:no; resizable: no; status: no;resizable:yes');
    }
}
