'use strict';

/**
 * aframe.
 * # -> component
 * # -> template
 * # -> validator rules
 */
angular.module("aframe", [
    "ngTouch",
    "ngCookies",
    "aframe.interceptors",
    "aframe.templates",
    "aframe.components",
    "aframe.services",
    "aframe.validator.rules"
]);

