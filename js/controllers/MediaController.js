/**
 * Created by s.adamo on 07/07/2014.
 */
/**
 * Created by s.adamo on 04/07/2014.
 */
var MediaController = (function() {

    function MediaController($scope, $location, oSharedService, oEventService, oMediaService) {
        this.m_oScope = $scope;
        this.m_oScope.m_oController = this;
        this.m_oLocation = $location;
        this.m_oEventService = oEventService;
        this.m_oMediaService = oMediaService;
        this.m_sFilePath = null;
        this.uploadRightAway = true;
        this.description = null;
        this.m_oUploading = false;
        this.date = null;
        this.m_oSharedService = oSharedService;
        this.NewMedia;
        this.m_oPositionMark = null;
        this.Marker;
        this.Media = [];

        var oRootScope = this.m_oScope;

        var map_canvas = document.getElementById('media_map_canvas');
        var map_options = {
            center: new google.maps.LatLng(44.40338, 2.17403),
            zoom: 5,
            mapTypeId: google.maps.MapTypeId.ROADMAP
        }

        var map = new google.maps.Map(map_canvas, map_options);

        google.maps.event.addListener(map, 'click', function(event) {

            placeMarker(event.latLng);

        });

        function placeMarker(location) {

            if (this.Marker != null)
                this.Marker.setMap(null);

            this.Marker = new google.maps.Marker({
                position: location,
                map: map,
                draggable: true
            });

            oRootScope.m_oController.m_oPositionMark = location;
            oRootScope.$apply();
        }

        var oEvent = this.m_oSharedService.getEvent();
        if (oEvent != null)
        {
            if (oEvent.Media == null && oEvent.id != null) {
                this.m_oMediaService.LoadMedia(oEvent.id).success(function (data) {

                    //add marker
                    for (var i = 0; i < data.length; i++) {
                        var myLatlng = new google.maps.LatLng(data[i].lat, data[i].lon);
                        $scope.m_oController.m_oPositionMark = myLatlng;
                        // To add the marker to the map, use the 'map' property
                        var marker = new google.maps.Marker({
                            position: myLatlng,
                            map: map
                        });

                        oEvent.Media = data;
                    }

                    $scope.m_oController.description = oEvent.Media[0].description;
                    $scope.m_oController.date = oEvent.Media[0].date;
                });
            }
            else if (oEvent.Media != null)
            {
                //add marker
                for (var i = 0; i < oEvent.Media.length; i++) {
                    var myLatlng = new google.maps.LatLng(oEvent.Media[i].lat, oEvent.Media[i].lon);
                    $scope.m_oController.m_oPositionMark = myLatlng;
                    // To add the marker to the map, use the 'map' property
                    var marker = new google.maps.Marker({
                        position: myLatlng,
                        map: map
                    });
                }

                if (oEvent.Media.length > 0) {
                    $scope.m_oController.description = oEvent.Media[0].description;
                    $scope.m_oController.date = oEvent.Media[0].date;
                }
            }
    }

        $scope.$watch('m_oController.m_sFilePath', function(newVal, oldVal) {
            if (newVal !== oldVal) {

                $scope.m_oController.NewMedia = new Object();
                $scope.m_oController.NewMedia.lat = $scope.m_oController.m_oPositionMark.lat();
                $scope.m_oController.NewMedia.lon = $scope.m_oController.m_oPositionMark.lng();
                $scope.m_oController.NewMedia.latlon = $scope.m_oController.m_oPositionMark;
                $scope.m_oController.NewMedia.downloadPath = newVal.name;
                $scope.m_oController.NewMedia.description = $scope.m_oController.description;
                $scope.m_oController.NewMedia.date = $scope.m_oController.date;
                if ($scope.m_oController.m_oSharedService.getEvent().Media == null)
                    $scope.m_oController.m_oSharedService.getEvent().Media = new Array();
                $scope.m_oController.m_oSharedService.getEvent().Media.push($scope.m_oController.NewMedia);
                //$scope.m_oController.Files.push(newVal);
            }
        });

        $scope.onFileSelect = function ($files) {

            $scope.selectedFiles = [];
            $scope.progress = [];
            if ($scope.upload && $scope.upload.length > 0) {
                for (var i = 0; i < $scope.upload.length; i++) {
                    if ($scope.upload[i] != null) {
                        $scope.upload[i].abort();
                    }
                }
            }
            $scope.upload = [];
            $scope.uploadResult = [];
            $scope.selectedFiles = $files;
            $scope.dataUrls = [];
            for (var i = 0; i < $files.length; i++) {
                var $file = $files[i];
                if (window.FileReader && $file.type.indexOf('image') > -1) {
                    var fileReader = new FileReader();
                    fileReader.readAsDataURL($files[i]);
                    var loadFile = function (fileReader, index) {
                        fileReader.onload = function (e) {
                            $timeout(function () {
                                $scope.dataUrls[index] = e.target.result;
                            });
                        }
                    }(fileReader, i);
                }
                $scope.progress[i] = -1;
                if ($scope.m_oController.uploadRightAway) {
                    $scope.start(i);
                }

            }


        };

        $scope.start = function (index) {
            $scope.progress[index] = 0;
            $scope.errorMsg = null;
            $scope.m_oController.m_oUploading = true;
            $scope.m_oController.m_oEventService.Save($scope.m_oController.m_oSharedService.getEvent()).success(function(data){
                $scope.m_oController.m_oSharedService.getEvent().id = data.id;
                $scope.m_oController.NewMedia.eventId = data.id;
                $scope.m_oController.m_oEventService.SaveMedia($scope.m_oController.NewMedia).success(function(data){
                    $scope.m_oController.NewMedia.id = data.id;
                    $scope.m_oController.m_oEventService.UploadMedia($scope.m_oController.m_oSharedService.getEvent(), $scope.m_oController.NewMedia, $scope.selectedFiles[index]).success(function(data){
                        $scope.m_oController.m_oSharedService.getEvent().Media = data;
                        $scope.m_oController.m_oUploading = false;
                    });
                })
            });



        };

    }

    MediaController.prototype.Save = function() {

        if (this.m_oScope.m_oController.NewMedia == null) {
            this.m_oLocation.path('event');
            return;
        }

        if (this.m_oScope.m_oController.NewMedia != null) {
            this.m_oScope.m_oController.NewMedia.description = this.m_oScope.m_oController.description;
            this.m_oScope.m_oController.NewMedia.date = this.m_oScope.m_oController.date;
        }

        var oController = this.m_oScope.m_oController;
        this.m_oEventService.SaveMedia(this.m_oScope.m_oController.NewMedia).success(function (data) {

            if (data != null)
                oController.m_oLocation.path('event');
            else
                alert('Error during save');
        });
    };


    MediaController.prototype.cancel = function() {
        this.m_oLocation.path('event');

    };

    MediaController.prototype.getMedia = function() {
        return this.m_oSharedService.getEvent().Media;

    };

    MediaController.prototype.canUpload = function() {
        return this.m_oScope.m_oController.m_oPositionMark != null &&
            this.m_oScope.m_oController.description != null &&
            this.m_oScope.m_oController.date != null;

    };

    MediaController.prototype.DeleteMedia = function(idMedia, idEvent) {

        this.m_oMediaService.DeleteMedia(idMedia, idEvent).success(function(data){
            this.m_oSharedService.getEvent().Media = data;
        });

    };




    MediaController.$inject = [
        '$scope',
        '$location',
        'SharedService',
        'EventService',
        'MediaService'
    ];


    return MediaController;
}) ();
