/**
 * Created by s.adamo on 07/07/2014.
 */
var GisController = (function() {

    function GisController($scope, $location, $modalInstance, oSharedService, oEventService) {
        this.m_oScope = $scope;
        this.m_oScope.m_oController = this;
        this.m_oLocation = $location;
        this.m_oModaleInstance = $modalInstance;
        this.m_oSharedService = oSharedService;
        this.m_oEventService = oEventService;
        this.m_oEventService.setUploaded(false);
        this.m_sGisFilePath = null;
        this.m_sInspireFilePath = null;
        this.m_oGISFiles;
        this.m_oInspireFiles;
        this.uploadRightAway = true;

        if ($scope.m_oController.m_oSharedService.getEvent().GIS == null)
            $scope.m_oController.m_oSharedService.getEvent().GIS = new Object();

        $scope.$watch('m_oController.m_sGisFilePath', function(newVal, oldVal) {
            if (newVal !== oldVal) {
                $scope.m_oController.m_oGISFiles = newVal;
            }
        });

        $scope.$watch('m_oController.m_sInspireFilePath', function(newVal, oldVal) {
            if (newVal !== oldVal) {
                $scope.m_oController.m_oInspireFiles = newVal;
            }
        });

        $scope.onFileSelect = function ($files, type) {

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
            $scope.type = type;
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

            $scope.m_oController.m_oEventService.UploadGis($scope.m_oController.m_oSharedService.getEvent(), $scope.m_oController.m_oSharedService.getEvent().GIS, $scope.selectedFiles[index], $scope.type);

        };

    };

    GisController.prototype.ok = function () {
        this.m_oModaleInstance.close(this.m_oGISFiles, this.m_oInspireFiles);

    };

    GisController.prototype.cancel  = function () {
        this.m_oModaleInstance.dismiss('cancel');

    };

    GisController.prototype.Uploaded = function() {
        return this.m_oEventService.Uploaded();

    };

    GisController.$inject = [
        '$scope',
        '$location',
        '$modalInstance',
        'SharedService',
        'EventService'

    ];

    return GisController;
}) ();