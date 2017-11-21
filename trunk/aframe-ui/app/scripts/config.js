'use strict';

var root_restful = constant_env.root_restful_env;
var logout_redirect = constant_env.logout_redirect_env;
var login_redirect = constant_env.login_redirect_env;

function getLoginRedirect() {
    return login_redirect;
}

angular.module(APP_NAME).value(
    "config", {
        "defaultPage": "layout.home",
        "restful": {
            "code": {
                "get": root_restful + "json/common/code/getCode/",
                "gets": root_restful + "json/common/code/getCodes/",
                "codeType": {
                    "list": root_restful + "json/common/codetype/codeTypeSearch",
                    "find": root_restful + "json/common/codetype/find/",
                    "create": root_restful + "json/common/codetype/createCodeType",
                    "update": root_restful + "json/common/codetype/updateCodeType",
                    "delete": root_restful + "json/common/codetype/deleteCodeType/",
                    "idList": root_restful + "json/common/codetype/codeTypeIdSearch"
                },
                "codeInt": {
                    "list": root_restful + "json/common/codeint/codeIntSearch",
                    "find": root_restful + "json/common/codeint/find/",
                    "create": root_restful + "json/common/codeint/createCodeInt",
                    "update": root_restful + "json/common/codeint/updateCodeInt",
                    "delete": root_restful + "json/common/codeint/deleteCodeInt/"
                }
            },
            "layout": {
                "showMenu": root_restful + "json/menu/getMenusUI/"
            },
            "menu": {
                "menuList": root_restful + "json/menu/getMenuList/",
                "find": root_restful + "json/menu/findMenu/",
                "create": root_restful + "json/menu/createMenu",
                "update": root_restful + "json/menu/updateMenu",
                "delete": root_restful + "json/menu/deleteMenu/",
                "getRootCaptions": root_restful + "json/menu/getRootCaptions",
            },
            "user": {
                "login": root_restful + "api/acm/ajaxLogin",
                "logout": root_restful + "api/acm/ajaxLogout",
                "loginname": root_restful + "api/acm/loginname"
            },
            "auditLog": {
                "searchAuditLog": root_restful + "json/auditLog/searchAuditLog",
                "convertAuditLogTOtoMap": root_restful + "json/auditLog/convertAuditLogTOtoMap"
            },
            "statistic": {
                "searchStatistic": root_restful + "json/statistic/searchStatistic",
                "download": root_restful + "json/statistic/statisticDownload"
            },
            /*mergepart*/
            "config_end": {}

        }
    }
);
