'use strict';

angular.module(APP_NAME + '.common.auditlog.controllers').controller('audit.ctrl', ['$scope', 'paginator', 'sorter', 'ctrlStateMgr', 'auditLogService', 'icCommonToastr', 'icCommonBootbox', '$uibModal',
    function ($scope, paginator, sorter, ctrlStateMgr, auditLogService, icCommonToastr, icCommonBootbox, $uibModal) {
        $scope.auditLogList = [];
        $scope.auditLogSearchTO = {
            class_nameLike: "",
            actionLike: "",
            start_date: "",
            end_date: ""
        };
        $scope.actionList = ["UPDATE", "DELETE", "CREATE"];

        $scope.searchCriteria = ctrlStateMgr.get('searchCriteria', $scope.auditLogSearchTO);
        $scope.pagination = ctrlStateMgr.get('pagination', paginator.create());
        $scope.sorter = ctrlStateMgr.get('sorter', sorter.create());
        $scope.sorter.state = "id,desc";

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
            reset: function () {
                $scope.auditLogSearchTO.class_nameLike = "";
                $scope.auditLogSearchTO.actionLike = "";
                $scope.auditLogSearchTO.start_date = "";
                $scope.auditLogSearchTO.end_date = "";
            },
            openDialog: function (auditLog) {
                var dialog = $uibModal.open({
                    templateUrl: 'views/common/auditlog/audit.log.detail.html',
                    size: 'lg',
                    backdrop: 'static',
                    keyboard: true,
                    controller: 'audit.detail.ctrl',
                    resolve: {
                        $stateParams: function () {
                            return {
                                auditLog: auditLog
                            };
                        }
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

            auditLogService.searchAuditLog(params).then(function (data) {
                $scope.auditList = data.list;
                $scope.pagination.count = data.totalRecords;
            });
        };

        loadData();
    }
]);