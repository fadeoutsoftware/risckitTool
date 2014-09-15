/**
 * Created by s.adamo on 08/07/2014.
 */
angular.module('risckit.mediaService', []).
    service('MediaService',  ['$http', '$upload', function ($http) {
        //this.APIURL = 'http://risckit.cloudapp.net/risckit/rest';
        this.APIURL = 'http://localhost:8080/risckit/rest';
        this.m_oHttp = $http;

        this.LoadMedia = function (idEvent) {

            return this.m_oHttp({method: 'GET', url: this.APIURL + '/media/event/' + idEvent});

        };

        this.DeleteMedia = function (idMedia, idEvent) {

            return this.m_oHttp.post(this.APIURL + '/media/delete/' + idMedia + '/' + idEvent);

        };

        this.DownloadMedia = function (idMedia) {

            return this.m_oHttp({method: 'GET', url: this.APIURL + '/media/download/' + idMedia});

        };

    }]);

