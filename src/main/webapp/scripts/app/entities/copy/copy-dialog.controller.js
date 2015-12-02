'use strict';

angular.module('booksterApp').controller('CopyDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'Copy',
        function($scope, $stateParams, $modalInstance, entity, Copy) {

        $scope.copy = entity;
        $scope.load = function(id) {
            Copy.get({id : id}, function(result) {
                $scope.copy = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('booksterApp:copyUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.copy.id != null) {
                Copy.update($scope.copy, onSaveFinished);
            } else {
                Copy.save($scope.copy, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
