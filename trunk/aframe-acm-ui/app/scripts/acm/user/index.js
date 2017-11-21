'use strict';

var user_module = angular.module(APP_NAME_ACM + '.acm.user', [
    APP_NAME_ACM + '.acm.user.controllers',
    APP_NAME_ACM + '.acm.user.filters',
    APP_NAME_ACM + '.acm.user.services'
]);

/**
 * customer module configuration.
 * a. ui-router
 */
user_module.config(['$stateProvider',
    function ($stateProvider) {

        // ui-router state
        $stateProvider
            .state('layout.user-list', {
                templateUrl: 'views/acm/user/user.list.html',
                url: '/user/list',
                controller: 'user.ctrl',
                ncyBreadcrumb: {
                    label: APP_NAME_ACM + '.label_maintain_user'
                }
            })
            .state('layout.user-create', {
                templateUrl: 'views/acm/user/user.maintain.html',
                url: '/user/create',
                controller: 'user.maintain.ctrl',
                ncyBreadcrumb: {
                    label: APP_NAME_ACM + '.label_create_user',
                    parent: 'layout.user-list'
                }
            })
            .state('layout.user-edit', {
                templateUrl: 'views/acm/user/user.profile.html',
                url: '/user/edit/:id',
                controller: 'user.maintain.ctrl',
                ncyBreadcrumb: {
                    label: APP_NAME_ACM + '.label_edit_User',
                    parent: 'layout.user-list'
                }
            })
            .state('layout.user-assign', {
                templateUrl: 'views/acm/user/user.assign.priv.html',
                url: '/user/assign/:id',
                controller: 'user.assign.priv.ctrl',
                ncyBreadcrumb: {
                    label: APP_NAME_ACM + '.label_user_assign_priv',
                    parent: 'layout.user-list'
                }
            })

            .state('user-changePassword',{
                templateUrl:'views/acm/user/user.change.password.html',
                url:'/user/changePassword',
                controller:'user.change.password.ctrl'
            })

        ;
    }
]);