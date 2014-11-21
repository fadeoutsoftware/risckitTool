/**
 * Created by s.adamo on 08/07/2014.
 */
angular.module('risckit.gisService', []).
    service('GisService',  ['$http', '$upload', function ($http) {
        this.APIURL = 'http://risckit.cloudapp.net/risckit/rest';
        //this.APIURL = 'http://localhost:8080/risckit/rest';
        this.m_oHttp = $http;

        this.LoadGis = function (idEvent) {

            return this.m_oHttp({method: 'GET', url: this.APIURL + '/gis/event/' + idEvent});

        };

        this.DeleteGis = function (idGis, idEvent, type) {

            var answer = confirm("Are you sure to delete?");
            if (answer) {
                return this.m_oHttp.post(this.APIURL + '/gis/delete/' + idGis + '/' + idEvent + '/' + type);
            }
            else{
                return null;
            }

        };

        this.DownloadGis = function (idGis, type) {

            return this.APIURL + '/gis/download/' + idGis + '/' + type;

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

        this.SaveGis = function (Gis) {
            return this.m_oHttp.post(this.APIURL + '/gis/save', Gis);
        };

    }]);

