'use strict';

angular.module(APP_NAME + '.common.menu.controllers').controller('menu.ctrl', ['$scope', 'menuService', 'icCommonToastr', 'icCommonBootbox',
    function ($scope, menuService, icCommonToastr, icCommonBootbox) {
        $scope.menuList = {};
        $scope.menuSearchTO = {
            "caption": "root_en"
        };
        $scope.rootCaptions = [];

        $scope.onPageChanged = function () {
            loadData();
        };
        $scope.onSortChanged = function () {
            loadData();
        };
        $scope.onSizeChanged = function () {
            loadData();
        };
        $scope.getNumber = function (num) {
            return new Array(num);
        };
        $scope.actions = {
            search: function () {
                loadData();
            },
            delete: function (id) {
                icCommonBootbox.confirm_delete(function (result) {
                    if (result) {
                        menuService.delete(id).then(
                            function (data) {
                                icCommonToastr.success_deleted();
                                loadData();
                            },
                            function (data) {
                                icCommonToastr.error_deleted();
                            }
                        );
                    }
                });
            },
            change_caption: function () {
                loadData();
            }
        };

        var loadData = function () {
            menuService.menuList($scope.menuSearchTO.caption).then(function (data) {
                $scope.menuList = data;
            });
            menuService.getRootCaptions($scope.menuSearchTO.getRootCaptions).then(function (data) {
                $scope.rootCaptions = data;
            });
        };
        loadData();
    }
]);