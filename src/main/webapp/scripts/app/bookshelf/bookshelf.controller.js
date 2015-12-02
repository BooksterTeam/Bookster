'use strict';

angular.module('booksterApp')
    .controller('BookshelfController', function ($scope, Bookshelf, ParseLinks) {
        $scope.copys = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            Bookshelf.copies.query({page: $scope.page, size: 20}, function(result, headers) {
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

    });