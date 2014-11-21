/**
 * Created by s.adamo on 02/07/2014.
 */
var EventController = (function() {

    function EventController($scope, $location, $modal, oEventService, oSharedService, oLoginService, oMediaService, oGisService, oSocioimpactService) {
        this.m_oScope = $scope;
        this.m_oScope.m_oController = this;
        this.m_oLocation = $location;
        this.m_oModal = $modal;
        this.m_oEventService = oEventService; //Service
        this.m_oSharedService = oSharedService;
        this.m_oLoginService = oLoginService;
        this.m_oMediaService = oMediaService;
        this.m_oGisService = oGisService;
        this.m_oSocioimpactService = oSocioimpactService;
        this.m_bSaving = false;
        this.Flooding = false;

        if (this.m_oScope.m_oController.m_oSharedService.getEvent() == null) {
            this.m_oScope.m_oController.m_oEvent = new Object();
            this.m_oScope.m_oController.m_oEvent.userId = this.m_oLoginService.getUserId();
            this.m_oScope.m_oController.m_oEvent.Media = null;
            this.m_oScope.m_oController.m_oEvent.GIS = null;
        }
        else {
            this.m_oScope.m_oController.m_oEvent = this.m_oScope.m_oController.m_oSharedService.getEvent();
            //Media
            if (this.m_oSharedService.getEvent().Media == null) {
                //load Media
                this.m_oMediaService.LoadMedia($scope.m_oController.m_oEvent.id).success(function (data) {
                    $scope.m_oController.m_oEvent.Media = data;
                    $scope.m_oController.m_oEventService.setUnchanged();
                });
            }
            else {
                this.m_oScope.m_oController.m_oEvent.Media = this.m_oSharedService.getEvent().Media;
            }
            //Gis
            if (this.m_oSharedService.getEvent().GIS == null) {
                //load GIS
                this.m_oGisService.LoadGis($scope.m_oController.m_oEvent.id).success(function (data) {
                    $scope.m_oController.m_oEvent.GIS = data;
                    $scope.m_oController.m_oEventService.setUnchanged();
                });
            }
            else {
                this.m_oScope.m_oController.m_oEvent.GIS = this.m_oSharedService.getEvent().GIS;
            }
            //Socio Impact
            if (this.m_oSharedService.getEvent().SocioImpacts == null) {
                //load GIS
                this.m_oSocioimpactService.LoadSocioImpact($scope.m_oController.m_oEvent.id).success(function (data) {
                    $scope.m_oController.m_oEvent.SocioImpacts = data;
                    $scope.m_oController.m_oEventService.setUnchanged();
                });
            }
            else {
                this.m_oScope.m_oController.m_oEvent.SocioImpacts = this.m_oSharedService.getEvent().SocioImpacts;
            }
            if (this.m_oScope.m_oController.m_oEvent.peakWaterDischarge != null || this.m_oScope.m_oController.m_oEvent.floodHeight != null)
                this.Flooding = true;

        }

        if (this.m_oLoginService.isLogged()) {
            this.m_oScope.m_oController.m_oEvent.login = this.m_oLoginService.getUserName();
            this.m_oScope.m_oController.m_oEvent.userId = this.m_oLoginService.getUserId();
        }
        else {
            var oModalLogin = this.m_oModal.open({
                templateUrl: 'partials/login.html',
                controller: LoginController,
                backdrop: 'static',
                keyboard: false
            });
        }

        this.uploadRightAway = true;

        //load countries and region
        if (this.m_oLoginService.isLogged()) {
            this.m_oEventService.LoadCountries();

            if (this.m_oSharedService.getEvent() != null) {
                $scope.m_oController.m_oEvent.countryCode = this.m_oSharedService.getEvent().countryCode;
                $scope.m_oController.m_oEventService.GetAllRegions($scope.m_oController.m_oEvent.countryCode).success(function (data, status) {
                    $scope.m_oController.m_oRegions = data;
                    $scope.m_oController.m_oEventService.setRegions(data);
                    if ($scope.m_oController.m_oSharedService.getEvent() != null) {
                        $scope.m_oController.m_oEvent.countryId = $scope.m_oController.m_oSharedService.getEvent().countryId;
                        $scope.m_oController.m_oEventService.setUnchanged();
                    }

                });
            }
            else {
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
                $scope.m_oController.m_oEventService.setAsModified();
                //load regions
                if ($scope.m_oController.m_oEvent.countryCode != null) {
                    $scope.m_oController.m_oEventService.GetAllRegions($scope.m_oController.m_oEvent.countryCode).success(function (data, status) {
                        $scope.m_oController.m_oRegions = data;
                        $scope.m_oController.m_oEventService.setRegions(data);
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
                $scope.m_oController.m_oEventService.setAsModified();
                for (var iCount = 0; iCount < $scope.m_oController.m_oRegions.length; iCount++) {
                    if ($scope.m_oController.m_oRegions[iCount].id == $scope.m_oController.m_oEvent.countryId)
                        $scope.m_oController.m_oEvent.regionName = $scope.m_oController.m_oRegions[iCount].countryname;
                }
            }
        });

        $scope.$watch('m_oController.m_oEvent.description', function (newVal, oldVal) {
            if (newVal != oldVal) {
                $scope.m_oController.m_oEventService.setAsModified();
            }
        });

        $scope.$watch('m_oController.m_oEvent.startDate', function (newVal, oldVal) {
            if (newVal != oldVal) {
                $scope.m_oController.m_oEventService.setAsModified();
            }
        });

        $scope.$watch('m_oController.m_oEvent.startHour', function (newVal, oldVal) {
            if (newVal != oldVal) {
                $scope.m_oController.m_oEventService.setAsModified();
            }
        });

        $scope.$watch('m_oController.m_oEvent.unitHour', function (newVal, oldVal) {
            if (newVal != oldVal) {
                $scope.m_oController.m_oEventService.setAsModified();
            }
        });

        $scope.$watch('m_oController.m_oEvent.unitValue', function (newVal, oldVal) {
            if (newVal != oldVal) {
                $scope.m_oController.m_oEventService.setAsModified();
            }
        });

        $scope.$watch('m_oController.m_oEvent.unitApproximated', function (newVal, oldVal) {
            if (newVal != oldVal) {
                $scope.m_oController.m_oEventService.setAsModified();
            }
        });

        $scope.$watch('m_oController.m_oEvent.waveHeightType', function (newVal, oldVal) {
            if (newVal != oldVal) {
                $scope.m_oController.m_oEventService.setAsModified();
            }
        });

        $scope.$watch('m_oController.m_oEvent.waveHeightValue', function (newVal, oldVal) {
            if (newVal != oldVal) {
                $scope.m_oController.m_oEventService.setAsModified();
            }
        });

        $scope.$watch('m_oController.m_oEvent.waveDirectionType', function (newVal, oldVal) {
            if (newVal != oldVal) {
                $scope.m_oController.m_oEventService.setAsModified();
            }
        });

        $scope.$watch('m_oController.m_oEvent.waveDirectionClustered', function (newVal, oldVal) {
            if (newVal != oldVal) {
                $scope.m_oController.m_oEventService.setAsModified();
            }
        });

        $scope.$watch('m_oController.m_oEvent.windIntensityType', function (newVal, oldVal) {
            if (newVal != oldVal) {
                $scope.m_oController.m_oEventService.setAsModified();
            }
        });

        $scope.$watch('m_oController.m_oEvent.windIntensityValue', function (newVal, oldVal) {
            if (newVal != oldVal) {
                $scope.m_oController.m_oEventService.setAsModified();
            }
        });

        $scope.$watch('m_oController.m_oEvent.windDirectionType', function (newVal, oldVal) {
            if (newVal != oldVal) {
                $scope.m_oController.m_oEventService.setAsModified();
            }
        });

        $scope.$watch('m_oController.m_oEvent.windDirectionClustered', function (newVal, oldVal) {
            if (newVal != oldVal) {
                $scope.m_oController.m_oEventService.setAsModified();
            }
        });

        $scope.$watch('m_oController.m_oEvent.waterLevelType', function (newVal, oldVal) {
            if (newVal != oldVal) {
                $scope.m_oController.m_oEventService.setAsModified();
            }
        });

        $scope.$watch('m_oController.m_oEvent.waterLevelValue', function (newVal, oldVal) {
            if (newVal != oldVal) {
                $scope.m_oController.m_oEventService.setAsModified();
            }
        });

        $scope.$watch('m_oController.m_oEvent.Flooding', function (newVal, oldVal) {
            if (newVal != oldVal) {
                $scope.m_oController.m_oEventService.setAsModified();
            }
        });

        $scope.$watch('m_oController.m_oEvent.peakWaterDischarge', function (newVal, oldVal) {
            if (newVal != oldVal) {
                $scope.m_oController.m_oEventService.setAsModified();
            }
        });

        $scope.$watch('m_oController.m_oEvent.floodHeight', function (newVal, oldVal) {
            if (newVal != oldVal) {
                $scope.m_oController.m_oEventService.setAsModified();
            }
        });


        $scope.onFileSelect = function ($files, parameter) {

            if ($files != null && $files.length > 0) {
                $scope.m_oController.m_oEventService.setUnchanged();


                if (parameter == 'waveHeightDirectionInspire') {
                    $scope.m_oController.m_oEvent.waveDirectionInspire = $files[0].name;
                    $scope.m_oController.waveDirectionInspireuploading = true;
                    $scope.m_oController.m_oEvent.waveHeightInspire = $files[0].name;
                    $scope.m_oController.waveHeightInspireuploading = true;
                }
                if (parameter == 'waveHeightDirectionTimeSeries') {
                    $scope.m_oController.m_oEvent.waveHeightTimeSeries = $files[0].name;
                    $scope.m_oController.waveHeightTimeSeriesuploading = true;
                    $scope.m_oController.m_oEvent.waveDirectionTimeSeries = $files[0].name;
                    $scope.m_oController.waveDirectionTimeSeriesuploading = true;
                }
                if (parameter == 'windIntensityDirectionInspire') {
                    $scope.m_oController.m_oEvent.windIntensityInspire = $files[0].name;
                    $scope.m_oController.windIntensityInspireuploading = true;
                    $scope.m_oController.m_oEvent.windDirectionInspire = $files[0].name;
                    $scope.m_oController.windDirectionInspireuploading = true;
                }
                if (parameter == 'windIntensityDirectionTimeSeries') {
                    $scope.m_oController.m_oEvent.windIntensitySeries = $files[0].name;
                    $scope.m_oController.windIntensitySeriesuploading = true;
                    $scope.m_oController.m_oEvent.windDirectionTimeSeries = $files[0].name;
                    $scope.m_oController.windDirectionTimeSeriesuploading = true;
                }
                if (parameter == 'waterLevelInspire') {
                    $scope.m_oController.m_oEvent.waterLevelInspire = $files[0].name;
                    $scope.m_oController.waterLevelInspireuploading = true;
                }
                if (parameter == 'waterLevelTimeSeries') {
                    $scope.m_oController.m_oEvent.waterLevelTimeSeries = $files[0].name;
                    $scope.m_oController.waterLevelTimeSeriesuploading = true;
                }
                if (parameter == 'peakWaterInpire') {
                    $scope.m_oController.m_oEvent.peakWaterInpire = $files[0].name;
                    $scope.m_oController.peakWaterInpireuploading = true;
                }
                if (parameter == 'peakWaterTimeSeries') {
                    $scope.m_oController.m_oEvent.peakWaterTimeSeries = $files[0].name;
                    $scope.m_oController.peakWaterTimeSeriesuploading = true;
                }
                if (parameter == 'floodHeightInspire') {
                    $scope.m_oController.m_oEvent.floodHeightInspire = $files[0].name;
                    $scope.m_oController.floodHeightInspireuploading = true;
                }
                if (parameter == 'floodHeightTimeSeries') {
                    $scope.m_oController.m_oEvent.floodHeightTimeSeries = $files[0].name;
                    $scope.m_oController.floodHeightTimeSeriesuploading = true;
                }
                if (parameter == 'reporedCasualtiesInspire') {
                    $scope.m_oController.m_oEvent.reporedCasualtiesInspire = $files[0].name;
                    $scope.m_oController.reporedCasualtiesInspireuploading = true;
                }
                if (parameter == 'reporedCasualtiesTimeSeries') {
                    $scope.m_oController.m_oEvent.reporedCasualtiesTimeSeries = $files[0].name;
                    $scope.m_oController.reporedCasualtiesTimeSeriesuploading = true;
                }
                if (parameter == 'damageToBuildingsInspire') {
                    $scope.m_oController.m_oEvent.damageToBuildingsInspire = $files[0].name;
                    $scope.m_oController.damageToBuildingsInspireuploading = true;
                }
                if (parameter == 'damageToBuildingsTimeSeries') {
                    $scope.m_oController.m_oEvent.damageToBuildingsTimeSeries = $files[0].name;
                    $scope.m_oController.damageToBuildingsTimeSeriesuploading = true;
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


            //Geocoding
            var oCountry = $scope.m_oController.m_oEventService.GetRegion($scope.m_oController.m_oEvent.countryId);
            var geocoder = new google.maps.Geocoder();
            geocoder.geocode({ 'address': oCountry.countryname }, function (results, status) {
                if (status == google.maps.GeocoderStatus.OK) {
                    $scope.m_oController.m_oEvent.lat = results[0].geometry.location.lat();
                    $scope.m_oController.m_oEvent.lon = results[0].geometry.location.lng();
                }


                $scope.m_oController.m_oEventService.Save($scope.m_oController.m_oEvent).success(function (data) {
                    if (data == null) {
                        alert("Error saving event");
                        return;
                    }

                    $scope.m_oController.m_oEvent.id = data.id;
                    $scope.m_oController.m_oEventService.Upload($scope.m_oController.m_oEvent, $scope.selectedFiles[index], $scope.parameter).success(function (data) {

                        if (data == null || data == '') {
                            alert('Error uploading file!');
                            //imposto il dato a vuoto se è andato male il caricamento
                            data = null;
                        }

                        if ($scope.parameter == 'waveHeightDirectionInspire') {
                            $scope.m_oController.m_oEvent.waveDirectionInspire = data;
                            $scope.m_oController.waveDirectionInspireuploading = false;
                            $scope.m_oController.m_oEvent.waveHeightInspire = data;
                            $scope.m_oController.waveHeightInspireuploading = false;
                        }
                        if ($scope.parameter == 'waveHeightDirectionTimeSeries') {
                            $scope.m_oController.m_oEvent.waveHeightTimeSeries = data;
                            $scope.m_oController.waveHeightTimeSeriesuploading = false;
                            $scope.m_oController.m_oEvent.waveDirectionTimeSeries = data;
                            $scope.m_oController.waveDirectionTimeSeriesuploading = false;
                        }
                        if ($scope.parameter == 'windIntensityDirectionInspire') {
                            $scope.m_oController.m_oEvent.windIntensityInspire = data;
                            $scope.m_oController.windIntensityInspireuploading = false;
                            $scope.m_oController.m_oEvent.windDirectionInspire = data;
                            $scope.m_oController.windDirectionInspireuploading = false;
                        }
                        if ($scope.parameter == 'windIntensityDirectionTimeSeries') {
                            $scope.m_oController.m_oEvent.windIntensitySeries = data;
                            $scope.m_oController.windIntensitySeriesuploading = false;
                            $scope.m_oController.m_oEvent.windDirectionTimeSeries = data;
                            $scope.m_oController.windDirectionTimeSeriesuploading = false;
                        }

                        if ($scope.parameter == 'waterLevelInspire') {
                            $scope.m_oController.m_oEvent.waterLevelInspire = data;
                            $scope.m_oController.waterLevelInspireuploading = false;
                        }
                        if ($scope.parameter == 'waterLevelTimeSeries') {
                            $scope.m_oController.m_oEvent.waterLevelTimeSeries = data;
                            $scope.m_oController.waterLevelTimeSeriesuploading = false;
                        }
                        if ($scope.parameter == 'peakWaterInpire') {
                            $scope.m_oController.m_oEvent.peakWaterInpire = data;
                            $scope.m_oController.peakWaterInpireuploading = false;
                        }
                        if ($scope.parameter == 'peakWaterTimeSeries') {
                            $scope.m_oController.m_oEvent.peakWaterTimeSeries = data;
                            $scope.m_oController.peakWaterTimeSeriesuploading = false;
                        }
                        if ($scope.parameter == 'floodHeightInspire') {
                            $scope.m_oController.m_oEvent.floodHeightInspire = data;
                            $scope.m_oController.floodHeightInspireuploading = false;
                        }
                        if ($scope.parameter == 'floodHeightTimeSeries') {
                            $scope.m_oController.m_oEvent.floodHeightTimeSeries = data;
                            $scope.m_oController.floodHeightTimeSeriesuploading = false;
                        }
                        if ($scope.parameter == 'reporedCasualtiesInspire') {
                            $scope.m_oController.m_oEvent.reporedCasualtiesInspire = data;
                            $scope.m_oController.reporedCasualtiesInspireuploading = false;
                        }
                        if ($scope.parameter == 'reporedCasualtiesTimeSeries') {
                            $scope.m_oController.m_oEvent.reporedCasualtiesTimeSeries = data;
                            $scope.m_oController.reporedCasualtiesTimeSeriesuploading = false;
                        }
                        if ($scope.parameter == 'damageToBuildingsInspire') {
                            $scope.m_oController.m_oEvent.damageToBuildingsInspire = data;
                            $scope.m_oController.damageToBuildingsInspireuploading = false;
                        }
                        if ($scope.parameter == 'damageToBuildingsTimeSeries') {
                            $scope.m_oController.m_oEvent.damageToBuildingsTimeSeries = data;
                            $scope.m_oController.damageToBuildingsTimeSeriesuploading = false;
                        }

                    }).error(function (data) {

                        alert('Error uploading file!');
                    });


                });

            });
        };

    }


    EventController.prototype.AddMedia = function (partial, size) {

        if (this.m_oScope.m_oController.Check()) {
            this.m_oSharedService.setEvent(this.m_oEvent);
            this.m_oLocation.path('media');
        }
    };

    EventController.prototype.AddSocioimpact = function (partial, size) {
        var oScope = this.m_oScope;
        if (oScope.m_oController.Check()) {
            if (oScope.m_oController.m_oEventService.isModified()) {
                oScope.m_oController.m_bSaving = true;
                //Save event
                oScope.m_oController.m_oEventService.Save(oScope.m_oController.m_oEvent).success(function (data, status) {
                    if (data != null) {
                        oScope.m_oController.m_oSharedService.setEvent(data);
                        oScope.m_oController.m_oEvent = data;
                        oScope.m_oController.m_oEventService.setUnchanged();
                        oScope.m_oController.m_oLocation.path('socioimpact');
                    }

                    oScope.m_oController.m_bSaving = false;
                });
            }
            else
            {
                oScope.m_oController.m_oLocation.path('socioimpact');
            }

        }
    };

    EventController.prototype.GetCountries = function() {
        return this.m_oEventService.GetCountries();

    };

    EventController.prototype.GetWaveHeightType = function() {
        return this.m_oEventService.GetWaveHeightType();

    };

    EventController.prototype.GetWaveDiectionType = function() {
        return this.m_oEventService.GetWaveDiectionType();

    };

    EventController.prototype.GetWindDiectionType = function() {
        return this.m_oEventService.GetWindDiectionType();

    };

    EventController.prototype.GetWaterLevelType = function() {
        return this.m_oEventService.GetWaterLevelType();

    };

    EventController.prototype.GetWindIntensityType = function() {
        return this.m_oEventService.GetWindIntensityType();

    };

    EventController.prototype.GetCostDetails = function() {
        return this.m_oEventService.GetCostDetails();

    };

    EventController.prototype.DeleteMedia = function(idMedia, idEvent) {

        var oScope = this.m_oScope;

        this.m_oMediaService.DeleteMedia(idMedia, idEvent).success(function(data){
            oScope.m_oController.m_oEvent.Media = data;
        });

    };

    EventController.prototype.DeleteGis = function(idGis, idEvent, type) {

        var oScope = this.m_oScope;

        this.m_oGisService.DeleteGis(idGis, idEvent, type).success(function(data){
            oScope.m_oController.m_oEvent.GIS = data;
        });

    };

    EventController.prototype.DownloadMedia = function(idMedia) {

        return this.m_oMediaService.DownloadMedia(idMedia);

    };

    EventController.prototype.DownloadGis = function(idGis, type) {

        return this.m_oGisService.DownloadGis(idGis, type);

    };

    EventController.prototype.DownloadAttachment = function(idEvent,parameter) {

        return this.m_oEventService.DownloadAttachment(idEvent, parameter);

    };

    EventController.prototype.DeleteAttachment = function(idEvent,  parameter) {

        var oScope = this.m_oScope;

        this.m_oEventService.DeleteAttachment(idEvent, parameter).success(function(data){
            oScope.m_oController.m_oEvent = data;
        });

    };


    EventController.prototype.AddGIS = function (size) {

        if(this.m_oScope.m_oController.Check()) {
            this.m_oSharedService.setEvent(this.m_oEvent);
            var oScope = this.m_oScope;

            var modalInstance = this.m_oModal.open({
                templateUrl: 'partials/gis.html',
                controller: GisController,
                size: size
            });

            modalInstance.result.then(function (GIS) {
                    oScope.m_oController.m_oEvent.GIS = GIS;
            });
        }

    };

    EventController.prototype.Save = function() {
        var oScope = this.m_oScope;
        if (this.m_oEvent != null) {
            if (this.m_oLoginService.isLogged()) {
                this.m_oEvent.userId = this.m_oLoginService.getUserId();
                this.m_oEvent.login = this.m_oLoginService.getUserName();

                if (oScope.m_oController.m_oEventService.isModified()) {

                    if (!oScope.m_oController.Check()) {
                        alert('Insert mandatory fields');
                        return;
                    }

                    //Geocoding
                    var oCountry = null;
                    var oCountries = oScope.m_oController.GetCountries();
                    for (var iCount = 0; iCount < oCountries.length; iCount++) {
                        if (oScope.m_oController.m_oEvent.countryCode == oCountries[iCount].countryCode)
                            oCountry = oCountries[iCount];
                    }
                    var oRegion = oScope.m_oController.m_oEventService.GetRegion(oScope.m_oController.m_oEvent.countryId);
                    if (oRegion != null) {
                        var geocoder = new google.maps.Geocoder();
                        geocoder.geocode({ 'address': oCountry.countryname + ' ' + oRegion.countryname }, function (results, status) {
                            if (status == google.maps.GeocoderStatus.OK) {
                                oScope.m_oController.m_oEvent.lat = results[0].geometry.location.lat();
                                oScope.m_oController.m_oEvent.lon = results[0].geometry.location.lng();
                            }
                            else {
                                //se è andata male la decodifica metto almeno le coordinate del country
                                oScope.m_oController.m_oEvent.lat = oCountries.lat;
                                oScope.m_oController.m_oEvent.lon = oCountries.lon;
                            }

                            //ora posso salvare
                            oScope.m_oController.m_bSaving = true;
                            oScope.m_oController.m_oEventService.Save(oScope.m_oController.m_oEvent).success(function (data, status) {

                                //Per ora salviamo tutto in modo separato perchè non riusciamo a far funzionare la deserializzazione
                                //di liste contenute
                                if (data != null) {

                                    if (data.id == null || data.id == 0) {
                                        alert("Error saving event");
                                        return;
                                    }
                                    oScope.m_oController.m_bSaving = false;
                                    oScope.m_oController.m_oEventService.setUnchanged();

                                    var answer = confirm("Do you want to insert another event?");
                                    if (answer)
                                        oScope.m_oController.m_oEvent = new Object();
                                    else
                                        oScope.m_oController.m_oLocation.path('eventslist');
                                }
                                else {

                                    alert("Error saving event");
                                }

                                oScope.m_oController.m_bSaving = false;
                                oScope.m_oController.m_oEventService.setUnchanged();
                            });


                        });
                    }
                    else {

                        oScope.m_oController.m_bSaving = true;
                        oScope.m_oController.m_oEventService.Save(oScope.m_oController.m_oEvent).success(function (data, status) {

                            //Per ora salviamo tutto in modo separato perchè non riusciamo a far funzionare la deserializzazione
                            //di liste contenute
                            if (data != null) {

                                if (data.id == null || data.id == 0) {
                                    alert("Error saving event");
                                    return;
                                }

                                oScope.m_oController.m_bSaving = false;
                                oScope.m_oController.m_oEventService.setUnchanged();
                                var answer = confirm("Do you want to insert another event?");
                                if (answer)
                                    oScope.m_oController.m_oEvent = new Object();
                                else
                                    oScope.m_oController.m_oLocation.path('eventslist');
                            }
                            else {
                                alert("Error saving event");
                            }

                            oScope.m_oController.m_bSaving = false;
                            oScope.m_oController.m_oEventService.setUnchanged();
                        });
                    }
                }
                else {
                    oScope.m_oController.m_oLocation.path('eventslist');
                }

            }
        }

    };


    EventController.prototype.Check = function () {
        var oEvent = this.m_oScope.m_oController.m_oEvent;
        if (oEvent.countryId == null || oEvent.startDate == null ||
            oEvent.countryCode == null || oEvent.countryCode == '' ||
            oEvent.startDate == null || oEvent.description == null || oEvent.description == '') {
            alert("Insert mandatory fields!");
            event.preventDefault();
            return false;
        }
        return true;
    };

    EventController.prototype.CheckAddMediaGis = function () {
        if (this.m_oScope.m_oController.m_oEvent.countryId == null || this.m_oScope.m_oController.m_oEvent.startDate == null) {
            alert("Select country, region and start date, please!");
            return false;
        }

        return true;
    };

    EventController.prototype.OpenMedia = function (idmedia) {
        this.m_oLocation.path('/media/' + idmedia);
    };


    EventController.prototype.DeleteSocioImpact = function (idsocio) {
        var oScope = this.m_oScope;

        this.m_oSocioimpactService.Delete(idsocio).success(function(data){
            if (data != null)
                oScope.m_oController.m_oEvent.SocioImpacts = data;
        });
    };

    EventController.prototype.EditSocioImpact = function (idsocio) {
        this.m_oLocation.path('/socioimpact/' + idsocio);
    };

    EventController.prototype.GetTotalCost = function () {
        var totalCost = 0;
        if (this.m_oScope.m_oController.m_oEvent != null) {
            if (this.m_oScope.m_oController.m_oEvent.SocioImpacts != null) {
                var socioimpacts = this.m_oScope.m_oController.m_oEvent.SocioImpacts;
                for (var iCount = 0; iCount < socioimpacts.length; iCount++) {
                    totalCost = totalCost + socioimpacts[iCount].cost;
                }
            }
        }
        return totalCost;
    };


    EventController.$inject = [
            '$scope',
            '$location',
            '$modal',
            'EventService',
            'SharedService',
            'LoginService',
            'MediaService',
            'GisService',
            'SocioimpactService',
        ];

    return EventController;
}) ();
