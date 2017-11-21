'use strict';

angular.module(APP_NAME + '.itrust.controllers').controller('logout.ctrl', ['$scope', '$rootScope', '$state',  '$cookies', 'loginService',
    function ($scope, $rootScope, $state, $cookies, loginService) {
        $scope.doLogout = function () {
            loginService.logout();
            $cookies.remove('auth-token');
            $cookies.remove('login-user');
            window.location = logout_redirect;
        };
    }
]);