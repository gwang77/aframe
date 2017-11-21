'use strict';

angular.module(APP_NAME_ACM + '.acm.user.controllers').controller('user.change.password.ctrl', ['$scope', '$state', '$validator', 'userService', 'loginService', 'icCommonToastr',
    function ($scope, $state, $validator, userService, loginService, icCommonToastr) {
        $scope.user = {
            username: null,
            password: null,
            new_password: null,
            confirm_password: null
        };

        $scope.actions = {
            save: function () {
                $validator.validate($scope, 'user').success(function () {
                    if ($scope.user.new_password === $scope.user.confirm_password) {
                        userService.changePassword($scope.user).then(function (data) {
                            var flag = data;
                            if (flag) {
                                icCommonToastr.success_saved();
                                $state.go("itrust/login");
                            } else {
                                icCommonToastr.error_saved('acmUIApp.errors_check_username_password');
                            }
                        }, function (data) {
                            saveFailed(data);
                        });
                    } else {
                        //new password is different with confirm password
                        icCommonToastr.error_saved('acmUIApp.errors_check_username_password');
                    }
                });
            }
        };

        function saveFailed(data) {
            var msg = data && data.message ? APP_NAME_ACM + '.' + data.message : "";
            icCommonToastr.error_saved(msg);
        }
    }
]);