'use strict';

angular.module('booksterApp')
    .controller('AuthorDetailController', function ($scope, $rootScope, $stateParams, entity, Author) {
        $scope.author = entity;
        $scope.load = function (id) {
            Author.get({id: id}, function(result) {
                $scope.author = result;
            });
        };
        var unsubscribe = $rootScope.$on('booksterApp:authorUpdate', function(event, result) {
            $scope.author = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
