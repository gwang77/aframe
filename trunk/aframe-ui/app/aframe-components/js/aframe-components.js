'use strict';

/**
 * aframe components.
 * # -> sidebar
 * # -> datetimepicker
 * # -> timeline
 * # -> stats
 */
angular.module("aframe.components", [
    "aframe.component.stats",
    "aframe.component.sidebar",
    "aframe.component.timeline",
    "aframe.component.picklist",
    "aframe.component.fileupload",
    "aframe.component.radiolist",
    "aframe.component.checkboxlist",
    "aframe.component.form",
    "aframe.component.paging",
    "aframe.component.requiredmark",
    "aframe.component.formlegend",
    "aframe.component.inputclear",
    "aframe.component.i18n",
    "aframe.component.updown",
    "aframe.component.topmenu",
    "aframe.component.treemenu",
    "aframe.component.datetimepicker"
]);

// aframe component - [stats]
var ic_cmpt_stats = angular.module("aframe.component.stats", []);
ic_cmpt_stats
    .directive("icStats", function () {
        return {
            templateUrl: "ic/template/stats/stats.html",
            restrict: "E",
            replace: true,
            scope: {
                model: "=",
                comments: "@",
                number: "@",
                name: "@",
                colour: "@",
                details: "@",
                type: "@",
                goto: "@"
            }
        };
    });

// aframe component - [sidebar]
var ic_cmpt_sidebar = angular.module("aframe.component.sidebar", []);
ic_cmpt_sidebar
    .directive("icSidebar", ["$state", function ($state) {
        return {
            templateUrl: "ic/template/sidebar/sidebar.html",
            restrict: "E",
            replace: true,
            scope: {
                menus: "="
            },
            controller: ["$scope", function ($scope) {
                $scope.collapseVar = 0;
                $scope.multiCollapseVar = 0;

                $scope.rootLayerClick = function () {
                    $scope.collapseVar = 0;
                    $scope.multiCollapseVar = 0;
                };

                $scope.secondLayerClick = function (x) {
                    if (x === $scope.collapseVar) {
                        $scope.collapseVar = 0;
                    } else {
                        $scope.collapseVar = x;
                    }
                };

                $scope.thirdLayerClick = function (y) {
                    if (y === $scope.multiCollapseVar) {
                        $scope.multiCollapseVar = 0;
                    } else {
                        $scope.multiCollapseVar = y;
                    }
                };

                // auto collapse menu
                $scope.autoCollapseMenu = function () {
                    var width = getViewPort().width;
                    if (width < getResponsiveBreakpoint("sm")) {
                        $(".navbar-toggle").click();
                    }
                };

                // scroll to top
                $scope.scrollTop = function (offeset) {
                    $("html,body").animate({scrollTop: 0}, "slow");
                };

                this.setCollapse = function setCollapse(x, y) {
                    $scope.collapseVar = x ? x : 0;
                    $scope.multiCollapseVar = y ? y : 0;
                };
            }],
            link: function (scope, element, attrs, ctrl) {
                var currentUrl = $state.current.name;

                // initialize metismenu
                element.find("#side-menu").metisMenu();

                // Loads the correct sidebar on window load,
                // collapses the sidebar on window resize.
                // Sets the min-height of #page-wrapper to window size
                $(window).bind("load resize", function () {
                    var topOffset = 50;
                    var width = getViewPort().width;
                    if (width < getResponsiveBreakpoint("sm")) {
                        element.find(".navbar-collapse").addClass("collapse");
                        topOffset = 100; // 2-row-menu
                    } else {
                        element.find(".navbar-collapse").removeClass("collapse");
                    }

                    var height = getViewPort().height - 1;
                    height = height - topOffset;
                    if (height < 1) height = 1;
                    if (height > topOffset) {
                        $("#page-wrapper").css("min-height", (height) + "px");
                    }
                });

                // Get second and third menu id when open the page by url
                var width = getViewPort().width;
                if (width < getResponsiveBreakpoint("sm")) {
                    element.find(".navbar-collapse").addClass("collapse");
                }
                else {
                    element.find(".navbar-collapse").removeClass("collapse");

                    var secLevelMenuId = 0, thirdLevelMenuId = 0;
                    var found_parent_menu = getMenu(scope.menus, null, currentUrl);
                    if (found_parent_menu != null) {
                        if (found_parent_menu.parent_id == null) {
                            secLevelMenuId = found_parent_menu.id;
                        } else {
                            secLevelMenuId = found_parent_menu.parent_id;
                            thirdLevelMenuId = found_parent_menu.id;
                        }
                    }
                    ctrl.setCollapse(secLevelMenuId, thirdLevelMenuId);
                }
            }
        };
    }]);

var found_parent_menu;
function getMenu(menus, parent_menu, currentUrl) {
    for (var i = 0; menus && i < menus.length; i ++) {
        var menu = menus[i];
        if (menu.submenus != null && menu.submenus.length > 0) {
            getMenu(menu.submenus, menu, currentUrl);
        }
        else {
            if (menu.url.length > 0 && currentUrl === menu.url) {
                found_parent_menu = parent_menu;
                break;
            }
        }
    }

    return found_parent_menu;
}

function getResponsiveBreakpoint(size) {
    // bootstrap responsive breakpoints
    var sizes = {
        "xs" : 480,     // extra small
        "sm" : 768,     // small
        "md" : 992,     // medium
        "lg" : 1200     // large
    };

    return sizes[size] ? sizes[size] : 0;
};

function getViewPort() {
    var e = window, a = "inner";
    if (!("innerWidth" in window)) {
        a = "client";
        e = document.documentElement || document.body;
    }

    return {
        width: e[a + "Width"],
        height: e[a + "Height"]
    };
};

// aframe component - [timeline]
var ic_cmpt_timeline = angular.module("aframe.component.timeline", []);
ic_cmpt_timeline
    .directive("icTimeline", function () {
        return {
            templateUrl: "ic/template/timeline/timeline.html",
            restrict: "E",
            replace: true,
            scope: {
                model: "=ngModel"
            }
        };
    });

// aframe component - [picklist]
var ic_cmpt_picklist = angular.module("aframe.component.picklist", []);
ic_cmpt_picklist
    .directive("icPicklist", ["$timeout", function ($timeout) {
        return {
            templateUrl: "ic/template/picklist/picklist.html",
            restrict: "E",
            replace: true,
            require: ["ngModel"],
            scope: {
                results: "=ngModel",
                sourceItems: "="
            },
            controller: ["$scope", "$element", function ($scope, $element) {
                $scope.selectedItems = [];
                $scope.activeItems = [];
                $scope.items = angular.copy($scope.sourceItems);

                $scope.addAll = function () {
                    angular.forEach($scope.items, function (object) {
                        if ($scope.activeItems.map(function (data) {
                                return data.value
                            }).indexOf(object.value) == -1) {
                            $scope.activeItems.push(object);
                        }
                    });
                    $scope.results = $scope.activeItems;
                    $scope.items = [];
                };

                $scope.add = function () {
                    angular.forEach($scope.selectedItems, function (object) {
                        object = JSON.parse(object);
                        if ($scope.activeItems.map(function (data) {
                                return data.value
                            }).indexOf(object.value) == -1) {
                            $scope.activeItems.push(object);
                            $scope.items.splice($scope.items.map(function (data) {
                                return data.value
                            }).indexOf(object.value), 1);
                        }
                    });

                    $scope.results = $scope.activeItems;
                };

                $scope.remove = function () {
                    var index = -1;
                    angular.forEach($scope.removedItems, function (object) {
                        object = JSON.parse(object);
                        index = $scope.activeItems.map(function (data) {
                            return data.value
                        }).indexOf(object.value);
                        if (index != -1) {
                            $scope.items.push(object);
                            $scope.activeItems.splice(index, 1);
                        }
                    });

                    $scope.results = $scope.activeItems;
                };

                $scope.removeAll = function () {
                    angular.forEach($scope.activeItems, function (object) {
                        if ($scope.items.map(function (data) {
                                return data.value
                            }).indexOf(object.value) == -1) {
                            $scope.items.push(object);
                        }
                    });

                    $scope.activeItems = [];
                    $scope.results = $scope.activeItems;
                }
            }],
            link: function (scope, element, attrs, ctrls) {
                scope.$watch("sourceItems", function (newValue) {
                    scope.items = angular.copy(newValue);
                });
                scope.$watch("results", function (newValue) {
                    scope.activeItems = angular.copy(newValue);
                });
            }
        };
    }]);

// aframe component - [fileupload]
var ic_cmpt_fileupload = angular.module("aframe.component.fileupload", []);
ic_cmpt_fileupload
    .directive("icFileupload", ["$q", "$compile", "$timeout", function ($q, $compile, $timeout) {
        return {
            restrict: "A",
            templateUrl: "ic/template/fileupload/fileupload.html",
            replace: true,
            require: "ngModel",
            scope: {
                files: "=ngModel",
                api: "=?",
                option: "=?",
                placeholder: "@?",
                dragAndDropLabel: "@?",
                browseLabel: "@?",
                removeLabel: "@?",
                accept: "@?",
                ngDisabled: "=?"
            },
            link: function (scope, element, attrs, ngModelCtrl) {
                var elFileinput = angular.element(element[0].querySelector(".file-input-tag"));
                var elDragview = angular.element(element[0].querySelector(".file-input-drag"));
                var elThumbnails = angular.element(element[0].querySelector(".file-input-thumbnails"));

                scope.isPreview = false;
                scope.isDrag = false;
                scope.isMutiple = false;
                scope.isShowFileSize = false;
                scope.isShowTotalSize = false;
                scope.isDisabled = false;

                if ("preview" in attrs) {
                    scope.isPreview = true;
                }

                if ("drag" in attrs) {
                    scope.isDrag = true;
                }

                if ("multiple" in attrs) {
                    elFileinput.attr("multiple", "multiple");
                    scope.isMutiple = true;
                } else {
                    elFileinput.removeAttr("multiple");
                }

                if ("showfilesize" in attrs) {
                    scope.isShowFileSize = true;
                }

                if ("showtotalsize" in attrs) {
                    scope.isShowTotalSize = true;
                }

                if (angular.isDefined(attrs.ngDisabled)) {
                    scope.$watch("ngDisabled", function (isDisabled) {
                        scope.isDisabled = isDisabled;
                    });
                }

                scope.strBrowseIconCls = "glyphicon glyphicon-folder-open";
                scope.strRemoveIconCls = "glyphicon glyphicon-trash";
                scope.strCaptionIconCls = "glyphicon glyphicon-file";
                scope.strUnknowIconCls = "glyphicon glyphicon-file";

                if (angular.isDefined(attrs.option)) {
                    if (angular.isObject(scope.option)) {
                        if (scope.option.hasOwnProperty("browseIconCls")) {
                            scope.strBrowseIconCls = scope.option.browseIconCls;
                        }
                        if (scope.option.hasOwnProperty("removeIconCls")) {
                            scope.strRemoveIconCls = scope.option.removeIconCls;
                        }
                        if (scope.option.hasOwnProperty("captionIconCls")) {
                            scope.strCaptionIconCls = scope.option.captionIconCls;
                        }
                        if (scope.option.hasOwnProperty("unknowIconCls")) {
                            scope.strUnknowIconCls = scope.option.unknowIconCls;
                        }
                    }
                }

                scope.accept = scope.accept || "";

                scope.api = new function () {
                    var self = this;
                    self.removeAll = function () {
                        scope.removeAllFiles();
                    };
                };

                scope.isFilesNull = true;
                scope.strCaption = "";
                scope.strCaptionFileName = "";
                scope.strCaptionFileNumbers = "";
                scope.strCaptionTotalSize = "";
                scope.strCaptionPlaceholder = scope.placeholder || "aframe.label_fileupload_placeholder";
                scope.strCaptionDragAndDrop = scope.dragAndDropLabel || "aframe.label_fileupload_draganddrop";
                scope.strCaptionBrowse = scope.browseLabel || "aframe.label_fileupload_browse";
                scope.strCaptionRemove = scope.removeLabel || "aframe.label_fileupload_remove";

                var readFile = function (file) {
                    readAsDataURL(file).then(
                        function (result) {
                            /*if (!ngModelCtrl.$valid) {
                                // remove from scope files
                                scope.files.every(function (obj, idx) {
                                    if (obj.fileName == file.name) {
                                        scope.files.splice(idx, 1);
                                        return false;
                                    }
                                    return true;
                                });

                                // update ng-model
                                var _files = [];
                                for (var i = 0; i < scope.files.length; i++) {
                                    _files.push(scope.files[i]);
                                }
                                ngModelCtrl.$setViewValue(_files);

                                return;
                            }*/

                            var lfFileName = file.name;
                            var lfFileType = file.type;
                            var lfFileSize = file.size;
                            var lfDataUrl = result.result;  // window.URL.createObjectURL(file)
                            var lfFile = {
                                "name": lfFileName,
                                "type": lfFileType,
                                "size": lfFileSize,
                                "content": lfDataUrl
                            };

                            if (scope.files === undefined || scope.files === null || scope.files.length === undefined || scope.files.length === 0/* || !scope.isMutiple*/) {
                                scope.files = [];
                            }
                            scope.files.push(lfFile);

                            var _files =[];
                            for (var i = 0; i < scope.files.length; i++) {
                                _files.push(scope.files[i]);
                            }

                            ngModelCtrl.$setViewValue(_files);

                            //showUploadFile(lfFileName, lfFileType, lfFileSize, lfDataUrl);

                            //updateTextCaption();

                        }, function (error) {

                        }, function (notify) {

                        }
                    );
                };

                var showUploadFile = function (lfFileName, lfFileType, lfFileSize, lfDataUrl) {
                    var elFrame = angular.element('<div class="file-input-frame"></div>');
                    var elFrameX = angular.element('<div class="file-input-remove"><a href="#" ng-click="removeFileByName(\'' + lfFileName + '\',$event)">&times;</a></div>');
                    var tplPreview = '';

                    var lfTagType = parseFileType(lfFileName, lfFileType);

                    if (lfTagType == 'image') {
                        tplPreview = '<img src="' + lfDataUrl + '" >';
                    }
                    else if (lfTagType == 'video') {
                        tplPreview = [
                            '<video controls>',
                            '   <source src="' + lfDataUrl + '" type="' + lfFileType + '">',
                            '</video>'].join('');
                    }
                    else if (lfTagType == 'audio') {
                        tplPreview = [
                            '<audio controls>',
                            '   <source src="' + lfDataUrl + '" type="' + lfFileType + '">',
                            '</audio>'].join('');
                    }
                    else {
                        tplPreview = [
                            '<object data="' + lfDataUrl + '" type="' + lfFileType + '"><param name="movie" value="' + lfFileName + '" />',
                            '   <div class="file-input-preview-default file-icon-4x">',
                            '       <i class="file-input-preview-icon" ng-class="strUnknowIconCls"></i>',
                            '   </div>',
                            '</object>'].join('');
                    }

                    var elPreview = angular.element(tplPreview);
                    var elFooter = angular.element(
                        '<div class="file-input-frame-footer">' +
                        '   <div class="file-input-frame-caption" title="' + lfFileName + '">' + lfFileName + '</div>' +
                        '   <div class="file-input-frame-size" ng-show="isShowFileSize">( ' + calcFileSize(lfFileSize) + ' )</div>' +
                        '</div>');

                    elFrame.append(elFrameX);
                    elFrame.append(elPreview);
                    elFrame.append(elFooter);

                    $compile(elFrame)(scope);

                    elThumbnails.append(elFrame);
                };

                var updateTextCaption = function () {
                    if (scope.files !== undefined && scope.files !== null && scope.files.length == 1) {
                        scope.strCaption = 'aframe.label_fileupload_selectedfile';
                        scope.strCaptionFileName = scope.files[0].name;

                        if (scope.isShowTotalSize) {
                            scope.strCaption = 'aframe.label_fileupload_selectedfile_withsize';
                            scope.strCaptionTotalSize = calcFileSize(scope.files[0].size);
                        }
                        scope.isFilesNull = false;
                    } else if (scope.files !== undefined && scope.files !== null && scope.files.length > 1) {
                        scope.strCaption = 'aframe.label_fileupload_selectedfiles';
                        scope.strCaptionFileNumbers = scope.files.length;

                        if (scope.isShowTotalSize) {
                            scope.strCaption = 'aframe.label_fileupload_selectedfiles_withsize';
                            var totalSize = 0;
                            for (var i = 0; i < scope.files.length; i ++) {
                                totalSize += scope.files[i].size
                            }
                            scope.strCaptionTotalSize = calcFileSize(totalSize);
                        }
                        scope.isFilesNull = false;
                    } else {
                        scope.strCaption = '';
                        scope.isFilesNull = true;
                    }
                };

                var parseFileType = function (lfFileName, lfFileType) {
                    if (isImageType(lfFileType, lfFileName)) {
                        return "image";
                    } else if (isVideoType(lfFileType, lfFileName)) {
                        return "video";
                    } else if (isAudioType(lfFileType, lfFileName)) {
                        return "audio";
                    }
                    return "object";
                };

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

                var isImageType = function (type, name) {
                    return (type.match('image.*') || name.match(/\.(gif|png|jpe?g)$/i)) ? true : false;
                };

                var isVideoType = function (type, name) {
                    return (type.match('video.*') || name.match(/\.(og?|mp4|webm|3gp)$/i)) ? true : false;
                };

                var isAudioType = function (type, name) {
                    return (type.match('audio.*') || name.match(/\.(ogg|mp3|wav)$/i)) ? true : false;
                };

                var readAsDataURL = function (file, index) {
                    var deferred = $q.defer();

                    var reader = new FileReader();
                    reader.onloadstart = function () {
                        deferred.notify(0);

                        /*if (!ngModelCtrl.$valid) {
                         reader.abort();
                         }*/

                        var onloadstart = '<div class="file-loading" />';
                        element.find('.file-progress').html(onloadstart);
                    };
                    reader.onload = function (event) {
                        /*var lfFile = file;
                        var lfFileName = file.name;
                        var lfFileType = file.type;
                        var lfTagType = parseFileType(file);
                        var lfDataUrl = window.URL.createObjectURL(file);

                        scope.files.push({
                            "file": lfFile,
                            "fileName": lfFileName,
                            "dataUrl": lfDataUrl
                        });

                        var _files = [];
                        for (var i = 0; i < scope.files.length; i++) {
                            _files.push(scope.files[i]);
                        }

                        ngModelCtrl.$setViewValue(_files);*/
                    };
                    reader.onloadend = function (event) {
                        deferred.resolve({
                            'index': index,
                            'result': reader.result
                        });

                        element.find('.file-progress').html('');
                        element.find('.file-progress').addClass('hide');
                    };
                    reader.onerror = function (event) {
                        deferred.reject(reader.result);
                    };
                    reader.onprogress = function (event) {
                        deferred.notify(event.loaded / event.total);

                        if (event.lengthComputable) {
                            element.find('.file-progress').removeClass('hide');

                            // show upload progress
                            var onprogressTemp =
                                '<p>' +
                                '   <strong>{filename}</strong>' +
                                '   <span class="pull-right">{percent}% Complete</span>' +
                                '</p>' +

                                '<div class="progress">' +
                                '   <div class="progress-bar" role="progressbar" ' +
                                '       aria-valuenow="{percent}" aria-valuemin="0" aria-valuemax="100" style="width: {percent}%">' +
                                '   </div>' +
                                '</div>';

                            var fact = (event.loaded / event.total) * 100, progress = Math.ceil(fact);
                            setTimeout(function () {
                                element.find('.file-progress').html(onprogressTemp.replace(/{percent}/g, progress).replace('{filename}', file.name));
                            }, 1000);
                        }
                    };
                    //reader.readAsArrayBuffer(file);
                    reader.readAsDataURL(file);         // base64

                    return deferred.promise;
                };

                /*if (scope.files !== undefined && scope.files !== null && scope.files.length > 0) {
                    angular.forEach(scope.files, function (value, key) {
                        showUploadFile(value.name, value.type, value.size, value.content);
                    });

                    updateTextCaption();
                }*/

                scope.$watch("files", function (newValue) {
                    if (newValue !== undefined && newValue !== null && newValue.length > 0) {
                        if (scope.files === undefined || scope.files === null || scope.files.length === 0) {
                            ngModelCtrl.$setViewValue(newValue);
                        }

                        // clear thumbnails
                        elThumbnails.empty();

                        angular.forEach(newValue, function (value, key) {
                            showUploadFile(value.name, value.type, value.size, value.content);
                        });

                        updateTextCaption();
                    }
                });

                elDragview.bind("dragover", function (e) {
                    e.stopPropagation();
                    e.preventDefault();
                    if (scope.isDisabled || !scope.isDrag) {
                        return;
                    }
                    elDragview.addClass("file-input-drag-hover");
                });

                elDragview.bind("dragleave", function (e) {
                    e.stopPropagation();
                    e.preventDefault();
                    if (scope.isDisabled || !scope.isDrag) {
                        return;
                    }
                    elDragview.removeClass("file-input-drag-hover");
                });

                elDragview.bind("drop", function (e) {
                    e.stopPropagation();
                    e.preventDefault();
                    if (scope.isDisabled || !scope.isDrag) {
                        return;
                    }

                    elDragview.removeClass("file-input-drag-hover");

                    var files = e.target.files || e.originalEvent.dataTransfer.files;
                    var i = 0;
                    var file;

                    if (files.length <= 0) {
                        return;
                    }

                    var names = scope.files.map(function (obj) {
                        return obj.name;
                    });

                    var regexp = new RegExp(scope.accept, "i");

                    if (scope.isMutiple) {
                        for (i = 0; i < files.length; i++) {
                            file = files[i];
                            if (file.type.match(regexp)) {
                                if (names.indexOf(file.name) == -1) {
                                    setTimeout(readFile(file), i * 100);
                                }
                            }
                        }
                    } else {
                        for (i = 0; i < files.length; i++) {
                            file = files[i];
                            if (file.type.match(regexp)) {
                                if (names.indexOf(file.name) == -1) {
                                    scope.removeAllFiles();
                                    setTimeout(readFile(file), i * 100);
                                    break;
                                }
                            }
                        }
                    }
                });

                scope.openDialog = function (event, el) {
                    if (navigator.userAgent.indexOf("Firefox") != -1) {
                        if (event) {
                            $timeout(function () {
                                event.preventDefault();
                                event.stopPropagation();
                                var elFileinput = event.target.children[2];
                                if (elFileinput !== undefined) {
                                    elFileinput.click();
                                }
                            }, 0);
                        }
                    }
                };

                scope.removeAllFiles = function (event) {
                    if (scope.isDisabled) {
                        return;
                    }
                    if (scope.files !== undefined && scope.files !== null &&  scope.files.length > 0) {
                        scope.files.length = 0;

                        var _files = [];
                        ngModelCtrl.$setViewValue(_files);
                    }
                    elThumbnails.empty();
                    updateTextCaption();
                };

                scope.removeFileByName = function (name, event) {
                    if (scope.isDisabled) {
                        return;
                    }

                    scope.files.every(function (obj, idx) {
                        if (obj.name == name) {
                            scope.files.splice(idx, 1);
                            return false;
                        }
                        return true;
                    });

                    if (event) {
                        angular.element(event.target).parent().parent().remove();
                    }

                    updateTextCaption();

                    var _files = [];
                    for (var i = 0; i < scope.files.length; i++) {
                        _files.push(scope.files[i]);
                    }

                    ngModelCtrl.$setViewValue(_files);
                };

                scope.onFileChanged = function (e) {
                    var names = [];
                    if (scope.files !== undefined && scope.files !== null && scope.files !== '' && scope.files.length !== undefined && scope.files.length > 0) {
                        names = scope.files.map(function (obj) {
                            return obj.name;
                        });
                    }

                    var files = e.files;
                    if (files.length <= 0) {
                        return;
                    }

                    if (scope.isMutiple) {
                        for (var i = 0; i < files.length; i++) {
                            var file = files[i];
                            var intAvail = 1;
                            if (names === null || names.indexOf(file.name) == -1) {
                                setTimeout(readFile(file), intAvail * 100);
                                intAvail++;
                            }
                        }
                    } else {
                        for (var i = 0; i < files.length; i++) {
                            var file = files[i];
                            if (names === null || names.indexOf(file.name) == -1) {
                                scope.removeAllFiles();
                                setTimeout(readFile(file), 100);
                                break;
                            }
                        }
                    }

                    elFileinput.val("");
                };
            }
        };
    }]);

// aframe component - [radio]
var ic_cmpt_radio = angular.module("aframe.component.radiolist", []);
ic_cmpt_radio
    .directive("icRadioList", function () {
        return {
            restrict: "E",
            require: ["ngModel"],
            controller: ["$scope", "$element", "$attrs", function($scope, $element, $attrs) {
                var ctrl = this, ngModel, ngModelCtrl;

                $scope.$evalAsync(function() {
                    ngModel = $attrs.ngModel;
                    ngModelCtrl = $element.controller("ngModel");

                    // update ngModel
                    ctrl.setViewValue = function (value) {
                        ngModelCtrl.$setViewValue(angular.copy(value));
                    };

                    // get ngModel value
                    ctrl.getViewValue = function () {
                        return ngModelCtrl.$viewValue;
                    };

                    // get ngModel object
                    ctrl.getNgModel = function () {
                        return ngModel;
                    }
                });
            }],
            controllerAs: "$icRadioListCtrl"
        };
    })
    .directive("icRadioListItem", ["$parse", "$compile" , function ($parse, $compile) {
        return {
            restrict: "A",
            require: ["^icRadioList", "^ngModel"],
            scope: true,
            link: function (scope, element, attrs, ctrls) {
                if (!attrs.itemValue && !attrs.value) {
                    throw "You should provide `item-value`.";
                }

                var $icRadioListCtrl = ctrls[0];
                var radioNgModel = $icRadioListCtrl.getNgModel();

                // attribute
                attrs.$set("icRadioListItem", null);

                // set radio button group
                if (attrs.name == undefined || attrs.name == null) {
                    element.attr("name", "radio");
                }

                // set ngModel
                if (attrs.ngModel == undefined) {
                    attrs.$set("ngModel", radioNgModel);
                }

                // set ngValue
                if (attrs.ngValue == undefined) {
                    attrs.$set("ngValue", attrs.itemValue);
                }

                // compile
                $compile(element)(scope);

                // radio click event
                /*element.bind("click", function (e) {
                 $icRadioListCtrl.setViewValue(getItemValue());
                 });

                 function getItemValue() {
                 return attrs.itemValue ? $parse(attrs.itemValue)(scope.$parent) : attrs.value;
                 }*/
            }
        };
    }]);

// aframe component - [checkbox]
var ic_cmpt_checkbox = angular.module("aframe.component.checkboxlist", []);
ic_cmpt_checkbox
    .directive("icCheckboxList", function () {
        return {
            restrict: "E",
            require: ["ngModel"],
            controller: ["$scope", "$element", "$attrs", function($scope, $element, $attrs) {
                var ctrl = this, ngModel, ngModelCtrl;

                $scope.$evalAsync(function() {
                    ngModel = $attrs.ngModel;
                    ngModelCtrl = $element.controller("ngModel");

                    // update ngModel
                    ctrl.setViewValue = function (value) {
                        ngModelCtrl.$setViewValue(angular.copy(value));
                    };

                    // get ngModel value
                    ctrl.getViewValue = function () {
                        return ngModelCtrl.$viewValue;
                    };

                    // get ngModel object
                    ctrl.getNgModel = function () {
                        return ngModel;
                    }
                });
            }],
            controllerAs: "$icCheckboxListCtrl"
        };
    })
    .directive("icCheckboxListItem", ["$parse", "$compile" , function ($parse, $compile) {
        return {
            restrict: "A",
            priority: 1000,
            terminal: true,
            require: ["^icCheckboxList", "^ngModel"],
            scope: true,
            compile: function (tElement, tAttrs) {
                if (!tAttrs.itemValue && !tAttrs.value) {
                    throw "You should provide `value` or `item-value`.";
                }

                // by default ngModel is "checked", so we set it if not specified
                if (!tAttrs.ngModel) {
                    // local scope var storing individual checkbox model
                    tAttrs.$set("ngModel", "checked");
                }

                return postLinkFn;
            }
        };

        // http://stackoverflow.com/a/19228302/1458162
        function postLinkFn(scope, elem, attrs, ctrls) {
            attrs.$set("icCheckboxListItem", null);
            // compile with `ng-model` pointing to `checked`
            $compile(elem)(scope);

            // getter for original model
            var ngModelGetter = $parse(attrs.ngModel);
            var $icCheckboxListCtrl = ctrls[0];
            var checkboxNgModel = $icCheckboxListCtrl.getNgModel();
            var comparator = angular.equals;

            // watch icCheckboxListItem checked change
            scope.$watch(attrs.ngModel, function (newValue, oldValue) {
                if (newValue === oldValue) {
                    return;
                }

                setValueInIcCheckboxListItem(getItemValue(), newValue);
            });

            // watches for value change of itemValue (Credit to @blingerson)
            scope.$watch(getItemValue, function (newValue, oldValue) {
                if (newValue != oldValue && angular.isDefined(oldValue) && scope[attrs.ngModel] === true) {
                    var current = getCheckboxValue();
                    $icCheckboxListCtrl.setViewValue(remove(current, oldValue, comparator));
                    $icCheckboxListCtrl.setViewValue(add(current, newValue, comparator));
                }
            });

            function getItemValue() {
                return attrs.itemValue ? $parse(attrs.itemValue)(scope.$parent) : attrs.value;
            }

            function getCheckboxValue() {
                return $icCheckboxListCtrl.getViewValue();
            }

            function setValueInIcCheckboxListItem(value, checked) {
                var current = getCheckboxValue();
                if (checked === true) {
                    $icCheckboxListCtrl.setViewValue(add(current, value, comparator));
                } else {
                    $icCheckboxListCtrl.setViewValue(remove(current, value, comparator));
                }
            }

            // declare one function to be used for both $watch functions
            function setChecked(newArr, oldArr) {
                ngModelGetter.assign(scope, contains(getCheckboxValue(), getItemValue(), comparator));
            }

            // watch original model change
            // use the faster $watchCollection method if it's available
            if (angular.isFunction(scope.$parent.$watchCollection)) {
                scope.$parent.$watchCollection(checkboxNgModel, setChecked);
            } else {
                scope.$parent.$watch(checkboxNgModel, setChecked, true);
            }
        }

        // contains
        function contains(arr, item, comparator) {
            if (angular.isArray(arr)) {
                for (var i = arr.length; i--;) {
                    if (comparator(arr[i], item)) {
                        return true;
                    }
                }
            }
            return false;
        }

        // add
        function add(arr, item, comparator) {
            arr = angular.isArray(arr) ? arr : [];
            if (!contains(arr, item, comparator)) {
                arr.push(item);
            }
            return arr;
        }

        // remove
        function remove(arr, item, comparator) {
            if (angular.isArray(arr)) {
                for (var i = arr.length; i--;) {
                    if (comparator(arr[i], item)) {
                        arr.splice(i, 1);
                        break;
                    }
                }
            }
            return arr;
        }
    }]);

// aframe component - [form]
var ic_cmpt_form = angular.module("aframe.component.form", []);
ic_cmpt_form
    .directive("icFormGroup", function () {
        return {
            restrict: "EA",
            template: '<div class="form-group" ng-transclude></div>',
            replace: true,
            transclude: true
        };
    });

// aframe component - [paging]
var ic_cmpt_paging = angular.module("aframe.component.paging", []);
ic_cmpt_paging
    .factory("paginator", function () {
        var Paginator = function (size) {
            this.size = (size === null || size === undefined) ? 10 : size;// how many records in single page
            this.page = 1; // current page index, start from 1
            this.count = 0; // total records returned by server
            this.max = 5; // how many pages should be display
        };

        Paginator.prototype.getPagerParams = function () {
            return {
                size: this.size,
                page: this.page
            };
        };

        Paginator.prototype.reset = function(){
            this.page = 1;
            this.count = 0;
        };

        return {
            create: function (size) {
                return new Paginator(size);
            }
        };
    })
    .factory("sorter", function () {
        var Sorter = function () {
            this.state = '';
        };

        Sorter.prototype.getSorterParams = function () {
            return {
                sort: this.state
            };
        };

        Sorter.prototype.reset = function () {
            this.state = '';
        };

        return {
            create: function () {
                return new Sorter();
            }
        };
    })
    .directive("icSorter", function () {
        return {
            restrict: "A",
            require: "ngModel",
            scope: {
                ngModel: "="
            },
            link: function postLink(scope, element, attrs, ctrl) {
                var sortKey = attrs.sortKey;
                var sortOrder = "";

                var setupClass = function () {
                    element.removeClass("sorting sorting_asc sorting_desc");

                    var model = scope.ngModel;
                    var modelSortKey = !ctrl.$isEmpty(model) ? model.split(",")[0] : "";
                    var modelSortOrder = !ctrl.$isEmpty(model) ? model.split(",")[1] : "";

                    if (modelSortKey === sortKey) {
                        switch (modelSortOrder) {
                            case "asc":
                                element.addClass("sorting_asc");
                                break;
                            case "desc":
                                element.addClass("sorting_desc");
                                break;
                            default:
                                element.addClass("sorting");
                                break;
                        }
                    }
                    else {
                        element.addClass("sorting");
                    }
                };
                setupClass();

                scope.$watch("ngModel", function () {
                    setupClass();
                });

                //attach events
                element.click(function () {
                    scope.$apply(function () {
                        var model = scope.ngModel;
                        var modelSortKey = !ctrl.$isEmpty(model) ? model.split(",")[0] : "";
                        var modelSortOrder = !ctrl.$isEmpty(model) ? model.split(",")[1] : "";

                        if (sortKey !== modelSortKey) {
                            sortOrder = "asc";
                        } else {
                            switch (modelSortOrder) {
                                case "asc":
                                    sortOrder = "desc";
                                    break;
                                case "desc":
                                    sortOrder = "asc";
                                    break;
                                default :
                                    sortOrder = "asc";
                                    break;
                            }
                        }
                        var sort = sortKey + "," + sortOrder;
                        ctrl.$setViewValue(sort);
                    });
                });
            }
        };
    });

/**
 * aframe component - [required mark]
 */
var ic_requiredmark = angular.module('aframe.component.requiredmark', []);
ic_requiredmark
    .provider('$icRequiredMark', function () {
        var $$options = {
            defaults: {
                mark: 'aframe.label_required_mark',
                color: 'red'
            }
        };

        this.setOptions = function(options) {
            angular.extend($$options, options);
        };

        this.$get = function() {
            return $$options;
        };
    })
    .directive('icRequiredMark', ['$icRequiredMark', '$compile', function ($icRequiredMark, $compile) {
        return {
            restrict: 'EA',
            replace: true,
            template: "<label></label>",
            link: function (scope, element, attrs, ctrl) {
                var mark = $icRequiredMark.defaults.mark;
                var color = $icRequiredMark.defaults.color;

                element.css('color', color);
                element.attr('i18n', mark);

                $compile(element)(scope);
            }
        };
    }]);

/**
 * aframe component - [legend]
 */
var ic_formlegend = angular.module('aframe.component.formlegend', []);
ic_formlegend
    .provider('$icFormLegend', function () {
        var $$options = {
            defaults: {
                note: 'aframe.label_form_legend',
                color: 'red'
            }
        };

        this.setOptions = function(options) {
            angular.extend($$options, options);
        };

        this.$get = function() {
            return $$options;
        };
    })
    .directive('icFormLegend', ['$icFormLegend', '$compile', function ($icFormLegend, $compile) {
        return {
            restrict: 'EA',
            replace: true,
            template: "<span class='help-block'></span>",
            link: function (scope, element, attrs, ctrl) {
                var note = $icFormLegend.defaults.note;
                var color = $icFormLegend.defaults.color;

                element.css('color', color);
                element.attr('i18n', note);

                $compile(element)(scope);
            }
        };
    }]);

/**
 * aframe component - [input clear]
 */
var ic_inputclear = angular.module('aframe.component.inputclear', []);
ic_inputclear.directive('icInputClear', ['$compile',
    function ($compile) {
        var inputTypes = /^(text|search|tel|mobile|url|email|password)$/i;
        return {
            restrict: 'A',
            require: 'ngModel',
            scope: {
                model: "=ngModel"
            },
            link: function (scope, element, attrs, ctrl) {
                var parent = $(element).parent();

                var inputElem = element[0];
                if (inputElem.nodeName.toUpperCase() !== "INPUT") {
                    // reserved to <input> elements
                    throw new Error('Directive ice-input-clear is reserved to input elements');
                } else if(!inputTypes.test(attrs.type)) {
                    // with a correct "type" attribute
                    throw new Error("Invalid input type for ice-input-clear: " + attrs.type);
                }

                // initialized to false so the clear icon is hidden (see the ng-show directive)
                scope.clearable = false;

                // more "testable" when exposed in the scope...
                scope.clearInput = clearInput;

                // watch ngModel checked change
                scope.$watch("model", function (newValue, oldValue) {
                    scope.clearable = ctrl.$isEmpty(newValue) ? false : true;
                });

                // build and insert the "clear icon"
                var clearTpl = '<span class="glyphicon glyphicon-remove" ng-show="clearable"></span>';
                var clearElem = $compile(clearTpl)(scope);
                if (!parent.hasClass("input-group")) {
                    clearElem.attr('id', 'input-clear');
                } else {
                    clearElem.attr('id', 'input-group-clear');
                }
                element.after(clearElem);

                // make it clear the <input> on click
                clearElem.bind('click', clearInput);

                // if the user types something: show the "clear icon" if <input> is not empty
                element.bind('input', showIconIfInputNotEmpty);

                // if the user focuses the input: show the "clear icon" if it is not empty
                element.bind('focus', showIconIfInputNotEmpty);

                // if the <input> loses the focus: hide the "clear icon"
                //element.bind('blur', hideIcon);

                function clearInput() {
                    ctrl.$setViewValue('');
                    // rendering the updated viewValue also updates the value of the bound model
                    ctrl.$render();
                    // hide the "clear icon" as it is not necessary anymore
                    scope.clearable = false;
                    // as user may want to types again, put the focus back on the <input>
                    inputElem.focus();
                }

                function showIconIfInputNotEmpty() {
                    setContainerClearable(!ctrl.$isEmpty(scope.model));
                }

                /*function hideIcon() {
                 setContainerClearable(false);
                 }*/

                function setContainerClearable(newValue){
                    scope.clearable = newValue;
                    scope.$apply();
                }
            }
        };
    }
]);

/**
 * aframe component - [i18n]
 */
var ic_i18n = angular.module('aframe.component.i18n', []);
ic_i18n
    .filter('icI18n', ['locale', 'localeConf', function (locale, localeConf) {
        var i18nFilter = function (input, args) {
            input = localeConf.sharedDictionary + '.' + input;
            return locale.getString(input, args);
        };

        i18nFilter.$stateful = true;

        return i18nFilter;
    }])
    .filter('icCodeI18n', ['locale', 'localeConf', function(locale, localeConf) {
        return function (input, args) {
            var result = '';
            if(input === null || input === undefined) {
                result = input;
            } else {
                result = input[locale.getLocale()];
                if(result === null || result === undefined) {
                    result = input[localeConf.defaultLocale];
                    if(result === null || result === undefined) {
                        result = input;
                    }
                }
            }
            return result;
        };
    }])
    .directive('icI18n', ['$sce', 'locale', 'localeEvents', 'localeConf',
        function ($sce, locale, localeEvents, localeConf) {
            function setText(elm, tag) {
                if (tag !== elm.html()) {
                    elm.html($sce.getTrustedHtml(tag));
                }
            }

            function update(elm, string, optArgs) {
                var string2 = localeConf.sharedDictionary + '.' + string;
                if (locale.isToken(string2)) {
                    locale.ready(locale.getPath(string2)).then(function () {
                        setText(elm, locale.getString(string2, optArgs));
                    });
                } else {
                    setText(elm, string);
                }
            }

            return function (scope, elm, attrs) {
                var hasObservers;

                attrs.$observe('icI18n', function (newVal, oldVal) {
                    if (newVal && newVal != oldVal) {
                        update(elm, newVal, hasObservers);
                    }
                });

                angular.forEach(attrs.$attr, function (attr, normAttr) {
                    if (localeConf.observableAttrs.test(attr)) {
                        attrs.$observe(normAttr, function (newVal, oldVal) {
                            if ((newVal && newVal != oldVal) || !hasObservers || !hasObservers[normAttr]) {
                                hasObservers = hasObservers || {};
                                hasObservers[normAttr] = attrs[normAttr];
                                update(elm, attrs.icI18n, hasObservers);
                            }
                        });
                    }
                });

                scope.$on(localeEvents.resourceUpdates, function () {
                    update(elm, attrs.icI18n, hasObservers);
                });
                scope.$on(localeEvents.localeChanges, function () {
                    update(elm, attrs.icI18n, hasObservers);
                });
            };
        }
    ])
    .directive('icI18nAttr', ['locale', 'localeEvents', 'localeConf',
        function (locale, localeEvents, localeConf) {
            return function (scope, elem, attrs) {
                var lastValues = {};

                function updateText(target, attributes) {
                    var values = scope.$eval(attributes),
                        langFiles = [],
                        exp;

                    for(var key in values) {
                        exp = localeConf.sharedDictionary + '.' + values[key];
                        if (locale.isToken(exp) && langFiles.indexOf(locale.getPath(exp)) == -1) {
                            langFiles.push(locale.getPath(exp));
                        }
                    }

                    locale.ready(langFiles).then(function () {
                        var value = '';

                        for(var key in values) {
                            exp = localeConf.sharedDictionary + '.' + values[key];
                            value = locale.getString(exp);
                            if (lastValues[key] !== value) {
                                attrs.$set(key, lastValues[key] = value);
                            }
                        }
                    });
                }

                attrs.$observe('icI18nAttr', function (newVal, oldVal) {
                    if (newVal && newVal != oldVal) {
                        updateText(elem, newVal);
                    }
                });

                scope.$on(localeEvents.resourceUpdates, function () {
                    updateText(elem, attrs.icI18nAttr);
                });
                scope.$on(localeEvents.localeChanges, function () {
                    updateText(elem, attrs.icI18nAttr);
                });
            };
        }
    ]);

/**
 * TODO aframe component - [updown]
 */
var ic_updown = angular.module('aframe.component.updown', []);
ic_updown.directive("icUpDown", function () {
    return {
        restrict: "E",
        require: "ngModel",
        scope: {
            list: "=ngModel",
            record: "="
        },
        link: function postLink(scope, element, attrs, ctrl) {
            var move = attrs.move;
            var index = scope.list.indexOf(scope.record);

            if (index === 0 && move === "up") {
                // TODO
            }

            element.click(function () {
                console.log(index);
            });
        }
    };
});

/**
 * aframe component - [topmenu]
 */
var ic_topmenu = angular.module('aframe.component.topmenu', []);
ic_topmenu.directive('icTopMenu', ['$window', function ($window) {
    return {
        restrict: 'E',
        replace: true,
        templateUrl: "ic/template/topmenu/topmenu.html",
        scope: {
            menus: '='
        },
        link: function (scope, element, attrs, ctrl) {
            scope.currentMenu = {};
            scope.currentModeIsMobile = getViewPort().width < getResponsiveBreakpoint('sm');

            if (getViewPort().width < getResponsiveBreakpoint('sm')) {
                $(".hor-menu").addClass("collapse");
            }

            angular.element($window).bind('load resize', function () {
                if (getViewPort().width >= getResponsiveBreakpoint('sm')) {
                    $(".hor-menu").removeClass("collapse");

                    $('.hor-menu .ic5Layer1').not('.hover-initialized').each(function () {
                        $(this).dropdownHover();
                        $(this).addClass('hover-initialized');
                    });
                    $('.hor-menu .navbar-nav li.open').removeClass('open');
                    $('.page-header-menu').css('display', 'block');

                    scope.currentMenu = {};
                    scope.currentModeIsMobile = false;
                } else if (getViewPort().width < getResponsiveBreakpoint('sm')) {
                    $(".hor-menu").addClass("collapse");

                    $('.hor-menu .ic5Layer1.hover-initialized').each(function () {
                        $(this).unbind('mouseenter').unbind('mouseleave');
                        $(this).parent().unbind('mouseenter').unbind('mouseleave').find('.dropdown-submenu').each(function () {
                            $(this).unbind('mouseenter').unbind('mouseleave');
                        });
                        $(this).removeClass('hover-initialized');
                    });

                    scope.currentMenu = {};
                    scope.currentModeIsMobile = true;
                }
            });

            scope.layerClick = function (newLayer, newMenu, isCollapseMenu) {
                if (scope.currentModeIsMobile && isCollapseMenu) {
                    $(".navbar-toggle").click();
                }

                var oldMenu = scope.currentMenu[newLayer];

                angular.forEach(scope.currentMenu, function (menu, layer) {
                    if (layer >= newLayer) {
                        scope.currentMenu[layer] = '';
                    }
                });

                if (scope.currentModeIsMobile && oldMenu !== newMenu) {
                    scope.currentMenu[newLayer] = newMenu;
                }
            };
        }
    };
}]);

ic_topmenu.directive('icTopMenuRepeatHover', function() {
    return {
        restrict: 'A',
        link: function (scope, element, attr) {
            if (getViewPort().width >= getResponsiveBreakpoint('sm')) {
                element.dropdownHover();
                element.addClass('hover-initialized');
            }
        }
    };
});

/**
 * aframe component - [treemenu]
 */
var ic_treemenu = angular.module('aframe.component.treemenu', []);
ic_topmenu.directive('icTreeMenu', function () {
    return {
        restrict: 'E',
        replace: true,
        templateUrl: "ic/template/treemenu/treemenu.html",
        scope: {
            menus: '='
        },
        link: function (scope, element, attrs, ctrl) {

        }
    };
});

ic_treemenu.directive('icTreeMenuRepeat', ['$timeout', function($timeout) {
    return {
        restrict: 'A',
        link: function (scope, element, attr) {
            if(scope.$last) {
                $timeout(function(){ // run after DOM has been manipulated by Angular
                    $('.metisFolder').metisMenu({
                        toggle: false
                    });
                });
            }
        }
    };
}]);

// aframe component - [datetimepicker]
var ic_cmpt_datetimepicker = angular.module("aframe.component.datetimepicker", []);
ic_cmpt_datetimepicker
    .directive("icDatetimePicker", ["$icDatetimePicker", "locale", "localeEvents", "$timeout", function($icDatetimePicker, locale, localeEvents, $timeout) {
        return {
            templateUrl: "ic/template/datetimepicker/datetimepicker.html",
            restrict: "E",
            replace: true,
            require: "ngModel",
            scope: {
                model: "=ngModel"
            },
            link: function (scope, element, attrs, ctrl) {
                // prepare option
                setDateTimePickerLangRes();

                var allKeys = _.allKeys($icDatetimePicker.defaults);
                var options = _.pick(attrs, function (value, key) {
                    return _.contains(allKeys, key);
                });

                angular.forEach(options, function (value, key) {
                    if (value === "true" || value === "false") {
                        options[key] = value == "true";
                    }
                });

                if (options.locale) {
                    options.locale = moment.locale(options.locale);
                } else {
                    options.locale = moment.locale(locale.getLocale());
                }

                options = _.defaults(options, $icDatetimePicker.defaults);

                element.datetimepicker(options);

                // support readonly
                if (attrs.readonly === '') {
                    element.find("input").attr("readonly", true);
                }

                // allow click input to open datepicker
                element.find("input").click(function () {
                    element.data('DateTimePicker').show();
                });

                element.on("dp.change", function (e) {
                    $timeout(function () {
                        scope.$apply(function () {
                            var datetime = e.date;
                            if (datetime) {
                                var format12to24 = attrs.format.replace(/(| )(a|A)/g, '').replace(/h/g, 'H');
                                datetime = moment(e.date).format(format12to24);
                            } else {
                                datetime = null;
                            }
                            ctrl.$setViewValue(datetime);
                        });
                    });
                });

                scope.$on(localeEvents.localeChanges, function(event, data) {
                    element.data('DateTimePicker').locale(moment.locale(data));
                });

                function setPickerValue() {
                    var datetime = options.defaultDate || null;

                    if (ctrl && ctrl.$viewValue) {
                        datetime = scope.model;
                    }

                    element.data('DateTimePicker').date(datetime);
                }

                if (ctrl) {
                    ctrl.$render = function() {
                        setPickerValue();
                    };
                }

                setPickerValue();
            }
        };
    }])
    .directive("icDatetimePickerWithoutAddon", ["$icDatetimePicker", "locale", "localeEvents", "$timeout", function($icDatetimePicker, locale, localeEvents, $timeout) {
        return {
            template: '<input type="text" class="form-control" id="withoutAddon">',
            restrict: "E",
            replace: true,
            require: "ngModel",
            scope: {
                model: "=ngModel"
            },
            link: function (scope, element, attrs, ctrl) {
                // prepare option
                setDateTimePickerLangRes();

                var allKeys = _.allKeys($icDatetimePicker.defaults);
                var options = _.pick(attrs, function (value, key) {
                    return _.contains(allKeys, key);
                });

                angular.forEach(options, function (value, key) {
                    if (value === "true" || value === "false") {
                        options[key] = value == "true";
                    }
                });

                if (options.locale) {
                    options.locale = moment.locale(options.locale);
                } else {
                    options.locale = moment.locale(locale.getLocale());
                }

                options = _.defaults(options, $icDatetimePicker.defaults);

                element.datetimepicker(options);

                // support readonly
                if (attrs.readonly === '') {
                    element.find("input").attr("readonly", true);
                }

                // allow click input to open datepicker
                element.find("input").click(function () {
                    element.data('DateTimePicker').show();
                });

                element.on("dp.change", function (e) {
                    $timeout(function () {
                        scope.$apply(function () {
                            var datetime = e.date;
                            if (datetime) {
                                var format12to24 = attrs.format.replace(/(| )(a|A)/g, '').replace(/h/g, 'H');
                                datetime = moment(e.date).format(format12to24);
                            } else {
                                datetime = null;
                            }
                            ctrl.$setViewValue(datetime);
                        });
                    });
                });

                scope.$on(localeEvents.localeChanges, function(event, data) {
                    element.data('DateTimePicker').locale(moment.locale(data));
                });

                function setPickerValue() {
                    var datetime = options.defaultDate || null;

                    if (ctrl && ctrl.$viewValue) {
                        datetime = scope.model;
                    }

                    element.data('DateTimePicker').date(datetime);
                }

                if (ctrl) {
                    ctrl.$render = function() {
                        setPickerValue();
                    };
                }

                setPickerValue();
            }
        };
    }])
    .provider("$icDatetimePicker", function () {
        var provider = {
            defaults: {
                format: false,
                dayViewHeaderFormat: 'MMMM YYYY',
                extraFormats: false,
                stepping: 1,
                minDate: false,
                maxDate: false,
                useCurrent: true,
                collapse: true,
                locale: moment.locale(),
                defaultDate: false,
                disabledDates: false,
                enabledDates: false,
                icons: {
                    time: 'glyphicon glyphicon-time',
                    date: 'glyphicon glyphicon-calendar',
                    up: 'glyphicon glyphicon-chevron-up',
                    down: 'glyphicon glyphicon-chevron-down',
                    previous: 'glyphicon glyphicon-chevron-left',
                    next: 'glyphicon glyphicon-chevron-right',
                    today: 'glyphicon glyphicon-screenshot',
                    clear: 'glyphicon glyphicon-trash',
                    close: 'glyphicon glyphicon-remove'
                },
                tooltips: {
                    today: 'Go to today',
                    clear: 'Clear selection',
                    close: 'Close the picker',
                    selectMonth: 'Select Month',
                    prevMonth: 'Previous Month',
                    nextMonth: 'Next Month',
                    selectYear: 'Select Year',
                    prevYear: 'Previous Year',
                    nextYear: 'Next Year',
                    selectDecade: 'Select Decade',
                    prevDecade: 'Previous Decade',
                    nextDecade: 'Next Decade',
                    prevCentury: 'Previous Century',
                    nextCentury: 'Next Century'
                },
                useStrict: false,
                sideBySide: false,
                daysOfWeekDisabled: false,
                calendarWeeks: false,
                viewMode: 'days',
                toolbarPlacement: 'default',
                showTodayButton: false,
                showClear: false,
                showClose: false,
                widgetPositioning: {
                    horizontal: 'auto',
                    vertical: 'auto'
                },
                widgetParent: null,
                ignoreReadonly: false,
                keepOpen: false,
                focusOnShow: true,
                inline: false,
                keepInvalid: false,
                datepickerInput: '.datepickerinput',
                keyBinds: {
                    up: function (widget) {
                        if (!widget) {
                            return;
                        }
                        var d = this.date() || moment();
                        if (widget.find('.datepicker').is(':visible')) {
                            this.date(d.clone().subtract(7, 'd'));
                        } else {
                            this.date(d.clone().add(this.stepping(), 'm'));
                        }
                    },
                    down: function (widget) {
                        if (!widget) {
                            this.show();
                            return;
                        }
                        var d = this.date() || moment();
                        if (widget.find('.datepicker').is(':visible')) {
                            this.date(d.clone().add(7, 'd'));
                        } else {
                            this.date(d.clone().subtract(this.stepping(), 'm'));
                        }
                    },
                    'control up': function (widget) {
                        if (!widget) {
                            return;
                        }
                        var d = this.date() || moment();
                        if (widget.find('.datepicker').is(':visible')) {
                            this.date(d.clone().subtract(1, 'y'));
                        } else {
                            this.date(d.clone().add(1, 'h'));
                        }
                    },
                    'control down': function (widget) {
                        if (!widget) {
                            return;
                        }
                        var d = this.date() || moment();
                        if (widget.find('.datepicker').is(':visible')) {
                            this.date(d.clone().add(1, 'y'));
                        } else {
                            this.date(d.clone().subtract(1, 'h'));
                        }
                    },
                    left: function (widget) {
                        if (!widget) {
                            return;
                        }
                        var d = this.date() || moment();
                        if (widget.find('.datepicker').is(':visible')) {
                            this.date(d.clone().subtract(1, 'd'));
                        }
                    },
                    right: function (widget) {
                        if (!widget) {
                            return;
                        }
                        var d = this.date() || moment();
                        if (widget.find('.datepicker').is(':visible')) {
                            this.date(d.clone().add(1, 'd'));
                        }
                    },
                    pageUp: function (widget) {
                        if (!widget) {
                            return;
                        }
                        var d = this.date() || moment();
                        if (widget.find('.datepicker').is(':visible')) {
                            this.date(d.clone().subtract(1, 'M'));
                        }
                    },
                    pageDown: function (widget) {
                        if (!widget) {
                            return;
                        }
                        var d = this.date() || moment();
                        if (widget.find('.datepicker').is(':visible')) {
                            this.date(d.clone().add(1, 'M'));
                        }
                    },
                    enter: function () {
                        this.hide();
                    },
                    escape: function () {
                        this.hide();
                    },
                    //tab: function (widget) { //this break the flow of the form. disabling for now
                    //    var toggle = widget.find('.picker-switch a[data-action="togglePicker"]');
                    //    if(toggle.length > 0) toggle.click();
                    //},
                    'control space': function (widget) {
                        if (widget.find('.timepicker').is(':visible')) {
                            widget.find('.btn[data-action="togglePeriod"]').click();
                        }
                    },
                    t: function () {
                        this.date(moment());
                    },
                    'delete': function () {
                        this.clear();
                    }
                },
                debug: false,
                allowInputToggle: false,
                disabledTimeIntervals: false,
                disabledHours: false,
                enabledHours: false,
                viewDate: false
            }
        };
        // Method for instantiating
        this.$get = function () {
            return provider;
        };
    });

function setDateTimePickerLangRes() {
    !function (a) {
        // chinese
        a.fn.datepicker.dates["zh-CN"] = {
            days: ["\u661f\u671f\u65e5", "\u661f\u671f\u4e00", "\u661f\u671f\u4e8c", "\u661f\u671f\u4e09", "\u661f\u671f\u56db", "\u661f\u671f\u4e94", "\u661f\u671f\u516d"],
            daysShort: ["\u5468\u65e5", "\u5468\u4e00", "\u5468\u4e8c", "\u5468\u4e09", "\u5468\u56db", "\u5468\u4e94", "\u5468\u516d"],
            daysMin: ["\u65e5", "\u4e00", "\u4e8c", "\u4e09", "\u56db", "\u4e94", "\u516d"],
            months: ["\u4e00\u6708", "\u4e8c\u6708", "\u4e09\u6708", "\u56db\u6708", "\u4e94\u6708", "\u516d\u6708", "\u4e03\u6708", "\u516b\u6708", "\u4e5d\u6708", "\u5341\u6708", "\u5341\u4e00\u6708", "\u5341\u4e8c\u6708"],
            monthsShort: ["1\u6708", "2\u6708", "3\u6708", "4\u6708", "5\u6708", "6\u6708", "7\u6708", "8\u6708", "9\u6708", "10\u6708", "11\u6708", "12\u6708"],
            today: "\u4eca\u65e5",
            clear: "\u6e05\u9664",
            format: "yyyy\u5e74mm\u6708dd\u65e5",
            titleFormat: "yyyy\u5e74mm\u6708",
            weekStart: 1
        }
    }(jQuery);
}
