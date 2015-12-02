/* globals $ */
'use strict';

angular.module('booksterApp')
    .directive('booksterAppPager', function() {
        return {
            templateUrl: 'scripts/components/form/pager.html'
        };
    });
