'use strict';

angular.module(APP_NAME_ACM + '.acm.application.controllers').controller('application.ctrl', ['$scope', 'paginator', 'sorter', 'ctrlStateMgr', 'applicationService', 'icCommonToastr', 'icCommonBootbox',
    function ($scope, paginator, sorter, ctrlStateMgr, applicationService, icCommonToastr, icCommonBootbox) {
        $scope.applicationList = [];
        $scope.applicationTO = {
            "name_like": ""
        };

        $scope.searchCriteria = ctrlStateMgr.get('searchCriteria', $scope.applicationTO);
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
                        applicationService.delete(id).then(
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

            applicationService.searchPage(params).then(function (data) {
                $scope.applicationList = data.list;
                $scope.pagination.count = data.totalRecords;
            });
        };

        loadData();

    }
]);