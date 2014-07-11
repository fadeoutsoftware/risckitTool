/**
 * Created by s.adamo on 07/07/2014.
 */
/**
 * Created by s.adamo on 04/07/2014.
 */
var MediaController = (function() {

    function MediaController($scope, $location, oSharedService) {
        this.m_oScope = $scope;
        this.m_oScope.m_oController = this;
        this.m_oLocation = $location;
        this.m_sFilePath = null;
        this.Files = [];
        this.m_oSharedService = oSharedService;
        this.NewMedia = new Object();
        this.NewMedia.Files = new Array();
        this.Marker;

        var map_canvas = document.getElementById('media_map_canvas');
        var map_options = {
            center: new google.maps.LatLng(44.40338, 2.17403),
            zoom: 5,
            mapTypeId: google.maps.MapTypeId.ROADMAP
        }

        var map = new google.maps.Map(map_canvas, map_options);

        google.maps.event.addListener(map, 'click', function(event) {

            placeMarker(event.latLng);

        });

        function placeMarker(location) {

            if (this.Marker != null)
                this.Marker.setMap(null);

            this.Marker = new google.maps.Marker({
                position: location,
                map: map,
                draggable: true
            });

            $scope.m_oController.NewMedia.lat = location.lat();
            $scope.m_oController.NewMedia.lon = location.lng();

            //map.setCenter(location);
        }

        $scope.$watch('m_oController.m_sFilePath', function(newVal, oldVal) {
            if (newVal !== oldVal) {
                $scope.m_oController.NewMedia.Files.push(newVal);
                $scope.m_oController.Files.push(newVal);
            }
        });

    }

    MediaController.prototype.Save = function() {
        this.m_oScope.m_oController.m_oSharedService.getEvent().Media.push(this.NewMedia);
        this.m_oLocation.path('event');

    };


    MediaController.$inject = [
        '$scope',
        '$location',
        'SharedService'
    ];


    return MediaController;
}) ();
