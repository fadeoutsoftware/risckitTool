/**
 * Created by s.adamo on 14/07/2014.
 */
var UserProfile = (function() {
    function UserProfile($scope, oUsersService, oLoginService)
    {
        this.m_oScope = $scope;
        this.m_oScope.$ctrl = this;

        oLoginService.goHomeIfNotLogged();

        this.m_oUsersService = oUsersService;
        this.m_oLoginService = oLoginService;

        this.m_oUpdatePassword = {
            oldPassword : "",
            newPassword : "",
            newPasswordRepeated : "",
        };
        this.m_sUserName = this.m_oLoginService.getUserName();

    }



    UserProfile.prototype.updatePassword = function ()
    {
        var oThis = this;

        if(this.m_oUpdatePassword.newPassword == "" || this.m_oUpdatePassword.newPassword == null || this.m_oUpdatePassword.newPassword != this.m_oUpdatePassword.newPasswordRepeated)
        {
            alert("Check new password.");
            return;
        }
        if(this.m_oUpdatePassword.oldPassword == "" || this.m_oUpdatePassword.oldPassword == null)
        {
            alert("Old password cannot be empty");
            return;
        }


        var oOldUser = {
            id : this.m_oLoginService.getUserId(),
            userName : this.m_oLoginService.getUserName(),
            password : this.m_oUpdatePassword.oldPassword
        }

        var oNewUser = {
            id : this.m_oLoginService.getUserId(),
            userName : this.m_oLoginService.getUserName(),

            password : this.m_oUpdatePassword.newPassword
        }

        this.m_oUsersService.updateUserPassword(oOldUser, oNewUser)
            .then(function (oResponse)
            {
                alert("Password changed succefully.")
            })
            .catch(function (oReason)
            {

            })
    }


    UserProfile.prototype.updateUsername = function ()
    {
        var oThis = this;

        if(this.m_sUserName == "" || this.m_sUserName == null)
        {
            alert("Username cannot be empty");
            return;
        }


        var oOldUser = {
            id : this.m_oLoginService.getUserId(),
            userName : this.m_oLoginService.getUserName(),

        }

        var oNewUser = {
            id : this.m_oLoginService.getUserId(),
            userName : this.m_sUserName
        }

        this.m_oUsersService.updateUserPassword(oOldUser, oNewUser)
            .then(function (oResponse)
            {
                oThis.m_oLoginService.setUserName(oNewUser.userName)
                alert("Username changed succefully.")
            })
            .catch(function (oReason)
            {

            })
    }








    UserProfile.prototype.getRepeatPasswordStyle = function ()
    {
        if(this.m_oUpdatePassword.newPassword == this.m_oUpdatePassword.newPasswordRepeated)
        {
            return {'background-color':'white'}
        }
        else
            return {'background-color':'#ff6e6e'}
    }





    UserProfile.$inject = [
        '$scope',
        'UsersService',
        'LoginService'
    ];
    return UserProfile;
}) ();