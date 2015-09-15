/**
 * Created by s.adamo on 01/07/2014.
 */
var MainController = (function() {
    function MainController($scope, $location, $modal, oLoginService, $templateCache) {
        this.m_oScope = $scope;
        this.m_oTemplateCache = $templateCache;
        this.m_oLocation = $location;
        this.m_oScope.m_oController = this;
        this.m_oModal = $modal;
        this.m_oLoginService = oLoginService;

        //$scope.m_oController.m_oLocation.path('map');

        /*
        if (oLoginService.isLogged() == false && !oLoginService.getLogDialogOn()) {
            var oModalLogin = this.m_oModal.open({
                templateUrl: 'partials/login.html',
                controller: LoginController,
                backdrop: 'static',
                keyboard: false
            });

            oModalLogin.result.then(function (Loginresult) {
                if (Loginresult == true)
                    $scope.m_oController.m_oLocation.path('map');
                else
                    alert('Login Error');

            })
        }
        */

    }

    MainController.prototype.Login = function() {

        var oScope = this.m_oScope;
        var pathbefore = oScope.m_oController.m_oLocation.path();
        if (this.m_oLoginService.isLogged() == false && !this.m_oLoginService.getLogDialogOn()) {
            var oModalLogin = this.m_oModal.open({
                templateUrl: 'partials/login.html',
                controller: LoginController,
                backdrop: 'static',
                keyboard: false
            });


            oModalLogin.result.then(function (Loginresult) {
                if (Loginresult == false)
                    alert('Login Error');
                if (Loginresult == 'cancel')
                    oScope.m_oController.m_oLocation.path('/');
                if (Loginresult == true)
                    oScope.m_oController.m_oLocation.path(pathbefore);

            });
        }
    };

    MainController.prototype.Logout = function() {

        if (this.m_oLoginService.isLogged()) {
            this.m_oLoginService.Logout();
            this.m_oTemplateCache.removeAll();
        }

    };

    MainController.prototype.getUserName = function() {
        var oScope = this.m_oScope;
        if (oScope.m_oController.m_oLoginService.isLogged()) {
            return ' Welcome ' + oScope.m_oController.m_oLoginService.getUserName();
        }

        return '';

    };

    MainController.$inject = [
        '$scope',
        '$location',
        '$modal',
        'LoginService',
        '$templateCache'
    ];
    return MainController;
}) ();