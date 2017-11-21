'use strict';

angular.module(APP_NAME_ACM + '.acm.permission.services').service('permissionService', ['config', 'icCommonReq',
    function (config, icCommonReq) {
        this.list = function (params) {
            return icCommonReq.request_sync(config.restful.acm.permission.list, "POST", null, params);
        };

        this.searchPage = function (params) {
            return icCommonReq.request_sync(config.restful.acm.permission.searchPage, "POST", null, params);
        };

        this.find = function (id) {
            return icCommonReq.request_sync(config.restful.acm.permission.find + id, "GET");
        };

        this.findByName = function (permissionname) {
            return icCommonReq.request_sync(config.restful.acm.permission.findByName + permissionname, "GET");
        };

        this.create = function (params) {
            return icCommonReq.request_sync(config.restful.acm.permission.create, "POST", null, params);
        };

        this.update = function (params) {
            return icCommonReq.request_sync(config.restful.acm.permission.update, "POST", null, params);
        };

        this.delete = function (id) {
            return icCommonReq.request_sync(config.restful.acm.permission.delete + id, "POST");
        };

    }
]);