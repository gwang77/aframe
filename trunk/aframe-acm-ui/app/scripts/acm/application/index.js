'use strict';

var application_module = angular.module(APP_NAME_ACM + '.acm.application', [
    APP_NAME_ACM + '.acm.application.controllers',
    APP_NAME_ACM + '.acm.application.filters',
    APP_NAME_ACM + '.acm.application.services'
]);

/**
 * customer module configuration.
 * a. ui-router
 */
application_module.config(['$stateProvider',
    function ($stateProvider) {

        // ui-router state
        $stateProvider
            .state('layout.application-list', {
                templateUrl: 'views/acm/application/application.list.html',
                url: '/application/list',
                controller: 'application.ctrl',
                ncyBreadcrumb: {
                    label: APP_NAME_ACM + '.label_maintain_application'
                }
            })
            .state('layout.application-create', {
                templateUrl: 'views/acm/application/application.maintain.html',
                url: '/application/create',
                controller: 'application.maintain.ctrl',
                ncyBreadcrumb: {
                    label: APP_NAME_ACM + '.label_create_application',
                    parent: 'layout.application-list'
                }
            })
            .state('layout.application-edit', {
                templateUrl: 'views/acm/application/application.maintain.html',
                url: '/application/edit/:id',
                controller: 'application.maintain.ctrl',
                ncyBreadcrumb: {
                    label: APP_NAME_ACM + '.label_edit_application',
                    parent: 'layout.application-list'
                }
            })
        ;
    }
]);