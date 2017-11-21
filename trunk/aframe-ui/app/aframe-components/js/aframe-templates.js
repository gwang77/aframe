'use strict';

/**
 * aframe component templates.
 *
 * @Arthur zechuan@ncsi.com.cn
 * @CreateDt 3/10/2016 9:00AM
 * @UpdateDt 3/28/2016 4:27PM
 *
 *@Arthur zechuan@ncsi.com.cn
 * @CreateDt 3/10/2016 9:00AM
 * @UpdateDt 3/28/2016 4:27PM
 */
angular.module("aframe.templates", [
    "ic/template/stats/stats.html",
    "ic/template/sidebar/sidebar.html",
    "ic/template/timeline/timeline.html",
    "ic/template/picklist/picklist.html",
    "ic/template/fileupload/fileupload.html",
    "ic/template/breadcrumb.html",
    "ic/template/topmenu/topmenu.html",
    "ic/template/treemenu/treemenu.html",
    "ic/template/datetimepicker/datetimepicker.html"
]);

// aframe component template - [stats]
var ic_tpl_stats = angular.module("ic/template/stats/stats.html", []);
ic_tpl_stats
    .run(["$templateCache", function($templateCache) {
        $templateCache.put("ic/template/stats/stats.html",
            '<div class="col-lg-3 col-md-6">'+
            '    <div class="panel panel-{{colour}}">' +
            '       <div class="panel-heading">' +
            '           <div class="row">' +
            '               <div class="col-xs-3">' +
            '                   <i class="fa fa-{{type}} fa-5x"></i>'+
            '               </div>' +
            '               <div class="col-xs-9 text-right">' +
            '                   <div class="huge">{{number}}</div>' +
            '                   <div>{{comments}}</div>' +
            '               </div>'+
            '           </div>'+
            '       </div>' +
            '       <a ui-sref="{{goto}}">' +
            '           <div class="panel-footer">' +
            '               <span class="pull-left">View Details</span>' +
            '               <span class="pull-right"><i class="fa fa-arrow-circle-right"></i></span>' +
            '               <div class="clearfix"></div>' +
            '           </div>' +
            '       </a>' +
            '   </div>' +
            '</div>'
        );
    }]);

// aframe component template - [sidebar]
var ic_tpl_sidebar = angular.module("ic/template/sidebar/sidebar.html", []);
ic_tpl_sidebar
    .run(['$templateCache', function($templateCache) {
        $templateCache.put("ic/template/sidebar/sidebar.html",
            '<div class="navbar-default sidebar" role="navigation">' +
            '   <div class="sidebar-nav navbar-collapse">' +
            '       <ul class="nav in" id="side-menu">' +
            '           <!-- First Level -->' +
            '           <li ng-repeat="menu in menus" ui-sref-active="active">' +
            '               <a ui-sref="{{menu.url}}" ng-click="rootLayerClick(); autoCollapseMenu()" ng-if="!menu.submenus">' +
            '                   <i class="{{menu.icon}}"></i>{{menu.name|i18n}}' +
            '               </a>' +
            '               <a href="javascript:;" ng-click="secondLayerClick(\'{{menu.id}}\');  scrollTop()" ng-if="menu.submenus" ng-class="{active: collapseVar==menu.id}">' +
            '                   <i class="{{menu.icon}}"></i>{{menu.name|i18n}}<span class="fa arrow"></span>' +
            '               </a>' +
            '               <!-- Second Level -->' +
            '               <ul class="nav nav-second-level" uib-collapse=\"collapseVar!={{menu.id}}\" ng-if="menu.submenus" ng-class="{active: collapseVar==menu.id}">' +
            '                   <li ng-repeat="secmenu in menu.submenus" ui-sref-active="active">' +
            '                       <a ui-sref="{{secmenu.url}}" ng-click="thirdLayerClick(\'{{secmenu.id}}\'); autoCollapseMenu()" ng-if="!secmenu.submenus">' +
            '                           <i ng-if="secmenu.icon" class="{{secmenu.icon}}"></i>{{secmenu.name|i18n}}' +
            '                       </a>' +
            '                       <a href="javascript:;" ng-click="thirdLayerClick(\'{{secmenu.id}}\');  scrollTop()" ng-if="secmenu.submenus" ng-class="{active: multiCollapseVar==secmenu.id}">' +
            '                           <i ng-if="secmenu.icon" class="{{secmenu.icon}}"></i>{{secmenu.name|i18n}}<span class="fa arrow"></span>' +
            '                       </a>' +
            '                       <!-- Third Level -->' +
            '                       <ul class="nav nav-third-level" uib-collapse="multiCollapseVar!={{secmenu.id}}" ng-if="secmenu.submenus" ng-class="{active: multiCollapseVar==secmenu.id}">' +
            '                           <li ng-repeat="thirdmenu in secmenu.submenus" ui-sref-active="active">' +
            '                               <a ui-sref="{{thirdmenu.url}}" ng-click="autoCollapseMenu(); scrollTop()">' +
            '                                   <i ng-if="thirdmenu.icon" class="{{thirdmenu.icon}}"></i>{{thirdmenu.name|i18n}}' +
            '                               </a>' +
            '                           </li>' +
            '                       </ul>' +
            '                   </li>' +
            '               </ul>' +
            '           </li>' +
            '       </ul>' +
            '   </div>' +
            '</div>'
        );
    }]);

// aframe component template - [timeline]
var ic_tpl_timeline = angular.module("ic/template/timeline/timeline.html", []);
ic_tpl_timeline
    .run(['$templateCache', function($templateCache) {
        $templateCache.put("ic/template/timeline/timeline.html",
            '<ul class="timeline">' +
            '   <li ng-repeat="timeline in model" class="{{timeline.inverted}}">' +
            '       <div ng-show="timeline.hasBadge" class="timeline-badge {{timeline.badgeColor}}">' +
            '           <i class="fa fa-{{timeline.badgeFa}}"></i>' +
            '       </div>' +
            '       <div class="timeline-panel">' +
            '           <div class="timeline-heading">' +
            '               <h4 class="timeline-title">{{timeline.title}}</h4>' +
            '               <p ng-show="timeline.clock">' +
            '                   <small class="text-muted"><i class="fa fa-clock-o"></i> {{timeline.clock}}</small>' +
            '               </p>' +
            '           </div>' +
            '           <div class="timeline-body">' +
            '               {{timeline.body}}' +
            '               <hr ng-show="timeline.hasBtnGroup"/>' +
            '               <div ng-show="timeline.hasBtnGroup" class="btn-group">' +
            '                   <button type="button" class="btn btn-primary btn-sm dropdown-toggle" data-toggle="dropdown">' +
            '                       <i class="fa fa-gear"></i> <span class="caret"></span>' +
            '                   </button>' +
            '                   <ul class="dropdown-menu" role="menu">' +
            '                       <li ng-repeat="dropdownMenu in timeline.dropdownMenus">' +
            '                           <a ui-sref="{{dropdownMenu.link}}">{{dropdownMenu.title}}</a>' +
            '                       </li>' +
            '                   </ul>' +
            '               </div>' +
            '           </div>' +
            '       </div>' +
            '   </li>' +
            '</ul>'
        );
    }]);

// aframe component template - [picklist]
var ic_tpl_picklist = angular.module("ic/template/picklist/picklist.html", []);
ic_tpl_picklist
    .run(["$templateCache", function($templateCache) {
        $templateCache.put("ic/template/picklist/picklist.html",
            '<div class="row">' +
            '   <div class="col-md-5 col-xs-5">' +
            '       <div class="row">' +
            '           <span i18n="aframe.msg_data_picker_assigned"/>' +
            '       </div>' +
            '       <div class="row">' +
            '           <select class="form-control picklist-select" ng-dblclick="remove()" ng-model="removedItems" multiple>' +
            '               <option class="{{item.value}}" ng-repeat="item in activeItems" value="{{item}}">{{item.label}}</option>' +
            '           </select>' +
            '       </div>' +
            '   </div>' +
            '   <div class="col-md-2 col-xs-2 text-center picklist-center-actions">' +
            '           <input ng-disabled="items.length == 0" class="btn btn-primary picklist-action-btn" type="button" value="&nbsp;<&nbsp;" ng-click="add()" /> <br/>' +
            '           <input ng-disabled="activeItems.length == 0" class="btn btn-danger picklist-action-btn" type="button" value="&nbsp;>&nbsp;" ng-click="remove()" /> <br/>' +
            '           <input ng-disabled="items.length == 0" class="btn btn-primary picklist-action-btn" type="button" value="<<" ng-click="addAll()" /> <br/>' +
            '           <input ng-disabled="activeItems.length == 0" class="btn btn-danger picklist-action-btn" type="button" value=">>" ng-click="removeAll()" /> <br/>' +
            '   </div>' +
            '   <div class="col-md-5 col-xs-5">' +
            '       <div class="row">' +
            '           <span i18n="aframe.msg_data_picker_unassigned"/>' +
            '       </div>' +
            '       <div class="row">' +
            '           <select class="form-control picklist-select" ng-dblclick="add()" ng-model="selectedItems" multiple>' +
            '               <option class="{{item.value}}" ng-repeat="item in items" value="{{item}}">{{item.label}}</option>' +
            '           </select>' +
            '       </div>' +
            '   </div>' +
            '</div>'
        );
    }]);

// aframe component template - [fileupload]
var ic_tpl_fileupload = angular.module("ic/template/fileupload/fileupload.html", []);
ic_tpl_fileupload
    .run(["$templateCache", function($templateCache) {
        $templateCache.put("ic/template/fileupload/fileupload.html",
            '<div class="file-input">' +
            '   <div class="file-preview" ng-class="{\'disabled\':isDisabled}" ng-show="isDrag || (isPreview && !isFilesNull)">' +
            '       <div class="file-input-remove" ng-hide="isFilesNull"><a href="#" ng-click="removeAllFiles($event)">&times;</a></div>' +
            '       <div class="file-input-drag">' +
            '           <div class="file-input-drag-text-container" ng-show="isFilesNull && isDrag">' +
            '               <div class="file-input-drag-text" i18n="{{strCaptionDragAndDrop}}"></div>' +
            '           </div>' +
            '           <div class="file-input-thumbnails"></div>' +
            '           <div class="clearfix" style="clear:both"></div>' +
            '       </div>' +
            '   </div>' +
            '   <div class="file-progress hide">' +
            '   </div>' +
            '   <div class="input-group">' +
            '       <div class="form-control file-caption" ng-class="{\'disabled\':isDisabled}">' +
            '           <div class="file-input-caption-name" ng-show="isFilesNull" i18n="{{strCaptionPlaceholder}}"></div>' +
            '           <div class="file-input-caption-name" ng-hide="isFilesNull">' +
            '               <i ng-class="strCaptionIconCls"></i>&nbsp;' +
            '               <label i18n="{{strCaption}}" data-filename="{{strCaptionFileName}}" data-filenumbers="{{strCaptionFileNumbers}}" data-totalsize="{{strCaptionTotalSize}}" />' +
            '           </div>' +
            '       </div>' +
            '       <div class="input-group-btn">' +
            '           <div ng-disabled="isDisabled" ng-click="removeAllFiles()" ng-hide="isFilesNull" class="btn btn-default">' +
            '               <i ng-class="strRemoveIconCls"></i>&nbsp;' +
            '               <span class="hidden-xs" i18n="{{strCaptionRemove}}"></span>' +
            '           </div>' +
            '           <div ng-disabled="isDisabled" ng-click="openDialog($event, this)" class="btn btn-primary btn-file">' +
            '               <i ng-class="strBrowseIconCls"></i>&nbsp; ' +
            '               <span class="hidden-xs" i18n="{{strCaptionBrowse}}"></span>' +
            '               <input type="file" accept="{{accept}}" ng-disabled="isDisabled" class="file-input-tag" onchange="angular.element(this).scope().onFileChanged(this)"/>' +
            '           </div>' +
            '       </div>' +
            '   </div>' +
            '</div>'
        );
    }]);

var ic_tpl_pagination = angular.module("uib/template/pagination/pagination.html", []);
ic_tpl_pagination.run(["$templateCache", function($templateCache) {
    $templateCache.put("uib/template/pagination/pagination.html",
        "<ul class=\"pagination\">\n" +
        "  <li ng-if=\"::boundaryLinks\" ng-class=\"{disabled: noPrevious()||ngDisabled}\" class=\"pagination-first\"><a href ng-click=\"selectPage(1, $event)\">{{getText('first')|i18n}}</a></li>\n" +
        "  <li ng-if=\"::directionLinks\" ng-class=\"{disabled: noPrevious()||ngDisabled}\" class=\"pagination-prev\"><a href ng-click=\"selectPage(page - 1, $event)\">{{getText('previous')|i18n}}</a></li>\n" +
        "  <li ng-repeat=\"page in pages track by $index\" ng-class=\"{active: page.active,disabled: ngDisabled&&!page.active}\" class=\"pagination-page\"><a href ng-click=\"selectPage(page.number, $event)\">{{page.text}}</a></li>\n" +
        "  <li ng-if=\"::directionLinks\" ng-class=\"{disabled: noNext()||ngDisabled}\" class=\"pagination-next\"><a href ng-click=\"selectPage(page + 1, $event)\">{{getText('next')|i18n}}</a></li>\n" +
        "  <li ng-if=\"::boundaryLinks\" ng-class=\"{disabled: noNext()||ngDisabled}\" class=\"pagination-last\"><a href ng-click=\"selectPage(totalPages, $event)\">{{getText('last')|i18n}}</a></li>\n" +
        "</ul>\n" +
        "");
}]);

var ic_tpl_breadcrumb = angular.module("ic/template/breadcrumb.html", []);
ic_tpl_breadcrumb.run(["$templateCache", function($templateCache) {
    $templateCache.put("ic/template/breadcrumb.html",
        '<ol class="breadcrumb">' +
        '   <li ng-repeat="step in steps" ng-class="{active: $last}" ng-switch="$last || !!step.abstract">' +
        '       <a ng-switch-when="false" href="{{step.ncyBreadcrumbLink}}" i18n="{{step.ncyBreadcrumbLabel}}"></a>' +
        '       <span ng-switch-when="true" i18n="{{step.ncyBreadcrumbLabel}}"></span>' +
        '   </li>' +
        '</ol>');
}]);

var ic_tpl_topmenu = angular.module("ic/template/topmenu/topmenu.html", []);
ic_tpl_topmenu.run(["$templateCache", function($templateCache) {
    $templateCache.put("ic/template/topmenu/topmenu.html",
        '<div class="page-header-menu">' +
        '       <div class="hor-menu navbar-collapse">' +
        '           <ul class="nav navbar-nav">' +
        '               <li ng-repeat="menu in menus" class="menu-dropdown classic-menu-dropdown" ng-class="{open: currentMenu[\'layer1\'] == menu.name}">' +
        '                   <a ng-if="!menu.submenus" ui-sref="{{menu.url}}" ng-click="layerClick(\'layer1\', menu.name, true)">' +
        '                       <i class="{{menu.icon}}"></i>{{menu.name|i18n}}' +
        '                   </a>' +
        '                   <a ng-if="menu.submenus" class="ic5Layer1" href="javascript:;" ng-click="layerClick(\'layer1\', menu.name)" ic-top-menu-repeat-hover>' +
        '                       <i class="{{menu.icon}}"></i>{{menu.name|i18n}} <span class="fa fa-angle-down"></span>' +
        '                   </a>' +
        '                   <ul ng-if="menu.submenus" class="dropdown-menu">' +
        '                       <li ng-repeat="secmenu in menu.submenus" class="{{secmenu.submenus ? \'dropdown-submenu\' : \'\'}}" ng-class="{open: currentMenu[\'layer2\'] == secmenu.name}">' +
        '                           <a ng-if="!secmenu.submenus" ui-sref="{{secmenu.url}}" ng-click="layerClick(\'layer2\', secmenu.name, true)">' +
        '                               <i ng-if="secmenu.icon" class="{{secmenu.icon}}"></i>{{secmenu.name|i18n}}' +
        '                           </a>' +
        '                           <a ng-if="secmenu.submenus" href="javascript:;" ng-click="layerClick(\'layer2\', secmenu.name)">' +
        '                               <i ng-if="secmenu.icon" class="{{secmenu.icon}}"></i>{{secmenu.name|i18n}}' +
        '                           </a>' +
        '                           <ul ng-if="secmenu.submenus" class="dropdown-menu">' +
        '                               <li ng-repeat="thirdmenu in secmenu.submenus" ng-class="{open: currentMenu[\'layer3\'] == thirdmenu.name}">' +
        '                                   <a ui-sref="{{thirdmenu.url}}" ng-click="layerClick(\'layer3\', thirdmenu.name, true)">' +
        '                                       <i ng-if="thirdmenu.icon" class="{{thirdmenu.icon}}"></i>{{thirdmenu.name|i18n}}' +
        '                                   </a>' +
        '                               </li>' +
        '                           </ul>' +
        '                       </li>' +
        '                   </ul>' +
        '               </li>' +
        '           </ul>' +
        '       </div>' +
        '</div>'
    );
}]);

var ic_tpl_treemenu = angular.module("ic/template/treemenu/treemenu.html", []);
ic_tpl_treemenu.run(["$templateCache", function($templateCache) {
    $templateCache.put("ic/template/treemenu/treemenu.html",
        '<div class="nav">' +
        '   <ul class="metisFolder">' +
        '       <li ng-repeat="menu in menus" ic-tree-menu-repeat>' +
        '           <a ng-if="!menu.submenus" ui-sref="{{menu.url}}">' +
        '               <i class="{{menu.icon}}"></i> {{menu.name|i18n}}' +
        '           </a>' +
        '           <a ng-if="menu.submenus" href="javascript:;">' +
        '               <i class="fa fa-folder-o"></i> {{menu.name|i18n}}' +
        '           </a>' +
        '           <ul ng-if="menu.submenus">' +
        '               <li ng-repeat="secmenu in menu.submenus">' +
        '                   <a ng-if="!secmenu.submenus" ui-sref="{{secmenu.url}}">' +
        '                       <i class="{{secmenu.icon}}"></i> {{secmenu.name|i18n}}' +
        '                   </a>' +
        '                   <a ng-if="secmenu.submenus" href="javascript:;">' +
        '                       <i class="fa fa-folder-o"></i> {{secmenu.name|i18n}}' +
        '                   </a>' +
        '                   <ul ng-if="secmenu.submenus">' +
        '                       <li ng-repeat="thirdmenu in secmenu.submenus">' +
        '                           <a ui-sref="{{thirdmenu.url}}">' +
        '                               <i ng-if="thirdmenu.icon" class="{{thirdmenu.icon}}"></i> {{thirdmenu.name|i18n}}' +
        '                           </a>' +
        '                       </li>' +
        '                   </ul>' +
        '               </li>' +
        '           </ul>' +
        '       </li>' +
        '   </ul>' +
        '</div>'
    );
}]);

// aframe component template - [datetimepicker]
var ic_tpl_datetimepicker = angular.module("ic/template/datetimepicker/datetimepicker.html", []);
ic_tpl_datetimepicker
    .run(['$templateCache', function($templateCache) {
        $templateCache.put("ic/template/datetimepicker/datetimepicker.html",
            '<div class="input-group">' +
            '   <input type="text" class="form-control">' +
            '   <span class="input-group-addon" style="cursor: pointer">' +
            '       <i class="fa fa-calendar"></i>' +
            '   </span>' +
            '</div>'
        );
    }]);