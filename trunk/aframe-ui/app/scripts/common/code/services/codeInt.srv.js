'use strict';

angular.module(APP_NAME + '.code.services').service('codeIntService', ['config', 'icCommonReq',
    function (config, icCommonReq) {
        this.list = function (params) {
            return icCommonReq.request_sync(config.restful.code.codeInt.list, "POST", null, params);
        };

        this.find = function (code_id, locales, codetype_id) {
            return icCommonReq.request_sync(config.restful.code.codeInt.find + code_id + "/" + locales + "/" + codetype_id, "GET");
        };

        this.create = function (params) {
            return icCommonReq.request_sync(config.restful.code.codeInt.create, "POST", null, params);
        };

        this.update = function (params) {
            return icCommonReq.request_sync(config.restful.code.codeInt.update, "POST", null, params);
        };

        this.delete = function (code_id, locales, codetype_id) {
            return icCommonReq.request_sync(config.restful.code.codeInt.delete + code_id + "/" + locales + "/" + codetype_id, "POST");
        };
    }
]);