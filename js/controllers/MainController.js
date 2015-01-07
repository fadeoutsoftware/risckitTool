/**
 * Created by s.adamo on 01/07/2014.
 */
var MainController = (function() {
    function MainController($scope, $location, $modal, oLoginService) {
        this.m_oScope = $scope;
        this.m_oLocation = $location;
        this.m_oScope.m_oController = this;
        this.m_oModal = $modal;
        this.m_oLoginService = oLoginService;

        $scope.m_oController.m_oLocation.path('map');

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

    MainController.$inject = [
        '$scope',
        '$location',
        '$modal',
        'LoginService'
    ];
    return MainController;
}) ();