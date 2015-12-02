'use strict';

angular.module('booksterApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('bookshelf', {
                parent: 'site',
                url: '/bookshelf',
                data: {
                    authorities: []
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/bookshelf/bookshelf.html',
                        controller: 'BookshelfController'
                    }
                },
                resolve: {
                    mainTranslatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate,$translatePartialLoader) {
                        $translatePartialLoader.addPart('main');
                        return $translate.refresh();
                    }]
                }
            })
            .state('bookshelf.copy',{
                parent: 'bookshelf',
                url: '/addcopy',
                data: {
                    authorities: []
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/bookshelf/bookshelf-copy-dialog.html',
                        controller: 'BookshelfCopyDialogController',
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
                        $state.go('bookshelf', null, { reload: true });
                    }, function() {
                        $state.go('bookshelf');
                    })
                }],
                resolve: {
                    mainTranslatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate,$translatePartialLoader) {
                        $translatePartialLoader.addPart('main');
                        return $translate.refresh();
                    }]
                }
            });
    });
