'use strict';

angular.module('booksterApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('copy', {
                parent: 'entity',
                url: '/copys',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'booksterApp.copy.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/copy/copys.html',
                        controller: 'CopyController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('copy');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('copy.detail', {
                parent: 'entity',
                url: '/copy/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'booksterApp.copy.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/copy/copy-detail.html',
                        controller: 'CopyDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('copy');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Copy', function($stateParams, Copy) {
                        return Copy.get({id : $stateParams.id});
                    }]
                }
            })
            .state('copy.new', {
                parent: 'copy',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/copy/copy-dialog.html',
                        controller: 'CopyDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    verified: null,
                                    available: null,
                                    book: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('copy', null, { reload: true });
                    }, function() {
                        $state.go('copy');
                    })
                }]
            })
            .state('copy.edit', {
                parent: 'copy',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/copy/copy-dialog.html',
                        controller: 'CopyDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Copy', function(Copy) {
                                return Copy.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('copy', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
