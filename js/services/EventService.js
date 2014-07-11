/**
 * Created by s.adamo on 08/07/2014.
 */
angular.module('risckit.eventService', []).
    service('EventService',  ['$http', '$upload', function ($http, $upload) {
        this.APIURL = 'http://localhost:8080/risckit/rest';
        //this.APIURL = 'http://95.110.165.229/GenUserServer/rest';
        this.m_oHttp = $http;
        this.m_oUpload = $upload;

        this.GetAllCountries = function () {
            return this.m_oHttp.get(this.APIURL + '/countries/all');
        };

        this.GetAllRegions = function (countryCode) {
            return this.m_oHttp.get(this.APIURL + '/countries/regions/' + countryCode);
        };

        this.Save = function (Event) {

            return this.m_oHttp.post(this.APIURL + '/events/save', Event);
        };

        this.Upload = function (event, selectedfile){

            var Url = this.APIURL;

            var fd = new FormData();
            fd.append('file', selectedfile);

            return this.m_oUpload.upload({
                url: Url + '/events/upload',
                method: 'POST',
                headers: {'Content-Type': undefined},
                transformRequest: angular.identity,
                file: selectedfile

                /* transformRequest: [function(val, h) {
                 console.log(val, h('my-header')); return val + '-modified';
                 }], */

            });
        };

    }]);

