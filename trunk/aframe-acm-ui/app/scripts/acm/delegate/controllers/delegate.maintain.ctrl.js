'use strict';

angular.module(APP_NAME_ACM + '.acm.delegate.controllers').controller('delegate.maintain.ctrl', ['$scope', '$state', '$stateParams', 'ctrlStateMgr','$timeout', '$validator', 'delegateService', 'userService', 'icCommonToastr', 'icCommonBootbox',
    function ($scope, $state, $stateParams,ctrlStateMgr, $timeout, $validator, delegateService, userService, icCommonToastr, icCommonBootbox) {
        $scope.delegate={
            "id":"",
            "user_from":""
        };
        $scope.data = {
            "user": {},
            "assignedRole": [],
            "unassignedRole": []
        };
        $scope.user=[];
        $scope.searchCriteria = ctrlStateMgr.get('searchCriteria', $scope.user);
        function initPageData() {
            if ($stateParams.id) {
                delegateService.find($stateParams.id).then(function (data) {
                    $scope.delegate=data;
                    console.log(data);
                    // $scope.data.assignedRole = dataToPick(data.userList);
                });
                var params = _.extend({}, $scope.searchCriteria);
                userService.list(params).then(function (data) {
                    $scope.user=data;
                    console.log(data);
                });
            }else{
                delegateService.findDelegateUserFrom().then(function (data) {
                    $scope.delegate=data;
                });
                userService.findUserByUser_from().then(function (data) {
                    $scope.data.user = data;
                    $scope.data.assignedRole = dataToPick($scope.data.user);
                    $scope.originalData = angular.copy($scope.data);
                });
                userService.findUserNotUser_from().then(function (data) {
                    $scope.data.unassignedRole = dataToPick(data);
                });
            }
        }

        function dataToPick(dataList) {
            var pick = [];
            angular.forEach(dataList, function (data) {
                var item = {};
                item.label = data.username;
                console.log(data.username);
                item.value = data.id;
                pick.push(item);
            });
            return pick;
        }
        function pickToData(pick) {
            var dataList = [];
            angular.forEach(pick, function (data) {
                var item = {};
                item.username = data.label;
                item.id = data.value;
                dataList.push(item);
            });
            return dataList;
        }
        initPageData();
        $scope.actions = {
            submit: function () {
                var promise = '';
                if ($scope.delegate.id) {
                    console.log($scope.delegate);
                    promise=delegateService.update($scope.delegate);
                }else{
                    $scope.delegate.userList = pickToData($scope.data.assignedRole);
                    console.log($scope.delegate);
                    promise=delegateService.create($scope.delegate);
                }
                promise.then(
                    function (data) {
                        saveSuccess();
                    }, function (data) {
                        saveFailed();
                    }
                );
            },
            cancel: function () {
                goSource();
            }
        };

        function saveSuccess() {
            if ($scope.delegate.id) {
                icCommonToastr.success_updated();
            } else {
                icCommonToastr.success_saved();
            }
            goSource();
        }

        function saveFailed() {
            if ($scope.delegate.id) {
                icCommonToastr.error_updated();
            } else {
                icCommonToastr.error_saved();
            }
        }

        function goSource() {
            $state.go('layout.delegate-list');
        }
    }
]);