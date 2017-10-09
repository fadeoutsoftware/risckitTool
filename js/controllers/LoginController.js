/**
 * Created by s.adamo on 14/07/2014.
 */
var LoginController = (function() {
    function LoginController($scope, $modalInstance, oLoginServicee, oLocation) {
        this.m_oScope = $scope;
        this.m_oScope.m_oController = this;
        this.m_oModalInstance = $modalInstance;
        this.m_oLoginService = oLoginServicee;
        this.m_oLocation = oLocation;
        this.m_oLoginService.setLogDialogOn(true);

    }

    LoginController.prototype.onLogin = function (sUserName, sPassword) {
        if (sUserName == null) return false;
        if (sPassword == null) return false;

        var oLoginService = this.m_oLoginService;
        var oModalService = this.m_oModalInstance;

        this.m_oLoginService.login(sUserName, sPassword).success(function(data){

            if (data != null && data != "") {
                oLoginService.m_sUserName = data.userName;
                oLoginService.m_iUserId = data.id;
                oLoginService.m_sUserRole = "Administrator";
                oLoginService.m_bIsLogged = true;
                oLoginService.m_bIsAdmin = data.isAdmin;
                oModalService.close(true);
                oLoginService.setLogDialogOn(false);
            }
            else {
                oLoginService.m_bIsLogged = false;
                alert('Login Error');
                //oModalService.dismiss('cancel');
            }

        }).error(function(data){
            oLoginService.m_bIsLogged = false;
        });
    };

    LoginController.prototype.Cancel = function()
    {
        var oModalService = this.m_oModalInstance;
        oModalService.close('cancel');
        this.m_oScope.m_oController.m_oLoginService.m_bIsLogged = false;
        this.m_oScope.m_oController.m_oLoginService.setLogDialogOn(false);
    };




    LoginController.$inject = [
        '$scope',
        '$modalInstance',
        'LoginService',
        '$location'
    ];
    return LoginController;
}) ();