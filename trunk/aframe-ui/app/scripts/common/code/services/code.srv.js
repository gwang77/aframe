'use strict';

angular.module(APP_NAME + ".code.services").service("codeService", ["icCommonReq", "config",
    function (icCommonReq, config) {
        this.get = function (codeTypeId) {
            return icCommonReq.request_sync(config.restful.code.get + codeTypeId, "GET", null, null, true);
        };
        this.gets = function (codeTypeIds) {
            return icCommonReq.request_sync(config.restful.code.gets + codeTypeIds, "GET", null, null, true);
        };
    }
]);