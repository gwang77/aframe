'use strict';

angular.module(APP_NAME_ACM + '.acm.user.controllers').controller('user.assign.priv.ctrl', ['$scope', '$state', '$stateParams', '$timeout', '$validator', 'userService', 'applicationService', 'icCommonToastr', 'icCommonBootbox',
    function ($scope, $state, $stateParams, $timeout, $validator, userService, applicationService, icCommonToastr, icCommonBootbox) {
        $scope.appList = [];
        $scope.curr_app = {"single": {"app_id": "acm", "name": "Application Access Control"}};

        $scope.data = {
            "user": {},
            "assignedRole": [],
            "unassignedRole": []
        };

        //get appList
        applicationService.list({}).then(function (data) {
            $scope.appList = data;
        });

        var user_id = $stateParams.id;

        function initPageData() {
            $scope.curr_app_id = $scope.curr_app.single.app_id;
            if (!$scope.curr_app_id) {
                return;
            }
            //get user info
            if ($stateParams.id) {
                userService.findUserWithPriv(user_id, $scope.curr_app_id).then(function (data) {
                    $scope.data.user = data;
                    $scope.data.assignedRole = dataToPick($scope.data.user.roleTOList);
                    console.log($scope.data.user.roleTOList);
                    $scope.originalData = angular.copy($scope.data);
                });
            }
            //prepare assigned and unassigned
            userService.userUnassignedRole(user_id, $scope.curr_app_id).then(function (data) {
                $scope.data.unassignedRole = dataToPick(data);
                console.log($scope.data.unassignedRole);
            });
        }

        function dataToPick(dataList) {
            var pick = [];
            angular.forEach(dataList, function (data) {
                var item = {};
                item.label = data.role;
                console.log(data.role);
                item.value = data.id;
                pick.push(item);
            });
            return pick;
        }

        function pickToData(pick) {
            var dataList = [];
            angular.forEach(pick, function (data) {
                var item = {};
                item.role = data.label;
                item.id = data.value;
                dataList.push(item);
            });
            return dataList;
        }

        initPageData();

        $scope.actions = {
            change_app: function () {
                initPageData();
            },
            submit: function () {
                $scope.curr_app_id = $scope.curr_app.single.app_id;
                if (!$scope.curr_app_id) {
                    return;
                }
                $validator.validate($scope, 'user').success(function () {
                    $scope.data.user.app_id = $scope.curr_app_id;
                    $scope.data.user.roleTOList = pickToData($scope.data.assignedRole);
                    console.log($scope.data.assignedRole);
                    console.log($scope.data.user);
                    var promise = userService.saveUserPriv($scope.data.user);
                    promise.then(
                        function (data) {
                            saveSuccess();
                        }, function (data) {
                            saveFailed();
                        }
                    );
                });
            },
            reset: function () {
                $scope.data = $scope.originalData;
            },
            cancel: function () {
                goSource();
            }
        };
        function saveSuccess() {
            icCommonToastr.success_saved();
            goSource();
        }

        function saveFailed() {
            icCommonToastr.error_saved();
        }

        function goSource() {
            $state.go('layout.user-list');
        }
    }
]);