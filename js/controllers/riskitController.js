/**
 * Created by s.adamo on 01/07/2014.
 */

var riskitController = (function() {
    function riskitController($scope, $location, $rootScope, $modal, $timeout, $routeParams, $filter) {
        this.m_oScope = $scope;
        this.m_oScope.m_oController = this;

    };

    riskitController.$inject = [
        '$scope',
        '$location',
        '$rootScope',
        '$modal',
        '$timeout',
        '$routeParams',
        '$filter'

    ];
    return riskitController;

}) ();

