/**
 * Created by s.adamo on 14/07/2014.
 */
var LoginRequestController = (function() {
    function LoginRequestController($scope, oLoginService)
    {
        this.m_oScope = $scope;
        this.m_oScope.$ctrl = this;

        this.m_oLoginService = oLoginService;

        this.m_oLoginRequest = {
            firstname : "",
            lastname : "",
            address : "",
            state : "",
            phone : "",
            email : "",
            foundation : "",
            role : "",
            reasons : "",
            disclaimerAccepted : false
        }

    }

    // LoginRequestController.prototype.onLogin = function (sUserName, sPassword) {
    //     if (sUserName == null) return false;
    //     if (sPassword == null) return false;
    //
    //     var oLoginService = this.m_oLoginService;
    //     var oModalService = this.m_oModalInstance;
    //
    //     this.m_oLoginService.login(sUserName, sPassword).success(function(data){
    //
    //         if (data != null && data != "") {
    //             oLoginService.m_sUserName = data.userName;
    //             oLoginService.m_iUserId = data.id;
    //             oLoginService.m_sUserRole = "Administrator";
    //             oLoginService.m_bIsLogged = true;
    //             oLoginService.m_bIsAdmin = data.isAdmin;
    //             oModalService.close(true);
    //             oLoginService.setLogDialogOn(false);
    //         }
    //         else {
    //             oLoginService.m_bIsLogged = false;
    //             alert('Login Error');
    //             //oModalService.dismiss('cancel');
    //         }
    //
    //     }).error(function(data){
    //         oLoginService.m_bIsLogged = false;
    //     });
    // };
    //
    // LoginRequestController.prototype.Cancel = function()
    // {
    //     var oModalService = this.m_oModalInstance;
    //     oModalService.close('cancel');
    //     this.m_oScope.$ctrl.m_oLoginService.m_bIsLogged = false;
    //     this.m_oScope.$ctrl.m_oLoginService.setLogDialogOn(false);
    // };

    LoginRequestController.prototype.sendRequest = function()
    {
        // var oModalService = this.m_oModalInstance;
        // oModalService.close('cancel');
        // this.m_oScope.$ctrl.m_oLoginService.m_bIsLogged = false;
        // this.m_oScope.$ctrl.m_oLoginService.setLogDialogOn(false);

        if(this.m_oLoginRequest.disclaimerAccepted == true)
        {
            //TODO: request data validation

            var httpReqData = {
                userName        : this.m_oLoginRequest.username,
                firstName        : this.m_oLoginRequest.firstname,
                userSurname     : this.m_oLoginRequest.lastname,
                institutionName : this.m_oLoginRequest.foundation,
                role            : this.m_oLoginRequest.role,
                state           : this.m_oLoginRequest.state,
                address         : this.m_oLoginRequest.address,
                phoneNumber     : this.m_oLoginRequest.phone,
                reason          : this.m_oLoginRequest.reasons,
                email           : this.m_oLoginRequest.email
            }

            console.debug("I will send:", httpReqData);

            this.m_oLoginService.loginRequest(httpReqData);
        }
        else
        {
            alert("You must accept login condition to proceed.");
        }
    };


    LoginRequestController.$inject = [
        '$scope',
        'LoginService',
    ];
    return LoginRequestController;
}) ();