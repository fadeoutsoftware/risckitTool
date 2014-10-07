/**
 * Created by s.adamo on 04/07/2014.
 */
var MapController = (function() {



    function MapController($scope, $location, oEventService, oLigingService, oMediaService, oSanitize) {
        this.m_oScope = $scope;
        this.m_oScope.m_oController = this;
        this.m_oLocation = $location;
        this.m_oEventService = oEventService;
        this.m_oLoginService = oLigingService;
        this.m_oMediaService = oMediaService;
        this.m_oEventList = [];
        this.EventHtml = '';
        this.countryEventMarkers = [];
        this.mediaMarkers = [];
        this.eventMarkers = [];
        this.m_iSelectedEventId;
        this.m_oMap;

        var infowindow = new google.maps.InfoWindow({maxWidth: 300});

        var map_canvas = document.getElementById('map_canvas');
        var map_options = {
            center: new google.maps.LatLng(44.40338, 34.17403),
            zoom: 3,
            mapTypeId: google.maps.MapTypeId.ROADMAP
        };
        var map = new google.maps.Map(map_canvas, map_options);
        this.m_oMap = map;

        google.maps.event.addListener(map, 'zoom_changed', function () {

            if (map.getZoom() < 6)
            {
                //rimuovo i region marker
                clearRegionMarkers();
                clearEventsInfo();
                $scope.m_oController.clearMediaMarkers();

                //Visualizzo nuovamente i marker per country
                for (var iCount = 0; iCount < $scope.m_oController.countryEventMarkers.length; iCount++)
                {
                    $scope.m_oController.countryEventMarkers[iCount].setMap(map);
                }
            }

        });

        var eventmarker = 'img/marker_event.png';

        if (this.m_oLoginService.isLogged())
            this.m_oScope.m_oController.m_oEventService.LoadEventsByCountries().success(function (data) {
                $scope.m_oController.m_oEventCountryList = data;
                for (var iCount = 0; iCount < data.length; iCount++) {
                    var oEvent = data[iCount];

                    var Latlng = new google.maps.LatLng(oEvent.lat, oEvent.lon);
                    var marker = new MarkerWithLabel({
                        position: Latlng,
                        map: map,
                        title: oEvent.countryName,
                        icon: eventmarker,
                        labelContent: oEvent.eventsCount,
                        labelAnchor: new google.maps.Point(26, 32),
                        labelClass: "labels-map", // the CSS class for the label
                        labelInBackground: false
                    });

                    //showInfo(marker, oEvent.countryName);
                    //closeInfo(marker);
                    addCountryMarker(marker, oEvent.countryCode);
                    $scope.m_oController.countryEventMarkers.push(marker);

                }

            });

        function clearEventsInfo()
        {
            $scope.m_oController.EventHtml = '';
            $scope.m_oController.m_iSelectedEventId = null;
            $scope.m_oController.m_oEventList = [];
            $scope.$digest();
        }


        function addCountryMarker(marker, countryCode) {

            var locCountryCode = countryCode;
            var localmarker = marker;
            google.maps.event.addListener(marker, 'click', function () {
                $scope.m_oController.m_oEventService.LoadEventsByCountryForMap(locCountryCode).success(function (data) {

                    clearCountryMarkers();

                    map_options = {
                        zoom: 6,
                        center: new google.maps.LatLng(localmarker.position.lat(), localmarker.position.lng()),
                        mapTypeId: google.maps.MapTypeId.ROADMAP
                    };

                    map.setOptions(map_options);

                    for (var iCount = 0; iCount < data.length; iCount++) {
                        var oEvent = data[iCount];
                        if (oEvent.lat != null && oEvent.lon != null) {
                            var Latlng = new google.maps.LatLng(oEvent.lat, oEvent.lon);
                            var marker = new MarkerWithLabel({
                                position: Latlng,
                                map: map,
                                title: oEvent.regionName,
                                icon: eventmarker,
                                labelContent: oEvent.eventsCount,
                                labelAnchor: new google.maps.Point(26, 32),
                                labelClass: "labels-map", // the CSS class for the label
                                labelInBackground: false
                            });
                            showEvents(marker, oEvent.regionId)
                            //showInfo(marker, oEvent.regionName);
                            //closeInfo(marker);
                            $scope.m_oController.eventMarkers.push(marker);
                        }

                    }

                });


            });

        }

        function showEvents(marker, regionId) {
            google.maps.event.addListener(marker, 'click', function () {
                $scope.m_oController.m_oEventService.LoadEventsByRegionForMap(regionId).success(function (data) {
                    $scope.m_oController.EventHtml = '';
                    $scope.m_oController.m_iSelectedEventId = null;
                    $scope.m_oController.m_oEventList = data;
                });
            });
        }

        function showInfo(marker, stringToShow) {
            var localString = stringToShow;
            google.maps.event.addListener(marker, 'mouseover', function () {

                var content = '<div style="width: 200px; height: 70px"><p>' + localString + '</p></div>';
                infowindow.setContent(content);
                infowindow.setPosition(marker.position);
                infowindow.open(map, marker);

            });
        }

        function closeInfo(marker) {
            google.maps.event.addListener(marker, 'mouseout', function () {

                infowindow.close();

            });
        }

        function clearCountryMarkers(){

            var bRet = true;
            //clear
            if ($scope.m_oController.countryEventMarkers != null) {
                for (var iEventCount = 0; iEventCount < $scope.m_oController.countryEventMarkers.length; iEventCount++) {
                    $scope.m_oController.countryEventMarkers[iEventCount].setMap(null);
                    bRet = false;
                }

            }
            return bRet;
        }

        function clearRegionMarkers(){

            var bRet = true;
            //clear
            if ($scope.m_oController.eventMarkers != null) {
                for (var iEventCount = 0; iEventCount < $scope.m_oController.eventMarkers.length; iEventCount++) {
                    $scope.m_oController.eventMarkers[iEventCount].setMap(null);
                    bRet = false;
                }

            }
            return bRet;
        }
    }

    MapController.prototype.DownloadMedia = function (mediaMarker, media)
    {
        var localMedia = media;
        var oScope = this.m_oScope;
        google.maps.event.addListener(mediaMarker, 'click', function () {

            var a = document.createElement('a');
            a.href = oScope.m_oController.m_oMediaService.DownloadMedia(localMedia.id);
            a.click();

        });
    };

    MapController.prototype.clearMediaMarkers = function(){

        var oScope = this.m_oScope;
        //Ritorno true  se non ci sono media marker da eliminare, false altrimenti
        var bRet = true;
        //clear media
        if (oScope.m_oController.mediaMarkers != null) {
            for (var iMediaCount = 0; iMediaCount < oScope.m_oController.mediaMarkers.length; iMediaCount++) {
                oScope.m_oController.mediaMarkers[iMediaCount].setMap(null);
                bRet = false;
            }

        }

        oScope.m_oController.mediaMarkers = [];
        return bRet;
    }


    MapController.prototype.EventToHtml = function(event){

        var oScope = this.m_oScope;

        var row = '<hr align="center" size="1">';
        var html = '<b>Country: </b>' + event.countryCode + '<BR>'+
            '<b>Region: </b>' + event.regionName + '<BR>'+
            '<b>Start Date: </b>' + event.startDate + '<BR>';

        html = html + '<b>Start Hour: </b>';
        if (event.startHour != null)
            html = html + event.startHour;
        html = html + '<BR>';

        html = html + '<b>Description: </b>' + event.description + '<BR>';
        html = html + row;

        html = html + '<u><b>Duration</b></u><BR>';

        html = html + '<b>Unit: </b>';
        if (event.unitHour == true)
            html = html + 'Hours';
        else if (event.unitHour == false)
            html = html + 'Days';
        html = html + '<BR>';

        html = html + '<b>Value: </b>';
        if (event.unitValue != null)
            html = html + event.unitValue;
        html = html + '<BR>';

        html = html + '<b>Type: </b>';
        if (event.unitApproximated == true)
            html = html + 'Approximate';
        else if (event.unitHour == false)
            html = html + 'Exact';
        html = html + '<BR>';
        html = html + row;


        html = html + '<u><b>Wave Height</b></u><BR>';
        html = html + '<b>Type: </b>';
        if (event.waveHeightType != null && event.waveHeightType > 0)
            html = html + oScope.m_oController.m_oEventService.GetWaveHeightTypeByIndex(event.waveHeightType).name;
        html = html + '<BR>';
        html = html + '<b>Value: </b>';
        if (event.waveHeightValue != null)
            html = html + event.waveHeightValue;
        html = html + '<BR>';
        html = html + row;

        html = html + '<u><b>Wave Direction</b></u><BR>';
        if (event.waveDirectionType == null || event.waveDirectionType == 0)
        {
            html = html + '<b>Type: </b><BR>';
            html = html + '<b>Value: </b><BR>';
        }
        else {
            html = html + '<b>Type: </b>' + oScope.m_oController.m_oEventService.GetWaveDiectionTypeByIndex(event.waveDirectionType).name + '<BR>';
            if (event.waveDirectionType == 1)
                html = html + '<b>Value: </b>'+ event.waveDirectionDegree + '<BR>';
            else
                html = html + '<b>Value: </b>'+ event.waveDirectionClustered + '<BR>';
        }
        html = html + row;

        html = html + '<u><b>Wind Intensity</b></u><BR>';
        html = html + '<b>Type: </b>';
        if (event.windIntensityType != null)
            html = html + oScope.m_oController.m_oEventService.GetWindIntensityTypeByIndex(event.windIntensityType).name;
        html = html + '<BR>';

        html = html + '<b>Value: </b>';
        if (event.windIntensityValue != null)
            html = html + event.windIntensityValue;
        html = html + '<BR>';
        html = html + row;

        html = html + '<u><b>Wind Direction</b></u><BR>';
        if (event.windDirectionType == null || event.windDirectionType == 0)
        {
            html = html + '<b>Type: </b><BR>';
            html = html + '<b>Value: </b><BR>';
        }
        else {
            html = html + '<b>Type: </b>' + oScope.m_oController.m_oEventService.GetWindDiectionTypeByIndex(event.windDirectionType).name + '<BR>';
            if (event.windDirectionType == 1)
                html = html + '<b>Value: </b>'+ event.windDirectionDegree + '<BR>';
            else
                html = html + '<b>Value: </b>'+ event.windDirectionClustered + '<BR>';
        }
        html = html + row;


        html = html + '<u><b>Water Level</b></u><BR>';
        html = html + '<b>Type: </b>';
        if (event.waterLevelType != null && event.waterLevelType > 0)
            html = html + oScope.m_oController.m_oEventService.GetWaterLevelTypeByIndex(event.waterLevelType).name;
        html = html + '<BR>';
        html = html + '<b>Value: </b>';
        if (event.waterLevelValue != null)
            html = html + event.waterLevelValue;
        html = html + '<BR>';
        html = html + row;

        html = html + '<u><b>River Flooding</b></u><BR>';
        html = html + '<b>Peak Water discharge: </b>';
        if (event.peakWaterDischarge != null)
            html = html + event.peakWaterDischarge;
        html = html + '<BR>';
        html = html + '<b>Flood Height: </b>';
        if (event.floodHeight != null)
            html = html + event.floodHeight;
        html = html + '<BR>';
        html = html + row;

        //socio impacts
        html = html + '<u><b>Socio Impacts</b></u><BR>';
        if (event.socioimpacts != null)
        {
            for (var iCount = 0; iCount < event.socioimpacts.length; iCount++) {
                html = html + '<b>Category: </b>';
                html = html + event.socioimpacts[iCount].category + '<BR>';
                html = html + '<b>Subcategory: </b>';
                html = html + event.socioimpacts[iCount].subcategory + '<BR>';
                html = html + '<b>Description: </b>';
                html = html + event.socioimpacts[iCount].description + '<BR>';
                html = html + '<b>Unit of measure: </b>';
                html = html + event.socioimpacts[iCount].unitMeasure + '<BR>';
                html = html + '<b>Cost: </b>';
                html = html + event.socioimpacts[iCount].cost + '<BR>';
            }
        }
        html = html + row;

        //media
        html = html + '<u><b>Media</b></u><BR>';
        if (event.media != null) {
            for (var iCount = 0; iCount < event.media.length; iCount++) {
                html = html + '<b>' + event.media[iCount].shortDownloadPath + '</b><BR>';
            }
        }
        html = html + row;
        //gis
        html = html + '<u><b>Gis</b></u><BR>';
        if (event.gis != null){
            html = html + '<b>' + event.gis.shortGisFile + '</b><BR>';
            html = html + '<b>' + event.gis.shortInspireFile + '</b><BR>';
        }
        html = html + row;

        oScope.m_oController.EventHtml = html;


    };


    MapController.prototype.ShowMedia = function(event) {

        var oScope = this.m_oScope;

        if (event == null)
            return;

        if (!this.clearMediaMarkers() && this.m_iSelectedEventId == event.id) {
            this.m_iSelectedEventId = null;
            this.EventHtml = '';
            return;
        }

        this.m_iSelectedEventId = event.id;

        this.EventToHtml(event);

        if (event != null) {

            if (event.media != null) {
                //Set map center and zoom on first media
                //var mapCenter = new google.maps.LatLng(oInnerEvent.lat, oInnerEvent.lon);
                //map.setCenter(mapCenter);
                //map.setZoom(5);

                for (var imediaCount = 0; imediaCount < event.media.length; imediaCount++) {
                    //Show media
                    var imageMediaAttach = 'img/attach.png';
                    if (event.media[imediaCount].thumbnail != null)
                        imageMediaAttach = 'img/thumb/' + event.media[imediaCount].thumbnail;
                    var mediaLatlng = new google.maps.LatLng(event.media[imediaCount].lat, event.media[imediaCount].lon);
                    var mediaMarker = new google.maps.Marker({
                        position: mediaLatlng,
                        map: oScope.m_oController.m_oMap,
                        title: event.media[imediaCount].shortDownloadPath + '-' + event.media[imediaCount].description,
                        icon: imageMediaAttach
                    });

                    this.DownloadMedia(mediaMarker, event.media[imediaCount]);
                    oScope.m_oController.mediaMarkers.push(mediaMarker);

                }


            }

        }

    };


    MapController.$inject = [
        '$scope',
        '$location',
        'EventService',
        'LoginService',
        'MediaService',
        '$sanitize'

    ];

    return MapController;
}) ();
