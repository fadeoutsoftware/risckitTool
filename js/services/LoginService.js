/**
 * Created by s.adamo on 14/07/2014.
 */
'use strict';
angular.module('risckit.loginService', []).
    service('LoginService', ['$http',  function ($http) {
        this.APIURL = 'http://risckit.cloudapp.net/risckit/rest';
        //this.APIURL = 'http://localhost:8080/risckit/rest';

        this.m_oHttp = $http;
        this.m_bIsLogged = null;
        this.m_iUserId = '';
        this.m_sUserName = '';
        this.m_sUserRole = '';
        this.m_bLogDialogOn = false;
        this.m_sLastLogin = '';
        this.m_bIsAdmin = false;

        this.m_iSelectedMenuIndex = 1;


        this.isLogged = function() {
            if (this.m_bIsLogged == null)
                return false;

            return this.m_bIsLogged;
        };

        this.isAdmin = function() {
            if (this.m_bIsAdmin == null)
                return false;

            return this.m_bIsAdmin;
        };


        this.login = function(sUserName, sPassword) {
            if (sUserName == null) return false;
            if (sPassword == null) return false;

            var fd = new FormData();
            fd.append('username', sUserName);
            fd.append('password', sPassword);
            return this.m_oHttp.post(this.APIURL + "/users/login", fd, {
                transformRequest: angular.identity,
                headers: {'Content-Type': undefined}
            });
        };


        this.Logout = function() {
            this.m_bIsLogged = false;

        };

        this.getUserName = function() {
            return this.m_sUserName;
        };

        this.setLogDialogOn = function(bOn) {
            this.m_bLogDialogOn = bOn;
        };

        this.getLogDialogOn = function () {
            return this.m_bLogDialogOn;
        };

        this.getUserRole = function() {
            return this.m_sUserRole;
        };

        this.getLastLogin = function() {
            return this.m_sLastLogin;
        };

        this.getUserId = function() {
            return this.m_iUserId;
        };

        this.getSelectedMenuIndex = function() {
            return this.m_iSelectedMenuIndex;
        };

        this.setSelectedMenuIndex = function(iValue) {
            this.m_iSelectedMenuIndex = iValue;
        };


    }]);
