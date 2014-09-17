/**
 * This module defines the resource mappings required by Angular JS to map to a
 * Grails CRUD URL scheme that uses `"/$controller/$id?"(resource: controller)`.
 */
angular.module('grailsService', ['ngResource']).factory('Grails', function($resource) {
    
    return $resource(rootUrl + ':controller/:id', {id: '@id', controller: controller}, {
        list: {method: 'GET', isArray: true},
        get: {method: 'GET'},
        save: {method: 'PUT'},
        update: {method: 'POST'},
        delete: {method: 'DELETE'}
    });
});
