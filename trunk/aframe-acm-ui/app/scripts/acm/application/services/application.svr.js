'use strict';

angular.module(APP_NAME_ACM + '.acm.application.services').service('applicationService', ['config', 'icCommonReq',
    function (config, icCommonReq) {
        this.list = function (params) {
            return icCommonReq.request_sync(config.restful.acm.application.list, "POST", null, params);
        };

        this.searchPage = function (params) {
            return icCommonReq.request_sync(config.restful.acm.application.searchPage, "POST", null, params);
        };

        this.find = function (id) {
            return icCommonReq.request_sync(config.restful.acm.application.find + id, "GET");
        };

        this.create = function (params) {
            return icCommonReq.request_sync(config.restful.acm.application.create, "POST", null, params);
        };

        this.update = function (params) {
            return icCommonReq.request_sync(config.restful.acm.application.update, "POST", null, params);
        };

        this.delete = function (id) {
            return icCommonReq.request_sync(config.restful.acm.application.delete + id, "POST");
        };

        this.findByAppId = function (appId) {
            return icCommonReq.request_sync(config.restful.acm.application.findByAppId + appId, "POST");
        };

    }
]);