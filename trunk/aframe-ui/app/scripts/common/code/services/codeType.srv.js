'use strict';

angular.module(APP_NAME + '.code.services').service('codeTypeService', ['config', 'icCommonReq',
    function (config, icCommonReq) {
        this.list = function (params) {
            return icCommonReq.request_sync(config.restful.code.codeType.list, "POST", null, params);
        };

        this.find = function (codetype_id) {
            return icCommonReq.request_sync(config.restful.code.codeType.find + codetype_id, "GET");
        };

        this.create = function (params) {
            return icCommonReq.request_sync(config.restful.code.codeType.create, "POST", null, params);
        };

        this.update = function (params) {
            return icCommonReq.request_sync(config.restful.code.codeType.update, "POST", null, params);
        };

        this.delete = function (codetype_id) {
            return icCommonReq.request_sync(config.restful.code.codeType.delete + codetype_id, "POST");
        };

        this.idList = function (params) {
            return icCommonReq.request_sync(config.restful.code.codeType.idList, "POST", null, params);
        };
    }
]);