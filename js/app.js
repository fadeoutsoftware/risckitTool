/**
 * Created by s.adamo on 01/07/2014.
 */
var riskit = angular.module('riskitapp',[
    'ngRoute',
    'ui.bootstrap',
    'google-maps',
    'risckit.eventService',
    'risckit.sharedService',
    'angularFileUpload',
    'riskitapp.directives']);



riskit.config(function($routeProvider) {
        $routeProvider.when('/event', {templateUrl: 'partials/event.html', controller: 'EventController'});
        $routeProvider.when('/map', {templateUrl: 'partials/map.html', controller: 'MapController'});
        $routeProvider.when('/media', {templateUrl: 'partials/media.html', controller: 'MediaController'});
        $routeProvider.otherwise({redirectTo: '/'});
    }
);




