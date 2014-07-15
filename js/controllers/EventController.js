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


        if (this.m_oScope.m_oController.m_oSharedService.getEvent() == null)
        {
            this.m_oScope.m_oController.m_oEvent = new Object();
            this.m_oScope.m_oController.m_oEvent.Media = new Array(); //ipotizzo si possano inserire più media con coordinate diverse
            this.m_oScope.m_oController.m_oEvent.GIS = null; //ipotizzo sia solo un file
        }
        else
        {
            this.m_oScope.m_oController.m_oEvent = this.m_oScope.m_oController.m_oSharedService.getEvent();
            this.m_oScope.m_oController.m_oEvent.Media = this.m_oSharedService.getEvent().Media;
        }

        this.uploadRightAway = true;

        //Init html
        this.m_oEvent.WaveDiectionType = 0;
        this.m_oEvent.WindDiectionType = 0;
        this.m_oEvent.unitHour = true;
        this.m_oEvent.unitApproximated = true;
        this.m_oWaveHeightType = ["Mean", "Significant", "Peak"];
        this.m_oWaveDiectionType = ["Degrees", "Clustered"];
        this.m_oWindDiectionType = ["Degrees", "Clustered"];
        this.m_oWindIntensityType = ["Maximun values", "Time average", "Maximun gust"];
        this.m_oCostDetails = ["Direct Cost", "Business Interruption Cost", "Indirect Cost", "Intangible Cost", "Risk mitigation Cost"];
        this.Flooding = false;

        //load countries
         this.m_oEventService.GetAllCountries().success(function (data, status) {
             $scope.m_oController.m_oEvent.countryCode = null;
             $scope.m_oController.m_oCountries = data;
         });

        $scope.$watch('m_oController.m_oEvent.countryCode', function (newVal, oldVal) {
            if (newVal !== oldVal) {
                //load regions
                $scope.m_oController.m_oEventService.GetAllRegions($scope.m_oController.m_oEvent.countryCode).success(function (data, status) {
                    $scope.m_oController.m_oRegions = data;
                    if ($scope.m_oController.m_oSharedService.getEvent() != null)
                    {
                        $scope.m_oController.m_oEvent.countryId = $scope.m_oController.m_oSharedService.getEvent().countryId;
                        $scope.$apply();
                    }

                });
            }
        });


        if (this.m_oSharedService.getEvent() != null) {
            $scope.m_oController.m_oEvent.countryCode = this.m_oSharedService.getEvent().countryCode;
        }



        $scope.onFileSelect = function ($files, parameter) {

            if ($files != null && $files.length > 0) {
                if (parameter == 'waveHeightInspire')
                    $scope.m_oController.m_oEvent.waveHeightInspire = $files[0].name;
                if (parameter == 'waveHeightTimeSeries')
                    $scope.m_oController.m_oEvent.waveHeightTimeSeries = $files[0].name;
                if (parameter == 'waveDirectionInspire')
                    $scope.m_oController.m_oEvent.waveDirectionInspire = $files[0].name;
                if (parameter == 'waveDirectionTimeSeries')
                    $scope.m_oController.m_oEvent.waveDirectionTimeSeries = $files[0].name;
                if (parameter == 'windIntensityInspire')
                    $scope.m_oController.m_oEvent.windIntensityInspire = $files[0].name;
                if (parameter == 'windIntensitySeries')
                    $scope.m_oController.m_oEvent.windIntensitySeries = $files[0].name;
                if (parameter == 'windDirectionInspire')
                    $scope.m_oController.m_oEvent.windDirectionInspire = $files[0].name;
                if (parameter == 'windDirectionTimeSeries')
                    $scope.m_oController.m_oEvent.windDirectionTimeSeries = $files[0].name;
                if (parameter == 'peakWaterInpire')
                    $scope.m_oController.m_oEvent.peakWaterInpire = $files[0].name;
                if (parameter == 'peakWaterTimeSeries')
                    $scope.m_oController.m_oEvent.peakWaterTimeSeries = $files[0].name;
                if (parameter == 'floodHeightInspire')
                    $scope.m_oController.m_oEvent.floodHeightInspire = $files[0].name;
                if (parameter == 'floodHeightTimeSeries')
                    $scope.m_oController.m_oEvent.floodHeightTimeSeries = $files[0].name;
                if (parameter == 'reporedCasualtiesInspire')
                    $scope.m_oController.m_oEvent.reporedCasualtiesInspire = $files[0].name;
                if (parameter == 'reporedCasualtiesTimeSeries')
                    $scope.m_oController.m_oEvent.reporedCasualtiesTimeSeries = $files[0].name;
                if (parameter == 'damageToBuildingsInspire')
                    $scope.m_oController.m_oEvent.damageToBuildingsInspire = $files[0].name;
                if (parameter == 'damageToBuildingsTimeSeries')
                    $scope.m_oController.m_oEvent.damageToBuildingsTimeSeries = $files[0].name;
            }
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
            $scope.parameter = parameter
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

            $scope.m_oController.m_oEventService.Upload($scope.m_oController.m_oEvent, $scope.selectedFiles[index], $scope.parameter);
            /*$scope.upload[index].then(function (response) {
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
            });*/

        };

    }


    EventController.prototype.AddMedia = function (partial, size) {
        this.m_oSharedService.setEvent(this.m_oEvent);
        this.m_oLocation.path('media');

    };

    EventController.prototype.AddGIS = function (size) {
        this.m_oSharedService.setEvent(this.m_oEvent);
        var oScope = this.m_oScope;

        var modalInstance = this.m_oModal.open({
            templateUrl:  'partials/gis.html',
            controller: GisController,
            size: size
        });

        modalInstance.result.then(function(GISFilesName, InspireFilesName) {
            if (GISFilesName != null)
                oScope.m_oController.m_oEvent.GIS.GisFile = GISFilesName.name;
            if (InspireFilesName != null)
                oScope.m_oController.m_oEvent.GIS.InspireFile = InspireFilesName.name;
        });

    };

    EventController.prototype.Save = function() {

        var oScope = this.m_oScope;

        if (this.m_oEvent != null)
        {
            this.m_oEventService.Save(this.m_oEvent).success(function (data, status) {

                //Per ora salviamo tutto in modo separato perchè non riusciamo a far funzionare la deserializzazione
                //di liste contenute
                if (data != null) {

                    var answer = confirm("Do you want to insert another event?");
                    if (answer)
                        oScope.m_oController.m_oEvent = new Object();
                    else
                        oScope.m_oController.m_oLocation.path('map');
                }
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
