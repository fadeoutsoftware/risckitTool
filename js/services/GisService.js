/**
 * Created by s.adamo on 08/07/2014.
 */
angular.module('risckit.gisService', []).
    service('GisService',  ['$http', '$upload', function ($http) {
        //this.APIURL = 'http://risckit.cloudapp.net/risckit/rest';
        this.APIURL = 'http://localhost:8080/risckit/rest';
        this.m_oHttp = $http;

        this.LoadGis = function (idEvent) {

            return this.m_oHttp({method: 'GET', url: this.APIURL + '/gis/event/' + idEvent});

        };

    }]);

