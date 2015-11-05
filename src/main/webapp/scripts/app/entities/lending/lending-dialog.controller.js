'use strict';

angular.module('booksterApp').controller('LendingDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'Lending',
        function($scope, $stateParams, $modalInstance, entity, Lending) {

        $scope.lending = entity;
        $scope.load = function(id) {
            Lending.get({id : id}, function(result) {
                $scope.lending = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('booksterApp:lendingUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.lending.id != null) {
                Lending.update($scope.lending, onSaveFinished);
            } else {
                Lending.save($scope.lending, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
