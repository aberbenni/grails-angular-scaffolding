/**
 * This module defines the resource mappings required by Angular JS to map to a
 * standard Grails CRUD URL scheme that uses `"/$controller/$action?/$id?"`.
 */
angular.module('grailsService').factory('Grails', function($resource, baseUrl) {
	//var baseUrl = $('body').data('base-url');

	return $resource(baseUrl + ':action/:id', {id: '@id'}, {
		list: {method: 'GET', params: {action: 'list'}, isArray: true},
		get: {method: 'GET', params: {action: 'get'}},
		save: {method: 'POST', params: {action: 'save'}},
		update: {method: 'POST', params: {action: 'update'}},
		delete: {method: 'POST', params: {action: 'delete'}}
	});
});
