'use strict';

/* Directives */


angular.module('riskitapp.directives', [])
    .directive('fileModel', ['$parse', function ($parse) {
        return {
            restrict: 'A',
            link: function(scope, element, attrs) {
                var model = $parse(attrs.fileModel);
                var modelSetter = model.assign;

                element.bind('change', function(){
                    scope.$apply(function(){
                        modelSetter(scope, element[0].files[0]);
                    });
                });
            }
        };
    }]).filter('pagination', function()
    {
        return function(input, start) {
            start = parseInt(start, 10);
            return input.slice(start);
        };
    });
