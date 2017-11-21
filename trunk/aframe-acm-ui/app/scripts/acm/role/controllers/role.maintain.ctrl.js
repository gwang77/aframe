'use strict';

angular.module(APP_NAME_ACM + '.acm.role.controllers').controller('role.maintain.ctrl', ['$scope', '$rootScope', '$state', '$stateParams', '$timeout', '$validator', 'roleService', 'applicationService', 'icCommonToastr', 'icCommonBootbox',
    function ($scope, $rootScope, $state, $stateParams, $timeout, $validator, roleService, applicationService, icCommonToastr, icCommonBootbox) {
        $scope.role = {};
        $scope.permission = {};
        $scope.permission_id_s = [];

        $scope.app = {};
        $scope.picklistResult = {
            list: []
        };
        var pickAssigned = [];
        var pickUnassigned = [];

        $scope.originalRole = angular.copy($scope.role);


        applicationService.findByAppId($rootScope.rootId).then(function (appTO) {
            $scope.app = appTO;
            if ($stateParams.id) {
                roleService.find($stateParams.id).then(function (data) {
                    $scope.role = data;
                    $scope.originalRole = angular.copy($scope.role);
                    permissionToPick(pickAssigned, data.permissionList);
                    $scope.picklistResult.list = pickAssigned;
                });
                roleService.roleUnassignedPermission($stateParams.id,$scope.app.app_id).then(function (data) {
                    permissionToPick(pickUnassigned, data);
                    $scope.picklistData = pickUnassigned;
                });
            } else {
                roleService.roleUnassignedPermission(0,$scope.app.app_id).then(function (data) {
                    permissionToPick(pickUnassigned, data);
                    $scope.picklistData = pickUnassigned;
                });
            }

        });

        $scope.actions = {
            submit: function () {
                pickToPermission($scope.picklistResult.list);
                $validator.validate($scope, 'role')
                    .success(function () {
                        var promise = '';
                        //$scope.value = [];
                        if ($scope.role.id) {
                            promise = roleService.update($scope.role);
                        } else {
                            pickToPermission($scope.picklistResult.list);
                            $scope.role.app_id = $rootScope.rootId;
                            promise = roleService.create($scope.role);
                        }
                        promise.then(
                            function (data) {
                                saveSuccess();
                            }, function (data) {
                                saveFailed(data);
                            }
                        );
                    })
                ;
            },
            reset: function () {
                $scope.role = $scope.originalRole;
            },
            cancel: function () {
                goSource();
            }
        };
        function saveSuccess() {
            if ($scope.role.id) {
                icCommonToastr.success_updated();
            } else {
                icCommonToastr.success_saved();
            }
            goSource();
        }

        function saveFailed(data) {
            var msg = data && data.message ? APP_NAME_ACM + '.' + data.message : "";
            if ($scope.role.id) {
                icCommonToastr.error_updated(msg);
            } else {
                icCommonToastr.error_saved(msg);
            }
            //$scope.actions.reset();
        }

        function goSource() {
            $state.go('layout.role-list', {appId: $rootScope.rootId});
        }

        function permissionToPick(pick, permissionList) {
            var i = 0;
            angular.forEach(permissionList, function () {
                var item = {};
                item.label = permissionList[i].name;
                item.value = permissionList[i].id;
                pick.push(item);
                i++;
            });

        }

        function pickToPermission(pick) {
            var i = 0;
            $scope.role.permissionList = [];
            angular.forEach(pick, function () {
                var item = {};
                item.name = pick[i].label;
                item.id = pick[i].value;
                $scope.role.permissionList.push(item);
                i++;
            });

        }


        //$scope.picklistData = pick;

    }
]);