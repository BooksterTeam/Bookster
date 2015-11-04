'use strict';

angular.module('booksterApp')
    .controller('CopyController', function ($scope, Copy, ParseLinks) {
        $scope.copys = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            Copy.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                for (var i = 0; i < result.length; i++) {
                    $scope.copys.push(result[i]);
                }
            });
        };
        $scope.reset = function() {
            $scope.page = 0;
            $scope.copys = [];
            $scope.loadAll();
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            Copy.get({id: id}, function(result) {
                $scope.copy = result;
                $('#deleteCopyConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Copy.delete({id: id},
                function () {
                    $scope.reset();
                    $('#deleteCopyConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.reset();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.copy = {
                verified: null,
                available: null,
                id: null
            };
        };
    });
