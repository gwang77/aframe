function validDate(value, datePattern) {
    var MONTH = "MM";
    var DAY = "dd";
    var YEAR = "yyyy";

    var orderMonth = datePattern.indexOf(MONTH);
    var orderDay = datePattern.indexOf(DAY);
    var orderYear = datePattern.indexOf(YEAR);

    var patternRegExpStr = datePattern.replace(MONTH, "\\d{2}");
    patternRegExpStr = patternRegExpStr.replace(DAY, "\\d{2}");
    patternRegExpStr = patternRegExpStr.replace(YEAR, "\\d{4}");
    var dateRegexp = new RegExp("^" + patternRegExpStr + "$");

    var matched = dateRegexp.exec(value);
    var matchedArr = new Array();
    if (matched != null) {
        matchedArr[0] = value.substr(orderYear, 4);
        matchedArr[1] = value.substr(orderMonth, 2);
        matchedArr[2] = value.substr(orderDay, 2);
        if (!isValidDate(matchedArr[2], matchedArr[1], matchedArr[0])) {
            return false;
        }
        return true;
    } else {
        return false;
    }
}

function isValidDate(day, month, year) {
    if (month < 1 || month > 12) {
        return false;
    }
    if (day < 1 || day > 31) {
        return false;
    }
    if ((month == 4 || month == 6 || month == 9 || month == 11) && (day == 31)) {
        return false;
    }
    if (month == 2) {
        var leap = (year % 4 == 0 && (year % 100 != 0 || year % 400 == 0));
        if (day > 29 || (day == 29 && !leap)) {
            return false;
        }
    }
    return true;
}

function compareDate(dateFromStr, dateToStr, datePattern) {
    var dateFrom = parseDate(dateFromStr, datePattern);
    var dateTo = parseDate(dateToStr, datePattern);
    return (dateTo - dateFrom) / (24 * 60 * 60 * 1000);
}

function parseDate(dateStr, datePattern) {
    var MONTH = "MM";
    var DAY = "dd";
    var YEAR = "yyyy";
    var orderYear = datePattern.indexOf(YEAR);
    var orderMonth = datePattern.indexOf(MONTH);
    var orderDay = datePattern.indexOf(DAY);
    var y = dateStr.substr(orderYear, 4);
    var m = dateStr.substr(orderMonth, 2) - 1;//as the month is 0-11, so the m need to subtract 1
    var d = dateStr.substr(orderDay, 2);
    var date = new Date();
    date.setFullYear(y);
    date.setMonth(m);
    date.setDate(d);
    return date;
}

function formatDate(date) {
    var result = "";
    var mm = (date.getMonth() + 1).toString();
    if (mm.length < 2) {
        mm = "0" + mm;
    }
    var dd = date.getDate().toString();
    if (dd.length < 2) {
        dd = "0" + dd;
    }
    var yyyy = date.getFullYear();
    result = yyyy + "-" + mm + "-" + dd;
    return result;
}

