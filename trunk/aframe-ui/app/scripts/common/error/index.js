'use strict';

var common_error_module = angular.module(APP_NAME + '.common.error', []);

common_error_module.config(['$stateProvider',
    function ($stateProvider) {
        $stateProvider
            .state('error/403', {
                templateUrl: 'views/common/error/403.html',
                url: '/error/403'
            })

            .state('error/404', {
                templateUrl: 'views/common/error/404.html',
                url: '/error/404'
            })

            .state('error/500', {
                templateUrl: 'views/common/error/500.html',
                url: '/error/500'
            })

            .state('error/502', {
                templateUrl: 'views/common/error/502.html',
                url: '/error/502'
            })

            .state('error/503', {
                templateUrl: 'views/common/error/503.html',
                url: '/error/503'
            })

            .state('error/504', {
                templateUrl: 'views/common/error/504.html',
                url: '/error/504'
            })

            .state('error/unknown', {
                templateUrl: 'views/common/error/unknown.html',
                url: '/error/unknown'
            });
    }
]);
