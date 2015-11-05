'use strict';

angular.module('booksterApp')
    .controller('LendingController', function ($scope, Lending, ParseLinks) {
        $scope.lendings = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            Lending.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                for (var i = 0; i < result.length; i++) {
                    $scope.lendings.push(result[i]);
                }
            });
        };
        $scope.reset = function() {
            $scope.page = 0;
            $scope.lendings = [];
            $scope.loadAll();
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            Lending.get({id: id}, function(result) {
                $scope.lending = result;
                $('#deleteLendingConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Lending.delete({id: id},
                function () {
                    $scope.reset();
                    $('#deleteLendingConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.reset();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.lending = {
                borrower:null,
                copi:null,
                from: null,
                due: null,
                id: null
            };
        };
    });
