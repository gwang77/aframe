'use strict';

angular.module(APP_NAME + '.common.statistic.services').service('statisticService', ['config', 'icCommonReq',
    function (config, icCommonReq) {
        this.searchStatistic = function () {
            return icCommonReq.request_sync(config.restful.statistic.searchStatistic, "POST", null, null);
        };
        this.download = function () {
            window.location.href = config.restful.statistic.download;
        };

    }
]);