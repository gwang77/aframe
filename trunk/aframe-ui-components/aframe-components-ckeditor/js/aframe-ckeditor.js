'use strict';

// aframe component - [ckeditor]
var ic_ckeditor = angular.module('aframe.component.ckeditor', []);
ic_ckeditor
    .provider('$icCkeditor', function () {
        var $$options = {
            defaults: {
                language: 'en',
                allowedContent: true,
                entities: false
            }
        };

        this.setOptions = function(options) {
            angular.extend($$options, options);
        };

        this.$get = function() {
            return $$options;
        };
    })
    .directive('icCkeditor', ['$parse', '$icCkeditor', function($parse, $icCkeditor) {
        return {
            restrict: 'A',
            replace: true,
            require: [
                'icCkeditor',
                'ngModel'
            ],
            controller: ['$scope', '$icCkeditor', '$element', '$attrs', '$parse', '$q', function ($scope, $icCkeditor, $element, $attrs, $parse, $q) {
                //prepare option
                var allKeys = _.allKeys($icCkeditor.defaults);
                var config = _.pick($attrs, function (value, key) {
                    return _.contains(allKeys, key);
                });
                config = _.defaults(config, $icCkeditor.defaults);

                var editorElement = $element[0];
                var instance;
                var readyDeferred = $q.defer(); // a deferred to be resolved when the editor is ready

                // Create editor instance.
                if (editorElement.hasAttribute('contenteditable') &&
                    editorElement.getAttribute('contenteditable').toLowerCase() == 'true') {
                    instance = this.instance = CKEDITOR.inline(editorElement, config);
                }
                else {
                    instance = this.instance = CKEDITOR.replace(editorElement, config);
                }

                /**
                 * Listen on events of a given type.
                 * This make all event asynchronous and wrapped in $scope.$apply.
                 *
                 * @param {String} event
                 * @param {Function} listener
                 * @returns {Function} Deregistration function for this listener.
                 */

                this.onCKEvent = function (event, listener) {
                    instance.on(event, asyncListener);

                    function asyncListener() {
                        var args = arguments;
                        // update for IE9
                        //setTimeout(applyListener.apply(null, args), 0);
                        applyListener.apply(null, args)
                    }

                    function applyListener() {
                        var args = arguments;
                        $scope.$apply(function () {
                            listener.apply(null, args);
                        });
                    }

                    // Return the deregistration function
                    return function $off() {
                        instance.removeListener(event, applyListener);
                    };
                };

                this.onCKEvent('instanceReady', function() {
                    readyDeferred.resolve(true);
                });

                /**
                 * Check if the editor if ready.
                 *
                 * @returns {Promise}
                 */
                this.ready = function ready() {
                    return readyDeferred.promise;
                };

                // Destroy editor when the scope is destroyed.
                $scope.$on('$destroy', function onDestroy() {
                    // do not delete too fast or pending events will throw errors
                    readyDeferred.promise.then(function() {
                        try {
                            instance.destroy(false);
                        } catch(e) {

                        }
                    });
                });
            }],
            link: function (scope, element, attrs, ctrls) {
                // get needed controllers
                var controller = ctrls[0]; // our own, see below
                var ngModelController = ctrls[1];
                // Initialize the editor content when it is ready.
                controller.ready().then(function initialize() {
                    // Sync view on specific events.
                    ['dataReady', 'change', 'blur', 'saveSnapshot'].forEach(function (event) {
                        controller.onCKEvent(event, function syncView() {
                            ngModelController.$setViewValue(controller.instance.getData() || '');
                        });
                    });

                    controller.instance.setReadOnly(!! attrs.readonly);
                    attrs.$observe('readonly', function (readonly) {
                        controller.instance.setReadOnly(!! readonly);
                    });

                    // Defer the ready handler calling to ensure that the editor is
                    // completely ready and populated with data.
                    setTimeout($parse(attrs.ready)(scope), 0);
                });

                // Set editor data when view data change.
                ngModelController.$render = function syncEditor() {
                    controller.ready().then(function () {
                        // "noSnapshot" prevent recording an undo snapshot
                        controller.instance.setData(ngModelController.$viewValue || '', {
                            noSnapshot: true,
                            callback: function () {
                                // Amends the top of the undo stack with the current DOM changes
                                // ie: merge snapshot with the first empty one
                                // http://docs.ckeditor.com/#!/api/CKEDITOR.editor-event-updateSnapshot
                                controller.instance.fire('updateSnapshot');
                            }
                        });
                    });
                };
            }
        };
    }]);