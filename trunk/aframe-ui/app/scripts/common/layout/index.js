'use strict';

var common_layout_module = angular.module(APP_NAME + '.common.layout', [
    APP_NAME + '.common.layout.controllers',
    APP_NAME + '.common.layout.services'
]);

common_layout_module.config(['$stateProvider',
    function ($stateProvider) {
        $stateProvider
            .state('layout', {
                abstract: true,
                url: '/layout',
                templateUrl: 'views/common/layout/main.html',
                controller: 'common.layout.main.ctrl'
            })

            .state('layout.home', {
                templateUrl: 'views/common/layout/home.html',
                url: '/home',
                ncyBreadcrumb: {
                    label: APP_NAME + '.label_menu_home'
                }
            });
    }
]);
