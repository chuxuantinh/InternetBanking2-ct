"use strict";
var noclientApp = angular.module("noclientApp", ["ngRoute"]);

noclientApp.run(function ($rootScope, $location) {

    $rootScope.go = function (path) {
        $location.path(path);
    };

});

noclientApp.config(function ($routeProvider) {
    $routeProvider
        .when("/", {
            templateUrl: "resources/htmlMain/newclient.htm",
            controller: "registrationCtrl"
        })
        ;



});

noclientApp.constant("basicUrl", "http://localhost:8080/");
noclientApp.constant("clientUrl", "client/");

noclientApp.constant("countryUrl", "country/");

//REST calls functions service
noclientApp.service("rest_s", function ($http, basicUrl) {

    this.getAllRecord = function (contScope, entityUrl, noContentM, errorM) {
        return $http.get(basicUrl + entityUrl)
            .then(function (response) {
                contScope.valid = true;
                if (response.status == 204) { //NO_CONTENT
                    $.notify(noContentM, "info");
                }
                contScope.tempObject = response.data;
            }, function () {
                contScope.valid = false;
                $.notify(errorM, "warn");
            });
    };

    this.getRecord = function (contScope, entityUrl, id, notFoundM, errorM) {
        return $http.get(basicUrl + entityUrl + id)
            .then(function (response) {
                contScope.valid = true;
                contScope.tempObject = response.data;
                console.log(contScope.tempObject);
            }, function (response) {
                if (response.status == 404) { //NOT_FOUND
                }
                contScope.valid = false;
                $.notify(errorM, "warn");
            });
    };

    this.createRecord = function (contScope, entityObject, entityUrl, successM, conflictM, errorM) {
        console.log(entityObject);
        var entityObjectJSON = JSON.stringify(entityObject);
        return $http.post(basicUrl + entityUrl, entityObjectJSON)
            .then(function () {
                contScope.valid = true;
                $.notify(successM, "success");
            }, function (response) {
                contScope.valid = false;
                if (response.status == 409) { //CONFLICT
                    $.notify(conflictM, "warn");
                } else {
                    $.notify(errorM, "error");
                }
            });
    };

});



//CLIENT CONTROLER ------------------------------------------------------------------------------------

noclientApp.controller("registrationCtrl", function ($scope, $routeParams, $location, $filter, rest_s,
    clientUrl, countryUrl) {

    //ENTITY variables

    $scope.AddressObj = {
        id: "", country: {}, city: "", street: "", streetNumber: "", zipCode: ""
    };

    $scope.ClientObj = {
        id: "", residenceAddress: {}, firstName: "", lastName: "", personalNumber: "", email: "",
        telephoneNumber: "", ssoId: "", password: "", state: ""
    };


    //Variables
    $scope.countryCollection = [];
    $scope.clDetail = {};

    $scope.detail_id = $routeParams.id;

    //REST calls

    $scope.getAllCountry = function () {
        var getAllPromise = rest_s.getAllRecord($scope, countryUrl,
            "Žádná země nebyla nalezena",
            "Nastala chyba při načítání dat");

        getAllPromise.then(function () {
            $scope.countryCollection = $scope.tempObject;
        });

    };

    $scope.createInit = function () {
        $scope.getAllCountry();
    };

    $scope.createClient = function () {

        //Country
        //Address
        $scope.AddressObj.country = $scope.selectedCountry;
        $scope.ClientObj.residenceAddress = $scope.AddressObj;

        var createPromise = rest_s.createRecord($scope, $scope.ClientObj, clientUrl,
            "Přístup založen, nyní se můžete přihlásit",
            "Přístup už existuje, nelze vložit znovu",
            "Přístup nebyl nastaven, nastala chyba");

        createPromise.then(function () {
            //$location.path("/main");
            window.location.replace("http://localhost:8080/main#/");
        });



    };

});