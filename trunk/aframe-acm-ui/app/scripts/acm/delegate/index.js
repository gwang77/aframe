'use strict';

var role_module = angular.module(APP_NAME_ACM + '.acm.delegate', [
    APP_NAME_ACM + '.acm.delegate.controllers',
    APP_NAME_ACM + '.acm.delegate.filters',
    APP_NAME_ACM + '.acm.delegate.services'
]);

/**
 * customer module configuration.
 * a. ui-router
 */
role_module.config(['$stateProvider',
    function ($stateProvider) {

        // ui-router state
        $stateProvider
            .state('layout.delegate-list', {
                templateUrl: 'views/acm/delegate/delegate.list.html',
                url: '/delegate/list',
                controller: 'delegate.ctrl',
                ncyBreadcrumb: {
                    label: APP_NAME_ACM + '.label_maintain_delegate'
                }
            })
            .state('layout.delegate-create', {
                templateUrl: 'views/acm/delegate/delegate.maintain.html',
                url: '/delegate/create',
                controller: 'delegate.maintain.ctrl',
                ncyBreadcrumb: {
                    label: APP_NAME_ACM + '.label_create_delegate',
                    parent: 'layout.delegate-list'
                }
            })
            .state('layout.delegate-edit', {
                templateUrl: 'views/acm/delegate/delegate.maintain.html',
                url: '/delegate/edit/:id',
                controller: 'delegate.maintain.ctrl',
                ncyBreadcrumb: {
                    label: APP_NAME_ACM + '.label_edit_delegate',
                    parent: 'layout.delegate-list'
                }
            })
        ;
    }
]);