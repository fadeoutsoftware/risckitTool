/**
 * Created by s.adamo on 04/07/2014.
 */
var MapController = (function() {



    function MapController($scope, $location) {
        this.m_oScope = $scope;
        this.m_oScope.m_oController = this;
        this.m_oLocation = $location;

        var map_canvas = document.getElementById('map_canvas');
        var map_options = {
            center: new google.maps.LatLng(44.40338, 2.17403),
            zoom: 5,
            mapTypeId: google.maps.MapTypeId.ROADMAP
        }
        this.map = new google.maps.Map(map_canvas, map_options);

    };

    MapController.$inject = [
        '$scope',
        '$location'

    ];

    return MapController;
}) ();
