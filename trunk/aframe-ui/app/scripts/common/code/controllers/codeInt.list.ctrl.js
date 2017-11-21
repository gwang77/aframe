'use strict';

angular.module(APP_NAME + '.code.controllers').controller('codeInt.list.ctrl', ['$scope', '$state', '$stateParams', 'ctrlStateMgr', 'codeIntService', 'codeTypeService', 'icCommonToastr', 'icCommonBootbox',
    function ($scope, $state, $stateParams, ctrlStateMgr, codeIntService, codeTypeService, icCommonToastr, icCommonBootbox) {
        $scope.codeIntList = [];
        $scope.codeIntTO = {
            "code_id": "",
            "codetype_id": "acm_usertype",
            "locales": "en"
        };
        $scope.codeTypeList = [];
        $scope.codeTypeTO = {};

        $scope.codeIntLocaleList = ["en", "zh"];

        $scope.en_or_zh = [
            {
                "locales": "en",
                "describe": "English"
            },
            {
                "locales": "zh",
                "describe": "Chinese"
            },
        ];

        if ($stateParams.codetype_id) {
            $scope.codeIntTO.codetype_id = $stateParams.codetype_id;
            $scope.codeIntTO.locales = $stateParams.locales;
        }

        $scope.actions = {
            delete: function (code_id, locales, codetype_id) {
                icCommonBootbox.confirm_delete(function (result) {
                    if (result) {//if user clicked ok
                        codeIntService.delete(code_id, locales, codetype_id).then(
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
            },

            changeCondition: function () {
                loadData();
            }
        };

        var loadData = function () {
            codeIntService.list($scope.codeIntTO).then(function (data) {
                $scope.codeIntList = data;
            });

            codeTypeService.list($scope.codeTypeTO).then(function (data) {
                $scope.codeTypeList = data;
            });
        };

        loadData();
    }
]);