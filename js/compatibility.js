var BrowserDetect = {

    isInitialized: function(){
        if( this.browser && this.version )
        {
            console.debug("BrowserDetect initialized");
            return true;
        }
        else
        {
            console.debug("BrowserDetect NOT initialized");
            return false;
        }
    },
    init: function () {
        this.browser = this.searchString(this.dataBrowser) || "Other";
        this.version = this.searchVersion(navigator.userAgent) || this.searchVersion(navigator.appVersion) || "Unknown";
    },
    searchString: function (data) {
        for (var i = 0; i < data.length; i++) {
            var dataString = data[i].string;
            this.versionSearchString = data[i].subString;

            if (dataString.indexOf(data[i].subString) !== -1) {
                return data[i].identity;
            }
        }
    },
    searchVersion: function (dataString) {
        var index = dataString.indexOf(this.versionSearchString);
        if (index === -1) {
            return;
        }

        var rv = dataString.indexOf("rv:");
        if (this.versionSearchString === "Trident" && rv !== -1) {
            return parseFloat(dataString.substring(rv + 3));
        } else {
            return parseFloat(dataString.substring(index + this.versionSearchString.length + 1));
        }
    },

    dataBrowser: [
        {string: navigator.userAgent, subString: "Chrome", identity: "Chrome"},
        {string: navigator.userAgent, subString: "MSIE", identity: "Explorer"},
        {string: navigator.userAgent, subString: "Trident", identity: "Explorer"},
        {string: navigator.userAgent, subString: "Firefox", identity: "Firefox"},
        {string: navigator.userAgent, subString: "Safari", identity: "Safari"},
        {string: navigator.userAgent, subString: "Opera", identity: "Opera"}
    ]

};


function isBrowserCompliant()
{
    if( BrowserDetect.isInitialized() == false)
        BrowserDetect.init();


    console.debug("You are using: " + BrowserDetect.browser + ", version" + BrowserDetect.version);

    if( BrowserDetect.browser == "Explorer")
        return false;
    if( BrowserDetect.browser == "Chrome" && BrowserDetect.version < 10)
        return false;
    if( BrowserDetect.browser == "Firefox" && BrowserDetect.version < 5)
        return false;
    if( BrowserDetect.browser == "Safari" && BrowserDetect.version < 5)
        return false;
    if( BrowserDetect.browser == "Opera" && BrowserDetect.version < 11)
        return false;


    return true;
}