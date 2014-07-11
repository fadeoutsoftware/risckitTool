/**
 * Created by s.adamo on 02/07/2014.
 */
var EventController = (function() {

    function EventController($scope, $location, $modal, oEventService, oSharedService) {
        this.m_oScope = $scope;
        this.m_oScope.m_oController = this;
        this.m_oLocation = $location;
        this.m_oModal = $modal;
        this.m_oEventService = oEventService; //Service
        this.m_oSharedService = oSharedService;
        this.m_oEvent = new Object();
        this.m_oEvent.Media = new Array(); //ipotizzo si possano inserire più media con coordinate diverse
        this.m_oEvent.GIS = null; //ipotizzo sia solo un file
        this.uploadRightAway = true;

        //Init html
        this.WaveDiectionType = 'Degrees';
        this.WindDiectionType = 'Degrees';
        this.m_oWaveHeightType = ["Mean", "Significant", "Peak"];
        this.m_oWaveDiectionType = ["Degrees", "Clustered"];
        this.m_oWindDiectionType = ["Degrees", "Clustered"];
        this.m_oWindIntensityType = ["Maximun values", "Time average", "Maximun gust"];
        this.m_oCostDetails = ["Direct Cost", "Business Interruption Cost", "Indirect Cost", "Intangible Cost", "Risk mitigation Cost"];
        this.Flooding = false;

        //load countries
        this.m_oEventService.GetAllCountries().success(function (data, status) {
            //$scope.m_oController.m_oEvent.countryCode = null;
            $scope.m_oController.m_oCountries = data;
        });

        $scope.$watch('m_oController.m_oEvent.countryCode', function (newVal, oldVal) {
            if (newVal !== oldVal) {
                //load regions
                $scope.m_oController.m_oEventService.GetAllRegions($scope.m_oController.m_oEvent.countryCode).success(function (data, status) {
                    $scope.m_oController.m_oEvent.countryId = null;
                    $scope.m_oController.m_oRegions = data;
                });
            }
        });

        if (this.m_oSharedService.getEvent() != null) {
            $scope.m_oController.m_oEvent = this.m_oSharedService.getEvent();
            $scope.m_oController.m_oEvent.Media = this.m_oSharedService.getEvent().Media;
        }

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

            $scope.upload[index] = $scope.m_oController.m_oEventService.Upload($scope.m_oController.m_oEvent, $scope.selectedFiles[index]);
            $scope.upload[index].then(function (response) {
                $timeout(function () {
                    $scope.uploadResult.push(response.data);
                });
            }, function (response) {
                if (response.status > 0) $scope.errorMsg = response.status + ': ' + response.data;
            }, function (evt) {
                // Math.min is to fix IE which reports 200% sometimes
                $scope.progress[index] = Math.min(100, parseInt(100.0 * evt.loaded / evt.total));
            });
            $scope.upload[index].xhr(function (xhr) {
//				xhr.upload.addEventListener('abort', function() {console.log('abort complete')}, false);
            });

        };

    }


    EventController.prototype.AddMedia = function (partial, size) {
        this.m_oSharedService.setEvent(this.m_oEvent);
        this.m_oLocation.path('media');

    };

    EventController.prototype.AddGIS = function (size) {

        var oScope = this.m_oScope;

        var modalInstance = this.m_oModal.open({
            templateUrl:  'partials/gis.html',
            controller: GisController,
            size: size
        });

        modalInstance.result.then(function(GISFiles, InspireFiles) {
            oScope.m_oController.m_oEvent.GIS = new Object();
            oScope.m_oController.m_oEvent.GIS.GisFile = GISFiles;
            oScope.m_oController.m_oEvent.GIS.InspireFile = InspireFiles;
        });

    };

    EventController.prototype.Save = function() {

        if (this.m_oEvent != null)
        {
            this.m_oEventService.Save(this.m_oEvent).success(function (data, status) {
                alert("Saved");
            });
        }

    };


    EventController.$inject = [
            '$scope',
            '$location',
            '$modal',
            'EventService',
            'SharedService'
        ];

    return EventController;
}) ();
