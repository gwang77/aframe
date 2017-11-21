'use strict';

angular.module("aframe.services", [
    "aframe.service.common",
    "aframe.service.serializer",
    "aframe.service.storage.cookie",
    "aframe.service.storage.local",
    "aframe.service.storage.remote",
    "aframe.service.storage.session",
    "aframe.service.mgr.state",
    "aframe.service.mgr.ctrlState"
]);


var ic_srv_common = angular.module("aframe.service.common", []);
/**
 * common toastr.
 * *string
 * *internationalization key
 * *saved
 * *updated
 * *deleted
 * @param locale
 */
ic_srv_common.service("icCommonToastr", ["locale",
    function (locale) {
        var lang_pre = "aframe.";

        var title_record_save = lang_pre + "title_record_save";
        var title_record_update = lang_pre + "title_record_update";
        var title_record_delete = lang_pre + "title_record_delete";

        var msg_record_saved = lang_pre + "msg_record_saved";
        var msg_record_not_saved = lang_pre + "msg_record_not_saved";
        var msg_record_updated = lang_pre + "msg_record_updated";
        var msg_record_not_updated = lang_pre + "msg_record_not_updated";
        var msg_record_deleted = lang_pre + "msg_record_deleted";
        var msg_record_not_deleted = lang_pre + "msg_record_not_deleted";

        // success with string
        this.success = function (msg, title) {
            toastr.success(msg, title);
        };
        // success with internationalization key
        this.success_locale = function (msg_key, title_key) {
            this.success(locale.getString(msg_key), locale.getString(title_key));
        };
        // common success saved
        this.success_saved = function () {
            this.success(locale.getString(msg_record_saved), locale.getString(title_record_save));
        };
        // common success updated
        this.success_updated = function () {
            this.success(locale.getString(msg_record_updated), locale.getString(title_record_update));
        };
        // common success deleted
        this.success_deleted = function () {
            this.success(locale.getString(msg_record_deleted), locale.getString(title_record_delete));
        };

        // error with string
        this.error = function (msg, title) {
            toastr.error(msg, title);
        };
        // error with internationalization key
        this.error_locale = function (msg_key, title_key) {
            this.error(locale.getString(msg_key), locale.getString(title_key));
        };
        // common error saved
        this.error_saved = function (msg) {
            this.error(locale.getString((msg !== '' && msg !== null) ? msg : msg_record_not_saved), locale.getString(title_record_save));
        };
        // common error updated
        this.error_updated = function (msg) {
            this.error(locale.getString((msg !== '' && msg !== null) ? msg : msg_record_not_updated), locale.getString(title_record_update));
        };
        // common error delete
        this.error_deleted = function () {
            this.error(locale.getString(msg_record_not_deleted), locale.getString(title_record_delete));
        };
    }
]);

/**
 * common bootbox.
 * *confirm(confirm, cancel buttons/title/message)
 * *alert(confirm buttons/title/message)
 */
ic_srv_common.service("icCommonBootbox", ["locale",
    function (locale) {
        var lang_pre = "aframe.";
        var title_confirm = lang_pre + "title_confirm";
        var title_warn = lang_pre + "title_warn";
        var btn_ok = lang_pre + "button_ok";
        var btn_cancel = lang_pre + "button_cancel";
        var msg_confirm_delete_record = lang_pre + "msg_confirm_delete_record";
        var msg_select_at_least_one_record = lang_pre + "msg_select_at_least_one_record";

        this.confirm = function (ok, cancel, title, message, callback) {
            var okLabel = locale.getString(ok ? ok : btn_ok);
            var cancelLabel = locale.getString(cancel ? cancel: btn_cancel);
            var titleLabel = locale.getString(title ? title : title_confirm);
            var message = locale.getString(message);

            bootbox.confirm(
                {
                    buttons: {
                        confirm: {
                            label: okLabel
                        },
                        cancel: {
                            label: cancelLabel
                        }
                    },
                    title: '<strong>' + titleLabel + '</strong>',
                    message: '<h4>' + message + '</h4>',
                    callback: callback
                }
            );
        };
        this.confirm_delete = function (callback) {
            this.confirm(null, null, null, msg_confirm_delete_record, callback);
        };
        this.alert = function (ok, title, message, callback) {
            var okLabel = locale.getString(ok ? ok : btn_ok);
            var titleLabel = locale.getString(title ? title : title_warn);
            var message = locale.getString(message);

            bootbox.alert(
                {
                    buttons: {
                        confirm: {
                            label: okLabel
                        }
                    },
                    title: '<strong>' + titleLabel + '</strong>',
                    message: '<h4>' + message + '</h4>',
                    callback: callback
                }
            );
        };
        this.alert_check_select = function () {
            this.alert(null, null, msg_select_at_least_one_record, null);
        }
    }
]);

/**
 * common front paging.
 */
ic_srv_common.service("icCommonPaging", function() {
    this.front_paging = function (data, params) {
        var result = {"content": [], "total_count": 0};
        if (!data) {
            return result;
        }

        var paging = {};
        if (!params) {
            paging = {"page": 1, "size": 10, "sort": ""};
        } else {
            paging = {"page": params.page, "size": params.size, "sort": params.sort};
        }

        if (paging.sort) {
            data = paging_sort(data, paging.sort);
        }

        // calcuate records start and end index
        var start = (paging.page - 1) * paging.size;
        var end = start + paging.size - 1;
        end = end > data.length - 1 ? data.length - 1 : end;

        // set paging records
        for (var i = start; i <= end; i ++) {
            result.content.push(data[i]);
        }

        // set paging records total count
        result.total_count = data.length;

        return result;
    };
});

function paging_sort(data, sort) {
    var sort_col = sort.split(",")[0];
    var sort_type = sort.split(",")[1];
    return data.sort(function(obj1, obj2) {
        return sort_type === 'desc' ? (obj1[sort_col] < obj2[sort_col] ? 1 : -1) : (obj1[sort_col] > obj2[sort_col] ? 1 : -1);
    })
}

/**
 * common http request.
 * *sync request
 */
ic_srv_common.service("icCommonReq", ["$http", "$q",
    function ($http, $q) {
        this.request_sync = function (url, method, params, data, cache, headers) {
            var deferred = $q.defer();
            var config = {url: url, method: method};

            if (params !== undefined && params !== null) {
                config.params  = params;
            }
            if (data !== undefined && data !== null) {
                config.data  = data;
            }
            if (cache !== undefined && cache !== null) {
                config.cache = cache;
            }
            if (headers !== undefined && headers !== null) {
                config.headers = headers;
            }

            $http(config)
                .success(function (data) {
                    /*if(typeof data === 'object' && !data.length) {
                        angular.forEach(data, function(value, key) {
                            if(value === null) {
                                data[key] = '';
                            }
                        })
                    }*/
                    deferred.resolve(data);
                })
                .error(function (data) {
                    deferred.reject(data);
                });

            return deferred.promise;
        };
    }
]);

/**
 * common handle 400 error message from server side.
 */
ic_srv_common.service("icCommonHandleErrorMsg", ["icCommonToastr",
    function (icCommonToastr) {
        this.handle400Error = function (data) {
            var errorMsg = '';
            if (angular.isDefined(data.status) && data.status === 400) {
                errorMsg = data.message;
            }

            icCommonToastr.error(errorMsg, "Error");
        }
    }
]);


var ic_srv_serializer = angular.module("aframe.service.serializer", []);
ic_srv_serializer
    .service("jsonSerializer", function () {
        this.serialize = function(object){
            return JSON.stringify(object);
        };

        this.deserialize = function(str){
            return JSON.parse(str);
        };
    });

var ic_srv_storage_cookie = angular.module("aframe.service.storage.cookie", []);
ic_srv_storage_cookie
    .service("cookieStorage", ["$cookies", "jsonSerializer", function ($cookies, serializer) {
        this.set = function (key, object) {
            $cookies.put(key, serializer.serialize(object));
        };

        this.get = function (key) {
            var s = $cookies.get(key);
            if (s) {
                return serializer.deserialize(s);
            }
        };

        this.clear = function (key) {
            $cookies.remove(key);
        };

        this.contains = function (key) {
            return this.get(key) !== undefined;
        };
    }]);

var ic_srv_storage_local =angular.module("aframe.service.storage.local", []);
ic_srv_storage_local
    .service("localStorage", ["$window", "jsonSerializer", function ($window, serializer) {
        var storage = $window.localStorage;

        this.set = function (key, object) {
            storage.setItem(key, serializer.serialize(object));
        };

        this.get = function (key) {
            return serializer.deserialize(storage.getItem(key));
        };

        this.clear = function (key) {
            storage.removeItem(key);
        };

        this.contains = function (key) {
            return this.get(key) !== undefined;
        };
    }]);

var ic_srv_storage_remote = angular.module("aframe.service.storage.remote", []);
ic_srv_storage_remote
    .service("remoteStorage", ["$http", function ($http) {
        this.set = function (key, object) {
            return $http.post("api/storage/" + key, object);
        };

        this.get = function (key) {
            return $http.get("api/storage/" + key);
        };

        this.clear = function (key) {
            return $http.delete("api/storage/" + key);
        };

        this.contains = function (key) {
            return this.get(key) !== undefined;
        };
    }]);

var ic_srv_storage_session = angular.module("aframe.service.storage.session", []);
ic_srv_storage_session
    .service("sessionStorage", ["$window", "jsonSerializer", function ($window, serializer) {
        var storage = $window.sessionStorage;

        this.set = function (key, object) {
            storage.setItem(key, serializer.serialize(object));
        };

        this.get = function (key) {
            return serializer.deserialize(storage.getItem(key));
        };

        this.clear = function (key) {
            storage.removeItem(key);
        };

        this.contains = function (key) {
            return this.get(key) !== undefined;
        };

    }]);

var ic_srv_mgr_state = angular.module("aframe.service.mgr.state", []);
ic_srv_mgr_state
    .service("stateMgr", ["cookieStorage", function (storage) {
        var stateManagerKey = "state-manager";

        this.set = function (key, object) {
            storage.set(getKeyName(key), object);
        };

        this.get = function (key) {
            return storage.get(getKeyName(key));
        };

        this.clear = function (key) {
            storage.clear(getKeyName(key));
        };

        this.contains = function (key) {
            return storage.contains(getKeyName(key));
        };

        var getKeyName = function(key){
            return stateManagerKey + "-" + key;
        };

    }]);

var ic_srv_mgr_ctrlState = angular.module("aframe.service.mgr.ctrlState", []);
ic_srv_mgr_ctrlState
    .service("ctrlStateMgr", ["$state", "stateMgr", function ($state, stateMgr) {
        this.set = function (key, object) {
            stateMgr.set(getKeyName(key), object);
        };

        this.get = function (key, defaults) {
            if (defaults !== undefined) {
                return _.extend(defaults, stateMgr.get(getKeyName(key)));
            }
            return stateMgr.get(getKeyName(key));
        };

        this.remove = function (key) {
            stateMgr.clear(getKeyName(key));
        };

        var getKeyName = function (key) {
            return $state.current.controller + "-state-" + key;
        };
    }]);
