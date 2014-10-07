/**
 * Created by s.adamo on 08/07/2014.
 */
angular.module('risckit.socioimpactService', []).
    service('SocioimpactService',  ['$http', '$upload', function ($http) {
        this.APIURL = 'http://risckit.cloudapp.net/risckit/rest';
        //this.APIURL = 'http://localhost:8080/risckit/rest';
        this.m_oHttp = $http;
        this.m_oCategories = null;
        this.m_oCurrencies = null;
        this.m_oSubCategories = null;

        this.LoadCategories = function () {

            return this.m_oHttp.get(this.APIURL + '/categories/');

        };

        this.LoadCurrencies = function () {

            return this.m_oHttp.get(this.APIURL + '/currencies/');

        };

        this.GetCategories = function(){
            return this.m_oCategories;
        };

        this.GetCurrencies = function(){
            return this.m_oCurrencies;
        };

        this.GetSubCategories = function(){
            return this.m_oSubCategories;
        };


        this.SetCategories = function(categories){
            this.m_oCategories = categories;
        };

        this.SetCurrencies = function(currencies){
            this.m_oCurrencies = currencies;
        };

        this.SetSubCategories = function(subcategories){
            this.m_oSubCategories = subcategories;
        };

        this.getSocioImpact = function (idsocio) {

            return this.m_oHttp({method: 'GET', url: this.APIURL + '/socioimpact/' + idsocio});

        };

        this.Delete = function (idsocio) {

            return this.m_oHttp.post(this.APIURL + '/socioimpact/delete/' + idsocio);

        };

        this.LoadSocioImpact = function (idEvent) {

            return this.m_oHttp({method: 'GET', url: this.APIURL + '/socioimpact/event/' + idEvent});

        };

        this.Save = function (Socio) {
            return this.m_oHttp.post(this.APIURL + '/socioimpact/save', Socio);
        };

    }]);
