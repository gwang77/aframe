'use strict';

angular.module(APP_NAME_ACM + '.acm.user.services').service('userService', ['config', 'icCommonReq',
    function (config, icCommonReq) {
        this.list = function (params) {
            return icCommonReq.request_sync(config.restful.acm.user.list, "POST", null, params);
        };

        this.searchPage = function (params) {
            return icCommonReq.request_sync(config.restful.acm.user.searchPage, "POST", null, params);
        };

        this.findUserByUser_from = function () {
            return icCommonReq.request_sync(config.restful.acm.user.findUserByUser_from, "POST");
        };
        this.findUserNotUser_from = function () {
            return icCommonReq.request_sync(config.restful.acm.user.findUserNotUser_from, "POST");
        };

        this.findUserNotUser_to = function (params) {
            return icCommonReq.request_sync(config.restful.acm.user.findUserNotUser_to, "POST", null, params);
        };

        this.find = function (id) {
            return icCommonReq.request_sync(config.restful.acm.user.find + id, "GET");
        };

        this.findByName = function (username) {
            return icCommonReq.request_sync(config.restful.acm.user.findByName + username, "GET");
        };

        this.create = function (params) {
            return icCommonReq.request_sync(config.restful.acm.user.create, "POST", null, params);
        };

        this.update = function (params) {
            return icCommonReq.request_sync(config.restful.acm.user.update, "POST", null, params);
        };

        this.delete = function (id) {
            return icCommonReq.request_sync(config.restful.acm.user.delete + id, "POST");
        };

        this.findUserWithPriv = function (id, app_id) {
            return icCommonReq.request_sync(config.restful.acm.user.findUserWithPriv + id + "/" + app_id, "GET");
        };

        this.userUnassignedRole = function (id, app_id) {
            return icCommonReq.request_sync(config.restful.acm.user.userUnassignedRole + id + "/" + app_id, "GET");
        };

       
        this.userUnassignedPermission = function (id, app_id) {
            return icCommonReq.request_sync(config.restful.acm.user.userUnassignedPermission + id + "/" + app_id, "GET");
        };

        this.saveUserPriv = function (params) {
            return icCommonReq.request_sync(config.restful.acm.user.saveUserPriv, "POST", null, params);
        };

        this.changePassword = function (params) {
            return icCommonReq.request_sync(config.restful.acm.user.changePassword, "POST", null, params);
        };
    }
]);