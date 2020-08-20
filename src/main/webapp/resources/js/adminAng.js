"use strict";
var adminApp = angular.module("adminApp", ["ngRoute", "smart-table"]);

adminApp.config(function ($routeProvider) {
    $routeProvider
        .when("/", {
            templateUrl: "resources/htmlAdmin/admin.htm"
        })
        .when("/country", {
            templateUrl: "resources/htmlAdmin/country.htm",
            controller: "countryCtrl"
        })
        .when("/accounttype", {
            templateUrl: "resources/htmlAdmin/accounttype.htm",
            controller: "accountTypeCtrl"
        })
        .when("/currency", {
            templateUrl: "resources/htmlAdmin/currency.htm",
            controller: "currencyCtrl"
        })
        .when("/bank", {
            templateUrl: "resources/htmlAdmin/bank.htm",
            controller: "bankCtrl"
        })
        .when("/announcement", {
            templateUrl: "resources/htmlAdmin/announcement.htm",
            controller: "announcementCtrl"
        });

});

adminApp.constant("basicUrl", "http://localhost:8080/admin/");
adminApp.constant("countryUrl", "country/");
adminApp.constant("accountTypeUrl", "accounttype/");
adminApp.constant("currencyUrl", "currency/");
adminApp.constant("bankUrl", "bank/");
adminApp.constant("announcementUrl", "announcement/");


//REST calls functions service
adminApp.service("rest_s", function ($http, basicUrl) {

    this.getAllRecord = function (contScope, entityUrl, noContentM, errorM) {
        $http.get(basicUrl + entityUrl)
            .then(function (response) {
                contScope.valid = true;
                if (response.status == 204) { //NO_CONTENT
                    $.notify(noContentM, "info");
                }
                contScope.rowCollection = response.data;
            }, function () {
                contScope.valid = false;
                $.notify(errorM, "warn");
            });
    };

    this.createRecord = function (contScope, entityObject, entityUrl, successM, conflictM, errorM) {
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

    this.updateRecord = function (contScope, entityObject, entityUrl, successM, notFoundM, conflictM, errorM) {
        var entityObjectJSON = JSON.stringify(entityObject);

        $http.put(basicUrl + entityUrl + entityObject.id, entityObjectJSON)
            .then(function () {
                contScope.valid = true;
                contScope.selected = {};
                $.notify(successM, "success");
            }, function (response) {
                contScope.valid = false;
                if (response.status == 404) { //NOT_FOUND
                    contScope.selected = {};
                    $.notify(notFoundM, "error");
                } else if (response.status == 409) { //CONFLICT
                    $.notify(conflictM, "warn");
                } else {
                    contScope.selected = {};
                    $.notify(errorM, "error");
                }
            });
    };

    this.removeRecord = function (contScope, entityObject, entityUrl, successM, notFoundM, conflictM, errorM) {

        $http.delete(basicUrl + entityUrl + entityObject.id)
            .then(function () {
                contScope.valid = true;
                var index = contScope.rowCollection.indexOf(entityObject);
                contScope.rowCollection.splice(index, 1);
                $.notify(successM, "success");
            }, function (response) {
                contScope.valid = false;
                if (response.status == 404) { //NOT_FOUND
                    $.notify(notFoundM, "error");
                } else if (response.status == 409) { //CONFLICT
                    $.notify(conflictM, "warn");
                } else {
                    $.notify(errorM, "error");
                }
            });
    };

});

//Inline table editation functions service
adminApp.service("table_s", function () {

    this.getTemplate = function (record, contScope) {
        if (record.id === contScope.selected.id) {
            return "edit";
        }
        else return "display";
    };

    this.editRow = function (record, contScope) {
        contScope.selected = angular.copy(record);
    };

    this.reset = function (record, contScope) {
        var index = contScope.rowCollection.indexOf(record);
        for (var k in contScope.selected) contScope.rowCollection[index][k] = contScope.selected[k];
        contScope.selected = {};
    };

});

//COUNTRY DIAL ADMINISTRATION ---------------------------------------------------------------------------------

adminApp.controller("countryCtrl", function ($scope, table_s, rest_s, countryUrl) {

    //ENTITY variables
    $scope.CountryObj = { id: "", countryName: "" };

    //TABLE variables
    $scope.selected = {};
    $scope.rowCollection = [];

    //TABLE functions

    $scope.getTemplate = function (record) {
        return table_s.getTemplate(record, $scope);
    };

    $scope.editRow = function (record) {
        table_s.editRow(record, $scope);
    };

    $scope.reset = function (record) {
        table_s.reset(record, $scope);
    };

    //REST calls

    $scope.getAllCountry = function () {
        rest_s.getAllRecord($scope, countryUrl,
            "Žádná země nebyla nalezena",
            "Nastala chyba při načítání dat");

    };

    $scope.createCountry = function () {

        var createRecordPromise = rest_s.createRecord($scope, $scope.CountryObj, countryUrl,
            "Země vložena",
            "Země už existuje, nelze vložit znovu",
            "Země nebyla vložena, nastala chyba");

        createRecordPromise.then(function () {
            if ($scope.valid === true) {
                $scope.getAllCountry();
                $scope.CountryObj.countryName = "";
            }
        });

    };

    $scope.updateCountry = function (country) {
        rest_s.updateRecord($scope, country, countryUrl,
            "Země editována",
            "Země nebyla nalezena",
            "Taková země už existuje, záznam nelze editovat",
            "Země nebyla editována, nastala chyba");
    };

    $scope.removeCountry = function (country) {
        //if (confirm("Opravdu chcete smazat tuto zemi?")) {
        rest_s.removeRecord($scope, country, countryUrl,
            "Země smazána",
            "Země nebyla nalezena",
            "Zemi nelze smazat kvůli integritě dat",
            "Země nebyla smazána, nastala chyba");
        //}


    };

});

//ACCOUNT TYPE DIAL ADMINISTRATION ---------------------------------------------------------------------------------

adminApp.controller("accountTypeCtrl", function ($scope, table_s, rest_s, accountTypeUrl) {

    //ENTITY variables
    $scope.AccountTypeObj = { id: "", accountTypeName: "", interestRate: "" };

    //TABLE variables
    $scope.selected = {};
    $scope.rowCollection = [];

    //TABLE functions

    $scope.getTemplate = function (record) {
        return table_s.getTemplate(record, $scope);
    };

    $scope.editRow = function (record) {
        table_s.editRow(record, $scope);
    };

    $scope.reset = function (record) {
        table_s.reset(record, $scope);
    };

    //REST calls

    $scope.getAllAccountType = function () {
        rest_s.getAllRecord($scope, accountTypeUrl,
            "Žádný typ účtu nebyl nalezen",
            "Nastala chyba při načítání dat");

    };

    $scope.createAccountType = function () {

        var createRecordPromise = rest_s.createRecord($scope, $scope.AccountTypeObj, accountTypeUrl,
            "Typ účtu vložen",
            "Typ účtu už existuje, nelze vložit znovu",
            "Typ účtu nebyl vložen, nastala chyba");

        createRecordPromise.then(function () {
            if ($scope.valid === true) {
                $scope.getAllAccountType();
                $scope.AccountTypeObj.accountTypeName = "";
                $scope.AccountTypeObj.interestRate = "";
            }
        });

    };

    $scope.updateAccountType = function (accountType) {
        rest_s.updateRecord($scope, accountType, accountTypeUrl,
            "Typ účtu editován",
            "Typ účtu nebyl nalezen",
            "Takovž typ účtu už existuje, záznam nelze editovat",
            "Typ účtu nebyl editován, nastala chyba");
    };

    $scope.removeAccountType = function (accountType) {
        //if (confirm("Opravdu chcete smazat tento typ účtu?")) {
        rest_s.removeRecord($scope, accountType, accountTypeUrl,
            "Typ účtu smazán",
            "Typ účtu nebyl nalezena",
            "Typ účtu nelze smazat kvůli integritě dat",
            "Typ účtu nebyl smazán, nastala chyba");
        //}


    };

});

//CURRENCY DIAL ADMINISTRATION ---------------------------------------------------------------------------------

adminApp.controller("currencyCtrl", function ($scope, table_s, rest_s, currencyUrl) {

    //ENTITY variables
    $scope.CurrencyObj = { id: "", symbol: "", currencyName: "" };

    //TABLE variables
    $scope.selected = {};
    $scope.rowCollection = [];

    //TABLE functions

    $scope.getTemplate = function (record) {
        return table_s.getTemplate(record, $scope);
    };

    $scope.editRow = function (record) {
        table_s.editRow(record, $scope);
    };

    $scope.reset = function (record) {
        table_s.reset(record, $scope);
    };

    //REST calls

    $scope.getAllCurrency = function () {
        rest_s.getAllRecord($scope, currencyUrl,
            "Žádná měna nebyla nalezena",
            "Nastala chyba při načítání dat");

    };

    $scope.createCurrency = function () {

        var createRecordPromise = rest_s.createRecord($scope, $scope.CurrencyObj, currencyUrl,
            "Měna vložena",
            "Měna už existuje, nelze vložit znovu",
            "Měna nebyla vložena, nastala chyba");

        createRecordPromise.then(function () {
            if ($scope.valid === true) {
                $scope.getAllCurrency();
                $scope.CurrencyObj.symbol = "";
                $scope.CurrencyObj.currencyName = "";
            }
        });

    };

    $scope.updateCurrency = function (currency) {
        rest_s.updateRecord($scope, currency, currencyUrl,
            "Měna editována",
            "Měna nebyla nalezena",
            "Taková měna už existuje, záznam nelze editovat",
            "Měna nebyla editována, nastala chyba");
    };

    $scope.removeCurrency = function (currency) {
        //if (confirm("Opravdu chcete smazat tuto měnu?")) {
        rest_s.removeRecord($scope, currency, currencyUrl,
            "Měna smazána",
            "Měna nebyla nalezena",
            "Měna nelze smazat kvůli integritě dat",
            "Měna nebyla smazána, nastala chyba");
        //}


    };

});

//BANK DIAL ADMINISTRATION ---------------------------------------------------------------------------------

adminApp.controller("bankCtrl", function ($scope, table_s, rest_s, bankUrl) {

    //ENTITY variables
    $scope.BankObj = { id: "", code: "", bankName: "", swift: "" };

    //TABLE variables
    $scope.selected = {};
    $scope.rowCollection = [];

    //TABLE functions

    $scope.getTemplate = function (record) {
        return table_s.getTemplate(record, $scope);
    };

    $scope.editRow = function (record) {
        table_s.editRow(record, $scope);
    };

    $scope.reset = function (record) {
        table_s.reset(record, $scope);
    };

    //REST calls

    $scope.getAllBank = function () {
        rest_s.getAllRecord($scope, bankUrl,
            "Žádná banka nebyla nalezena",
            "Nastala chyba při načítání dat");

    };

    $scope.createBank = function () {

        var createRecordPromise = rest_s.createRecord($scope, $scope.BankObj, bankUrl,
            "Banka vložena",
            "Banka už existuje, nelze vložit znovu",
            "Banka nebyla vložena, nastala chyba");

        createRecordPromise.then(function () {
            if ($scope.valid === true) {
                $scope.getAllBank();
                $scope.BankObj.code = "";
                $scope.BankObj.bankName = "";
                $scope.BankObj.swift = "";
            }
        });

    };

    $scope.updateBank = function (bank) {
        rest_s.updateRecord($scope, bank, bankUrl,
            "Banka editována",
            "Banka nebyla nalezena",
            "Taková banka už existuje, záznam nelze editovat",
            "Banka nebyla editována, nastala chyba");
    };

    $scope.removeBank = function (bank) {
        //if (confirm("Opravdu chcete smazat tuto banku?")) {
        rest_s.removeRecord($scope, bank, bankUrl,
            "Banka smazána",
            "Banka nebyla nalezena",
            "Banku nelze smazat kvůli integritě dat",
            "Banka nebyla smazána, nastala chyba");
        //}


    };

});