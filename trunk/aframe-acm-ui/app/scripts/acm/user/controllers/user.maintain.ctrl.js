'use strict';

angular.module(APP_NAME_ACM + '.acm.user.controllers').controller('user.maintain.ctrl', ['$scope', '$state', '$stateParams', '$timeout', '$validator', 'userService', 'codeService', 'icCommonToastr', 'icCommonBootbox',
    function ($scope, $state, $stateParams, $timeout, $validator, userService, codeService, icCommonToastr, icCommonBootbox) {
        $scope.user = {"locked": "N"};
        $scope.yes_no_code = [];
        $scope.sex_code = [];

        $scope.originalUser = angular.copy($scope.user);
        function initPageData() {
            if ($stateParams.id) {
                userService.find($stateParams.id).then(function (data) {
                    $scope.user = data;
                    $scope.originalUser = angular.copy($scope.user);
                });
            }
            codeService.gets('comm_yes_no,comm_sex').then(function (data) {
                $scope.yes_no_code = data[0];
                $scope.sex_code = data[1];
            });
        }

        initPageData();

        $scope.actions = {
            submit: function () {
                $validator.validate($scope, 'user')
                    .success(function () {
                        var promise = '';
                        if ($scope.user.id) {
                            promise = userService.update($scope.user);
                        } else {
                            promise = userService.create($scope.user);
                        }

                        promise.then(
                            function (data) {
                                saveSuccess();
                            }, function (data) {
                                saveFailed(data);
                            }
                        );
                    });
            },
            reset: function () {
                $scope.user = $scope.originalUser;
            },
            cancel: function () {
                goSource();
            }
        };
        function saveSuccess() {
            if ($scope.user.id) {
                icCommonToastr.success_updated();
            } else {
                icCommonToastr.success_saved();
            }
            goSource();
        }

        function saveFailed(data) {
            var msg = data && data.message ? APP_NAME_ACM + '.' + data.message : "";
            if ($scope.user.id) {
                icCommonToastr.error_updated(msg);
            } else {
                icCommonToastr.error_saved(msg);
            }
            //$scope.actions.reset();
        }

        function goSource() {
            $state.go('layout.user-list');
        }
    }
]);