'use strict';

angular.module("aframe.interceptors", [
    "aframe.interceptor.responseParser",
    "aframe.interceptor.blockUI"
]);

// aframe interceptor - [responseParser]
var ic_icpt_responseParser = angular.module("aframe.interceptor.responseParser", []);
ic_icpt_responseParser
    .factory("responseParser", ["$q", "$rootScope", function ($q, $rootScope) {
        return {
            "request": function (config) {
                return config;
            },

            "requestError": function (rejection) {
                return $q.reject(rejection);
            },

            "response": function (response) {
                //prepare "real" data for business layer
                /*if(response.config.url.toLowerCase().indexOf("api") === 0){
                    if(response.data.error){
                        console.log(response.data.error.message, response.data.error.code);
                    }
                    return response.data;
                }*/
                return response;
            },

            "responseError": function (rejection) {
                /*// check if is login by status
                if(rejection.status == "500999"){
                    // clear local token e.g.
                    $rootScope.user = {token:""};
                    // global event
                    $rootScope.$emit("userIntercepted","notLogin",response);
                }
                // session timeout
                if(rejection.status == "500998"){
                    $rootScope.$emit("userIntercepted","sessionOut",response);
                }
                return $q.reject(response);*/

                /*var status = '';
                switch(rejection.status){
                    case 403:
                        status = 403;
                        break;
                    case 404:
                        status = 404;
                        break;
                    case 500:
                        status = 500;
                        break;
                    case 502:
                        status = 502;
                        break;
                    case 503:
                        status = 503;
                        break;
                    case 504:
                        status = 504;
                        break;
                    /!*default:
                        status = 'unknown';
                        break;*!/
                }*/

                $rootScope.$emit("error", rejection);
                return $q.reject(rejection);
            }
        };
    }]);


// aframe interceptor - [blockUI]
var ic_icpt_blockUI = angular.module("aframe.interceptor.blockUI", []);
ic_icpt_blockUI
    .provider("$blockUI", function () {
        var $$options = {
            defaults: {
                boxed: false,
                animate: true,
                iconOnly: false,
                icon: "blue",
                textOnly: false,
                message: "",
                target: "",
                zIndex: "1050",
                overlayColor: ""
            }
        };

        this.setOptions = function(options) {
            angular.extend($$options, options);
        };

        this.$get = function() {
            return $$options;
        };
    })
    .factory("blockUI", ["$q", "$injector", "$blockUI", function ($q, $injector, $blockUI) {
        return {
            "request": function (config) {
                if (config.url.indexOf('i18n') < 0 && $injector.get("$http").pendingRequests.length < 1) {
                    var options = $blockUI.defaults;
                    var locale = $injector.get("locale");
                    var message = locale.getString(options.message ? options.message : 'aframe.msg_blockui_processing');
                    blockUI(message, $blockUI.defaults);
                }
                return config;
            },

            "requestError": function (rejection) {
                if ($injector.get("$http").pendingRequests.length < 1) {
                    unblockUI();
                }
                return $q.reject(rejection);
            },

            "response": function (response) {
                if ($injector.get("$http").pendingRequests.length < 1) {
                    unblockUI();
                }
                return response;
            },

            "responseError": function (rejection) {
                if ($injector.get("$http").pendingRequests.length < 1) {
                    unblockUI();
                }
                return $q.reject(rejection);
            }
        };
    }]);

// function to block element
function blockUI(message, options) {
    options = $.extend(true, {}, options);
    var html = '';
    if (options.animate) {
        html = '<div class="loading-message ' + (options.boxed ? 'loading-message-boxed' : '') + '">' + '<div class="block-spinner-bar"><div class="bounce1"></div><div class="bounce2"></div><div class="bounce3"></div></div>' + '</div>';
    } else if (options.iconOnly) {
        html = '<div class="loading-message ' + (options.boxed ? 'loading-message-boxed' : '') + '"><div class="page-loading-progress-' + (options.icon ? options.icon : 'blue') + '"/></div>';
    } else if (options.textOnly) {
        html = '<div class="loading-message ' + (options.boxed ? 'loading-message-boxed' : '') + '"><span style="color: ' + (options.textColor ? options.textColor : '#4aef4') + '">&nbsp;&nbsp;' + message + '</span></div>';
    } else {
        html = '<div class="loading-message ' + (options.boxed ? 'loading-message-boxed' : '') + '"><div class="hidden-xs page-loading-progress-' + (options.icon ? options.icon : 'blue') + '" style="float:left;" /><span style="color: ' + (options.textColor ? options.textColor : '#54aef4') + '">&nbsp;&nbsp;' + message + '</span></div>';
    }

    if (options.target) { // element blocking
        var el = $(options.target);
        if (el.height() <= ($(window).height())) {
            options.cenrerY = true;
        }
        el.block({
            message: html,
            baseZ: options.zIndex ? options.zIndex : 1050,
            centerY: options.cenrerY !== undefined ? options.cenrerY : false,
            css: {
                top: "10%",
                border: "0",
                padding: "0",
                backgroundColor: "none"
            },
            overlayCSS: {
                backgroundColor: options.overlayColor ? options.overlayColor : "#555",
                opacity: options.boxed ? 0.05 : 0.1,
                cursor: "wait"
            }
        });
    } else { // page blocking
        $.blockUI({
            message: html,
            baseZ: options.zIndex ? options.zIndex : 1050,
            css: {
                border: "0",
                padding: "0",
                backgroundColor: "none"
            },
            overlayCSS: {
                backgroundColor: options.overlayColor ? options.overlayColor : "#555",
                opacity: options.boxed ? 0.05 : 0.1,
                cursor: "wait"
            }
        });
    }
};

// function to  un-block element(finish loading)
function unblockUI(target) {
    if (target) {
        $(target).unblock({
            onUnblock: function () {
                $(target).css("position", "");
                $(target).css("zoom", "");
            }
        });
    } else {
        $.unblockUI();
    }
};

