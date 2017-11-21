'use strict';

angular.module(APP_NAME + '.common.statistic.controllers').controller('statistic.ctrl', ['$scope', 'paginator', 'sorter', 'ctrlStateMgr', 'statisticService', 'icCommonToastr', 'icCommonBootbox', '$uibModal',
    function ($scope, paginator, sorter, ctrlStateMgr, statisticService, icCommonToastr, icCommonBootbox, $uibModal) {
        $scope.StatisticList = [];
        $scope.actions={
            download: function(){
                statisticService.download();
            }
        };
        var loadData = function () {
            statisticService.searchStatistic().then(function (data) {
                $scope.StatisticList = data;
            });
        };
        loadData();
    }
]);