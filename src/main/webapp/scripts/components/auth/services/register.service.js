'use strict';

angular.module('booksterApp')
    .factory('Register', function ($resource) {
        return $resource('api/register', {}, {
        });
    });


