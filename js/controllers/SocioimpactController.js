var SocioimpactController = (function() {

    function SocioimpactController($scope, $location, oSharedService, oEventService, oSocioImpactService, $routeParams) {
        this.m_oScope = $scope;
        this.m_oScope.m_oController = this;
        this.m_oLocation = $location;
        this.m_oEventService = oEventService;
        this.m_oSharedService = oSharedService;
        this.m_oSocioImpactService = oSocioImpactService;
        this.m_oRouteParams = $routeParams;
        var idsocioimpact = this.m_oRouteParams.idsocioimpact;
        this.m_oSelectedCategoryId;
        this.m_oSelectedSubCategoryId;
        this.m_oSelectedCurrencyId;
        this.m_oSocioimpact;
        this.m_oSubCategories;


        var oEvent = this.m_oSharedService.getEvent();
        if (oEvent != null)
        {
            //Carico le categorie
            this.m_oSocioImpactService.LoadCategories().success(function(data){
                //Set category
                $scope.m_oController.m_oSocioImpactService.SetCategories(data);

                //Carico le valute
                $scope.m_oController.m_oSocioImpactService.LoadCurrencies().success(function(data){
                    //Set currency
                    $scope.m_oController.m_oSocioImpactService.SetCurrencies(data);

                    if (idsocioimpact != null) {
                        $scope.m_oController.m_oSocioImpactService.getSocioImpact(idsocioimpact).success(function (data) {
                            $scope.m_oController.m_oSocioimpact = data;
                            $scope.m_oController.m_oSelectedCategoryId = data.idCategory;
                            $scope.m_oController.m_oSelectedCurrencyId = data.idCurrency;
                        });
                    }
                    else
                        this.m_oSocioimpact = new Object();

                });

            });


            $scope.$watch('m_oController.m_oSelectedCategoryId', function (newVal, oldVal) {
                if (newVal !== oldVal) {

                    $scope.m_oController.LoadSubCategories($scope.m_oController.m_oSelectedCategoryId);
                    if ($scope.m_oController.m_oSocioimpact != null)
                        $scope.m_oController.m_oSelectedSubCategoryId = $scope.m_oController.m_oSocioimpact.idSubcategory;
                }
            });
        }

    }

    SocioimpactController.prototype.GetCategories = function() {
        return this.m_oSocioImpactService.GetCategories();

    };

    SocioimpactController.prototype.GetCurrencies = function() {
        return this.m_oSocioImpactService.GetCurrencies();

    };

    SocioimpactController.prototype.LoadSubCategories = function(idcategory) {

        var oScope = this.m_oScope;
        if (this.m_oSocioImpactService.GetCategories() != null) {
            var lenght = this.m_oSocioImpactService.GetCategories().length;
            for (var iCount = 0; iCount < lenght; iCount++) {
                if (idcategory == this.m_oSocioImpactService.GetCategories()[iCount].id)
                    oScope.m_oController.m_oSubCategories = this.m_oSocioImpactService.GetCategories()[iCount].subCategories;
            }
        }

        return null;

    };

    SocioimpactController.prototype.Save = function() {

        var oController = this.m_oScope.m_oController;
        if (oController.m_oSelectedSubCategoryId == null || oController.m_oSelectedCategoryId == null)
        {
            alert('Select Category!');
            return;
        }
        if (oController.m_oSelectedSubCategoryId == 0 || oController.m_oSelectedCategoryId == 0)
        {
            alert('Select Subcategory!');
            return;
        }
        if (oController.m_oSocioimpact != null || oController.m_oSocioimpact != "") {
            if (oController.m_oSelectedCurrencyId == 0 || oController.m_oSelectedCurrencyId == 0) {
                alert('Select Currency!');
                return;
            }
        }

        oController.m_oSocioimpact.idSubcategory = oController.m_oSelectedSubCategoryId;
        oController.m_oSocioimpact.idCategory = oController.m_oSelectedCategoryId;
        oController.m_oSocioimpact.idEvent = this.m_oSharedService.getEvent().id;
        oController.m_oSocioimpact.idCurrency = oController.m_oSelectedCurrencyId;
        this.m_oSocioImpactService.Save(oController.m_oSocioimpact).success(function (data) {
            if (data != null)
            {
                //Reload Socio impact
                oController.m_oSocioImpactService.LoadSocioImpact(oController.m_oSharedService.getEvent().id).success(function(result){
                    if (result != null)
                        oController.m_oSharedService.getEvent().SocioImpacts = result;

                    oController.m_oLocation.path('event');
                });

            }
            else
                alert('Error during save');
        });
    };


    SocioimpactController.prototype.cancel = function() {
        this.m_oLocation.path('event');

    };


    SocioimpactController.$inject = [
        '$scope',
        '$location',
        'SharedService',
        'EventService',
        'SocioimpactService',
        '$routeParams'
    ];


    return SocioimpactController;
}) ();
