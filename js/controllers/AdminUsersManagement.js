/**
 * Created by s.adamo on 14/07/2014.
 */
var AdminUsersManagement = (function() {
    function AdminUsersManagement($scope, oUsersService, oLoginService)
    {
        this.m_oScope = $scope;
        this.m_oScope.$ctrl = this;

        oLoginService.goHomeIfNotLogged();

        this.m_oUsersService = oUsersService;

        this.m_aoUsersList = [];
        this.m_oUserReasonVisibility = {};
        this.m_oPopUpMode = AdminUsersManagement.MODE_CREATE;
        this.m_bIsCreateEditUserPopupVisible = false;
        this.m_oUserToBeEdited = null;
        this.filters = {
            freeSearch : ""
        };

        this.m_oMode = null;
        var sUrl = location.href;
        if(sUrl.indexOf("/accessrequestmanagement") >= 0)
        {
            this.m_oMode = AdminUsersManagement.MODE_ACCESS_REQUESTS_MANAGEMENT;
        }
        else if(sUrl.indexOf("/adminusersmanagement") >= 0)
        {
            this.m_oMode = AdminUsersManagement.MODE_USERS_MANAGEMENT;
        }


        this.refereshList();


        // Register some methods on 'scope' to avoid 'this' reference mismatching
        this.m_oScope.itemFilterFn = function (oItem) {
            return $scope.$ctrl.itemFilterFn(oItem);
        };

    }

    AdminUsersManagement.MODE_EDIT = 0;
    AdminUsersManagement.MODE_CREATE = 1;

    AdminUsersManagement.MODE_USERS_MANAGEMENT = 0;
    AdminUsersManagement.MODE_ACCESS_REQUESTS_MANAGEMENT = 1;


    AdminUsersManagement.prototype.itemFilterFn = function(oItem)
    {
        if (this.filters.freeSearch == "" || this.filters.freeSearch == null)
        {
            return true;
        }

        var asFieldsToCompare = [
            "userName",
            "userSurname",
            "email",
            "institutionName",
        ];


        for(var i = 0; i < asFieldsToCompare.length; i++)
        {
            var sValue = oItem[asFieldsToCompare[i]];
            if( sValue != null && sValue.toLowerCase().indexOf(this.filters.freeSearch.toLowerCase()) >= 0)
            {
                return true;
            }
        }
        return false;
    }


    AdminUsersManagement.prototype.getPageTitle = function()
    {
        if( this.m_oMode == AdminUsersManagement.MODE_ACCESS_REQUESTS_MANAGEMENT)
        {
            return "Access Requests Manager";
        }
        else{
            return "Users Manager";
        }
    }



    /**
     * Fetch from server the pending users list
     */
    AdminUsersManagement.prototype.fetchPendingUsers = function()
    {
        var oThis = this;
        this.m_oUsersService.fetchAllUsers().then(function(oResponse){
            oThis.m_aoUsersList = [];
            for(var i = 0; i < oResponse.data.length; i++)
            {
                var oUser =  oResponse.data[i];

                // DEBUG (+)
                oUser.isConfirmed = false;
                oUser.phoneNumber = "+39 012 0123456";
                oUser.state = "Italy";
                oUser.address = "Via Sottoripa 1A/118";
                oUser.email = "testuser@fadeout.it";
                oUser.institutionName = "ANUBI";
                oUser.role = "Top Anubi Invoker";
                oUser.reason = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vestibulum mattis vel libero et sodales. Sed sapien neque, aliquet ut varius eget, tincidunt quis quam. Donec sodales neque vel orci fermentum iaculis. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Nam vehicula, ex eu accumsan fermentum, nunc nunc varius lorem, tincidunt consectetur metus eros vulputate arcu. Morbi vitae libero quis turpis laoreet fermentum ut at odio. Sed maximus dui vitae luctus consequat. Nullam sollicitudin mi vitae tortor molestie, quis bibendum orci congue. Quisque et mauris aliquet, porttitor quam eu, euismod tortor. Mauris et orci tellus.";
                // DEBUG (-)

                if(oUser.isConfirmed == false)
                {
                    oThis.m_aoUsersList.push(oUser);
                }
            }
        })
    }

    /**
     * Fetch from server the pending users list
     */
    AdminUsersManagement.prototype.fetchAllUsers = function()
    {
        var oThis = this;
        this.m_oUsersService.fetchAllUsers().then(function(oResponse){
            oThis.m_aoUsersList = [];
            for(var i = 0; i < oResponse.data.length; i++)
            {
                var oUser =  oResponse.data[i];

                // DEBUG (+)
                oUser.isConfirmed = ((i % 2) == 0);
                oUser.phoneNumber = "+39 012 0123456";
                oUser.state = "Italy";
                oUser.address = "Via Sottoripa 1A/118";
                oUser.email = "testuser@fadeout.it";
                oUser.institutionName = "ANUBI";
                oUser.role = "Top Anubi Invoker";
                oUser.reason = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vestibulum mattis vel libero et sodales. Sed sapien neque, aliquet ut varius eget, tincidunt quis quam. Donec sodales neque vel orci fermentum iaculis. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Nam vehicula, ex eu accumsan fermentum, nunc nunc varius lorem, tincidunt consectetur metus eros vulputate arcu. Morbi vitae libero quis turpis laoreet fermentum ut at odio. Sed maximus dui vitae luctus consequat. Nullam sollicitudin mi vitae tortor molestie, quis bibendum orci congue. Quisque et mauris aliquet, porttitor quam eu, euismod tortor. Mauris et orci tellus.";
                // DEBUG (-)

                oThis.m_aoUsersList.push(oUser);
            }
        })
    }


    AdminUsersManagement.prototype.refereshList = function()
    {
        if( this.m_oMode == AdminUsersManagement.MODE_ACCESS_REQUESTS_MANAGEMENT)
        {
            this.fetchPendingUsers();
        }
        else{
            this.fetchAllUsers();
        }
    }


    AdminUsersManagement.prototype.toggleUserReasonVisibility = function (oUser)
    {
        if( this.m_oUserReasonVisibility[oUser.id] == false || this.m_oUserReasonVisibility[oUser.id] == true)
        {
            this.m_oUserReasonVisibility[oUser.id] = !this.m_oUserReasonVisibility[oUser.id];
        }
        else{
            this.m_oUserReasonVisibility[oUser.id] = true;
        }
    }

    AdminUsersManagement.prototype.isUserReasonVisibile = function (oUser)
    {
        return (this.m_oUserReasonVisibility[oUser.id] == true);
    }



    AdminUsersManagement.prototype.isCreateUserMode = function ()
    {
        return (this.m_oPopUpMode == AdminUsersManagement.MODE_CREATE);
    }
    AdminUsersManagement.prototype.isEditUserMode = function ()
    {
        return (this.m_oPopUpMode == AdminUsersManagement.MODE_EDIT);
    }



    /**
     * Accept a pending user
     * @param oUser
     */
    AdminUsersManagement.prototype.acceptUser = function (oUser)
    {
        var oThis = this;
        this.m_oUsersService.acceptUser(oUser)
            .then(function (oResponse)
            {
                oThis.refereshList();
            })
            .catch(function (oReason)
            {

            })
    }

    /**
     * Delete and existing (or pending) user
     * @param oUser
     */
    AdminUsersManagement.prototype.deleteUser = function (oUser)
    {
        if(confirm( "User '"+ oUser.userName + " " + oUser.userSurname + "' will be deleted.\nAre you sure?") == true)
        {
            var oThis = this;
            this.m_oUsersService.deleteUser(oUser)
                .then(function (oResponse)
                {
                    oThis.refereshList();
                })
                .catch(function (oReason)
                {

                })
        }
    }

    /**
     * Open section to edit an existing user
     * @param oUser The user to be edited
     */
    AdminUsersManagement.prototype.editUser = function (oUser)
    {
        this.m_oPopUpMode = AdminUsersManagement.MODE_EDIT;
        this.m_oUserToBeEdited = angular.copy(oUser);

        this.m_bIsCreateEditUserPopupVisible = true;
    }


    AdminUsersManagement.prototype.resetUserPassword = function ()
    {
        var oThis = this;
        this.m_oUsersService.resetUserPassword(this.m_oUserToBeEdited)
            .then(function (oResponse)
            {
                oThis.refereshList();
            })
            .catch(function (oReason)
            {

            })
    }

    /**
     * Open section to create new user
     */
    AdminUsersManagement.prototype.createNewUser = function ()
    {
        this.m_oPopUpMode = AdminUsersManagement.MODE_CREATE
        this.m_oUserToBeEdited = {};

        this.m_bIsCreateEditUserPopupVisible = true;
    }


    AdminUsersManagement.prototype.closePopup = function ()
    {
        this.m_bIsCreateEditUserPopupVisible = false;
    }

    AdminUsersManagement.prototype.saveUserChanges = function ()
    {
        var oThis = this;

        var oPromise = null;
        if(this.m_oPopUpMode == AdminUsersManagement.MODE_CREATE){
            oPromise = this.m_oUsersService.createUser(this.m_oUserToBeEdited);
        }
        else if(this.m_oPopUpMode == AdminUsersManagement.MODE_EDIT)
        {
            oPromise = this.m_oUsersService.updateUser(this.m_oUserToBeEdited);
        }
        else
            return;

        oPromise
            .then(function (oResponse)
            {
                oThis.refereshList();
                oThis.closePopup();
            })
            .catch(function (oReason)
            {
                oThis.closePopup();
            })
    }


    /**
     * @return {string} The create/edit user section title according to
     * the mode (crete or edit)
     */
    AdminUsersManagement.prototype.getCreateEditUserPopupTitle = function ()
    {
        if(this.m_oPopUpMode == AdminUsersManagement.MODE_EDIT){
            return "Edit user";
        }
        if(this.m_oPopUpMode == AdminUsersManagement.MODE_CREATE){
            return "Create user";
        }
    }
    /**
     * @return {boolean} TRUE if the section to create/edit users is visible, FALSE otherwise
     */
    AdminUsersManagement.prototype.isCreateEditUserPopupVisible = function ()
    {
        return this.m_bIsCreateEditUserPopupVisible;
    }


    AdminUsersManagement.prototype.getUsersList = function ()
    {
        /*
         adresses
         email
         id
         institutionName
         isAdmin
         isConfirmed
         password
         phoneNumber
         reason
         role
         state
         userName
         userSurname
         */
        return this.m_aoUsersList;
    }


    AdminUsersManagement.prototype.userNeedsConfirm = function (oUser)
    {
        return (oUser.isConfirmed == false);
    }





    AdminUsersManagement.$inject = [
        '$scope',
        'UsersService',
        'LoginService'
    ];
    return AdminUsersManagement;
}) ();