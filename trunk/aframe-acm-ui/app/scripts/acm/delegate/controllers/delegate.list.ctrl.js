'use strict';

angular.module(APP_NAME_ACM + '.acm.delegate.controllers').controller('delegate.ctrl', ['$scope','$stateParams','$rootScope', 'paginator', 'sorter', 'ctrlStateMgr', 'delegateService','loginService', 'icCommonToastr', 'icCommonBootbox',
    function ($scope,$stateParams,$rootScope, paginator, sorter, ctrlStateMgr, delegateService,loginService, icCommonToastr, icCommonBootbox) {
        $scope.delegateList = [];
        $scope.delegateTO={
            "user_to":""
        };
        $scope.searchCriteria = ctrlStateMgr.get('searchCriteria', $scope.delegateTO);
        $scope.actions = {
            search: function () {
                loadData();
            },
            delete: function (id) {
                icCommonBootbox.confirm_delete(function (result) {
                    if (result) {
                        delegateService.delete(id).then(
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
            delegateService.list(params).then(function (data) {
                $scope.delegateList = data;
            });
        };

        loadData();

    }
]);
