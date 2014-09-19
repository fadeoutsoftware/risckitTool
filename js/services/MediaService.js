/**
 * Created by s.adamo on 08/07/2014.
 */
angular.module('risckit.mediaService', []).
    service('MediaService',  ['$http', '$upload', function ($http) {
        this.APIURL = 'http://risckit.cloudapp.net/risckit/rest';
        //this.APIURL = 'http://localhost:8080/risckit/rest';
        this.m_oHttp = $http;
        this.m_bModified = false;

        this.LoadMedia = function (idEvent) {

            return this.m_oHttp({method: 'GET', url: this.APIURL + '/media/event/' + idEvent});

        };

        this.getMedia = function (idMedia) {

            return this.m_oHttp({method: 'GET', url: this.APIURL + '/media/' + idMedia});

        };

        this.DeleteMedia = function (idMedia, idEvent) {

            return this.m_oHttp.post(this.APIURL + '/media/delete/' + idMedia + '/' + idEvent);

        };

        this.DownloadMedia = function (idMedia) {

            return this.APIURL + '/media/download/' + idMedia;

        };

        this.isModified = function () {
            return this.m_bModified;
        };

        this.setAsModified = function () {
            this.m_bModified = true;
        };

        this.setUnchanged = function () {
            this.m_bModified = false;
        };

    }]);

