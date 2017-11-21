'use strict';

angular.module(APP_NAME + '.common.layout.services').service('layoutService', ['$cookies', 'config', 'icCommonReq',
    function ($cookies, config, icCommonReq) {
        this.showMenu = function (caption) {
            return icCommonReq.request_sync(config.restful.layout.showMenu + caption, "GET");
        };
    }
]);