angular.module('booksterApp')
    .controller('MarketController', function ($scope, Market, ParseLinks) {
        $scope.books = [];
        $scope.query = '';
        $scope.loadAll = function() {
            Market.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.books = result;
            });
        };

        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };

        $scope.loadAll();
    })
    .filter;
