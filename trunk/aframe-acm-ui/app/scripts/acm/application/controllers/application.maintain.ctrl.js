'use strict';

angular.module(APP_NAME_ACM + '.acm.application.controllers').controller('application.maintain.ctrl', ['$scope', '$state', '$stateParams', '$timeout', '$validator', 'applicationService','codeService', 'icCommonToastr', 'icCommonBootbox',
    function ($scope, $state, $stateParams, $timeout, $validator, applicationService,codeService, icCommonToastr, icCommonBootbox) {
        $scope.application = {locked: "N"};
        $scope.yes_no_code = [];

        $scope.originalApplication = angular.copy($scope.application);
        function initData() {
            if ($stateParams.id) {
                applicationService.find($stateParams.id).then(function (data) {
                    $scope.application = data;
                    $scope.originalApplication = angular.copy($scope.application);
                });
            }
            codeService.gets('comm_yes_no').then(function (data){
                $scope.yes_no_code = data[0];
            });
        }

        initData();

        $scope.actions = {
            submit: function () {
                $validator.validate($scope, 'application')
                    .success(function () {
                        var promise = '';
                        if ($scope.application.id) {
                            promise = applicationService.update($scope.application);
                        } else {
                            promise = applicationService.create($scope.application);
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
                $scope.application = $scope.originalApplication;
            },
            cancel: function () {
                goSource();
            }
        };
        function saveSuccess() {
            if ($scope.application.id) {
                icCommonToastr.success_updated();
            } else {
                icCommonToastr.success_saved();
            }
            goSource();
        }

        function saveFailed(data) {
            var msg = data && data.message ? APP_NAME_ACM + '.' + data.message : "";
            if ($scope.application.id) {
                icCommonToastr.error_updated(msg);
            } else {
                icCommonToastr.error_saved(msg);
            }
            //$scope.actions.reset();
        }

        function goSource() {
            $state.go('layout.application-list');
        }
    }
]);