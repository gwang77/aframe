'use strict';

angular.module(APP_NAME + '.code.controllers').controller('codeType.maintain.ctrl', ['$scope', '$state', '$stateParams', '$timeout', '$validator', 'codeTypeService','codeService', 'icCommonToastr', 'icCommonBootbox',
    function ($scope, $state, $stateParams, $timeout, $validator, codeTypeService,codeService, icCommonToastr, icCommonBootbox) {
        $scope.codeType = {
        };
        function initData() {
            if ($stateParams.codetype_id) {
                codeTypeService.find($stateParams.codetype_id).then(function (data) {
                    $scope.codeType = data;
                });
            }
        }
        initData();
        $scope.actions = {
            submit: function () {
                var promise = '';
                if($stateParams.codetype_id){
                    promise = codeTypeService.update($scope.codeType);
                }else{
                    promise = codeTypeService.create($scope.codeType);
                }
                promise.then(
                    function (data) {
                        if($stateParams.codetype_id){
                            icCommonToastr.success_updated();
                        }else{
                            icCommonToastr.success_saved();
                        }
                        goSource();
                    }, function (data) {
                        var msg = data && data.message ? APP_NAME + '.' + data.message : "";
                        if($stateParams.codetype_id){
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
            $state.go('layout.code-codeType');
        }
    }
]);