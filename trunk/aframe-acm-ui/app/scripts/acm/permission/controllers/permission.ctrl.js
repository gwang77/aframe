'use strict';

angular.module(APP_NAME_ACM + '.acm.permission.controllers').controller('permission.ctrl', ['$scope','$rootScope','$stateParams', 'paginator', 'sorter', 'ctrlStateMgr', 'permissionService','applicationService', 'icCommonToastr', 'icCommonBootbox',
    function ($scope,$rootScope, $stateParams,paginator, sorter, ctrlStateMgr, permissionService,applicationService, icCommonToastr, icCommonBootbox) {
        $scope.permissionList = [];

        $scope.permissionTO={
            "name":"",
            "name_like":"",
            "app_id":$stateParams.appId
        };
        $rootScope.rootId = $stateParams.appId;
        ctrlStateMgr.remove('searchCriteria');
        ctrlStateMgr.remove('pagination');
        ctrlStateMgr.remove('sorter');
        $scope.searchCriteria = ctrlStateMgr.get('searchCriteria', $scope.permissionTO);
        $scope.pagination = ctrlStateMgr.get('pagination', paginator.create());
        $scope.sorter = ctrlStateMgr.get('sorter', sorter.create());

        $scope.onPageChanged = function () {
            loadData();
        };
        $scope.onSortChanged = function () {
            loadData();
        };
        $scope.onSizeChanged = function () {
            loadData();
        };

        $scope.actions = {
            search: function () {
                loadData();
            },
            delete: function (id) {
                icCommonBootbox.confirm_delete(function (result) {
                    if (result) {
                        permissionService.delete(id).then(
                            function (data) {
                                //toastr.success('Customer deleted successfully!', 'Successfully');
                                icCommonToastr.success_deleted();
                                loadData();
                            },
                            function (data) {
                                //toastr.error('Customer deleted failed!', 'Failed');
                                icCommonToastr.error_deleted();
                            }
                        );
                    }
                });
            }
        };

        var loadData = function () {
            var params = _.extend({}, $scope.searchCriteria);

            _.extend(params, $scope.pagination.getPagerParams());
            _.extend(params, $scope.sorter.getSorterParams());

            ctrlStateMgr.set('pagination', $scope.pagination);
            ctrlStateMgr.set('searchCriteria', $scope.searchCriteria);
            ctrlStateMgr.set('sorter', $scope.sorter);

            permissionService.searchPage(params).then(function (data) {
                $scope.permissionList = data.list;
                $scope.pagination.count = data.totalRecords;
                console.log(data.list);
            });

            applicationService.findByAppId($rootScope.rootId).then(function (appTO){
                $scope.app = appTO;
            });
        };

        loadData();

    }
]);