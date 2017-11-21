'use strict';

angular.module(APP_NAME + '.common.auditlog.services').service('auditLogService', ['config', 'icCommonReq',
    function (config, icCommonReq) {
        this.searchAuditLog = function (params) {
            return icCommonReq.request_sync(config.restful.auditLog.searchAuditLog, "POST", null, params);
        };

        this.convertAuditLogTOtoMap = function (params) {
            return icCommonReq.request_sync(config.restful.auditLog.convertAuditLogTOtoMap, "POST", null, params);
        };
    }
]);