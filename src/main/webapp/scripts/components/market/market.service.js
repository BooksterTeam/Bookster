'use strict';

angular.module('booksterApp')
    .factory('Market', function ($resource, DateUtils) {
        return $resource('api/books/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.published = DateUtils.convertLocaleDateFromServer(data.published);
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    data.published = DateUtils.convertLocaleDateToServer(data.published);
                    return angular.toJson(data);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    data.published = DateUtils.convertLocaleDateToServer(data.published);
                    return angular.toJson(data);
                }
            }
        });
    });
