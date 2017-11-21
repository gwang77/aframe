'use strict';

var common_menu_module = angular.module(APP_NAME + '.common.menu', [
    APP_NAME + '.common.menu.services',
    APP_NAME + '.common.menu.controllers'
]);

common_menu_module.config(['$stateProvider',
    function ($stateProvider) {
        $stateProvider
            .state('layout.menu-list', {
                templateUrl: 'views/common/menu/menu.list.html',
                url: '/menu/list',
                controller: 'menu.ctrl',
                ncyBreadcrumb: {
                    label: APP_NAME + '.label_maintain_menu'
                }
            })
            .state('layout.menu-create', {
                templateUrl: 'views/common/menu/menu.maintain.html',
                url: '/menu/create/:parent_id',
                controller: 'menu.maintain.ctrl',
                ncyBreadcrumb: {
                    label: APP_NAME + '.label_create_menu',
                    parent: 'layout.menu-list'
                }
            })
            .state('layout.menu-edit', {
                templateUrl: 'views/common/menu/menu.maintain.html',
                url: '/menu/edit/:id',
                controller: 'menu.maintain.ctrl',
                ncyBreadcrumb: {
                    label: APP_NAME + '.label_maintain_menu',
                    parent: 'layout.menu-list'
                }
            });

    }
]);