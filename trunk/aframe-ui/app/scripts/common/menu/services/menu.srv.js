'use strict';

angular.module(APP_NAME + ".common.menu.services").service("menuService", ["icCommonReq", "config",
    function (icCommonReq, config) {
        this.menuList = function (caption) {
            return icCommonReq.request_sync(config.restful.menu.menuList + caption, "GET");
        };

        this.find = function (id) {
            return icCommonReq.request_sync(config.restful.menu.find + id, "GET");
        };

        this.create = function (params) {
            return icCommonReq.request_sync(config.restful.menu.create, "POST", null, params);
        };

        this.update = function (params) {
            return icCommonReq.request_sync(config.restful.menu.update, "POST", null, params);
        };

        this.delete = function (id) {
            return icCommonReq.request_sync(config.restful.menu.delete + id, "POST");
        };

        this.getRootCaptions = function (id) {
            return icCommonReq.request_sync(config.restful.menu.getRootCaptions, "GET");
        };

    }
]);