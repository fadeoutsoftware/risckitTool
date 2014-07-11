/**
 * Created by s.adamo on 10/07/2014.
 */
angular.module('risckit.sharedService', [])
    .factory('SharedService', function($rootScope) {

        var service = {};

        service.setEvent = function (oEvent) {
            $rootScope.Event = oEvent;
        };

        service.getEvent = function () {
            return $rootScope.Event;
        };

        return service;
    });