'use strict';

/**
 * @ngdoc overview
 * @name aframeApp
 * @description
 * # aframeApp
 *
 * Main module of the application.
 */

var APP_NAME = 'aframeUIApp';

angular.module(APP_NAME, [
    "ngAnimate",
    "ngResource",
    "ngSanitize",
    "ngNotificationsBar",
    "ui.router",
    "ui.bootstrap",
    "ui.mask",
    "ui.select",
    "ui-notification",
    "angucomplete-alt",
    "ncy-angular-breadcrumb",
    'ngLocalize',

    "aframe",
    "aframe.ext",

    /*mergepart*/

    APP_NAME + ".common.auditlog",
    APP_NAME + ".common.statistic",
    APP_NAME + ".base",
    APP_NAME + ".common"

])

    // setup block ui interceptor while making ajax request
    .config(["$httpProvider", function ($httpProvider) {
        $httpProvider.interceptors.push("responseParser");
        $httpProvider.interceptors.push("blockUI");
    }])

    // router configuration
    .config(["$urlRouterProvider", "$urlMatcherFactoryProvider",
        function ($urlRouterProvider, $urlMatcherFactoryProvider) {
            $urlMatcherFactoryProvider.caseInsensitive(true);
            $urlMatcherFactoryProvider.strictMode(false);

            $urlRouterProvider.otherwise("/layout/home");
        }
    ])

    // angular breadcrumb configuration
    .config(["$breadcrumbProvider", function ($breadcrumbProvider) {
        $breadcrumbProvider.setOptions({
            prefixStateName: "layout",
            //template: "bootstrap3",
            templateUrl: "ic/template/breadcrumb.html"
        });
    }])

    // sample configuration for [required mark]
    /*.config(["$icRequiredMarkProvider", function($icRequiredMarkProvider) {
     $icRequiredMarkProvider.setOptions({
     defaults: {
     mark: "aframe.label_required_mark",
     color: "red"
     }
     });
     }])*/

    // sample configuration for [form legend]
    /*.config(["$icFormLegendProvider", function($icFormLegendProvider) {
     $icFormLegendProvider.setOptions({
     defaults: {
     note: "aframe.label_form_legend",
     color: "red"
     }
     });
     }])*/

    // sample configuration [ckeditor], note: need add dependency at bower.json and add aframe module
    /*.config(["$icCkeditorProvider", function($icCkeditorProvider) {
     $icCkeditorProvider.setOptions({
     defaults: {
     language: "en",
     allowedContent: true,
     entities: false
     }
     });
     }])*/

    // ui-notification configuration
    .config(["NotificationProvider", function (NotificationProvider) {
        NotificationProvider.setOptions({
            delay: 2000,
            startTop: 20,
            startRight: 10,
            verticalSpacing: 20,
            horizontalSpacing: 20
        });
    }])

    // notifications-bar configuration
    .config(["notificationsConfigProvider", function (notificationsConfigProvider) {
        // auto hide
        notificationsConfigProvider.setAutoHide(true);
        // delay before hide
        notificationsConfigProvider.setHideDelay(3000);
        // support HTML
        notificationsConfigProvider.setAcceptHTML(false);
        // Set an animation for hiding the notification
        notificationsConfigProvider.setAutoHideAnimation("fadeOutNotifications");
        // delay between animation and removing the nofitication
        notificationsConfigProvider.setAutoHideAnimationDelay(1200);
    }])

    // blockUI configuration
    .config(["$blockUIProvider", function ($blockUIProvider) {
        $blockUIProvider.setOptions({
            defaults: {
                boxed: false,
                animate: false,
                iconOnly: false,
                icon: "blue",   // "black", "blue", "grey"
                textOnly: true,
                textColor: "#54aef4", // "#c6c6c6"
                message: "",
                target: "",
                zIndex: "1050",
                overlayColor: ""
            }
        });
    }])

    // ngLocalize configuration
    .value('localeConf', {
        basePath: 'i18n', // where the resource files are located
        defaultLocale: 'en-US',
        sharedDictionary: APP_NAME, // deprecated
        fileExtension: '.lang.json',
        persistSelection: true, // whether to save the selected language to cookies
        cookieName: 'COOKIE_LOCALE_LANG',
        observableAttrs: new RegExp('^data-(?!ng-|i18n|ic-i18n)'), // a regular expression which is used to match which custom data attributes may be watched by the i18n directive for 2-way bindings to replace in a tokenized string
        delimiter: '::' // the delimiter to be used when passing params along with the string token to the service, filter etc
    })
    .value('localeSupported', [
        'en-US',
        'zh-CN'
    ])
    .value('localeFallbacks', {
        'English': 'en-US',
        '\u4e2d\u6587': 'zh-CN'
    })

    // uib-pagination internationalization
    .constant('uibPaginationConfig', {
        itemsPerPage: 10,
        boundaryLinks: false,
        boundaryLinkNumbers: false,
        directionLinks: true,
        firstText: 'aframe.msg_pagination_first',
        previousText: 'aframe.msg_pagination_previous',
        nextText: 'aframe.msg_pagination_next',
        lastText: 'aframe.msg_pagination_last',
        rotate: true,
        forceEllipses: false
    })

    // run
    .run(["$rootScope", "$state", "loginService", "locale",
        function ($rootScope, $state, loginService, locale) {
            // toastr
            toastr.options = {
                "closeButton": true,
                "debug": false,
                "positionClass": "toast-top-center",
                "onclick": null,
                "showDuration": "800",
                "hideDuration": "800",
                "timeOut": "1500",
                "extendedTimeOut": "800",
                "showEasing": "swing",
                "hideEasing": "linear",
                "showMethod": "fadeIn",
                "hideMethod": "fadeOut"
            };

            locale.setLocale('zh-CN');
            $rootScope.appName = APP_NAME;
            $rootScope.$state = $state; // state to be accessed from view
            $rootScope.loginService = loginService;

            $state.previous = function () {
                $state.transitionTo($state.previousState.state, $state.previousState.stateParams);
            };

            $rootScope.$on("$stateChangeStart", function (event, toState) {
                console.log("test1");
                if (!loginService.isLogin() && toState.name !== "itrust/login" && toState.name !== "user-changePassword") {
                    console.log("test2");
                    loginService.loginname().then(function (data) {
                        console.log("test3:" + data.loginname);
                        if (data.loginname) {
                            console.log("test4");
                            $scope.loginName = data.loginname;
                            $cookies.put('auth-token', 1);
                            $cookies.put('login-user', $scope.loginName);
                        } else {
                            console.log("test5");
                            event.preventDefault();
                            // $state.go("itrust/login");
                            window.location = getLoginRedirect();
                        }
                    })
                }
            });

            $rootScope.$on("$stateChangeSuccess", function (event, to, toParams, from, fromParams) {
                $state.previousState = {
                    state: from,
                    stateParams: fromParams
                };
            });

            $rootScope.$on("error", function (event, data) {
                var errCd = "";
                if (data === null || data.data === null) {
                    errCd = 500;
                }
                if (data !== null && data.data !== null && data.data.length === 3) {
                    errCd = data.data;
                }
                if (errCd) {
                    if (errCd === "401") {
                        window.location = getLoginRedirect();
                    } else {
                        $state.go("error/" + errCd);
                    }
                } else {
                    console.error(data.data);
                }
            });
        }
    ]);
