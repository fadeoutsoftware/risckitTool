/**
 * Created by s.adamo on 07/07/2014.
 */
var GisController = (function() {

    function GisController($scope, $location, $modalInstance) {
        this.m_oScope = $scope;
        this.m_oScope.m_oController = this;
        this.m_oLocation = $location;
        this.m_oModaleInstance = $modalInstance;
        this.m_sGisFilePath = null;
        this.m_sInspireFilePath = null;
        this.m_oGISFiles;
        this.m_oInspireFiles;

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

    };

    GisController.prototype.ok = function () {
        this.m_oModaleInstance.close(this.m_oGISFiles, this.m_oInspireFiles);

    };

    GisController.prototype.cancel  = function () {
        this.m_oModaleInstance.dismiss('cancel');

    };

    GisController.$inject = [
        '$scope',
        '$location',
        '$modalInstance'

    ];

    return GisController;
}) ();