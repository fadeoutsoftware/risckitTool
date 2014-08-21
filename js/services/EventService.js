/**
 * Created by s.adamo on 08/07/2014.
 */
angular.module('risckit.eventService', []).
    service('EventService',  ['$http', '$upload', function ($http, $upload) {
        this.APIURL = 'http://risckit.cloudapp.net/risckit/rest';
        //this.APIURL = 'http://localhost:8080/risckit/rest';
        this.m_oHttp = $http;
        this.m_oCountries;
        //this.m_oUpload = $upload;

        this.LoadCountries = function () {

            var oServiceVar = this;
            if (oServiceVar.m_oCountries == null) {
                this.m_oHttp.get(this.APIURL + '/countries/all/').success(function (data) {
                    oServiceVar.m_oCountries = data;
                });
            }

        };

        this.GetCountries = function(){
            return this.m_oCountries;
        };

        this.GetAllRegions = function (countryCode) {
            return this.m_oHttp.get(this.APIURL + '/countries/regions/' + countryCode);

        };

        this.Save = function (Event) {
            return this.m_oHttp.post(this.APIURL + '/events/save', Event);
        };

        this.SaveMedia = function (Media) {
            return this.m_oHttp.post(this.APIURL + '/media/save', Media);
        };


        this.SaveGis = function (Gis) {
            return this.m_oHttp.post(this.APIURL + '/gis/save', Gis);
        };


        this.Upload = function (event, selectedfile, parameter) {

            var Url = this.APIURL;
            var oService = this;
            //Verifico se devo salvare prima l'evento
            var fd = new FormData();
            fd.append('file', selectedfile);
            fd.append("eventid", event.id);
            fd.append("parameter", parameter);
            fd.append("login", event.login);
            fd.append("startDate", event.startDate);
            fd.append("regionName", event.regionName);
            fd.append("countryCode", event.countryCode);

            return $http.post(Url + "/events/upload", fd, {
                transformRequest: angular.identity,
                headers: {'Content-Type': undefined}
            });

        };


        this.UploadMedia = function (event, media, selectedfile) {
            var Url = this.APIURL;
            var oSelectedfile = selectedfile;
            //Verifico se devo salvare prima l'evento
            var fd = new FormData();
            fd.append('file', oSelectedfile);
            fd.append("mediaid", media.id);
            fd.append("login", event.login);
            fd.append("startDate", event.startDate);
            fd.append("regionName", event.regionName);
            fd.append("countryCode", event.countryCode);

            return $http.post(Url + "/media/upload", fd, {
                transformRequest: angular.identity,
                headers: {'Content-Type': undefined}
            });


        };


        this.UploadGis = function (event, gis, selectedfile, type) {
            var Url = this.APIURL;
            var oSelectedfile = selectedfile;
            //Verifico se devo salvare prima l'evento

            var fd = new FormData();
            fd.append('file', oSelectedfile);
            fd.append("gisid", gis.id);
            fd.append("type", type);
            fd.append("login", event.login);
            fd.append("startDate", event.startDate);
            fd.append("regionName", event.regionName);
            fd.append("countryCode", event.countryCode);

            return $http.post(Url + "/gis/upload", fd, {
                transformRequest: angular.identity,
                headers: {'Content-Type': undefined}
            });

        };

    }]);

