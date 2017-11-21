'use strict';

var common_itrust_module = angular.module(APP_NAME + '.common.itrust', [
    APP_NAME + '.itrust.controllers',
    APP_NAME + '.itrust.services'
]);

common_itrust_module.config(['$stateProvider',
    function ($stateProvider) {
        $stateProvider
            .state('itrust/login', {
                templateUrl: 'views/common/itrust/login.html',
                url: '/itrust/login',
                controller: 'login.ctrl'
            });
    }
]);
