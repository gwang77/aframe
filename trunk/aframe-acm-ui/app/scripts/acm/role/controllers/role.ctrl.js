'use strict';

angular.module(APP_NAME_ACM + '.acm.role.controllers').controller('role.ctrl', ['$scope','$stateParams','$rootScope', 'paginator', 'sorter', 'ctrlStateMgr', 'roleService','applicationService', 'icCommonToastr', 'icCommonBootbox',
    function ($scope,$stateParams,$rootScope, paginator, sorter, ctrlStateMgr, roleService,applicationService, icCommonToastr, icCommonBootbox) {
        $scope.roleList = [];

        $scope.roleTO={
            "role":"",
            "name_like":"",
            "app_id" :$stateParams.appId
        };
        $rootScope.rootId = $stateParams.appId;
        ctrlStateMgr.remove('searchCriteria');
        ctrlStateMgr.remove('pagination');
        ctrlStateMgr.remove('sorter');
        $scope.searchCriteria = ctrlStateMgr.get('searchCriteria', $scope.roleTO);
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
                        roleService.delete(id).then(
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

            roleService.searchPage(params).then(function (data) {
                $scope.roleList = data.list;
                $scope.pagination.count = data.totalRecords;
            });

            applicationService.findByAppId($rootScope.rootId).then(function (appTO){
                $scope.app = appTO;
            });
        };

        loadData();

    }
]);