/**
 * Class to handle the authentication
 * SINGLETON CLASS
 */
var AuthHelper = (function () {
    function AuthHelper() {
        this._sAuthToken = "";
    }
    /**
     *
     * @return {AuthHelper} The singleton for this class
     */
    AuthHelper.getInstance = function () {
        if (AuthHelper._oInstance == null || AuthHelper._oInstance == undefined) {
            AuthHelper._oInstance = new AuthHelper();
        }
        return AuthHelper._oInstance;
    };
    AuthHelper.prototype.setToken = function (sToken) { this._sAuthToken = sToken; };
    AuthHelper.prototype.getToken = function () { return this._sAuthToken; };
    AuthHelper.prototype.hasToken = function () { return (this._sAuthToken != ""); };
    return AuthHelper;
}());
AuthHelper._oInstance = null;
