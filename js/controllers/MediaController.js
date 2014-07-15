/**
 * Created by s.adamo on 07/07/2014.
 */
/**
 * Created by s.adamo on 04/07/2014.
 */
var MediaController = (function() {

    function MediaController($scope, $location, oSharedService, oEventService) {
        this.m_oScope = $scope;
        this.m_oScope.m_oController = this;
        this.m_oLocation = $location;
        this.m_oEventService = oEventService;
        this.m_sFilePath = null;
        this.uploadRightAway = true;
        this.description = null;
        this.date = null;
        this.m_oEventService.setUploaded(false)
        this.Files = [];
        this.m_oSharedService = oSharedService;
        this.NewMedia;
        this.m_oPositionMark = null;
        this.Marker;


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

            $scope.m_oController.m_oPositionMark = location;
        }

        $scope.$watch('m_oController.m_sFilePath', function(newVal, oldVal) {
            if (newVal !== oldVal) {

                $scope.m_oController.NewMedia = new Object();
                $scope.m_oController.NewMedia.lat = $scope.m_oController.m_oPositionMark.lat();
                $scope.m_oController.NewMedia.lon = $scope.m_oController.m_oPositionMark.lng();
                $scope.m_oController.NewMedia.latlon = $scope.m_oController.m_oPositionMark;
                $scope.m_oController.NewMedia.filename = newVal.name;
                $scope.m_oController.NewMedia.description = $scope.m_oController.description;
                $scope.m_oController.NewMedia.date = $scope.m_oController.date;
                $scope.m_oController.m_oSharedService.getEvent().Media.push($scope.m_oController.NewMedia);
                $scope.m_oController.Files.push(newVal);
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

            $scope.m_oController.m_oEventService.UploadMedia($scope.m_oController.m_oSharedService.getEvent(), $scope.m_oController.NewMedia, $scope.selectedFiles[index]);

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
        this.m_oEventService.UpdateMedia(this.m_oScope.m_oController.NewMedia).success(function (data) {

            if (data != null)
                oController.m_oLocation.path('event');
            else
                alert('Error during save');
        });
    };

        MediaController.prototype.Uploaded = function() {
            return this.m_oEventService.Uploaded();

        };

    MediaController.prototype.cancel = function() {
        this.m_oLocation.path('event');

    };


    MediaController.$inject = [
        '$scope',
        '$location',
        'SharedService',
        'EventService'
    ];


    return MediaController;
}) ();
