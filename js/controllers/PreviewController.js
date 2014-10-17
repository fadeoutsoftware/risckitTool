/**
 * Created by s.adamo on 14/10/2014.
 */

var PreviewController = (function() {
    function PreviewController($scope, $modalInstance) {
        this.m_oScope = $scope;
        this.m_oScope.m_oController = this;
        this.m_oModalInstance = $modalInstance;

    }

    PreviewController.prototype.close = function () {

        this.m_oModalInstance.close();

    };


    PreviewController.$inject = [
        '$scope',
        '$modalInstance'
    ];
    return PreviewController;
}) ();
