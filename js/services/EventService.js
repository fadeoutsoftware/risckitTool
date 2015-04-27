/**
 * Created by s.adamo on 08/07/2014.
 */
angular.module('risckit.eventService', []).
    service('EventService',  ['$http', '$upload', function ($http, $upload) {
        this.APIURL = 'http://risckit.cloudapp.net/risckit/rest';
        //this.APIURL = 'http://localhost:8080/risckit/rest';

        this.m_oHttp = $http;
        this.m_oCountries = null;
        this.m_oCategories = null;
        this.m_oRegions = null;
        this.m_oEvents = [];
        this.m_bModified = false;
        this.m_aGetWaveHeightType = [{name:"Mean significant wave height", value:1}, {name:"Peak significant wave height", value:2}, {name:"Maximum wave height", value:3}];
        this.m_aGetWaveDiectionType = [{name:"Degrees from N", value:1}, {name:"Compass", value:2}];
        this.m_aGetWindDiectionType = [{name:"Degrees from N", value:1}, {name:"Compass", value:2}];
        this.m_aGetWaterLevelType = [{name:"Maximum total water level", value:1}, {name:"Maximum astronomical tide", value:2}, {name:"Minimum total water level", value:3}];
        this.m_aGetWindIntensityType = [{name:"Mean wind speed", value:1}, {name:"Maximum wind speed", value:2}, {name:"Maximum wind gust", value:3}];
        this.m_aGetCostDetails = [{name:"Direct Cost", value:1}, {name:"Business Interruption Cost", value:2}, {name:"Indirect Cost", value:3}, {name:"Risk mitigation Cost", value:4}];

        this.LoadCountries = function () {

            var oServiceVar = this;
            if (oServiceVar.m_oCountries == null) {
                this.m_oHttp.get(this.APIURL + '/countries/all/').success(function (data) {
                    oServiceVar.m_oCountries = data;
                });
            }

        };

        this.LoadEvents = function (idUser) {
            return this.m_oHttp({method: 'GET', url: this.APIURL + '/events/all/' + idUser});
        };

        this.LoadEventsByCountryForMap = function (countryCode) {
            return this.m_oHttp({method: 'GET', url: this.APIURL + '/events/bycountry/' + countryCode});
        };

        this.LoadEventsByRegionForMap = function (regionid) {
            return this.m_oHttp({method: 'GET', url: this.APIURL + '/events/byregion/' + regionid});
        };

        this.LoadEventsByCountryForList = function (countryCode) {
            return this.m_oHttp({method: 'GET', url: this.APIURL + '/events/bycountrylist/' + countryCode});
        };

        this.LoadEventsByCountries = function () {
            return this.m_oHttp({method: 'GET', url: this.APIURL + '/events/groupevent'});
        };

        this.getEvents = function() {
            return this.m_oEvents;
        };

        this.getEvent = function(idEvent) {
            return this.m_oHttp.get(this.APIURL + '/events/' + idEvent);
        };

        this.GetCountries = function(){
            return this.m_oCountries;
        };

        this.GetRegion = function(IdRegionCountry){

            if (this.m_oRegions != null)
            {
                for(var iCount = 0; iCount < this.m_oRegions.length; iCount++){
                    if (this.m_oRegions[iCount].id == IdRegionCountry)
                        return this.m_oRegions[iCount];
                }
            }

            return null;
        };

        this.GetAllRegions = function (countryCode) {
            return this.m_oHttp.get(this.APIURL + '/countries/regions/' + countryCode);

        };

        this.setRegions = function (regions) {
            this.m_oRegions = regions;

        };

        this.Save = function (Event) {
              return this.m_oHttp.post(this.APIURL + '/events/save', Event);
        };

        this.CreatePdf = function (html) {
            var oObject = {html: html};
            return this.m_oHttp.post(this.APIURL + '/events/pdf', oObject);
        };

        this.CreatePdf2 = function (idEvent) {

            return this.APIURL + '/events/pdf/' + idEvent;
        };


        this.isModified = function () {
            return this.m_bModified;
        };

        this.setAsModified = function() {
            this.m_bModified = true;
        };

        this.setUnchanged = function() {
            this.m_bModified = false;
        };

        this.Upload = function (event, selectedfile, parameter) {

            var Url = this.APIURL;
            var oService = this;
            //Verifico se devo salvare prima l'evento
            var fd = new FormData();
            fd.append('file', selectedfile);
            fd.append("eventid", event.id);
            fd.append("parameter", parameter);
            fd.append("userid", event.userId);
            fd.append("startDate", event.startDate);
            fd.append("regionName", event.regionName);
            fd.append("countryCode", event.countryCode);

            return $http.post(Url + "/events/upload", fd, {
                transformRequest: angular.identity,
                headers: {'Content-Type': undefined}
            });

        };


        this.DownloadAttachment = function (idEvent, parameter) {

            return this.APIURL + '/events/download/' + idEvent + '/' + parameter;

        };

        this.DeleteAttachment = function (idEvent, parameter) {

            return this.m_oHttp.post(this.APIURL + '/events/deleteattach/' + idEvent + '/' + parameter);

        };

        this.DeleteEvent = function (idEvent) {

            var answer = confirm("Are you sure to delete?");
            if (answer) {
                return this.m_oHttp.post(this.APIURL + '/events/delete/' + idEvent);
            }
            else{
                return null;
            }

        };

        this.GetWaveHeightType = function() {
            return this.m_aGetWaveHeightType;

        };

        this.GetWaveHeightTypeByIndex = function(index) {
            return this.m_aGetWaveHeightType[index - 1];

        };


        this.GetWaveDiectionType = function() {
            return this.m_aGetWaveDiectionType;

        };

        this.GetWaveDiectionTypeByIndex = function(index) {
            return this.m_aGetWaveDiectionType[index - 1];

        };

        this.GetWindDiectionType = function() {
            return this.m_aGetWindDiectionType;

        };

        this.GetWindDiectionTypeByIndex = function(index) {
            return this.m_aGetWindDiectionType[index - 1];

        };

        this.GetWaterLevelType = function() {
            return this.m_aGetWaterLevelType;

        };

        this.GetWaterLevelTypeByIndex = function(index) {
            return this.m_aGetWaterLevelType[index - 1];

        };

        this.GetWindIntensityType = function() {
            return this.m_aGetWindIntensityType;

        };

        this.GetWindIntensityTypeByIndex = function(index) {
            return this.m_aGetWindIntensityType[index - 1];

        };

        this.GetCostDetails = function() {
            return this.m_aGetCostDetails;

        };

        this.GetCostDetailsByIndex = function(index) {
            return this.m_aGetCostDetails[index - 1];

        };


    }]);

