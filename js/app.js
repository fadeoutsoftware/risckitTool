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
    'risckit.socioimpactService',
    'angularFileUpload',
    'riskitapp.directives',
    'ngSanitize']);



riskit.config(function($routeProvider) {
        $routeProvider.when('/', {templateUrl: 'partials/home.html', controller: 'HomeController'});
        $routeProvider.when('/event', {templateUrl: 'partials/event.html', controller: 'EventController'});
        $routeProvider.when('/eventslist', {templateUrl: 'partials/eventslist.html', controller: 'EventsListController'});
        $routeProvider.when('/map', {templateUrl: 'partials/map.html', controller: 'MapController'});
        $routeProvider.when('/media', {templateUrl: 'partials/media.html', controller: 'MediaController'});
        $routeProvider.when('/media/:idmedia', {templateUrl: 'partials/media.html', controller: 'MediaController'});
        $routeProvider.when('/socioimpact', {templateUrl: 'partials/socioimpact.html', controller: 'SocioimpactController'});
        $routeProvider.when('/socioimpact/:idsocioimpact', {templateUrl: 'partials/socioimpact.html', controller: 'SocioimpactController'});
        $routeProvider.when('/links', {templateUrl: 'partials/links.html'});
        $routeProvider.otherwise({redirectTo: '/'});
    }
);




