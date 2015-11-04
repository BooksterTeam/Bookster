'use strict';

angular.module('booksterApp')
    .controller('BookDetailController', function ($scope, $rootScope, $stateParams, entity, Book) {
        $scope.book = entity;
        $scope.load = function (id) {
            Book.get({id: id}, function(result) {
                $scope.book = result;
            });
        };
        var unsubscribe = $rootScope.$on('booksterApp:bookUpdate', function(event, result) {
            $scope.book = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
