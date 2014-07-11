/**
 * Created by s.adamo on 01/07/2014.
 */
var MainController = (function() {
    function MainController($scope, $location) {
        this.m_oScope = $scope;
        this.m_oScope.m_oController = this;

    }

    MainController.$inject = [
        '$scope',
        '$location',
        '$rootScope'
    ];
    return MainController;
}) ();