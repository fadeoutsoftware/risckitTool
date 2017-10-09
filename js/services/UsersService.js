/**
 * Created by s.adamo on 14/07/2014.
 */
'use strict';
angular.module('risckit.usersService', []).
    service('UsersService', ['$http' ,'CONFIG',  function ($http, CONFIG) {
        this.APIURL = CONFIG.API_ADDRESS; // in app.js

        this.m_oHttp = $http;
        this.m_bIsLogged = null;
        this.m_iUserId = '';
        this.m_sUserName = '';
        this.m_sUserRole = '';
        this.m_bLogDialogOn = false;
        this.m_sLastLogin = '';
        this.m_bIsAdmin = false;

        this.m_iSelectedMenuIndex = 1;




        this.fetchAllUsers = function()
        {
            // var fd = new FormData();
            // fd.append('username', sUserName);
            // fd.append('password', sPassword);
            return this.m_oHttp.get(this.APIURL + "/users/list", {
                transformRequest: angular.identity,
                headers: {'Content-Type': undefined}
            });
        };


        this.acceptUser = function(oUser)
        {
            return this.m_oHttp.post(this.APIURL + "/users/accept", {
                transformRequest: angular.identity,
                headers: {'Content-Type': undefined}
            });
        }

        this.deleteUser = function(oUser)
        {
            // /deleteUser/{id} per ora l'ho messa come delete e non come post
            return this.m_oHttp.delete(this.APIURL + "/deleteUser/" + oUser.id, {
                transformRequest: angular.identity,
                headers: {'Content-Type': undefined}
            });
        }

        this.updateUser = function(oUser)
        {
            // "/updateUserName" (chiamata post mi mandi lo userViewModel con il nuovo name)
            return this.m_oHttp.post(this.APIURL + "/updateUserName", oUser, {
                transformRequest: angular.identity,
                headers: {'Content-Type': undefined}
            });
        }

        this.createUser = function(oUser)
        {
            // "/addUserByAdmin "(chiamata post mi mandi lo userViewModel i campi da metterci sono solo username ed email come da use case, la password verrà generata autoamticamente )
            return this.m_oHttp.post(this.APIURL + "/addUserByAdmin", oUser, {
                transformRequest: angular.identity,
                headers: {'Content-Type': undefined}
            });
        }



        this.resetUserPassword = function(oUser)
        {
            // "/generateNewPassword" (chiamata post mi mandi lo userViewModel) automaticamente genera una password e manda l'email all'utente selezionato
            return this.m_oHttp.post(this.APIURL + "/generateNewPassword", oUser, {
                transformRequest: angular.identity,
                headers: {'Content-Type': undefined}
            });
        }





    }]);
