'use strict';

angular.module(APP_NAME + '.code.controllers').controller('codeType.list.ctrl', ['$scope', 'paginator', 'sorter', 'ctrlStateMgr', 'codeTypeService', 'icCommonToastr', 'icCommonBootbox',
    function ($scope, paginator, sorter, ctrlStateMgr, codeTypeService, icCommonToastr, icCommonBootbox) {
        $scope.codeTypeList = [];
        $scope.codeTypeTO = {
        };

        $scope.actions = {
            delete: function (codetype_id) {
                icCommonBootbox.confirm_delete(function (result) {
                    if (result) {//if user clicked ok
                        codeTypeService.delete(codetype_id).then(
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
            codeTypeService.list($scope.codeTypeTO).then(function (data) {
                $scope.codeTypeList = data;
            });
        };

        loadData();
    }
]);