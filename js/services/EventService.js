/**
 * Created by s.adamo on 08/07/2014.
 */
angular.module('risckit.eventService', []).
    service('EventService',  ['$http', '$upload', function ($http, $upload) {
        //this.APIURL = 'http://risckit.cloudapp.net/risckit/rest';
        this.APIURL = 'http://localhost:8080/risckit/rest';
        this.m_oHttp = $http;
        //this.m_oUpload = $upload;

        this.GetAllCountries = function () {
            return this.m_oHttp.get(this.APIURL + '/countries/all');
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

        this.UpdateMedia = function (Media) {
            return this.m_oHttp.post(this.APIURL + '/media/update/' + Media.id, Media);
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
            var oService = this;
            var oSelectedfile = selectedfile;
            //Verifico se devo salvare prima l'evento
            if (event == null || event.id == null || event.id == 0) {
                return this.Save(event).success(function (data, status) {
                    media.eventId = data.id;
                    //Salvo i media
                    oService.SaveMedia(media).success(function (data, status){
                        media.id = data.id;
                        var fd = new FormData();
                        fd.append('file', oSelectedfile);
                        fd.append("mediaid", data.id);
                        fd.append("login", event.login);
                        fd.append("startDate", event.startDate);
                        fd.append("regionName", event.regionName);
                        fd.append("countryCode", event.countryCode);

                        return $http.post(Url + "/media/upload", fd, {
                            transformRequest: angular.identity,
                            headers: {'Content-Type': undefined}
                        }).success(function (data, status){
                            media.downloadPath = data;

                        });
                    });
                });
            }
            else {
                //Salvo i media
                media.eventId = event.id;
                this.m_oScope.SaveMedia(media).success(function (data, status){
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
                    }).success(function (data, status){
                        media.downloadPath = data;

                    });
                });

            }
        };


        this.UploadGis = function (event, gis, selectedfile, type) {
            var Url = this.APIURL;
            var oService = this;
            var oSelectedfile = selectedfile;
            //Verifico se devo salvare prima l'evento
            if (event == null || event.id == null || event.id == 0) {
                return this.Save(event).success(function (data, status) {
                    gis.eventId = data.id;
                    //Salvo Gis
                    if (gis == null || gis.id == null || gis.id == 0) {
                        oService.SaveGis(gis).success(function (data, status) {
                            gis.id = data.id;
                            var fd = new FormData();
                            fd.append('file', oSelectedfile);
                            fd.append("gisid", data.id);
                            fd.append("type", type);
                            fd.append("login", event.login);
                            fd.append("startDate", event.startDate);
                            fd.append("regionName", event.regionName);
                            fd.append("countryCode", event.countryCode);

                            return $http.post(Url + "/gis/upload", fd, {
                                transformRequest: angular.identity,
                                headers: {'Content-Type': undefined}
                            }).success(function (data, status) {
                                if (type == 0)
                                    gis.downloadGisPath = data;
                                else
                                    gis.downloadInspirePath = data;

                            });
                        });
                    }
                    else
                    {
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
                        }).success(function (data, status) {
                            if (type == 0)
                                gis.downloadGisPath = data;
                            else
                                gis.downloadInspirePath = data;

                        });
                    }
                });
            }
            else {
                //Salvo Gis
                gis.eventId = event.id;
                if (gis == null || gis.id == null || gis.id == 0) {
                    oService.SaveGis(gis).success(function (data, status) {
                        gis.id = data.id;
                        var fd = new FormData();
                        fd.append('file', oSelectedfile);
                        fd.append("gisid", data.id);
                        fd.append("type", type);
                        fd.append("login", event.login);
                        fd.append("startDate", event.startDate);
                        fd.append("regionName", event.regionName);
                        fd.append("countryCode", event.countryCode);

                        return $http.post(Url + "/gis/upload", fd, {
                            transformRequest: angular.identity,
                            headers: {'Content-Type': undefined}
                        }).success(function (data, status) {
                            if (type == 0)
                                gis.downloadGisPath = data;
                            else
                                gis.downloadInspirePath = data;

                        });
                    });
                }
                else
                {
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
                    }).success(function (data, status) {
                        if (type == 0)
                            gis.downloadGisPath = data;
                        else
                            gis.downloadInspirePath = data;

                    });
                }
            }
        };

    }]);

