'use strict';

var role_module = angular.module(APP_NAME_ACM + '.acm.role', [
    APP_NAME_ACM + '.acm.role.controllers',
    APP_NAME_ACM + '.acm.role.filters',
    APP_NAME_ACM + '.acm.role.services'
]);

/**
 * customer module configuration.
 * a. ui-router
 */
role_module.config(['$stateProvider',
    function ($stateProvider) {

        // ui-router state
        $stateProvider
            .state('layout.role-list', {
                templateUrl: 'views/acm/role/role.list.html',
                url: '/role/list/:appId',
                controller: 'role.ctrl',
                ncyBreadcrumb: {
                    label: APP_NAME_ACM + '.label_maintain_role'
                }
            })
            .state('layout.role-create', {
                templateUrl: 'views/acm/role/role.maintain.html',
                url: '/role/create/:appId',
                controller: 'role.maintain.ctrl',
                ncyBreadcrumb: {
                    label: APP_NAME_ACM + '.label_create_role',
                    parent: 'layout.role-list'
                }
            })
            .state('layout.role-edit', {
                templateUrl: 'views/acm/role/role.maintain.html',
                url: '/role/edit/:id',
                controller: 'role.maintain.ctrl',
                ncyBreadcrumb: {
                    label: APP_NAME_ACM + '.label_edit_role',
                    parent: 'layout.role-list'
                }
            })
        ;
    }
]);