'use strict';

angular.module(APP_NAME + '.common.auditlog.controllers').controller('audit.detail.ctrl', ['$scope', '$state', '$stateParams', '$timeout', 'auditLogService', '$validator', 'icCommonToastr', 'icCommonBootbox', '$uibModal', '$uibModalInstance',
    function ($scope, $state, $stateParams, $timeout, auditLogService, $validator, icCommonToastr, icCommonBootbox, $uibModal, $uibModalInstance) {
        $scope.auditLog = $stateParams.auditLog;
        $scope.details = [];

        function initData() {
            auditLogService.convertAuditLogTOtoMap($scope.auditLog).then(function (data) {
                $scope.details = data;
            });
        }

        initData();

        $scope.actions = {
            cancel: function () {
                $uibModalInstance.dismiss("cancel");
            }
        };
    }
]);
