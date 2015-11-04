'use strict';

angular.module('booksterApp')
    .controller('CopyDetailController', function ($scope, $rootScope, $stateParams, entity, Copy) {
        $scope.copy = entity;
        $scope.load = function (id) {
            Copy.get({id: id}, function(result) {
                $scope.copy = result;
            });
        };
        var unsubscribe = $rootScope.$on('booksterApp:copyUpdate', function(event, result) {
            $scope.copy = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
