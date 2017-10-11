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

        this.m_oModuleState = LoginRequestController.MODULE_STATE_IDLE;

    }

    LoginRequestController.MODULE_STATE_IDLE = 0;
    LoginRequestController.MODULE_STATE_WAITING = 1;

    LoginRequestController.prototype.isWaiting = function()
    {
        return (this.m_oModuleState == LoginRequestController.MODULE_STATE_WAITING);
    }

    LoginRequestController.prototype.sendRequest = function()
    {
        // var oModalService = this.m_oModalInstance;
        // oModalService.close('cancel');
        // this.m_oScope.$ctrl.m_oLoginService.m_bIsLogged = false;
        // this.m_oScope.$ctrl.m_oLoginService.setLogDialogOn(false);

        if(this.m_oLoginRequest.disclaimerAccepted == true)
        {
            //TODO: request data validation

            this.m_oModuleState = LoginRequestController.MODULE_STATE_WAITING;

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

            var oThis = this;
            this.m_oLoginService.loginRequest(httpReqData)
                .then(function ()
                {
                    oThis.m_oModuleState = LoginRequestController.MODULE_STATE_IDLE;
                    alert("Request sent succesfully. It will be checked by a supervisor and if approved you will get confirm on email address you entered.")
                })
                .catch(function ()
                {
                    oThis.m_oModuleState = LoginRequestController.MODULE_STATE_IDLE;
                })
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