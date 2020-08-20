"use strict";
var mainApp = angular.module("mainApp", ["ngRoute"]);

mainApp.run(function ($rootScope, $location) {

    $rootScope.go = function (path) {
        $location.path(path);
    };


});

mainApp.config(function ($routeProvider) {
    $routeProvider
        .when("/", {
            templateUrl: "resources/htmlMain/main.htm",
            controller: "mainCtrl"
        })
        .when("/registration", {
            templateUrl: "resources/htmlMain/newClient.htm",
            controller: "clientCtrl"
        })
        .when("/accountfav", {
            templateUrl: "resources/htmlMain/accountfav.htm",
            controller: "accountFavouriteCtrl"
        })
        .when("/newaccountfav", {
            templateUrl: "resources/htmlMain/newaccountfav.htm",
            controller: "accountFavouriteCtrl"
        })
        .when("/accountfavdetail/:id", {
            templateUrl: "resources/htmlMain/accountfavdetail.htm",
            controller: "accountFavouriteCtrl"
        })
        .when("/updateaccountfav/:id", {
            templateUrl: "resources/htmlMain/updateaccountfav.htm",
            controller: "accountFavouriteCtrl"
        })
        .when("/paymentsched", {
            templateUrl: "resources/htmlMain/paymentsched.htm",
            controller: "paymentScheduledCtrl"
        })
        .when("/newpaymentsched", {
            templateUrl: "resources/htmlMain/newpaymentsched.htm",
            controller: "paymentScheduledCtrl"
        })
        .when("/paymentscheddetail/:id", {
            templateUrl: "resources/htmlMain/paymentscheddetail.htm",
            controller: "paymentScheduledCtrl"
        })
        .when("/updatepaymentsched/:id", {
            templateUrl: "resources/htmlMain/updatepaymentsched.htm",
            controller: "paymentScheduledCtrl"
        })
        .when("/account", {
            templateUrl: "resources/htmlMain/account.htm",
            controller: "accountCtrl"
        })
        .when("/newaccount", {
            templateUrl: "resources/htmlMain/newaccount.htm",
            controller: "accountCtrl"
        })
        .when("/accountdetail/:id", {
            templateUrl: "resources/htmlMain/accountdetail.htm",
            controller: "accountCtrl"
        })
        .when("/updateaccount/:id", {
            templateUrl: "resources/htmlMain/updateaccount.htm",
            controller: "accountCtrl"
        })
        .when("/payment/account/:id", {
            templateUrl: "resources/htmlMain/accountpayment.htm",
            controller: "paymentCtrl"
        })
        .when("/payment/accountfav/:id", {
            templateUrl: "resources/htmlMain/accountpaymentfav.htm",
            controller: "paymentCtrl"
        })
        .when("/payment/:id", {
            templateUrl: "resources/htmlMain/paymentdetail.htm",
            controller: "paymentCtrl"
        })
        .when("/payment", {
            templateUrl: "resources/htmlMain/newpayment.htm",
            controller: "paymentCtrl"
        })
        .when("/paymentaccfav/:id", {
            templateUrl: "resources/htmlMain/newpaymentaccfav.htm",
            controller: "paymentCtrl"
        })
        .when("/card", {
            templateUrl: "resources/htmlMain/card.htm",
            controller: "cardCtrl"
        })
        .when("/newcard", {
            templateUrl: "resources/htmlMain/newcard.htm",
            controller: "cardCtrl"
        })
        .when("/carddetail/:id", {
            templateUrl: "resources/htmlMain/carddetail.htm",
            controller: "cardCtrl"
        })
        .when("/updatecard/:id", {
            templateUrl: "resources/htmlMain/updatecard.htm",
            controller: "cardCtrl"
        })
        .when("/clientdetail/", {
            templateUrl: "resources/htmlMain/clientdetail.htm",
            controller: "clientCtrl"
        })
        .when("/updateclient/", {
            templateUrl: "resources/htmlMain/updateclient.htm",
            controller: "clientCtrl"
        })
        ;



});

mainApp.constant("basicUrl", "http://localhost:8080/main/");
mainApp.constant("clientUrl", "client/");
mainApp.constant("accountUrl", "account/");
mainApp.constant("accountFavouriteUrl", "accountfavourite/");
mainApp.constant("cardUrl", "card/");
mainApp.constant("cardChangeStateUrl", "card/changestate/");
mainApp.constant("paymentUrl", "payment/");
mainApp.constant("accountPaymentUrl", "payment/account/");
mainApp.constant("accountPaymentFavUrl", "payment/accountfav/");
mainApp.constant("paymentScheduledUrl", "paymentscheduled/");

mainApp.constant("currencyUrl", "currency/");
mainApp.constant("countryUrl", "country/");
mainApp.constant("bankUrl", "bank/");
mainApp.constant("accountTypeUrl", "accounttype/");

//REST calls functions service
mainApp.service("rest_s", function ($http, basicUrl) {

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
                if (response.status == 204) { //NO_CONTENT
                    $.notify(notFoundM, "info");
                }
                contScope.valid = true;
                contScope.tempObject = response.data;
            }, function (response) {
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
        return $http.put(basicUrl + entityUrl + entityObject.id, entityObjectJSON)
            .then(function () {
                contScope.valid = true;
                $.notify(successM, "success");
            }, function (response) {
                contScope.valid = false;
                if (response.status == 404) { //NOT_FOUND
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

        return $http.delete(basicUrl + entityUrl + entityObject.id)
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


//MAIN PAGE CONTROLER ---------------------------------------------------------------------------------

mainApp.controller("mainCtrl", function ($scope, rest_s, accountUrl) {

    //TABLE variables
    $scope.rowCollection = [];

    //REST calls

    $scope.getAllAccount = function () {
        var getAllPromise = rest_s.getAllRecord($scope, accountUrl,
            "Žádný účet nebyl nalezen",
            "Nastala chyba při načítání dat");

        getAllPromise.then(function () {
            $scope.rowCollection = $scope.tempObject;
        });

    };

});




//ACCOUNT FAVOURITE CONTROLER ------------------------------------------------------------------------------------

mainApp.controller("accountFavouriteCtrl", function ($scope, $routeParams, $location, rest_s,
    accountFavouriteUrl, bankUrl) {

    //ENTITY variables
    $scope.AccountFavouriteObj = { id: "", client: {}, paymentInfo: {}, accountFavouriteName: "" };

    $scope.PaymentInfoObj = {
        id: "", bank: {}, accountPrefix: "", accountNumber: "", amount: "",
        variableSymbol: "", constantSymbol: "", specificSymbol: "",
        messageToRecipient: ""
    };

    //TABLE variables
    $scope.rowCollection = [];

    //Other variables
    $scope.bankCollection = [];
    $scope.bankIndex = "";
    $scope.afDetail = {};
    $scope.afDetailCompleteFlag = "0";

    $scope.detail_id = $routeParams.id;

    //Routing
    $scope.redirToList = function () {
        $scope.go("/accountfav");
    };

    //REST calls

    $scope.getAllBank = function (updateFlag) {
        var getAllPromise = rest_s.getAllRecord($scope, bankUrl,
            "Žádná banka nebyla nalezena",
            "Nastala chyba při načítání dat");

        getAllPromise.then(function () {
            $scope.bankCollection = $scope.tempObject;

            if (updateFlag === "1") {
                $scope.bankIndex = $scope.bankCollection.map(function (e) { return e.id; }).indexOf($scope.afDetail.paymentInfo.bank.id);
                $scope.selectedBank = $scope.bankCollection[$scope.bankIndex];
            }
        });

    };


    $scope.getAllAccountFavourite = function () {
        var getAllPromise = rest_s.getAllRecord($scope, accountFavouriteUrl,
            "Žádný oblíbený účet nebyl nalezen",
            "Nastala chyba při načítání dat");

        getAllPromise.then(function () {
            $scope.rowCollection = $scope.tempObject;
        });

    };

    $scope.createAccountFavourite = function () {

        //Bank
        //PaymentInfo
        $scope.PaymentInfoObj.bank = $scope.selectedBank;
        $scope.AccountFavouriteObj.paymentInfo = $scope.PaymentInfoObj;


        var createPromise = rest_s.createRecord($scope, $scope.AccountFavouriteObj, accountFavouriteUrl,
            "Oblíbený účet vložen",
            "Neuloženo - účet s tímto číslem neexistuje",
            "Oblíbený účet nebyl vložen, nastala chyba");

        createPromise.then(function () {
            $scope.redirToList();
        });

    };

    $scope.removeAccountFavourite = function (accountfavourite) {
        //if (confirm("Opravdu chcete smazat tento oblíbený účet?")) {
        var delPromise = rest_s.removeRecord($scope, accountfavourite, accountFavouriteUrl,
            "Oblíbený účet smazán",
            "Oblíbený účet nebyl nalezen",
            "Oblíbený účet nelze smazat kvůli integritě dat",
            "Oblíbený účet nebyl smazán, nastala chyba");
        //}

        delPromise.then(function () {
            $scope.redirToList();
        });

    };

    $scope.getAccountFavourite = function () {
        var createPromise = rest_s.getRecord($scope, accountFavouriteUrl, $scope.detail_id,
            "Oblíbený účet nebyl nalezen",
            "Oblíbený účet nebyl nalezen, nastala chyba");

        createPromise.then(function () {
            $scope.afDetail = $scope.tempObject;
            $scope.afDetailCompleteFlag = "1";
        });

    };

    $scope.updateInit = function () {
        $scope.getAccountFavourite();
        getBankAfterGetAccFav();

        function getBankAfterGetAccFav() {
            if ($scope.afDetailCompleteFlag === "0") {
                setTimeout(getBankAfterGetAccFav, 50);
                return;
            }
            $scope.getAllBank("1");
        }
    };

    $scope.updateAccountFavourite = function () {

        $scope.afDetail.paymentInfo.bank = $scope.selectedBank;

        var updatePromise = rest_s.updateRecord($scope, $scope.afDetail, accountFavouriteUrl,
            "Oblíbený účet editován",
            "Oblíbený účet nebyl nalezen",
            "Takový oblíbený účet už existuje, záznam nelze editovat",
            "Oblíbený účet nebyl editován, nastala chyba");

        updatePromise.then(function () {
            $scope.redirToList();
        });

    };

});




//PAYMENT SCHEDULED CONTROLER ------------------------------------------------------------------------------------

mainApp.controller("paymentScheduledCtrl", function ($scope, $routeParams, $location, $filter, rest_s, paymentScheduledUrl,
    bankUrl, accountUrl) {

    //ENTITY variables
    $scope.PaymentScheduledObj = { id: "", account: {}, paymentInfo: {}, periode: "", paymentDate: "", endDate: "" };

    $scope.PaymentInfoObj = {
        id: "", bank: {}, accountPrefix: "", accountNumber: "", amount: "",
        variableSymbol: "", constantSymbol: "", specificSymbol: "",
        messageToRecipient: ""
    };

    //TABLE variables
    $scope.rowCollection = [];

    //Other variables
    $scope.bankCollection = [];
    $scope.accountCollection = [];
    $scope.psDetail = {};
    $scope.bankIndex = "";
    $scope.accountIndex = "";

    //Today
    $scope.today = new Date().toISOString().split("T")[0];

    $scope.psDetailCompleteFlag = "0";

    $scope.detail_id = $routeParams.id;

    //Detail view variables
    $scope.periode = "";

    //Routing
    $scope.redirToList = function () {
        $scope.go("/paymentsched");
    };

    //REST calls

    $scope.getAllBank = function (updateFlag) {
        var getAllPromise = rest_s.getAllRecord($scope, bankUrl,
            "Žádná banka nebyla nalezena",
            "Nastala chyba při načítání dat");

        getAllPromise.then(function () {
            $scope.bankCollection = $scope.tempObject;
            if (updateFlag === "1") {
                $scope.bankIndex = $scope.bankCollection.map(function (e) { return e.id; }).indexOf($scope.psDetail.paymentInfo.bank.id);
                $scope.selectedBank = $scope.bankCollection[$scope.bankIndex];
            }
        });

    };

    $scope.getAllAccount = function (updateFlag) {
        var getAllPromise = rest_s.getAllRecord($scope, accountUrl,
            "Žádný účet nebyl nalezen",
            "Nastala chyba při načítání dat");

        getAllPromise.then(function () {
            $scope.accountCollection = $scope.tempObject;
            if (updateFlag === "1") {
                $scope.accountIndex = $scope.accountCollection.map(function (e) { return e.id; }).indexOf($scope.psDetail.account.id);
                $scope.selectedAccount = $scope.accountCollection[$scope.accountIndex];
            }
        });

    };



    $scope.getAllPaymentScheduled = function () {
        var getPromise = rest_s.getAllRecord($scope, paymentScheduledUrl,
            "Žádná pravidelná platba nebyla nalezena",
            "Nastala chyba při načítání dat");

        getPromise.then(function () {
            $scope.rowCollection = $scope.tempObject;

            if ($scope.rowCollection.length > 0) {
                $scope.rowCollection.forEach(function (psd) {
                    if (psd.periode === "MONTHLY") {
                        psd.periode = "Měsíční";
                    } else {
                        psd.periode = "Roční";
                    }
                });

            }

        });

    };

    $scope.createInit = function () {
        $scope.getAllBank("0");
        $scope.getAllAccount("0");
    };

    $scope.createPaymentScheduled = function () {

        //Bank + Account + Periode
        //PaymentInfo
        $scope.PaymentInfoObj.bank = $scope.selectedBank;
        $scope.PaymentScheduledObj.account = $scope.selectedAccount;
        $scope.PaymentScheduledObj.periode = $scope.selectedPeriode;
        $scope.PaymentScheduledObj.paymentInfo = $scope.PaymentInfoObj;

        if ($scope.PaymentScheduledObj.paymentDate <= $scope.PaymentScheduledObj.endDate) {

            var createPromise = rest_s.createRecord($scope, $scope.PaymentScheduledObj, paymentScheduledUrl,
                "Pravidelná platba vložena",
                "Neuloženo - účet s tímto číslem neexistuje",
                "Pravidelná platba nebyla vložena, nastala chyba");

            createPromise.then(function () {
                $scope.redirToList();
            });

        } else {
            $.notify("Datum ukončení je nižší než datum první platby", "warn");
        }

    };

    $scope.removePaymentScheduled = function (paymentscheduled) {
        //if (confirm("Opravdu chcete smazat tuto pravidelnou platbu?")) {
        var delPromise = rest_s.removeRecord($scope, paymentscheduled, paymentScheduledUrl,
            "Pravidelná platba smazána",
            "Pravidelná platba nebyla nalezena",
            "Pravidelná platba nelze smazat kvůli integritě dat",
            "Pravidelná platba nebyla smazána, nastala chyba");
        //}

        delPromise.then(function () {
            $scope.redirToList();
        });

    };

    $scope.getPaymentScheduled = function () {
        var createPromise = rest_s.getRecord($scope, paymentScheduledUrl, $scope.detail_id,
            "Pravidelná platba nebyla nalezena",
            "Pravidelná platba nebyla nalezena, nastala chyba");

        createPromise.then(function () {
            $scope.psDetail = $scope.tempObject;
            if ($scope.psDetail.periode === "MONTHLY") {
                $scope.periode = "Měsíční";
            } else {
                $scope.periode = "Roční";
            }

            //Detail 
            $scope.paymentDate = $filter("date")($scope.psDetail.paymentDate, "dd.MM.yyyy");
            $scope.endDate = $filter("date")($scope.psDetail.endDate, "dd.MM.yyyy");

            //Update
            $scope.psDetail.paymentDate = new Date($filter("date")($scope.psDetail.paymentDate, "yyyy-MM-dd"));
            $scope.psDetail.endDate = new Date($filter("date")($scope.psDetail.endDate, "yyyy-MM-dd"));

            $scope.psDetailCompleteFlag = "1";

        });

    };

    $scope.updateInit = function () {
        $scope.getPaymentScheduled();
        getAccountAndBankAfterGetPS();

        function getAccountAndBankAfterGetPS() {
            if ($scope.psDetailCompleteFlag === "0") {
                setTimeout(getAccountAndBankAfterGetPS, 50);
                return;
            }
            $scope.getAllAccount("1");
            $scope.getAllBank("1");
        }

    };

    $scope.updatePaymentScheduled = function () {

        $scope.psDetail.paymentInfo.bank = $scope.selectedBank;
        $scope.psDetail.account = $scope.selectedAccount;

        if ($scope.psDetail.paymentDate <= $scope.psDetail.endDate) {

            var updatePromise = rest_s.updateRecord($scope, $scope.psDetail, paymentScheduledUrl,
                "Pravidelná platba editována",
                "Pravidelná platba nebyla nalezena",
                "Taková pravidelná platba už existuje, záznam nelze editovat",
                "Pravidelná platba nebyla editována, nastala chyba");

            updatePromise.then(function () {
                $scope.redirToList();
            });

        } else {
            $.notify("Datum ukončení je nižší než datum první platby", "warn");
        }

    };

});


//ACCOUNT CONTROLER ------------------------------------------------------------------------------------

mainApp.controller("accountCtrl", function ($scope, $routeParams, $location, $filter, rest_s, accountUrl,
    accountTypeUrl, currencyUrl) {

    //ENTITY variables
    $scope.AccountObj = {
        id: "", accountType: {}, currency: {}, accountName: "", balance: "0", accountPrefix: "", accountNumber: "",
        cards: [], payments: [], paymentsScheduled: [], clients: []
    };

    //TABLE variables
    $scope.rowCollection = [];

    //Other variables
    $scope.accountTypeCollection = [];
    $scope.currencyCollection = [];
    $scope.acDetail = {};

    $scope.detail_id = $routeParams.id;

    //Routing
    $scope.redirToList = function () {
        $scope.go("/account");
    };

    //REST calls

    $scope.getAllAccountType = function () {
        var getAllPromise = rest_s.getAllRecord($scope, accountTypeUrl,
            "Žádný typ účtu nebyl nalezen",
            "Nastala chyba při načítání dat");

        getAllPromise.then(function () {
            $scope.accountTypeCollection = $scope.tempObject;
        });

    };

    $scope.getAllCurrency = function () {
        var getAllPromise = rest_s.getAllRecord($scope, currencyUrl,
            "Žádná měna nebyla nalezena",
            "Nastala chyba při načítání dat");

        getAllPromise.then(function () {
            $scope.currencyCollection = $scope.tempObject;
        });

    };



    $scope.getAllAccount = function () {
        var getPromise = rest_s.getAllRecord($scope, accountUrl,
            "Žádný účet nebyl nalezen",
            "Nastala chyba při načítání dat");

        getPromise.then(function () {
            $scope.rowCollection = $scope.tempObject;
        });

    };

    $scope.createInit = function () {
        $scope.getAllAccountType();
        $scope.getAllCurrency();
    };

    $scope.createAccount = function () {

        //AccountType + Country
        $scope.AccountObj.accountType = $scope.selectedAccountType;
        $scope.AccountObj.currency = $scope.selectedCurrency;

        var createPromise = rest_s.createRecord($scope, $scope.AccountObj, accountUrl,
            "Účet vložen",
            "Nezaloženo - účet s tímto účtem už existuje",
            "Účet nebyl vložen, nastala chyba");

        createPromise.then(function () {
            $scope.redirToList();
        });

    };

    $scope.removeAccount = function (account) {
        //if (confirm("Opravdu chcete zrušit tento účet?")) {
        if (account.balance == "0") {
            var delPromise = rest_s.removeRecord($scope, account, accountUrl,
                "Účet zrušen",
                "Účet nebyl nalezen",
                "Účet nelze zrušit kvůli integritě dat",
                "Účet nebyl zrušit, nastala chyba");
            //}

            delPromise.then(function () {
                $scope.redirToList();
            });

        } else {
            $.notify("Nelze zrušit účet se zůstatkem", "warn");
        }

    };

    $scope.getAccount = function () {
        var createPromise = rest_s.getRecord($scope, accountUrl, $scope.detail_id,
            "Účet nebyl nalezen",
            "Účet nebyl nalezen, nastala chyba");

        createPromise.then(function () {
            $scope.acDetail = $scope.tempObject;
        });

    };

    $scope.updateInit = function () {
        $scope.getAccount();
    };

    $scope.updateAccount = function () {
        var updatePromise = rest_s.updateRecord($scope, $scope.acDetail, accountUrl,
            "Účet byl editován",
            "Účet nebyl nalezen",
            "Takový účet už existuje, záznam nelze editovat",
            "Účet nebyl editován, nastala chyba");

        updatePromise.then(function () {
            $scope.redirToList();
        });

    };

});

//PAYMENT CONTROLER ------------------------------------------------------------------------------------

mainApp.controller("paymentCtrl", function ($scope, $routeParams, $location, $filter, rest_s,
    paymentUrl, bankUrl, accountUrl, accountFavouriteUrl, accountPaymentUrl,
    accountPaymentFavUrl) {

    //ENTITY variables
    $scope.PaymentObj = { id: "", fromAccount: {}, toAccount: {}, paymentInfo: {}, paymentDate: "" };

    $scope.PaymentInfoObj = {
        id: "", bank: {}, accountPrefix: "", accountNumber: "", amount: "",
        variableSymbol: "", constantSymbol: "", specificSymbol: "",
        messageToRecipient: ""
    };

    //TABLE variables
    $scope.rowCollection = [];

    //Other variables
    $scope.bankCollection = [];
    $scope.accountCollection = [];
    $scope.pyDetail = {};

    $scope.bankIndex = "";
    $scope.afDetailCompleteFlag = "0";

    $scope.detail_id = $routeParams.id;

    //Routing
    $scope.redirToMain = function () {
        $scope.go("/");
    };

    //REST calls

    $scope.getAllBank = function (updateFlag) {
        var getAllPromise = rest_s.getAllRecord($scope, bankUrl,
            "Žádná banka nebyla nalezena",
            "Nastala chyba při načítání dat");

        getAllPromise.then(function () {
            $scope.bankCollection = $scope.tempObject;
            if (updateFlag === "1") {
                $scope.bankIndex = $scope.bankCollection.map(function (e) { return e.id; }).indexOf($scope.afDetail.paymentInfo.bank.id);
                $scope.selectedBank = $scope.bankCollection[$scope.bankIndex];
            }
        });

    };

    $scope.getAllAccount = function () {
        var getAllPromise = rest_s.getAllRecord($scope, accountUrl,
            "Žádný účet nebyl nalezen",
            "Nastala chyba při načítání dat");

        getAllPromise.then(function () {
            $scope.accountCollection = $scope.tempObject;
        });

    };


    $scope.getAllAccountPayment = function () {
        var createPromise = rest_s.getRecord($scope, accountPaymentUrl, $scope.detail_id,
            "Žádná platba nebyla nalezena",
            "Žádné platby nebyly nalezeny, nastala chyba");

        createPromise.then(function () {
            $scope.rowCollection = $scope.tempObject;

            if ($scope.rowCollection.length > 0) {
                $scope.rowCollection.forEach(function (psd) {

                    psd.paymentDate = $filter("date")(psd.paymentDate, "dd.MM.yyyy");

                    //Protiúčet je zobrazen ve sloupci fromAccount
                    if (psd.fromAccount !== $scope.detail_id) {
                        psd.fromAccount = psd.toAccount;
                    }

                });

            }

        });

    };

    $scope.getSign = function (toAccountId) {
        if (toAccountId == $scope.detail_id) {
            return "+";
        } else { return "-"; }
    };

    $scope.getAllAccountPaymentFav = function () {
        var createPromise = rest_s.getRecord($scope, accountPaymentFavUrl, $scope.detail_id,
            "Žádná platba nebyla nalezena",
            "Žádné platby nebyly nalezeny, nastala chyba");

        createPromise.then(function () {
            $scope.rowCollection = $scope.tempObject;

            if ($scope.rowCollection.length > 0) {
                $scope.rowCollection.forEach(function (psd) {

                    psd.paymentDate = $filter("date")(psd.paymentDate, "dd.MM.yyyy");

                });

            }

        });

    };

    $scope.createInit = function () {
        $scope.getAllBank("0");
        $scope.getAllAccount("0");
    };

    $scope.createPayment = function () {

        //Bank
        $scope.PaymentInfoObj.bank = $scope.selectedBank;
        $scope.PaymentObj.fromAccount = $scope.selectedAccount;
        $scope.PaymentObj.paymentInfo = $scope.PaymentInfoObj;

        if ($scope.PaymentObj.paymentInfo.amount > "0") {

            if ($scope.PaymentObj.fromAccount.accountNumber !== $scope.PaymentObj.paymentInfo.accountNumber) {

                if ($scope.PaymentObj.fromAccount.balance >= $scope.PaymentObj.paymentInfo.amount) {

                    var createPromise = rest_s.createRecord($scope, $scope.PaymentObj, paymentUrl,
                        "Platba odeslána",
                        "Účet na který odesíláte neexistuje",
                        "Platba nebyla odeslána, nastala chyba");

                    createPromise.then(function () {
                        $scope.redirToMain();
                    });

                } else {
                    $.notify("Na zvoleném účtu není pro platbu dostatek prostředků", "warn");
                }

            } else {
                $.notify("Nelze poslat částku na ten samý účet", "warn");
            }

        } else {
            $.notify("Nelze poslat nulovou částku", "warn");
        }
    };

    $scope.getPayment = function () {
        var getPromise = rest_s.getRecord($scope, paymentUrl, $scope.detail_id,
            "Platba nebyla nalezena",
            "Platba nebyla nalezena, nastala chyba");

        getPromise.then(function () {
            $scope.pyDetail = $scope.tempObject;
        });

    };

    //Payment to Account Favourite------------------------------------------------------

    $scope.afDetail = {};

    $scope.paymentToAFInit = function () {
        $scope.getAccountFavourite();
        $scope.getAllAccount();

        getBankAfterGetAccFav();

        function getBankAfterGetAccFav() {
            if ($scope.afDetailCompleteFlag === "0") {
                setTimeout(getBankAfterGetAccFav, 50);
                return;
            }
            $scope.getAllBank("1");
        }


    };

    $scope.getAccountFavourite = function () {
        var createPromise = rest_s.getRecord($scope, accountFavouriteUrl, $scope.detail_id,
            "Oblíbený účet nebyl nalezen",
            "Oblíbený účet nebyl nalezen, nastala chyba");

        createPromise.then(function () {
            $scope.afDetail = $scope.tempObject;
            $scope.PaymentInfoObj.amount = $scope.afDetail.paymentInfo.amount;
            $scope.PaymentInfoObj.accountPrefix = $scope.afDetail.paymentInfo.accountPrefix;
            $scope.PaymentInfoObj.accountNumber = $scope.afDetail.paymentInfo.accountNumber;
            $scope.selectedBank = $scope.afDetail.paymentInfo.bank;
            $scope.PaymentInfoObj.variableSymbol = $scope.afDetail.paymentInfo.variableSymbol;
            $scope.PaymentInfoObj.constantSymbol = $scope.afDetail.paymentInfo.constantSymbol;
            $scope.PaymentInfoObj.specificSymbol = $scope.afDetail.paymentInfo.specificSymbol;
            $scope.PaymentInfoObj.messageToRecipient = $scope.afDetail.paymentInfo.messageToRecipient;

            $scope.afDetailCompleteFlag = "1";
        });

    };


});


//CARD CONTROLER ------------------------------------------------------------------------------------

mainApp.controller("cardCtrl", function ($scope, $routeParams, $location, $filter, rest_s, cardUrl,
    accountUrl, cardChangeStateUrl) {

    //ENTITY variables
    $scope.CardObj = {
        id: "", account: {}, cardNumber: "", securityCode: "", validityTo: "",
        activeFlag: "", dayWithdrawLimit: "", dayPayLimit: ""
    };

    //TABLE variables
    $scope.rowCollection = [];

    //Other variables
    $scope.accountCollection = [];
    $scope.caDetail = {};

    $scope.detail_id = $routeParams.id;

    //Detail view variables
    $scope.activeFlag = "";
    $scope.validityTo = "";
    $scope.activeInfo = "";

    //Routing
    $scope.redirToList = function () {
        $scope.go("/card");
    };

    //REST calls

    $scope.getAllAccount = function () {
        var getPromise = rest_s.getAllRecord($scope, accountUrl,
            "Žádný účet nebyl nalezen",
            "Nastala chyba při načítání dat");

        getPromise.then(function () {
            $scope.accountCollection = $scope.tempObject;
        });

    };



    $scope.getAllCard = function () {
        var getPromise = rest_s.getAllRecord($scope, cardUrl,
            "Žádná karta nebyla nalezena",
            "Nastala chyba při načítání dat");

        getPromise.then(function () {
            $scope.rowCollection = $scope.tempObject;

            if ($scope.rowCollection.length > 0) {
                $scope.rowCollection.forEach(function (card) {
                    if (card.activeFlag == "1") {
                        card.activeFlag = "Aktivní";
                    } else {
                        card.activeFlag = "Zablokovaná";
                    }

                    card.validityTo = $filter("date")(card.validityTo, "dd.MM.yyyy");

                });



            }

        });

    };

    $scope.createInit = function () {
        $scope.getAllAccount();
    };

    $scope.createCard = function () {

        //Account
        $scope.CardObj.id = "";
        $scope.CardObj.account = $scope.selectedAccount;

        var createPromise = rest_s.createRecord($scope, $scope.CardObj, cardUrl,
            "Karta objednána",
            "Karta s tímto číslem už existuje, nelze objednat",
            "Karta nebyla objednána, nastala chyba");

        createPromise.then(function () {
            $scope.redirToList();
        });

    };

    $scope.getCard = function () {
        var createPromise = rest_s.getRecord($scope, cardUrl, $scope.detail_id,
            "Karta nebyla nalezena",
            "Karta nebyla nalezena, nastala chyba");

        createPromise.then(function () {
            $scope.caDetail = $scope.tempObject;

            $scope.validityTo = $filter("date")($scope.caDetail.validityTo, "dd.MM.yyyy");
            if ($scope.caDetail.activeFlag === 1) {
                $scope.activeFlag = "Aktivní";
                $scope.activeInfo = "Zablokovat";
            } else {
                $scope.activeFlag = "Zablokovaná";
                $scope.activeInfo = "Odblokovat";
            }


        });



    };

    $scope.updateInit = function () {
        $scope.getCard();
    };

    $scope.updateCard = function () {
        var updatePromise = rest_s.updateRecord($scope, $scope.caDetail, cardUrl,
            "Limity na kartě editovány",
            "Karta nebyla nalezena",
            "Taková karta už existuje, záznam nelze editovat",
            "Karta nebyla editována, nastala chyba");

        updatePromise.then(function () {
            $scope.redirToList();
        });

    };

    $scope.changeStateOfCard = function () {
        var updatePromise = rest_s.updateRecord($scope, $scope.caDetail, cardChangeStateUrl,
            "Stav karty změnen",
            "Karta nebyla nalezena",
            "Taková karta už existuje, záznam nelze editovat",
            "Stav karty nebyl změněn, nastala chyba");

        updatePromise.then(function () {
            $scope.redirToList();
        });

    };

});


//CLIENT CONTROLER ------------------------------------------------------------------------------------

mainApp.controller("clientCtrl", function ($scope, $location, $filter, $http, rest_s, clientUrl) {

    //Variables
    $scope.countryCollection = [];
    $scope.clDetail = {};

    //Routing
    $scope.redirToDetail = function () {
        $scope.go("/clientdetail/");
    };

    //REST calls

    $scope.getClient = function () {
        var getPromise = rest_s.getRecord($scope, clientUrl, "0", //Phantom value
            "Uživatelské údaje nebyly nalezeny",
            "Uživateslké údaje nebyly nalezeny, nastala chyba");

        getPromise.then(function () {
            $scope.clDetail = $scope.tempObject;
        });

    };

    $scope.updateInit = function () {
        $scope.getClient();
    };

    $scope.updateClient = function () {
        var updatePromise = rest_s.updateRecord($scope, $scope.clDetail, clientUrl,
            "Uživatelské údaje editovány",
            "Uživatelské údaje nebyly nalezeny",
            "Takový detail už existuje, záznam nelze editovat",
            "Uživatelské údaje nebyly editovány, nastala chyba");

        updatePromise.then(function () {
            $scope.redirToDetail();
        });

    };

});
