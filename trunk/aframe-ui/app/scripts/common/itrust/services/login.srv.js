'use strict';

angular.module(APP_NAME + '.itrust.services').service('loginService', ['$cookies', 'config', 'icCommonReq',
    function ($cookies, config, icCommonReq) {
        this.login = function (user) {
            return icCommonReq.request_sync(config.restful.user.login, "GET", user);
        };

        this.logout = function () {
            $cookies.remove('auth-token');
            $cookies.remove('login-user');
            icCommonReq.request_sync(config.restful.user.logout, "GET");
        };

        this.loginname = function () {
            return icCommonReq.request_sync(config.restful.user.loginname, "GET");
        };

        this.isLogin = function(){
            var token =  $cookies.get('auth-token');
            return verifyToken(token);
        };

        var verifyToken = function(token){
            return token !== undefined && token !== null && token !=='';
        };
    }
]);