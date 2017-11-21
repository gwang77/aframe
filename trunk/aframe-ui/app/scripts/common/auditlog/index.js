'use strict';

var application_module = angular.module(APP_NAME + '.common.auditlog', [
    APP_NAME + '.common.auditlog.controllers',
    APP_NAME + '.common.auditlog.filters',
    APP_NAME + '.common.auditlog.services'
]);

/**
 * customer module configuration.
 * a. ui-router
 */
application_module.config(['$stateProvider',
    function ($stateProvider) {

        // ui-router state
        $stateProvider
            .state('layout.audit-list', {
                templateUrl: 'views/common/auditlog/audit.log.list.html',
                url: '/audit/list',
                controller: 'audit.ctrl',
                ncyBreadcrumb: {
                    label: APP_NAME + '.label_menu_audit_log'
                }
            })
            .state('layout.audit-detail', {
                templateUrl: 'views/common/auditlog/audit.log.detail.html',
                url: '/audit/detail/:id',
                controller: 'audit.detail.ctrl.js',
                ncyBreadcrumb: {
                    label: APP_NAME + '.label_audit_detail',
                    parent: 'layout.audit-list'
                }
            })
        ;
    }
]);

