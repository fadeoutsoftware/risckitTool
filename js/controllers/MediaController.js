/**
 * Created by s.adamo on 07/07/2014.
 */
/**
 * Created by s.adamo on 04/07/2014.
 */
var MediaController = (function() {

    function MediaController($scope, $location, oSharedService, oEventService, oMediaService, $routeParams, oLoginService) {
        this.m_oScope = $scope;
        this.m_oScope.m_oController = this;
        this.m_oLocation = $location;
        this.m_oEventService = oEventService;
        this.m_oMediaService = oMediaService;
        this.m_oLoginService = oLoginService;
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
        this.m_oRouteParams = $routeParams;
        this.m_oMediaService.setUnchanged();
        var oRootScope = this.m_oScope;
        var iIdMedia = this.m_oRouteParams.idmedia;


        var map_canvas = document.getElementById('media_map_canvas');
        var map_options = {
            center: new google.maps.LatLng(44.40338, 2.17403),
            zoom: 5,
            mapTypeId: google.maps.MapTypeId.ROADMAP
        };
        var map = new google.maps.Map(map_canvas, map_options);

        google.maps.event.addListener(map, 'click', function (event) {

            placeMarker(event.latLng);

            // set as modified
            $scope.m_oController.m_oMediaService.setAsModified();
        });


        function placeMarker(location) {

            if ($scope.m_oController.Marker != null) {
                $scope.m_oController.Marker.setPosition(location);
                $scope.m_oController.Marker.setMap(map);
            }

            if ($scope.m_oController.Marker == null) {

                $scope.m_oController.Marker = new google.maps.Marker({
                    position: location,
                    map: map,
                    draggable: true
                });

                //oRootScope.$apply();
            }

            oRootScope.m_oController.m_oPositionMark = location;
        }


        var oEvent = this.m_oSharedService.getEvent();
        if (oEvent != null) {
            if (iIdMedia != null) {
                this.m_oMediaService.getMedia(iIdMedia).success(function (data) {

                    //add marker
                    var myLatlng = new google.maps.LatLng(data.lat, data.lon);
                    $scope.m_oController.m_oPositionMark = myLatlng;
                    // To add the marker to the map, use the 'map' property
                    $scope.m_oController.Marker = new google.maps.Marker({
                        position: myLatlng,
                        map: map

                    });

                    $scope.m_oController.description = data.description;
                    $scope.m_oController.date = data.date;
                    $scope.m_oController.NewMedia = data;

                    google.maps.event.addListener($scope.m_oController.Marker, 'click', function() {
                        var content = 'Media';
                        if ($scope.m_oController.NewMedia != null)
                            content = '<div style="width: 100px; height: 100px"><a ng-href="{{m_oController.DownloadMedia(m_oController.NewMedia.id)}}"></div>'
                        infowindow.setContent(content);
                        infowindow.open(map,$scope.m_oController.Marker);
                    });

                });

            }
        }


        $scope.$watch('m_oController.m_sFilePath', function (newVal, oldVal) {
            if (newVal !== oldVal) {

                $scope.m_oController.m_oMediaService.setAsModified();
                $scope.m_oController.NewMedia = new Object();
                $scope.m_oController.NewMedia.lat = $scope.m_oController.m_oPositionMark.lat();
                $scope.m_oController.NewMedia.lon = $scope.m_oController.m_oPositionMark.lng();
                $scope.m_oController.NewMedia.latlon = $scope.m_oController.m_oPositionMark;
                //$scope.m_oController.NewMedia.downloadPath = newVal.name;
                $scope.m_oController.NewMedia.description = $scope.m_oController.description;
                $scope.m_oController.NewMedia.date = $scope.m_oController.date;
                if ($scope.m_oController.m_oSharedService.getEvent().Media == null)
                    $scope.m_oController.m_oSharedService.getEvent().Media = new Array();
                //$scope.m_oController.m_oSharedService.getEvent().Media.push($scope.m_oController.NewMedia);
                //$scope.m_oController.Files.push(newVal);
            }
        });

        $scope.$on('$locationChangeStart', function (event, next, current) {

            if ($scope.m_oController.m_oMediaService.isModified()) {
                var bAnswer = confirm("Are you sure you want to leave this page without saving?")
                if (!bAnswer) {
                    event.preventDefault();

                }
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
            $scope.m_oController.m_oEventService.Save($scope.m_oController.m_oSharedService.getEvent()).success(function (data) {
                $scope.m_oController.m_oSharedService.getEvent().id = data.id;
                $scope.m_oController.NewMedia.eventId = data.id;
                $scope.m_oController.m_oMediaService.SaveMedia($scope.m_oController.NewMedia).success(function (data) {
                    $scope.m_oController.NewMedia = data;
                    if ($scope.selectedFiles[index] != null) {
                        $scope.m_oController.m_oMediaService.UploadMedia($scope.m_oController.m_oSharedService.getEvent(), $scope.m_oController.NewMedia, $scope.selectedFiles[index]).success(function (data) {
                            if (data != null && data != '') {
                                $scope.m_oController.NewMedia = data;
                                $scope.m_oController.m_oSharedService.getEvent().Media.push(data);
                                // set unchanged
                                $scope.m_oController.m_oMediaService.setUnchanged();

                            }
                            else
                            {
                                $scope.m_oController.NewMedia.downloadPath = null;
                                $scope.m_oController.NewMedia.shortDownloadPath = null;
                                $scope.m_oController.m_oSharedService.getEvent().Media.push($scope.m_oController.NewMedia);
                                alert('Error uploading media')
                            }

                            $scope.m_oController.m_oUploading = false;
                        });
                    }
                    else
                        $scope.m_oController.NewMedia = null;
                })
            });


        };

    }

    MediaController.prototype.Save = function() {

        if (this.m_oScope.m_oController.NewMedia == null) {
            alert("Select attachment!");
            return;
        }

        if (this.m_oScope.m_oController.NewMedia != null) {
            this.m_oScope.m_oController.NewMedia.description = this.m_oScope.m_oController.description;
            this.m_oScope.m_oController.NewMedia.date = this.m_oScope.m_oController.date;
            this.m_oScope.m_oController.NewMedia.lat = this.m_oScope.m_oController.m_oPositionMark.lat();
            this.m_oScope.m_oController.NewMedia.lon = this.m_oScope.m_oController.m_oPositionMark.lng();
        }

        var oController = this.m_oScope.m_oController;
        if (oController.m_oMediaService.isModified()) {
            this.m_oMediaService.SaveMedia(this.m_oScope.m_oController.NewMedia).success(function (data) {

                if (data != null) {
                    oController.m_oMediaService.setUnchanged();
                    oController.m_oLocation.path('event');
                }
                else
                    alert('Error during save');
            });
        }
    };


    MediaController.prototype.cancel = function() {
        this.m_oLocation.path('event');

    };

    MediaController.prototype.canUpload = function() {
        return this.m_oScope.m_oController.m_oPositionMark != null &&
            this.m_oScope.m_oController.description != null &&
            this.m_oScope.m_oController.date != null;

    };

    MediaController.prototype.DownloadMedia = function(idMedia) {

        return this.m_oMediaService.DownloadMedia(idMedia);

    };

    MediaController.prototype.getIsReadOnly = function () {
        var isReadOnly = false;
        var oEvent = this.m_oSharedService.getEvent();
        if (oEvent.editMode != null)
            isReadOnly = !oEvent.editMode;

        if (this.m_oLoginService.isAdmin())
            isReadOnly = false;

        return isReadOnly;
    };


    MediaController.$inject = [
        '$scope',
        '$location',
        'SharedService',
        'EventService',
        'MediaService',
        '$routeParams',
        'LoginService'
    ];


    return MediaController;
}) ();
