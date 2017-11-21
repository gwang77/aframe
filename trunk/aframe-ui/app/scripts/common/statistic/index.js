'use strict';

var application_module = angular.module(APP_NAME + '.common.statistic', [
    APP_NAME + '.common.statistic.controllers',
    APP_NAME + '.common.statistic.services'
]);

/**
 * customer module configuration.
 * a. ui-router
 */
application_module.config(['$stateProvider',
    function ($stateProvider) {

        // ui-router state
        $stateProvider
            .state('layout.statistic-show', {
                templateUrl: 'views/common/statistic/statistic.list.html',
                url: '/statistic/show',
                controller: 'statistic.ctrl',
                ncyBreadcrumb: {
                    label: APP_NAME + '.label_statistic_statistic'
                }
            })

        ;
    }
]);

