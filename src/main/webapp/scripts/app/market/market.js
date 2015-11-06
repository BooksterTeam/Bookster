'use strict';

angular.module('booksterApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('market', {
                parent: 'site',
                url: '/',
                data: {
                    authorities: []
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/market/market.html',
                        controller: 'MarketController'
                    }
                },
                resolve: {
                    mainTranslatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate,$translatePartialLoader) {
                        $translatePartialLoader.addPart('market');
                        return $translate.refresh();
                    }]
                }
            });
    });
