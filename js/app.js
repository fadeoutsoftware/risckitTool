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
    'risckit.usersService',
    'angularFileUpload',
    'riskitapp.directives',
    'ngSanitize']);


//---------------------------------------------------------------------------
//- Set app configuration variables (API url, etc)
//---------------------------------------------------------------------------
var oConfig = {
    //API_ADDRESS : 'http://risckit.cloudapp.net/risckit/rest'
    API_ADDRESS : 'http://localhost:8080/risckit/rest'
}
riskit.constant("CONFIG", oConfig);
//---------------------------------------------------------------------------


riskit.config(['$routeProvider', '$httpProvider',
function ($routeProvider, $httpProvider)
{


    // Configure HTTP requests
    $httpProvider.defaults.headers.common = {
        //'Authorization': 'Basic d2VudHdvcnRobWFuOkNoYW5nZV9tZQ==',
        //'Accept': 'application/json;odata=verbose'
        'Auth-Token' : function(){
            var oAuthHelper = AuthHelper.getInstance();
            return oAuthHelper.getToken()
        }
    };

    // Configure routes
    $routeProvider.when('/', {templateUrl: 'partials/home.html', controller: 'HomeController'});
    $routeProvider.when('/event', {templateUrl: 'partials/event.html', controller: 'EventController'});
    $routeProvider.when('/eventslist', {templateUrl: 'partials/eventslist.html', controller: 'EventsListController'});
    $routeProvider.when('/map', {templateUrl: 'partials/map.html', controller: 'MapController'});
    $routeProvider.when('/media', {templateUrl: 'partials/media.html', controller: 'MediaController'});
    $routeProvider.when('/media/:idmedia', {templateUrl: 'partials/media.html', controller: 'MediaController'});
    $routeProvider.when('/socioimpact', {templateUrl: 'partials/socioimpact.html', controller: 'SocioimpactController'});
    $routeProvider.when('/socioimpact/:idsocioimpact', {templateUrl: 'partials/socioimpact.html', controller: 'SocioimpactController'});
    $routeProvider.when('/links', {templateUrl: 'partials/links.html'});

    $routeProvider.when('/architecture', {templateUrl: 'partials/architecture.html'});
    $routeProvider.when('/loginrequest', {templateUrl: 'partials/loginrequest.html', controller: 'LoginRequestController'});
    $routeProvider.when('/adminusersmanagement', {templateUrl: 'partials/admin_users_management.html', controller: 'AdminUsersManagement'});
    $routeProvider.when('/accessrequestmanagement', {templateUrl: 'partials/admin_users_management.html', controller: 'AdminUsersManagement'});
    $routeProvider.when('/userprofile', {templateUrl: 'partials/user_profile.html', controller: 'UserProfile'});


    $routeProvider.otherwise({redirectTo: '/'});
}]);




