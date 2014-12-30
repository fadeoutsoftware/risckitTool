/**
 * Created by s.adamo on 04/07/2014.
 */
var MapController = (function() {



    function MapController($scope, $location, oEventService, oLigingService, oMediaService, oSanitize, oModal) {
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
        this.m_oModal = oModal;
        this.m_sOrderBy = "startDate";
        this.m_bReverseOrder = false;
        this.m_bLoadingFlag = false;

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
                //remove region marker
                clearRegionMarkers();
                clearEventsInfo();
                $scope.m_oController.m_bLoadingFlag = false;
                $scope.m_oController.clearMediaMarkers();

                //Show marker for countries
                for (var iCount = 0; iCount < $scope.m_oController.countryEventMarkers.length; iCount++)
                {
                    $scope.m_oController.countryEventMarkers[iCount].setMap(map);
                }
            }

        });

        var eventmarker = 'img/marker_event.png';

        if (this.m_oLoginService.isLogged())
        {
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
        }
        else
        {
            $location.path('/');
        }

        function clearEventsInfo()
        {
            $scope.m_oController.EventHtml = '';
            $scope.m_oController.m_iSelectedEventId = null;
            $scope.m_oController.m_oEventList = [];
            $scope.$apply();
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
                            showEvents(marker, oEvent.regionId);
                            //showInfo(marker, oEvent.regionName);
                            //closeInfo(marker);
                            $scope.m_oController.eventMarkers.push(marker);
                        }

                    }

                });

                $scope.m_oController.m_bLoadingFlag = true;

                $scope.m_oController.m_oEventService.LoadEventsByCountryForList(locCountryCode).success(function (data) {
                    if ((angular.isDefined(data) && data!=null)) {
                        var iEventsCount = 0;

                        for (iEventsCount=0; iEventsCount<data.length; iEventsCount++) {
                            var sImageLink = 'img/event2.png';

                            var oEvent  = data[iEventsCount];

                            if (angular.isDefined(oEvent) && oEvent != null)
                            {
                                if (angular.isDefined(oEvent.media) && oEvent.media != null)
                                {
                                    if (oEvent.media.length>0) {
                                        if (oEvent.media[0].thumbnail != null)
                                            sImageLink = 'img/thumb/' + oEvent.media[0].thumbnail;
                                    }
                                }
                            }

                            data[iEventsCount].imagePath = sImageLink;
                        }
                    }

                    $scope.m_oController.EventHtml = '';
                    $scope.m_oController.m_iSelectedEventId = null;
                    $scope.m_oController.m_oEventList = data;
                    $scope.m_oController.clearMediaMarkers();
                    $scope.m_oController.m_bLoadingFlag = false;
                }).error(function(data,status){
                    $scope.m_oController.m_bLoadingFlag = false;
                });


            });

        }

        function showEvents(marker, regionId) {
            google.maps.event.addListener(marker, 'click', function () {

                $scope.m_oController.m_bLoadingFlag = true;

                $scope.m_oController.m_oEventService.LoadEventsByRegionForMap(regionId).success(function (data) {

                    if ((angular.isDefined(data) && data!=null)) {
                        var iEventsCount = 0;

                        for (iEventsCount=0; iEventsCount<data.length; iEventsCount++) {
                            var sImageLink = 'img/event2.png';

                            var oEvent  = data[iEventsCount];

                            if (angular.isDefined(oEvent) && oEvent != null)
                            {
                                if (angular.isDefined(oEvent.media) && oEvent.media != null)
                                {
                                    if (oEvent.media.length>0) {
                                        if (oEvent.media[0].thumbnail != null)
                                            sImageLink = 'img/thumb/' + oEvent.media[0].thumbnail;
                                    }
                                }
                            }

                            data[iEventsCount].imagePath = sImageLink;
                        }
                    }

                    $scope.m_oController.EventHtml = '';
                    $scope.m_oController.m_iSelectedEventId = null;
                    $scope.m_oController.m_oEventList = data;
                    $scope.m_oController.clearMediaMarkers();
                    $scope.m_oController.m_bLoadingFlag = false;
                }).error(function(data,status){
                    $scope.m_oController.m_bLoadingFlag = false;
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
        var oModal = this.m_oModal;
        google.maps.event.addListener(mediaMarker, 'click', function () {

            oScope.m_oController.m_oMediaService.PreviewMedia(localMedia.id).success(function(data){
                if (data != null && data != "") {

                    var templ = "<div class='modal-body' style='display: flex'>" +
                        "<img src= 'img/thumb/temp/" + data + "' style='max-height: 500px;'>" +
                        "</div>" +
                        "<div class='modal-footer'>" +
                        "<button class='col-xs-2 btn btn-warning' ng-click='m_oController.close()'>Close</button>" +
                        "<a ng-href='img/thumb/temp/" + data + "' download>" +
                        "<button class='col-lg-offset-1 col-xs-2 btn btn-primary'>Download</button>" +
                        "</a>" +
                        "</div>";

                    //preview if image
                    var modalInstance = oModal.open({
                        template: templ,
                        controller: 'PreviewController',
                        size: 'lg'
                    });

                }
                else
                {
                    //download per tutti gli altri files
                    var a = document.createElement('a');
                    a.href = oScope.m_oController.m_oMediaService.DownloadMedia(localMedia.id);
                    a.click();
                }
            });



        });
    };

    MapController.prototype.clearMediaMarkers = function(){

        var oScope = this.m_oScope;
        //true if no media marker to delete, false otherwise
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
    };


    MapController.prototype.EventToHtml = function(event){

        var oScope = this.m_oScope;

        var row = '<hr align="center" size="1"><hr/>';
        var html = '<b>Country: </b><body>' + event.countryCode + '</body><br/>'+
            '<b>Region: </b><body>' + event.regionName + '</body><br/>'+
            '<b>Start Date: </b><body>' + event.startDate + '</body><br/>';

        html = html + '<b>Start Hour: </b>';
        if (event.startHour != null)
            html = html + '<body>' + event.startHour + '</body>';
        html = html + '<br/>';

        html = html + '<b>Description: </b><body>' + event.description + '</body><br/>';
        html = html + row;

        html = html + '<h4><b>Duration</b></h4><br/>';

        html = html + '<b>Unit: </b>';
        if (event.unitHour == true)
            html = html + '<body>Hours</body>';
        else if (event.unitHour == false)
            html = html + '<body>Days<body>';
        html = html + '<br/>';

        html = html + '<b>Value: </b>';
        if (event.unitValue != null)
            html = html + '<body>' + event.unitValue + '</body>';
        html = html + '<br/>';

        html = html + '<b>Type: </b>';
        if (event.unitApproximated == true)
            html = html + '<body>Approximate</body>';
        else if (event.unitHour == false)
            html = html + '<body>Exact</body>';
        html = html + '<br/>';
        html = html + row;


        html = html + '<h4><b>Wave Height</b></h4><br/>';
        html = html + '<b>Type: </b>';
        if (event.waveHeightType != null && event.waveHeightType > 0)
            html = html + '<body>' + oScope.m_oController.m_oEventService.GetWaveHeightTypeByIndex(event.waveHeightType).name + '</body>';
        html = html + '<br/>';
        html = html + '<b>Value (meters): </b>';
        if (event.waveHeightValue != null)
            html = html + '<body>' +  event.waveHeightValue + '</body>';
        html = html + '<br/>';
        html = html + row;

        html = html + '<h4><b>Wave Direction</b></h4><br/>';
        if (event.waveDirectionType == null || event.waveDirectionType == 0)
        {
            html = html + '<b>Type: </b><br/>';
            html = html + '<b>Value: </b><br/>';
        }
        else {
            html = html + '<b>Type: </b>' + '<body>' + oScope.m_oController.m_oEventService.GetWaveDiectionTypeByIndex(event.waveDirectionType).name + '</body><br/>';
            if (event.waveDirectionType == 1)
                html = html + '<b>Value: </b><body>'+ event.waveDirectionDegree + '</body><br/>';
            else
                html = html + '<b>Value: </b><body>'+ event.waveDirectionClustered + '</body><br/>';
        }
        html = html + row;

        html = html + '<h4><b>Wind Intensity</b></h4><br/>';
        html = html + '<b>Type: </b>';
        if (event.windIntensityType != null && event.windIntensityType > 0)
            html = html + '<body>' + oScope.m_oController.m_oEventService.GetWindIntensityTypeByIndex(event.windIntensityType).name + '</body>';
        html = html + '<br/>';

        html = html + '<b>Value (m/s): </b>';
        if (event.windIntensityValue != null)
            html = html + '<body>' + event.windIntensityValue + '</body>';
        html = html + '<br/>';
        html = html + row;

        html = html + '<h4><b>Wind Direction</b></h4><br/>';
        if (event.windDirectionType == null || event.windDirectionType == 0)
        {
            html = html + '<b>Type: </b><br/>';
            html = html + '<b>Value: </b><br/>';
        }
        else {
            html = html + '<b>Type: </b><body>' + oScope.m_oController.m_oEventService.GetWindDiectionTypeByIndex(event.windDirectionType).name + '<body><br/>';
            if (event.windDirectionType == 1)
                html = html + '<b>Value: </b><body>'+ event.windDirectionDegree + '</body><br/>';
            else
                html = html + '<b>Value: </b><body>'+ event.windDirectionClustered + '</body><br/>';
        }
        html = html + row;


        html = html + '<h4><b>Water Level</b></h4><br/>';
        html = html + '<b>Type: </b>';
        if (event.waterLevelType != null && event.waterLevelType > 0)
            html = html + '<body>' + oScope.m_oController.m_oEventService.GetWaterLevelTypeByIndex(event.waterLevelType).name + '</body>';
        html = html + '<br/>';
        html = html + '<b>Value (meters): </b>';
        if (event.waterLevelValue != null)
            html = html + '<body>' + event.waterLevelValue + '</body>';
        html = html + '<br/>';
        html = html + row;

        html = html + '<h4><b>River Flooding</b></h4><br/>';
        html = html + '<b>Peak Water discharge (m^3/s): </b>';
        if (event.peakWaterDischarge != null)
            html = html + '<body>' + event.peakWaterDischarge + '</body>';
        html = html + '<br/>';
        html = html + '<b>Flood Height (meters): </b>';
        if (event.floodHeight != null)
            html = html + '<body>' + event.floodHeight + '</body>';
        html = html + '<br/>';
        html = html + row;

        //socio impacts
        html = html + '<h4><b>Impacts</b></h4><br/>';
        if (event.socioimpacts != null)
        {
            for (var iCount = 0; iCount < event.socioimpacts.length; iCount++) {
                html = html + '<b>Category: </b>';
                html = html + '<body>' + event.socioimpacts[iCount].category + '</body><br/>';
                html = html + '<b>Subcategory: </b>';
                html = html + '<body>' + event.socioimpacts[iCount].subcategory + '</body><br/>';
                html = html + '<b>Description: </b>';
                html = html + '<body>' + event.socioimpacts[iCount].description + '</body><br/>';
                html = html + '<b>Unit of measure: </b>';
                html = html + '<body>' + event.socioimpacts[iCount].unitMeasure + '</body><br/>';
                html = html + '<b>Cost: </b>';
                html = html + '<body>' + event.socioimpacts[iCount].cost + '</body><br/>';
            }
        }
        html = html + row;

        //media
        html = html + '<h4><b>Media</b></h4><br/>';
        if (event.media != null) {
            for (var iCount = 0; iCount < event.media.length; iCount++) {
                html = html + '<b><body>' + event.media[iCount].shortDownloadPath + '</body></b><br/>';
            }
        }
        html = html + row;
        //gis
        html = html + '<h4><b>Gis</b></h4><br/>';
        if (event.gis != null){
            html = html + '<b><body>' + event.gis.shortGisFile + '</body></b><br/>';
            html = html + '<b><body>' + event.gis.shortInspireFile + '</body></b><br/>';
        }
        html = html + row;

        oScope.m_oController.EventHtml = html;


    };

    MapController.prototype.ShowPleaseClickRow = function() {
        if (angular.isDefined(this.m_oEventList) == false) return true;
        if (this.m_oEventList == null) return true;
        if (this.m_oEventList.length == 0) return true;

        return false;
    }

    MapController.prototype.ShowMedia = function(event) {

        var oScope = this.m_oScope;

        if (event == null)
            return;

        if (!this.clearMediaMarkers() && this.m_iSelectedEventId == event.id) {
            this.m_iSelectedEventId = null;
            this.EventHtml = '';
            this.m_oPdfLink = '';
            return;
        }

        this.m_iSelectedEventId = event.id;

        this.EventToHtml(event);

        //this.createPdf();

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


    MapController.prototype.createPdf = function() {
        var oScope = this.m_oScope;
        /*
        oScope.m_oController.m_oEventService.CreatePdf(oScope.m_oController.EventHtml).success(function (data) {
            window.open("pdf/" + data, 'pdf');
        });
        */

        return oScope.m_oController.m_oEventService.CreatePdf2(oScope.m_oController.m_iSelectedEventId);

    };


    MapController.$inject = [
        '$scope',
        '$location',
        'EventService',
        'LoginService',
        'MediaService',
        '$sanitize',
        '$modal'

    ];

    return MapController;
}) ();
