'use strict';

angular.module(APP_NAME + '.common.layout.controllers')
    .controller('common.layout.main.ctrl', ['$scope', 'locale', '$cookies', 'loginService', 'layoutService', '$state', function ($scope, locale, $cookies, loginService, layoutService, $state) {
        //$scope.menus = [
        //    {
        //        id: 2,
        //        url: 'layout.home',
        //        name: 'aframeUIApp.label_menu_home',
        //        icon: 'fa fa-home'
        //    },
        //    {
        //        id: 3,
        //        url: 'layout.menu-list',
        //        name: 'aframeUIApp.label_menu_menu',
        //        icon: 'fa fa-home'
        //    },
        //    {
        //        id: 4,
        //        url: '',
        //        name: 'aframeUIApp.label_menu_code',
        //        icon: 'fa fa-unlock',
        //        submenus: [
        //            {
        //                id: 401,
        //                url: 'layout.code-codeType',
        //                name: 'aframeUIApp.label_menu_code_codetype',
        //                icon: 'fa fa-list'
        //            },
        //
        //            {
        //                id: 402,
        //                url: 'layout.code-codeInt',
        //                name: 'aframeUIApp.label_menu_code_codeint',
        //                icon: 'fa fa-list'
        //            },
        //        ]
        //    },
        //    {
        //        id: 5,
        //        url: 'layout.audit-list',
        //        name: 'aframeUIApp.label_menu_audit_log',
        //        icon: 'fa fa-home'
        //    },
        //    {
        //        id: 6,
        //        url: '',
        //        name: 'acmUIApp.label_menu_acm',
        //        icon: 'fa fa-unlock',
        //        submenus: [
        //            {
        //                id: 601,
        //                url: 'layout.user-list',
        //                name: 'acmUIApp.label_menu_user',
        //                icon: 'fa fa-user'
        //            },
        //            {
        //                id: 606,
        //                url: 'layout.application-list',
        //                name: 'acmUIApp.label_menu_application',
        //                icon: 'fa fa-file-text'
        //            },
        //            {
        //                id: 607,
        //                url: 'layout.delegate-list',
        //                name: 'acmUIApp.label_menu_deputize',
        //                icon: 'fa fa-file-text'
        //            },
        //        ]
        //    },
        //    {
        //        id: 7,
        //        url: '',
        //        name: 'examUIApp.label_menu_paper',
        //        icon: 'fa fa-unlock',
        //        submenus: [
        //            {
        //                id: 701,
        //                url: 'layout.paper-list',
        //                name: 'examUIApp.label_menu_list',
        //                icon: 'fa fa-user'
        //            },
        //        ]
        //    },
        //];
        layoutService.showMenu("menu_root").then(function (data) {
            if (data.submenus) {
                $scope.menus = data.submenus;
            }
            if ($scope.menus.length > 0) {
                $state.go($scope.menus[0].url);
            }
        });

        $scope.localeLang = locale.getLocale();

        $scope.changeLanguage = function (localeSelected) {
            $scope.localeLang = localeSelected;
            locale.setLocale(localeSelected);
        };

        var loginName = $cookies.get('login-user');
        if (loginName) {
            $scope.loginName = loginName;
        } else {
            // loginService.loginname().then(function (data) {
            //     if (data.loginname) {
            //         $scope.loginName = data.loginname;
            //         $cookies.put('auth-token', 1);
            //         $cookies.put('login-user', data);
            //     }
            // })
        }

    }]);