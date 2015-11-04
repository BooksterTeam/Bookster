'use strict';

angular.module('booksterApp')
    .controller('AuthorController', function ($scope, Author, ParseLinks) {
        $scope.authors = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            Author.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                for (var i = 0; i < result.length; i++) {
                    $scope.authors.push(result[i]);
                }
            });
        };
        $scope.reset = function() {
            $scope.page = 0;
            $scope.authors = [];
            $scope.loadAll();
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            Author.get({id: id}, function(result) {
                $scope.author = result;
                $('#deleteAuthorConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Author.delete({id: id},
                function () {
                    $scope.reset();
                    $('#deleteAuthorConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.reset();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.author = {
                forename: null,
                surname: null,
                id: null
            };
        };
    });
