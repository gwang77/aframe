'use strict';

var permission_module = angular.module(APP_NAME_ACM + '.acm.permission', [
    APP_NAME_ACM + '.acm.permission.controllers',
    APP_NAME_ACM + '.acm.permission.filters',
    APP_NAME_ACM + '.acm.permission.services'
]);

/**
 * customer module configuration.
 * a. ui-router
 */
permission_module.config(['$stateProvider',
    function ($stateProvider) {

        // ui-router state
        //$stateProvider
        //    .state('layout.permission-list', {
        //        templateUrl: 'views/acm/permission/permission.list.html',
        //        url: '/permission/list',
        //        controller: 'permission.ctrl',
        //        ncyBreadcrumb: {
        //            label: APP_NAME_ACM + '.label_list_permission'
        //        }
        //    })
        //    .state('layout.permission-create', {
        //        templateUrl: 'views/acm/permission/permission.maintain.html',
        //        url: '/permission/create',
        //        controller: 'permission.maintain.ctrl',
        //        ncyBreadcrumb: {
        //            label: APP_NAME_ACM + '.label_create_permission',
        //            parent: 'layout.permission-list'
        //        }
        //    })
        //    .state('layout.permission-edit', {
        //        templateUrl: 'views/acm/permission/permission.maintain.html',
        //        url: '/permission/edit/:id',
        //        controller: 'permission.maintain.ctrl',
        //        ncyBreadcrumb: {
        //            label: APP_NAME_ACM + '.label_edit_permission',
        //            parent: 'layout.permission-list'
        //        }
        //    })
        //
        //;
        $stateProvider
            .state('layout.permission-list', {
                templateUrl: 'views/acm/permission/permission.list.html',
                url: '/permission/list/:appId',
                controller: 'permission.ctrl',
                ncyBreadcrumb: {
                    label: APP_NAME_ACM + '.label_maintain_permission'
                }
            })
            .state('layout.permission-create', {
                templateUrl: 'views/acm/permission/permission.maintain.html',
                url: '/permission/create/:appId',
                controller: 'permission.maintain.ctrl',
                ncyBreadcrumb: {
                    label: APP_NAME_ACM + '.label_create_permission',
                    parent: 'layout.permission-list'
                }
            })
            .state('layout.permission-edit', {
                templateUrl: 'views/acm/permission/permission.maintain.html',
                url: '/permission/edit/:id',
                controller: 'permission.maintain.ctrl',
                ncyBreadcrumb: {
                    label: APP_NAME_ACM + '.label_edit_permission',
                    parent: 'layout.permission-list'
                }
            })

        ;
    }
]);