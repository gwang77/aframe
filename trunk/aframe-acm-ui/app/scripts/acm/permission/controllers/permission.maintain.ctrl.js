'use strict';

angular.module(APP_NAME_ACM + '.acm.permission.controllers').controller('permission.maintain.ctrl', ['$scope','$rootScope', '$state', '$stateParams', '$timeout', '$validator', 'permissionService','applicationService', 'icCommonToastr', 'icCommonBootbox',
    function ($scope,$rootScope, $state, $stateParams, $timeout, $validator, permissionService,applicationService, icCommonToastr, icCommonBootbox) {
        $scope.permission = {};

        $scope.originalPermission = angular.copy($scope.permission);
        if ($stateParams.id) {
            permissionService.find($stateParams.id).then(function (data) {
                $scope.permission = data;
                $scope.originalPermission = angular.copy($scope.permission);
            });
        }

        $scope.actions = {
            submit: function () {
                $validator.validate($scope, 'permission')
                    .success(function () {
                        var promise = '';
                        if ($scope.permission.id) {
                            promise = permissionService.update($scope.permission);
                        } else {
                            $scope.permission.app_id = $rootScope.rootId;
                            promise = permissionService.create($scope.permission);
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
                $scope.permission = $scope.originalPermission;
            },
            cancel: function () {
                goSource();
            }
        };
        function saveSuccess() {
            if ($scope.permission.id) {
                icCommonToastr.success_updated();
            } else {
                icCommonToastr.success_saved();
            }
            goSource();
        }

        function saveFailed(data) {
            var msg = data && data.message ? APP_NAME_ACM+'.'+data.message : "";
            if ($scope.permission.id) {
                icCommonToastr.error_updated(msg);
            } else {
                icCommonToastr.error_saved(msg);
            }
            //$scope.actions.reset();
        }

        function goSource() {
            $state.go('layout.permission-list',{appId:$rootScope.rootId});
        }
        applicationService.findByAppId($rootScope.rootId).then(function (appTO) {
            $scope.app = appTO;
        });
    }
]);