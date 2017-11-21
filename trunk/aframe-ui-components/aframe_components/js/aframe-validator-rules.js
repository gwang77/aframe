'use strict';

/**
 * override angular-validator default behavior
 */
angular.module("aframe.validator.rules", ["validator"]).config(["$validatorProvider",
    function ($validatorProvider) {
        /*
         *Convert rule.error.
         *@param error: error messate (string) or function(value, scope, element, attrs, $injector)
         *@return: function(value, scope, element, attrs, $injector)
         */
        $validatorProvider.convertError = function (error) {
            if (typeof error === "function") {
                return error;
            }
            var errorMsg = error.constructor === String ? error : "";
            return function (value, scope, element, attrs) {
                return showErrorMsg(errorMsg, null, scope, element, attrs);
            };
        };

        /*
         *Convert rule.success.
         *@param success: function(value, scope, element, attrs, $injector)
         *@return: function(value, scope, element, attrs, $injector)
         */
        $validatorProvider.convertSuccess = function (success) {
            if (typeof success === "function") {
                return success;
            }
            return function (value, scope, element) {
                return showSuccessMsg(null, scope, element, null);
            };
        };

        /**
         * FileUpload validator rules.
         */

        // [maxcount] attribute is required
        $validatorProvider.register("fileUploadMaxCount", {
            invoke: "watch",
            validator: function (value, scope, element, attrs) {
                if (isEmpty(value) || !angular.isDefined(attrs.maxcount)) {
                    return true;
                }

                var intVal = parseInt(attrs.maxcount, 10);
                var intMax = isNaN(intVal) ? -1 : intVal;

                return value.length <= intMax;
            },
            //error: "Too many files."
            error: function(value, scope, element, attrs) {
                if (isEmpty(value) || !angular.isDefined(attrs.maxcount)) {
                    return;
                }

                var maxcount = attrs.maxcount;
                var errorMsgKey = "aframe.errors_fileupload_maxcount";

                return showErrorMsg(errorMsgKey, new Array(maxcount), scope, element, attrs);
            }
        });

        // [filesize] attribute is required
        $validatorProvider.register("fileUploadFileSize", {
            invoke: "watch",
            validator: function (value, scope, element, attrs) {
                if (isEmpty(value) || !angular.isDefined(attrs.filesize)) {
                    return true;
                }

                var files = getInvalidFilesByFileSize(value, attrs);
                return files === null || files.length === 0;
            },
            //error: "File size too large."
            error: function(value, scope, element, attrs) {
                if (isEmpty(value) || !angular.isDefined(attrs.filesize)) {
                    return;
                }

                var errorMsg, params, files, filesname, maxfilesize;
                files = getInvalidFilesByFileSize(value, attrs);
                if (files !== null && files.length > 0) {
                    filesname = "";
                    for (var i = 0; i < files.length; i ++) {
                        var file = files[i];
                        filesname += file.name + " (" + calcFileSize(file.size) + ") & ";
                    }

                    maxfilesize = attrs.filesize;
                    errorMsg = "aframe.errors_fileupload_filessize";
                    params = new Array(filesname.substr(0, filesname.length - 3), maxfilesize);
                } else {
                    errorMsg = "aframe.errors_fileupload_filesize";
                    params = null;
                }

                return showErrorMsg(errorMsg, params, scope, element, attrs);
            }
        });

        // [totalsize] attribute is required
        $validatorProvider.register("fileUploadTotalSize", {
            invoke: "watch",
            validator: function (value, scope, element, attrs) {
                if (isEmpty(value) || !angular.isDefined(attrs.totalsize)) {
                    return true;
                }

                var intMax = getMaxSize(attrs.totalsize);
                var intTotal = 0;
                angular.forEach(value,function(obj, idx){
                    intTotal = intTotal + obj.size;
                });

                return intTotal < intMax;
            },
            //error: "Total size too large."
            error: function(value, scope, element, attrs) {
                if (isEmpty(value) || !angular.isDefined(attrs.totalsize)) {
                    return;
                }

                var totalsize = attrs.totalsize;
                var errorMsg = "aframe.errors_fileupload_totalsize";

                return showErrorMsg(errorMsg, new Array(totalsize), scope, element, attrs);
            }
        });

        // [mimetype] attribute is required
        $validatorProvider.register("fileUploadMimeType", {
            invoke: "watch",
            validator: function (value, scope, element, attrs) {
                if (isEmpty(value) || !angular.isDefined(attrs.mimetype)) {
                    return true;
                }

                return getInvalidFilesByMimeType(value, attrs) === null || getInvalidFilesByMimeType(value, attrs).length === 0;
            },
            error: function(value, scope, element, attrs) {
                if (isEmpty(value) || !angular.isDefined(attrs.mimetype)) {
                    return;
                }

                var errorMsg, files, filesname, types;
                files = getInvalidFilesByMimeType(value, attrs);
                if (files !== null && files.length > 0) {
                    filesname = "";
                    for (var i = 0; i < files.length; i ++) {
                        var file = files[i];
                        filesname += file.name + " & ";
                    }

                    types = attrs.mimetype;
                    errorMsg = "aframe.errors_fileupload_mimetype";
                }

                return showErrorMsg(errorMsg, new Array(filesname.substr(0, filesname.length - 3), types), scope, element, attrs);
            }
        });


        /**
         * Common validator rules.
         */

        $validatorProvider.register("required", {
            invoke: "watch",
            validator: /.+/,
            error: "aframe.errors_required"
        });

        // [min-value] attribute is required
        $validatorProvider.register("min", {
            invoke: "watch",
            validator: function (value, scope, element, attrs) {
                if (isEmpty(value) || !angular.isDefined(attrs.minValue)) {
                    return true;
                }

                return value >= attrs.minValue;
            },
            error: function(value, scope, element, attrs) {
                if (isEmpty(value) || !angular.isDefined(attrs.minValue)) {
                    return;
                }

                var minValue = attrs.minValue;
                var errorMsg = "aframe.errors_minvalue";

                return showErrorMsg(errorMsg, new Array(minValue), scope, element, attrs);
            }
        });

        // [max-value] attribute is required
        $validatorProvider.register("max", {
            invoke: "watch",
            validator: function (value, scope, element, attrs) {
                if (isEmpty(value) || !angular.isDefined(attrs.maxValue)) {
                    return true;
                }

                return value <= attrs.maxValue;
            },
            error: function(value, scope, element, attrs) {
                if (isEmpty(value) || !angular.isDefined(attrs.maxValue)) {
                    return;
                }

                var maxValue = attrs.maxValue;
                var errorMsg = "aframe.errors_maxvalue";

                return showErrorMsg(errorMsg, new Array(maxValue), scope, element, attrs);
            }
        });

        // [equal-to] attribute is required and must set an valid component id
        $validatorProvider.register("equalTo", {
            invoke: "blur",
            validator: function (value, scope, element, attrs) {
                if (isEmpty(value) || !angular.isDefined(attrs.equalTo) || $("#"+attrs.equalTo).val() == undefined) {
                    return true;
                }

                return value === $("#"+attrs.equalTo).val();
            },
            error: function(value, scope, element, attrs) {
                if (isEmpty(value) || !angular.isDefined(attrs.equalTo) || $("#"+attrs.equalTo).val() == undefined) {
                    return;
                }

                var errorMsgKey = "aframe.errors_equalto";

                return showErrorMsg(errorMsgKey, null, scope, element, attrs);
            }
        });

        // [min-length] attribute is required
        $validatorProvider.register("minlength", {
            invoke: "watch",
            validator: function (value, scope, element, attrs) {
                if (isEmpty(value) || !angular.isDefined(attrs.minLength)) {
                    return true;
                }

                return value.length >= attrs.minLength;
            },
            error: function(value, scope, element, attrs) {
                if (isEmpty(value) || !angular.isDefined(attrs.minLength)) {
                    return;
                }

                var minLength = attrs.minLength;
                var errorMsg = "aframe.errors_minlength";

                return showErrorMsg(errorMsg, new Array(minLength), scope, element, attrs);
            }
        });

        // [max-length] attribute is required
        $validatorProvider.register("maxlength", {
            invoke: "watch",
            validator: function (value, scope, element, attrs) {
                if (isEmpty(value) || !angular.isDefined(attrs.maxLength)) {
                    return true;
                }

                return value.length <= attrs.maxLength;
            },
            error: function(value, scope, element, attrs) {
                if (isEmpty(value) || !angular.isDefined(attrs.maxLength)) {
                    return;
                }

                var maxLength = attrs.maxLength;
                var errorMsg = "aframe.errors_maxlength";

                return showErrorMsg(errorMsg, new Array(maxLength), scope, element, attrs);
            }
        });

        $validatorProvider.register("number", {
            invoke: "watch",
            validator: function (value) {
                if (isEmpty(value)) {
                    return true;
                }

                return /^(?:-?\d+|-?\d{1,3}(?:,\d{3})+)?(?:\.\d+)?$/.test(value);
            },
            error: "aframe.errors_number"
        });

        $validatorProvider.register("email", {
            invoke: "watch",
            validator: function (value) {
                if (isEmpty(value)) {
                    return;
                }

                return /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/.test(value);
            },
            error: "aframe.errors_email"
        });

        $validatorProvider.register("date", {
            invoke: "watch",
            validator: function (value) {
                if (isEmpty(value)) {
                    return true;
                }

                return !/Invalid|NaN/.test(new Date(value).toString());
            },
            error: "aframe.errors_date"
        });

        $validatorProvider.register("digits", {
            invoke: "watch",
            validator: function (value) {
                if (isEmpty(value)) {
                    return true;
                }

                return /^\d+$/.test(value);
            },
            error: "aframe.errors_digits"
        });

        $validatorProvider.register("creditcard", {
            invoke: "watch",
            validator: function (value) {
                if (isEmpty(value)) {
                    return true;
                }
                else {
                    if (/[^0-9 \-]+/.test(value)) {
                        return false;
                    }
                    var nCheck = 0,
                        nDigit = 0,
                        bEven = false,
                        n, cDigit;

                    value = value.replace(/\D/g, "");

                    // Basing min and max length on
                    // http://developer.ean.com/general_info/Valid_Credit_Card_Types
                    if (value.length < 13 || value.length > 19) {
                        return false;
                    }

                    for (n = value.length - 1; n >= 0; n--) {
                        cDigit = value.charAt(n);
                        nDigit = parseInt(cDigit, 10);
                        if (bEven) {
                            if (( nDigit *= 2 ) > 9) {
                                nDigit -= 9;
                            }
                        }
                        nCheck += nDigit;
                        bEven = !bEven;
                    }

                    return ( nCheck % 10 ) === 0;
                }
            },
            error: "aframe.errors_creditcard"
        });

        $validatorProvider.register("url", {
            invoke: "watch",
            validator: function (value) {
                if (isEmpty(value)) {
                    return true;
                }

                return /^(?:(?:(?:https?|ftp):)?\/\/)(?:\S+(?::\S*)?@)?(?:(?!(?:10|127)(?:\.\d{1,3}){3})(?!(?:169\.254|192\.168)(?:\.\d{1,3}){2})(?!172\.(?:1[6-9]|2\d|3[0-1])(?:\.\d{1,3}){2})(?:[1-9]\d?|1\d\d|2[01]\d|22[0-3])(?:\.(?:1?\d{1,2}|2[0-4]\d|25[0-5])){2}(?:\.(?:[1-9]\d?|1\d\d|2[0-4]\d|25[0-4]))|(?:(?:[a-z\u00a1-\uffff0-9]-*)*[a-z\u00a1-\uffff0-9]+)(?:\.(?:[a-z\u00a1-\uffff0-9]-*)*[a-z\u00a1-\uffff0-9]+)*(?:\.(?:[a-z\u00a1-\uffff]{2,})).?)(?::\d{2,5})?(?:[/?#]\S*)?$/i.test(value);
            },
            error: "aframe.errors_url"
        });

        // [regex] and [regex-err-msg] attributes are required
        $validatorProvider.register("regex", {
            invoke: "watch",
            validator: function (value, scope, element, attrs) {
                if (isEmpty(value) || !angular.isDefined(attrs.regex) || !angular.isDefined(attrs.regexErrMsg)) {
                    return true;
                }

                var regexp = new RegExp(attrs.regex);
                return regexp.exec(value);
            },
            error: function(value, scope, element, attrs) {
                if (isEmpty(value) || !angular.isDefined(attrs.regex) || !angular.isDefined(attrs.regexErrMsg)) {
                    return;
                }

                return showErrorMsg(attrs.regexErrMsg, null, scope, element, attrs);
            }
        });


        /**
         * Common functions.
         */

        var optional = function (val) {
            return val === "" || val === null || val === undefined;
        };

        // check value if is empty
        var isEmpty = function (val) {
            return val === "" || val === null || val === undefined || val.length === 0;
        };

        // show custom error message
        var showErrorMsg = function (errorMsg, params, scope, element, attrs) {
            var $p, p, parent, _i, _len, _ref, _results;
            parent = $(element).parent();
            _results = [];

            while (parent.length !== 0) {
                if (!parent.hasClass("input-group")) {
                    _ref = parent.find("label.control-label");
                    for (_i = 0, _len = _ref.length; _i < _len; _i++) {
                        p = _ref[_i];
                        if ($(p).hasClass("error")) {
                            $(p).remove();
                        }
                    }
                    $p = $("<label class='control-label error' style='text-align: left; width: 100%;' i18n='" + errorMsg + "'></label>");
                    if (attrs.id) {
                        $p.attr("for", attrs.id);
                    }
                    if (params) {
                        for (var _i = 0; _i < params.length; _i ++) {
                            var param = params[_i];
                            $p.attr("data-" + _i, param);
                        }
                    }

                    parent.addClass("has-error").removeClass("has-success");
                    //parent.append($p);
                    angular.element(parent).injector().invoke(["$compile", function($compile) {
                        //var $scope = angular.element(parent).scope();
                        parent.append($compile($p)(scope));
                    }]);

                    break;
                }
                _results.push(parent = parent.parent());
            }
            return _results;
        };

        // show success message
        var showSuccessMsg = function (successMsg, scope, element, attrs) {
            var p, parent, _i, _len, _ref, _results;
            _results = [];
            parent = $(element).parent();

            while (parent.length !== 0) {
                if (!parent.hasClass("input-group")) {
                    parent.removeClass("has-error");
                    parent.removeClass("has-success");
                    if (!element.hasClass("ng-pristine")) {
                        parent.addClass("has-success");
                    }

                    _ref = parent.find("label.control-label");
                    for (_i = 0, _len = _ref.length; _i < _len; _i++) {
                        p = _ref[_i];
                        if ($(p).hasClass("error")) {
                            $(p).remove();
                        }
                    }

                    break;
                }

                _results.push(parent = parent.parent());
            }
            return _results;
        };

        // get invalid file by filesize
        var getInvalidFilesByFileSize = function (value, attrs) {
            var intMax = -1;
            if (angular.isDefined(attrs.filesize)) {
                intMax = getMaxSize(attrs.filesize);
            }

            var invalid_files = [];
            value.every(function (obj, idx) {
                if (obj.size > intMax) {
                    invalid_files.push(obj);
                }
                return true;
            });
            return invalid_files;
        };

        // get invalid file by mime type
        var getInvalidFilesByMimeType = function (value, attrs) {
            var invalid_files = [];

            if (angular.isDefined(attrs.mimetype)) {
                var reg = new RegExp(attrs.mimetype, "i");
                value.every(function (obj, idx) {
                    if (!obj.type.match(reg)) {
                        invalid_files.push(obj);
                    }
                    return true;
                });
            }

            return invalid_files;
        };

        // get max file and total file size
        var getMaxSize = function (size) {
            var intMax = -1;
            var reg = /^[1-9][0-9]*(Byte|KB|MB)$/;
            if (!reg.test(size)) {
                intMax = -1;
            } else {
                var sizes = ["Byte", "KB", "MB"];
                var unit = size.match(reg)[1];
                var number = size.substring(0, size.indexOf(unit));
                sizes.every(function (obj, idx) {
                    if (unit === obj) {
                        intMax = parseInt(number) * Math.pow(1024, idx);
                        return false;
                    } else {
                        return true;
                    }
                });
            }
            return intMax;
        };

        // calculation file size
        var calcFileSize = function (filesize) {
            if (filesize < 1000) {
                return filesize + " Byte";
            }
            if (filesize > 0 && parseInt(filesize/1000) < 1000) {
                return parseInt(filesize/1000) + " KB";
            }
            if (filesize > 0 && parseInt(filesize/1000/1000) < 1000) {
                return parseInt(filesize/1000/1000) + " MB";
            }
            return parseInt(filesize/1000/1000) + " GB";
        };
    }
]);

