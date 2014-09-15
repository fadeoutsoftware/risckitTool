/**
 * Created by s.adamo on 02/07/2014.
 */
var EventController = (function() {

    function EventController($scope, $location, $modal, oEventService, oSharedService, oLoginService, oMediaService) {
        this.m_oScope = $scope;
        this.m_oScope.m_oController = this;
        this.m_oLocation = $location;
        this.m_oModal = $modal;
        this.m_oEventService = oEventService; //Service
        this.m_oSharedService = oSharedService;
        this.m_oLoginService = oLoginService;
        this.m_oMediaService = oMediaService;
        this.Flooding = false;

        if (this.m_oScope.m_oController.m_oSharedService.getEvent() == null)
        {
            this.m_oScope.m_oController.m_oEvent = new Object();
            this.m_oScope.m_oController.m_oEvent.userId = this.m_oLoginService.getUserId();
            this.m_oScope.m_oController.m_oEvent.Media = null; //ipotizzo si possano inserire più media con coordinate diverse
            this.m_oScope.m_oController.m_oEvent.GIS = null; //ipotizzo sia solo un file
        }
        else
        {
            this.m_oScope.m_oController.m_oEvent = this.m_oScope.m_oController.m_oSharedService.getEvent();
            if (this.m_oSharedService.getEvent().Media == null)
            {
                //load Media
                this.m_oMediaService.LoadMedia( $scope.m_oController.m_oEvent.id).success(function(data){
                    $scope.m_oController.m_oEvent.Media = data;
                });
            }
            else
            {
                this.m_oScope.m_oController.m_oEvent.Media = this.m_oSharedService.getEvent().Media;
            }
            if (this.m_oScope.m_oController.m_oEvent.peakWaterDischarge != null || this.m_oScope.m_oController.m_oEvent.floodHeight != null)
                this.Flooding = true;

        }

        if (this.m_oLoginService.isLogged())
            this.m_oScope.m_oController.m_oEvent.login = this.m_oLoginService.getUserName();

        this.uploadRightAway = true;

        //load countries and region
        if (this.m_oLoginService.isLogged()) {
            this.m_oEventService.LoadCountries();

            if (this.m_oSharedService.getEvent() != null) {
                $scope.m_oController.m_oEvent.countryCode = this.m_oSharedService.getEvent().countryCode;
                $scope.m_oController.m_oEventService.GetAllRegions($scope.m_oController.m_oEvent.countryCode).success(function (data, status) {
                    $scope.m_oController.m_oRegions = data;
                    if ($scope.m_oController.m_oSharedService.getEvent() != null) {
                        $scope.m_oController.m_oEvent.countryId = $scope.m_oController.m_oSharedService.getEvent().countryId;
                    }

                });
            }
            else
            {
                //Init html
                this.m_oEvent.waveHeightType = 0;
                this.m_oEvent.waveDirectionType = 0;
                this.m_oEvent.windIntensityType = 0;
                this.m_oEvent.windDirectionType = 0;
                this.m_oEvent.costDetail = 0;
                this.m_oEvent.waterLevelType = 0;
                this.m_oEvent.unitHour = true;
                this.m_oEvent.unitApproximated = true;

            }

        }

        $scope.$watch('m_oController.m_oEvent.countryCode', function (newVal, oldVal) {
            if (newVal != oldVal) {
                //load regions
                if ($scope.m_oController.m_oEvent.countryCode != null) {
                    $scope.m_oController.m_oEventService.GetAllRegions($scope.m_oController.m_oEvent.countryCode).success(function (data, status) {
                        $scope.m_oController.m_oRegions = data;
                        if ($scope.m_oController.m_oSharedService.getEvent() != null) {
                            $scope.m_oController.m_oEvent.countryId = $scope.m_oController.m_oSharedService.getEvent().countryId;
                            $scope.$apply();
                        }

                    });
                }
            }
        });

        $scope.$watch('m_oController.m_oEvent.countryId', function (newVal, oldVal) {
            if (newVal != oldVal) {

                for(var iCount= 0;iCount<$scope.m_oController.m_oRegions.length; iCount++)
                {
                    if ($scope.m_oController.m_oRegions[iCount].id == $scope.m_oController.m_oEvent.countryId)
                        $scope.m_oController.m_oEvent.regionName = $scope.m_oController.m_oRegions[iCount].countryname;
                }
            }
        });



        $scope.onFileSelect = function ($files, parameter) {

            if ($files != null && $files.length > 0) {
                if (parameter == 'waveHeightInspire'){
                    $scope.m_oController.m_oEvent.waveHeightInspire = $files[0].name;
                    $scope.m_oController.waveHeightInspireuploading = true;
                    $scope.m_oController.waveHeightInspireuploaded = false;
                }
                if (parameter == 'waveHeightTimeSeries') {
                    $scope.m_oController.m_oEvent.waveHeightTimeSeries = $files[0].name;
                    $scope.m_oController.waveHeightTimeSeriesuploading = true;
                    $scope.m_oController.waveHeightTimeSeriesuploaded = false;
                }
                if (parameter == 'waveDirectionInspire'){
                    $scope.m_oController.m_oEvent.waveDirectionInspire = $files[0].name;
                    $scope.m_oController.waveDirectionInspireuploading = true;
                    $scope.m_oController.waveDirectionInspireuploaded = false;
                }
                if (parameter == 'waveDirectionTimeSeries') {
                    $scope.m_oController.m_oEvent.waveDirectionTimeSeries = $files[0].name;
                    $scope.m_oController.waveDirectionTimeSeriesuploading = true;
                    $scope.m_oController.waveDirectionTimeSeriesuploaded = false;
                }
                if (parameter == 'windIntensityInspire'){
                    $scope.m_oController.m_oEvent.windIntensityInspire = $files[0].name;
                    $scope.m_oController.windIntensityInspireuploading = true;
                    $scope.m_oController.windIntensityInspireuploaded = false;
                }
                if (parameter == 'windIntensitySeries'){
                    $scope.m_oController.m_oEvent.windIntensitySeries = $files[0].name;
                    $scope.m_oController.windIntensitySeriesuploading = true;
                    $scope.m_oController.windIntensitySeriesuploaded = false;
                }
                if (parameter == 'windDirectionInspire'){
                    $scope.m_oController.m_oEvent.windDirectionInspire = $files[0].name;
                    $scope.m_oController.windDirectionInspireuploading = true;
                    $scope.m_oController.windDirectionInspireuploaded = false;
                }
                if (parameter == 'windDirectionTimeSeries'){
                    $scope.m_oController.m_oEvent.windDirectionTimeSeries = $files[0].name;
                    $scope.m_oController.windDirectionTimeSeriesuploading = true;
                    $scope.m_oController.windDirectionTimeSeriesuploaded = false;
                }
                if (parameter == 'waterLevelInspire'){
                    $scope.m_oController.m_oEvent.waterLevelInspire = $files[0].name;
                    $scope.m_oController.waterLevelInspireuploading = true;
                    $scope.m_oController.waterLevelInspireuploaded = false;
                }
                if (parameter == 'waterLevelTimeSeries'){
                    $scope.m_oController.m_oEvent.waterLevelTimeSeries = $files[0].name;
                    $scope.m_oController.waterLevelTimeSeriesuploading = true;
                    $scope.m_oController.waterLevelTimeSeriesuploaded = false;
                }
                if (parameter == 'peakWaterInpire'){
                    $scope.m_oController.m_oEvent.peakWaterInpire = $files[0].name;
                    $scope.m_oController.peakWaterInpireuploading = true;
                    $scope.m_oController.peakWaterInpireuploaded = false;
                }
                if (parameter == 'peakWaterTimeSeries'){
                    $scope.m_oController.m_oEvent.peakWaterTimeSeries = $files[0].name;
                    $scope.m_oController.peakWaterTimeSeriesuploading = true;
                    $scope.m_oController.peakWaterTimeSeriesuploaded = false;
                }
                if (parameter == 'floodHeightInspire') {
                    $scope.m_oController.m_oEvent.floodHeightInspire = $files[0].name;
                    $scope.m_oController.floodHeightInspireuploading = true;
                    $scope.m_oController.floodHeightInspireuploaded = false;
                }
                if (parameter == 'floodHeightTimeSeries') {
                    $scope.m_oController.m_oEvent.floodHeightTimeSeries = $files[0].name;
                    $scope.m_oController.floodHeightTimeSeriesuploading = true;
                    $scope.m_oController.floodHeightTimeSeriesuploaded = false;
                }
                if (parameter == 'reporedCasualtiesInspire') {
                    $scope.m_oController.m_oEvent.reporedCasualtiesInspire = $files[0].name;
                    $scope.m_oController.reporedCasualtiesInspireuploading = true;
                    $scope.m_oController.reporedCasualtiesInspireuploaded = false;
                }
                if (parameter == 'reporedCasualtiesTimeSeries') {
                    $scope.m_oController.m_oEvent.reporedCasualtiesTimeSeries = $files[0].name;
                    $scope.m_oController.reporedCasualtiesTimeSeriesuploading = true;
                    $scope.m_oController.reporedCasualtiesTimeSeriesuploaded = false;
                }
                if (parameter == 'damageToBuildingsInspire') {
                    $scope.m_oController.m_oEvent.damageToBuildingsInspire = $files[0].name;
                    $scope.m_oController.damageToBuildingsInspireuploading = true;
                    $scope.m_oController.damageToBuildingsInspireuploaded = false;
                }
                if (parameter == 'damageToBuildingsTimeSeries') {
                    $scope.m_oController.m_oEvent.damageToBuildingsTimeSeries = $files[0].name;
                    $scope.m_oController.damageToBuildingsTimeSeriesuploading = true;
                    $scope.m_oController.damageToBuildingsTimeSeriesuploaded = false;
                }
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

            $scope.m_oController.m_oEventService.Save($scope.m_oController.m_oEvent).success(function(data){
                $scope.m_oController.m_oEvent.id = data.id;
                $scope.m_oController.m_oEventService.Upload($scope.m_oController.m_oEvent, $scope.selectedFiles[index], $scope.parameter).success(function(data){

                    //TODO Aggiornarnare il path corretto dell'evento
                    if ($scope.parameter == 'waveHeightInspire'){
                        $scope.m_oController.m_oEvent.waveHeightInspire = data;
                        $scope.m_oController.waveHeightInspireuploading = false;
                        $scope.m_oController.waveHeightInspireuploaded = true;
                    }
                    if ($scope.parameter == 'waveHeightTimeSeries'){
                        $scope.m_oController.m_oEvent.waveHeightTimeSeries = data;
                        $scope.m_oController.waveHeightTimeSeriesuploading = false;
                        $scope.m_oController.waveHeightTimeSeriesuploaded = true;
                    }
                    if ($scope.parameter == 'waveDirectionInspire'){
                        $scope.m_oController.m_oEvent.waveDirectionInspire = data;
                        $scope.m_oController.waveDirectionInspireuploading = false;
                        $scope.m_oController.waveDirectionInspireuploaded = true;
                    }
                    if ($scope.parameter == 'waveDirectionTimeSeries'){
                        $scope.m_oController.m_oEvent.waveDirectionTimeSeries = data;
                        $scope.m_oController.waveDirectionTimeSeriesuploading = false;
                        $scope.m_oController.waveDirectionTimeSeriesuploaded = true;
                    }
                    if ($scope.parameter == 'windIntensityInspire'){
                        $scope.m_oController.m_oEvent.windIntensityInspire = data;
                        $scope.m_oController.windIntensityInspireuploading = false;
                        $scope.m_oController.windIntensityInspireuploaded = true;
                    }
                    if ($scope.parameter == 'windIntensitySeries') {
                        $scope.m_oController.m_oEvent.windIntensitySeries = data;
                        $scope.m_oController.windIntensitySeriesuploading = false;
                        $scope.m_oController.windIntensitySeriesuploaded = true;
                    }
                    if ($scope.parameter == 'windDirectionInspire') {
                        $scope.m_oController.m_oEvent.windDirectionInspire = data;
                        $scope.m_oController.windDirectionInspireuploading = false;
                        $scope.m_oController.windDirectionInspireuploaded = true;
                    }
                    if ($scope.parameter == 'windDirectionTimeSeries') {
                        $scope.m_oController.m_oEvent.windDirectionTimeSeries = data;
                        $scope.m_oController.windDirectionTimeSeriesuploading = false;
                        $scope.m_oController.windDirectionTimeSeriesuploaded = true;
                    }
                    if ($scope.parameter == 'waterLevelInspire') {
                        $scope.m_oController.m_oEvent.waterLevelInspire = data;
                        $scope.m_oController.waterLevelInspireuploading = false;
                        $scope.m_oController.waterLevelInspireuploaded = true;
                    }
                    if ($scope.parameter == 'waterLevelTimeSeries') {
                        $scope.m_oController.m_oEvent.waterLevelTimeSeries = data;
                        $scope.m_oController.waterLevelTimeSeriesuploading = false;
                        $scope.m_oController.waterLevelTimeSeriesuploaded = true;
                    }
                    if ($scope.parameter == 'peakWaterInpire') {
                        $scope.m_oController.m_oEvent.peakWaterInpire = data;
                        $scope.m_oController.peakWaterInpireuploading = false;
                        $scope.m_oController.peakWaterInpireuploaded = true;
                    }
                    if ($scope.parameter == 'peakWaterTimeSeries') {
                        $scope.m_oController.m_oEvent.peakWaterTimeSeries = data;
                        $scope.m_oController.peakWaterTimeSeriesuploading = false;
                        $scope.m_oController.peakWaterTimeSeriesuploaded = true;
                    }
                    if ($scope.parameter == 'floodHeightInspire') {
                        $scope.m_oController.m_oEvent.floodHeightInspire = data;
                        $scope.m_oController.floodHeightInspireuploading = false;
                        $scope.m_oController.floodHeightInspireuploaded = true;
                    }
                    if ($scope.parameter == 'floodHeightTimeSeries') {
                        $scope.m_oController.m_oEvent.floodHeightTimeSeries = data;
                        $scope.m_oController.floodHeightTimeSeriesuploading = false;
                        $scope.m_oController.floodHeightTimeSeriesuploaded = true;
                    }
                    if ($scope.parameter == 'reporedCasualtiesInspire') {
                        $scope.m_oController.m_oEvent.reporedCasualtiesInspire = data;
                        $scope.m_oController.reporedCasualtiesInspireuploading = false;
                        $scope.m_oController.reporedCasualtiesInspireuploaded = true;
                    }
                    if ($scope.parameter == 'reporedCasualtiesTimeSeries') {
                        $scope.m_oController.m_oEvent.reporedCasualtiesTimeSeries = data;
                        $scope.m_oController.reporedCasualtiesTimeSeriesuploading = false;
                        $scope.m_oController.reporedCasualtiesTimeSeriesuploaded = true;
                    }
                    if ($scope.parameter == 'damageToBuildingsInspire') {
                        $scope.m_oController.m_oEvent.damageToBuildingsInspire = data;
                        $scope.m_oController.damageToBuildingsInspireuploading = false;
                        $scope.m_oController.damageToBuildingsInspireuploaded = true;
                    }
                    if ($scope.parameter == 'damageToBuildingsTimeSeries') {
                        $scope.m_oController.m_oEvent.damageToBuildingsTimeSeries = data;
                        $scope.m_oController.damageToBuildingsTimeSeriesuploading = false;
                        $scope.m_oController.damageToBuildingsTimeSeriesuploaded = true;
                    }

                }).error(function(data){

                });
            });
        };

    }



    EventController.prototype.AddMedia = function (partial, size) {

        if (this.m_oScope.m_oController.CheckAddMediaGis()) {
            this.m_oSharedService.setEvent(this.m_oEvent);
            this.m_oLocation.path('media');
        }
    };

    EventController.prototype.GetCountries = function() {
        return this.m_oEventService.GetCountries();

    };

    EventController.prototype.GetWaveHeightType = function() {
        return [{name:"Mean significant wave height", value:1}, {name:"Peak significant wave height", value:2}, {name:"Maximum wave height", value:3}];

    };

    EventController.prototype.GetWaveDiectionType = function() {
        return [{name:"Degrees from N", value:1}, {name:"Compass", value:2}];

    };

    EventController.prototype.GetWindDiectionType = function() {
        return [{name:"Degrees from N", value:1}, {name:"Compass", value:2}];

    };

    EventController.prototype.GetWaterLevelType = function() {
        return [{name:"Maximum total water level", value:1}, {name:"Maximum astronomical tide", value:2}];

    };

    EventController.prototype.GetWindIntensityType = function() {
        return [{name:"Mean wind speed", value:1}, {name:"Maximum wind speed", value:2}, {name:"Maximum wind gust", value:3}];

    };

    EventController.prototype.GetCostDetails = function() {
        return [{name:"Direct Cost", value:1}, {name:"Business Interruption Cost", value:2}, {name:"Indirect Cost", value:3}, {name:"Risk mitigation Cost", value:4}];

    };

    EventController.prototype.DeleteMedia = function(idMedia, idEvent) {

        var oScope = this.m_oScope;

        this.m_oMediaService.DeleteMedia(idMedia, idEvent).success(function(data){
            oScope.m_oController.m_oEvent.Media = data;
        });

    };

    EventController.prototype.DownloadMedia = function(idMedia) {

        return this.m_oMediaService.DownloadMedia(idMedia);

    };

    EventController.prototype.AddGIS = function (size) {

        if(this.m_oScope.m_oController.CheckAddMediaGis()) {
            this.m_oSharedService.setEvent(this.m_oEvent);
            var oScope = this.m_oScope;

            var modalInstance = this.m_oModal.open({
                templateUrl: 'partials/gis.html',
                controller: GisController,
                size: size
            });

            modalInstance.result.then(function (GISFilesName, InspireFilesName) {
                if (GISFilesName != null)
                    oScope.m_oController.m_oEvent.GIS.GisFile = GISFilesName.name;
                if (InspireFilesName != null)
                    oScope.m_oController.m_oEvent.GIS.InspireFile = InspireFilesName.name;

            });
        }

    };

    EventController.prototype.Save = function() {
        var oScope = this.m_oScope;
        if (this.m_oEvent != null)
        {
            if (this.m_oLoginService.isLogged()) {
                this.m_oEvent.userId = this.m_oLoginService.getUserId();
                this.m_oEventService.Save(this.m_oEvent).success(function (data, status) {

                    //Per ora salviamo tutto in modo separato perchè non riusciamo a far funzionare la deserializzazione
                    //di liste contenute
                    if (data != null) {

                        var answer = confirm("Do you want to insert another event?");
                        if (answer)
                            oScope.m_oController.m_oEvent = new Object();
                        else
                            oScope.m_oController.m_oLocation.path('eventslist');
                    }
                    else
                    {
                        alert("Error saving event");
                    }
                });
            }
        }

    };


    EventController.prototype.Check = function () {
        if (this.m_oScope.m_oController.m_oEvent.countryId == null || this.m_oScope.m_oController.m_oEvent.startDate == null) {
            alert("Select country, region and start date, please!");
            event.preventDefault();
        }
    };

    EventController.prototype.CheckAddMediaGis = function () {
        if (this.m_oScope.m_oController.m_oEvent.countryId == null || this.m_oScope.m_oController.m_oEvent.startDate == null) {
            alert("Select country, region and start date, please!");
            return false;
        }

        return true;
    };


    EventController.$inject = [
            '$scope',
            '$location',
            '$modal',
            'EventService',
            'SharedService',
            'LoginService',
            'MediaService'
        ];

    return EventController;
}) ();
