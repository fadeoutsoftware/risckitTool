/**
 * Created by s.adamo on 01/07/2014.
 */
var riskit = angular.module('riskitapp',[
    'ngRoute',
    'ui.bootstrap',
    'google-maps',
    'risckit.loginService',
    'risckit.eventService',
    'risckit.sharedService',
    'risckit.mediaService',
    'risckit.gisService',
    'angularFileUpload',
    'riskitapp.directives',
    'ngSanitize']);



riskit.config(function($routeProvider) {
        $routeProvider.when('/event', {templateUrl: 'partials/event.html', controller: 'EventController'});
        $routeProvider.when('/eventslist', {templateUrl: 'partials/eventslist.html', controller: 'EventsListController'});
        $routeProvider.when('/map', {templateUrl: 'partials/map.html', controller: 'MapController'});
        $routeProvider.when('/media', {templateUrl: 'partials/media.html', controller: 'MediaController'});
        $routeProvider.when('/media/:idmedia', {templateUrl: 'partials/media.html', controller: 'MediaController'});
        $routeProvider.otherwise({redirectTo: '/'});
    }
);




