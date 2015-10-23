/* globals $ */
'use strict';

angular.module('booksterApp')
    .directive('booksterAppPagination', function() {
        return {
            templateUrl: 'scripts/components/form/pagination.html'
        };
    });
