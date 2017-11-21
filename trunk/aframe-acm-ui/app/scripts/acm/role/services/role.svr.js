'use strict';

angular.module(APP_NAME_ACM + '.acm.role.services').service('roleService', ['config', 'icCommonReq',
    function (config, icCommonReq) {
        this.list = function (params) {
            return icCommonReq.request_sync(config.restful.acm.role.list, "POST", null, params);
        };

        this.searchPage = function (params) {
            return icCommonReq.request_sync(config.restful.acm.role.searchPage, "POST", null, params);
        };

        this.roleUnassignedPermission = function (id,app_id) {
            return icCommonReq.request_sync(config.restful.acm.role.roleUnassignedPermission+id+ "/" +app_id, "GET");
        };

        this.find = function (id) {
            return icCommonReq.request_sync(config.restful.acm.role.find + id, "GET");
        };

        this.findByName = function (username) {
            return icCommonReq.request_sync(config.restful.acm.role.findByName + username, "GET");
        };

        this.create = function (params) {
            return icCommonReq.request_sync(config.restful.acm.role.create, "POST", null, params);
        };

        this.update = function (params) {
            return icCommonReq.request_sync(config.restful.acm.role.update, "POST", null, params);
        };

        this.delete = function (id) {
            return icCommonReq.request_sync(config.restful.acm.role.delete + id, "POST");
        };
    }
]);