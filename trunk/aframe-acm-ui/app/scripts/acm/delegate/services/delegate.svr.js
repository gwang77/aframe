'use strict';

angular.module(APP_NAME_ACM + '.acm.delegate.services').service('delegateService', ['config', 'icCommonReq',
    function (config, icCommonReq) {
        this.list = function (params) {
            return icCommonReq.request_sync(config.restful.acm.delegate.list, "POST", null, params);
        };
        this.find = function (id) {
            return icCommonReq.request_sync(config.restful.acm.delegate.find + id, "GET");
        };
        this.findDelegateUserFrom = function () {
            return icCommonReq.request_sync(config.restful.acm.delegate.findDelegateUserFrom, "GET");
        };

        this.create = function (params) {
            return icCommonReq.request_sync(config.restful.acm.delegate.create, "POST", null, params);
        };

        this.update = function (params) {
            return icCommonReq.request_sync(config.restful.acm.delegate.update, "POST", null, params);
        };

        this.delete = function (id) {
            return icCommonReq.request_sync(config.restful.acm.delegate.delete + id, "POST");
        };

    }
]);
