'use strict';

angular.module('booksterApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('lending', {
                parent: 'entity',
                url: '/lendings',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'booksterApp.lending.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/lending/lendings.html',
                        controller: 'LendingController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('lending');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('lending.detail', {
                parent: 'entity',
                url: '/lending/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'booksterApp.lending.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/lending/lending-detail.html',
                        controller: 'LendingDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('lending');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Lending', function($stateParams, Lending) {
                        return Lending.get({id : $stateParams.id});
                    }]
                }
            })
            .state('lending.new', {
                parent: 'lending',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/lending/lending-dialog.html',
                        controller: 'LendingDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    from: null,
                                    due: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('lending', null, { reload: true });
                    }, function() {
                        $state.go('lending');
                    })
                }]
            })
            .state('lending.edit', {
                parent: 'lending',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/lending/lending-dialog.html',
                        controller: 'LendingDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Lending', function(Lending) {
                                return Lending.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('lending', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
