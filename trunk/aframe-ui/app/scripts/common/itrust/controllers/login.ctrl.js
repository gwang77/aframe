'use strict';

angular.module(APP_NAME + '.itrust.controllers').controller('login.ctrl', ['$scope', '$state', '$cookies', 'icCommonToastr', 'loginService', 'config',
    function ($scope, $state, $cookies, icCommonToastr, loginService, config) {
        $scope.config = config;
        $scope.user = {
            username: null,
            password: null,
            rememberMe: false
        };

        $scope.doLogin = function () {
            loginService.login($scope.user).then(
                function(data) {
                    if (data.status === "0") {
                        $cookies.put('auth-token', 1);
                        $cookies.put('login-user', $scope.user.username);

                        if ($state.previousState.state.name === '' || $state.previousState.state.name === 'user-changePassword') {
                            $state.go(config.defaultPage);
                        } else {
                            $state.previous();
                        }

                        icCommonToastr.success_locale('aframe.msg_login_successfully', 'aframe.title_login');
                    } else {
                        icCommonToastr.error_locale('Login Failed!', 'aframe.title_login');
                    }
                },
                function (data) {
                    icCommonToastr.error_locale('Login Failed!', 'aframe.title_login');
                }
            );
        };
    }
]);