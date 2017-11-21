'use strict';

angular.module(APP_NAME + '.common.menu.controllers').controller('menu.maintain.ctrl', ['$scope', '$state', '$stateParams', '$timeout', '$validator', 'menuService', 'codeService', 'icCommonToastr', 'icCommonBootbox',
    function ($scope, $state, $stateParams, $timeout, $validator, menuService, codeService, icCommonToastr, icCommonBootbox) {
        $scope.menu = {available: "Y"};
        $scope.yes_no_code = [];

        $scope.originalMenu = angular.copy($scope.menu);
        function initData() {
            if ($stateParams.id) {
                menuService.find($stateParams.id).then(function (data) {
                    $scope.menu = data;
                    $scope.originalMenu = angular.copy($scope.menu);
                });
            } else {
                if ($stateParams.parent_id) {
                    $scope.menu.parent_id = $stateParams.parent_id;
                }
                $scope.menu.available = "Y";
                $scope.menu.seq_no = "1";
            }
            codeService.gets('comm_yes_no').then(function (data) {
                $scope.yes_no_code = data[0];
            });
        }

        initData();

        $scope.actions = {
            submit: function () {
                $validator.validate($scope, 'menu')
                    .success(function () {
                        var promise = '';
                        if ($scope.menu.id) {
                            promise = menuService.update($scope.menu);
                        } else {
                            promise = menuService.create($scope.menu);
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
                $scope.menu = $scope.originalMenu;
            },
            cancel: function () {
                goSource();
            }
        };
        function saveSuccess() {
            if ($scope.menu.id) {
                icCommonToastr.success_updated();
            } else {
                icCommonToastr.success_saved();
            }
            goSource();
        }

        function saveFailed(data) {
            var msg = data && data.message ? APP_NAME + '.' + data.message : "";
            if ($scope.menu.id) {
                icCommonToastr.error_updated(msg);
            } else {
                icCommonToastr.error_saved(msg);
            }
        }

        function goSource() {
            $state.go('layout.menu-list');
        }
    }
]);