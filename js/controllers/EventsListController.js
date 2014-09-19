/**
 * Created by s.adamo on 02/07/2014.
 */
var EventsListController = (function() {

    function EventsListController($scope, $location, $modal, oEventService, oSharedService, oLoginService) {
        this.m_oScope = $scope;
        this.m_oScope.m_oController = this;
        this.m_oLocation = $location;
        this.m_oModal = $modal;
        this.m_oEventService = oEventService; //Service
        this.m_oSharedService = oSharedService;
        this.m_oLoginService = oLoginService;
        this.m_oEventList = [];

        //------pagination------
        this.itemsPerPage = 5;
        this.currentPage = 0;

        if (this.m_oLoginService.isLogged())
            this.m_oScope.m_oController.m_oEventService.LoadEvents(this.m_oLoginService.getUserId()).success(function(data){
                $scope.m_oController.m_oEventList = data;
            });


    };

    EventsListController.prototype.getEvents = function () {
        return this.m_oEventList;

    };

    EventsListController.prototype.openEvent = function (idEvent) {

        var Events = this.getEvents();
        for (var i = 0; i < Events.length; i++) {
            if (Events[i].id == idEvent)
            {
                this.m_oSharedService.setEvent(Events[i]);
                break;
            }
        }

        this.m_oLocation.path('/event');
    };

    EventsListController.prototype.newEvent = function () {
        this.m_oScope.m_oController.m_oSharedService.setEvent(null);
        this.m_oLocation.path('/event');
    };

    EventsListController.prototype.range = function () {
        var rangeSize = 4;
        var ps = [];
        var start;
        var oScope = this.m_oScope;
        start = oScope.m_oController.currentPage;
        if ( start > this.pageCount()-rangeSize ) {
            start = this.pageCount()-rangeSize+1;
        }
        for (var i=start; i<start+rangeSize; i++) {
            ps.push(i);
        }
        return ps;
    };

    EventsListController.prototype.prevPage = function() {
        if (this.m_oScope.m_oController.currentPage > 0) {
            this.m_oScope.m_oController.currentPage--;

        }
    };

    EventsListController.prototype.DisablePrevPage = function() {

        return this.m_oScope.m_oController.currentPage == 0 ? "disabled" : "";

    };

    EventsListController.prototype.pageCount = function() {

        return Math.ceil(this.getEvents().length/this.m_oScope.m_oController.itemsPerPage)-1;

    };

    EventsListController.prototype.nextPage = function() {

        if (this.m_oScope.m_oController.currentPage < this.m_oScope.m_oController.pageCount()) {

            this.m_oScope.m_oController.currentPage++;

        }
    };

    EventsListController.prototype.DisableNextPage = function() {

        return this.m_oScope.m_oController.currentPage == this.m_oScope.m_oController.pageCount() ? "disabled" : "";

    };

    EventsListController.prototype.setPage = function(n) {

           this.m_oScope.m_oController.currentPage = n;

    };

    EventsListController.prototype.deleteEvent = function(idEvent) {
        var oController = this;
        var oRetValue = this.m_oEventService.DeleteEvent(idEvent);
        if (oRetValue != null)  {
            oRetValue.success(function(data){

                if (data < 0) {
                   alert('Error deleting event');
                }

                //Reload Event
                oController.m_oEventService.LoadEvents(oController.m_oLoginService.getUserId()).success(function (data) {
                    oController.m_oEventList = data;
                });

            });
        }


    };


        EventsListController.$inject = [
        '$scope',
        '$location',
        '$modal',
        'EventService',
        'SharedService',
        'LoginService'
    ];

    return EventsListController;
}) ();
