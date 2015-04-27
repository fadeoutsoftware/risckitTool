/**
 * Created by p.campanella on 06/03/2015.
 */

var HomeController = (function() {

    function HomeController($scope, $location) {
        this.m_oScope = $scope;
        this.m_oScope.m_oController = this;
        this.m_oLocation = $location;
    }

    HomeController.prototype.test = function() {
        return ;
    };


    HomeController.$inject = [
        '$scope',
        '$location'
    ];

    return HomeController;
}) ();