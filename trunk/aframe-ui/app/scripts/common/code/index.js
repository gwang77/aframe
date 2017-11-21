'use strict';

var code_module = angular.module(APP_NAME + '.common.code', [
    APP_NAME + '.code.controllers',
    APP_NAME + '.code.filters',
    APP_NAME + '.code.services'
]);

code_module.config(['$stateProvider',
    function ($stateProvider) {

        $stateProvider
            .state('layout.code-codeType', {
                templateUrl: 'views/common/code/codeType.list.html',
                url: '/code/codeType',
                controller: 'codeType.list.ctrl',
                ncyBreadcrumb: {
                    label: APP_NAME + '.label_menu_code_codetype'
                }
            });

        $stateProvider
            .state('layout.code-codeType-update', {
                templateUrl: 'views/common/code/codeType.update.html',
                url: '/code/codeType/update/:codetype_id',
                controller: 'codeType.maintain.ctrl',
                ncyBreadcrumb: {
                    label: APP_NAME + '.label_code_codetype_update_title',
                    parent: 'layout.code-codeType'
                }
            });

        $stateProvider
            .state('layout.code-codeType-create', {
                templateUrl: 'views/common/code/codeType.create.html',
                url: '/code/codeType/create',
                controller: 'codeType.maintain.ctrl',
                ncyBreadcrumb: {
                    label: APP_NAME + '.label_code_codetype_create_title',
                    parent: 'layout.code-codeType'
                }
            });

        $stateProvider
            .state('layout.code-codeInt', {
                templateUrl: 'views/common/code/codeInt.list.html',
                url: '/code/codeInt',
                controller: 'codeInt.list.ctrl',
                ncyBreadcrumb: {
                    label: APP_NAME + '.label_menu_code_codeint',
                }
            });

        $stateProvider
            .state('layout.code-codeInt-create', {
                templateUrl: 'views/common/code/codeInt.create.html',
                url: '/code/codeInt/create/:codetype_id/:locales',
                controller: 'codeInt.maintain.ctrl',
                ncyBreadcrumb: {
                    label: APP_NAME + '.label_code_codeint_create_title',
                    parent: 'layout.code-codeInt'
                }
            });

        $stateProvider
            .state('layout.code-codeInt-update', {
                templateUrl: 'views/common/code/codeInt.update.html',
                url: '/code/codeType/update/:code_id/:locales/:codetype_id',
                controller: 'codeInt.maintain.ctrl',
                ncyBreadcrumb: {
                    label: APP_NAME + '.label_code_codeint_update_title',
                    parent: 'layout.code-codeInt'
                }
            });
    }
]);
