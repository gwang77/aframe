'use strict';

angular.module(APP_NAME + '.code.controllers').controller('codeInt.maintain.ctrl', ['$scope', '$state', '$stateParams', '$timeout', '$validator', 'codeIntService','codeService', 'icCommonToastr', 'icCommonBootbox',
    function ($scope, $state, $stateParams, $timeout, $validator, codeIntService,codeService, icCommonToastr, icCommonBootbox) {
        $scope.codeInt = {
            "codetype_id": "",
            "code_id": "",
            "code_desc": "",
            "status": "A",
            "locales": "en",
            "code_seq": ""
        };
        $scope.A_or_N = [
            {
                "status": "A",
                "describe": "Active"
            },
            {
                "status": "N",
                "describe": "In-Active"
            },
        ];
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
        function initData() {
            $scope.codeInt.codetype_id = $stateParams.codetype_id;
            $scope.codeInt.locales = $stateParams.locales;
            if ($stateParams.code_id) {
                codeIntService.find($stateParams.code_id, $stateParams.locales, $stateParams.codetype_id).then(function (data) {
                    $scope.codeInt = data;
                });
            }
        }
        initData();
        $scope.actions = {
            submit: function () {
                var promise = '';
                if ($stateParams.code_id) {
                    promise = codeIntService.update($scope.codeInt);
                }else{
                    promise = codeIntService.create($scope.codeInt);
                }
                promise.then(
                    function (data) {
                        if($stateParams.code_id){
                            icCommonToastr.success_updated();
                        }else{
                            icCommonToastr.success_saved();
                        }
                        goSource();
                    }, function (data) {
                        var msg = data && data.message ? APP_NAME + '.' + data.message : "";
                        if($stateParams.code_id){
                            icCommonToastr.error_updated(msg);
                        }else{
                            icCommonToastr.error_saved(msg);
                        }
                    }
                );
            },
            cancel: function () {
                goSource();
            }
        };

        function goSource() {
            $state.go('layout.code-codeInt',{codetype_id: $scope.codeInt.codetype_id, locales: $scope.codeInt.locales});
        }
    }
]);