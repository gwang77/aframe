'use strict';

var APP_NAME_ACM = 'acmUIApp';
angular.module(APP_NAME_ACM + '.acm', [
    APP_NAME_ACM + '.acm.application',
    APP_NAME_ACM + '.acm.delegate',
    APP_NAME_ACM + '.acm.permission',
    APP_NAME_ACM + '.acm.role',
    APP_NAME_ACM + '.acm.user'
]);
