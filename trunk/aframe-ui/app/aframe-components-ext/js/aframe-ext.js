'use strict';

/**
 * aframe extension components.
 */
angular.module("aframe.ext", [
    "aframe.ext.slimscroll",
    "aframe.ext.dropdownmenuhover",
    "aframe.ext.ignoreemptyhyperlink"
]);

/**
 * aframe extension component - [slimscroll].
 */
var ice_slimscroll = angular.module('aframe.ext.slimscroll', []);
ice_slimscroll.directive('iceSlimScroll', function () {
    return {
        restrict: 'A',
        link: function postLink(scope, element, attrs) {
            var height;
            if (element.attr("data-height")) {
                height = element.attr("data-height");
            } else {
                height = $(this).css('height');
            }

            var option = {
                allowPageScroll: true, // allow page scroll when the element scroll is ended
                size: '8px',
                color: (attrs.handleColor ? attrs.handleColor : '#bbb'),
                wrapperClass: (attrs.wrapperClass ? attrs.wrapperClass : 'slimScrollDiv'),
                railColor: (attrs.railColor ? attrs.railColor : '#eaeaea'),
                position: 'right',
                height: height,
                alwaysVisible: (attrs.alwaysVisible === "1" ? true : false),
                railVisible: (attrs.railVisible === "1" ? true : false),
                disableFadeOut: true
            };

            var off = [];

            var refresh = function () {
                var el = angular.element(element);
                el.slimScroll({destroy: true});
                el.slimScroll(option);
            };

            var registerWatch = function () {
                if (angular.isDefined(attrs.iceSlimScroll) && !option.noWatch) {
                    off.push(scope.$watchCollection(attrs.iceSlimScroll, refresh));
                }

                if (attrs.slimsScrollWatch) {
                    off.push(scope.$watchCollection(attrs.slimsScrollWatch, refresh));
                }

                if (attrs.slimScrollListento) {
                    off.push(scope.$on(attrs.slimScrollListento, refresh));
                }
            };

            var destructor = function () {
                angular.element(element).slimScroll({destroy: true});
                off.forEach(function (unbind) {
                    unbind();
                });
                off = null;
            };

            off.push(scope.$on('$destroy', destructor));

            registerWatch();
        }
    };
});

/**
 * aframe extension component - [dropdownmenuhover].
 */
var ice_dropdownmenuhover = angular.module('aframe.ext.dropdownmenuhover', []);
ice_dropdownmenuhover.directive('iceDropdownMenuHover', function () {
    return {
        link: function (scope, elem) {
            elem.dropdownHover();
        }
    };
});

/**
 * aframe extension component - [ignoreemptyhyperlink].
 * @desc Fix click empty a link( a href='', a href='#' ... ) auto forward to default page when use angular-ui-router( $urlRouterProvider.otherwise('/main/home') ) issue
 */
var ice_ignoreEmptyHyperlink = angular.module('aframe.ext.ignoreemptyhyperlink', []);
ice_ignoreEmptyHyperlink.directive('href', function () {
    return {
        restrict: 'A',
        link: function (scope, elem, attrs) {
            if (attrs.ngClick || attrs.href === '' || attrs.href == '/' || attrs.href === '#' || (attrs.href.indexOf('#') >= 0 && attrs.href.indexOf('#/') < 0)) {
                elem.on('click', function (e) {
                    e.preventDefault(); // prevent link click for above criteria
                });
            }
        }
    };
});