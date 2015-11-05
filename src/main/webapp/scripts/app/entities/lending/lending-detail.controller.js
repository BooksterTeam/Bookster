'use strict';

angular.module('booksterApp')
    .controller('LendingDetailController', function ($scope, $rootScope, $stateParams, entity, Lending) {
        $scope.lending = entity;
        $scope.load = function (id) {
            Lending.get({id: id}, function(result) {
                $scope.lending = result;
            });
        };
        var unsubscribe = $rootScope.$on('booksterApp:lendingUpdate', function(event, result) {
            $scope.lending = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
