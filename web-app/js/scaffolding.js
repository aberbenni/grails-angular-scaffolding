/**
 * A service for storing one-time messages to be displayed after redirecting to
 * another view.
 */
angular.module('flashService', []).factory('Flash', function() {
    var flash = {};

    flash.getMessage = function() {
        var value = this.message;
        this.message = undefined;
        return value;
    };

    flash.error = function(text) {
        this.message = {level: 'error', text: text};
    };
    flash.success = function(text) {
        this.message = {level: 'success', text: text};
    };
    flash.info = function(text) {
        this.message = {level: 'info', text: text};
    };

    return flash;
});

/**
 * The main scaffolding module.
 */
var scaffoldingModule = angular.module('scaffolding');

/**
 * Route definitions connecting URL fragments to views and controllers.
 */
scaffoldingModule.config([
    '$routeProvider', 'templateUrl',
    function($routeProvider, templateUrl) {
        $routeProvider.
            when('/create', { templateUrl: templateUrl + '/create.gsp', controller: "CreateCtrl" }).
            when('/edit/:id', { templateUrl: templateUrl + '/edit.gsp', controller: "EditCtrl" }).
            when('/list', { templateUrl: templateUrl + '/list.gsp', controller: "ListCtrl" }).
            when('/show/:id', { templateUrl: templateUrl + '/show.gsp', controller: "ShowCtrl" }).
            otherwise({redirectTo: '/list'});
    }
]);

/**
 * A directive for including an alert message in the page.
 */
scaffoldingModule.directive('gasAlert', function(commonTemplateUrl) {
	return {
        restrict: 'E', // can only be used as an element
        transclude: false, // the element should not contain any content so there's no need to transclude
        scope: {
			level: '@level',
			text: '@text'
		},
        templateUrl: commonTemplateUrl + '/alert.html',
        replace: true
    }
});

/**
 * A directive for including a standard pagination block in the page.
 */
scaffoldingModule.directive('gasPagination', function(commonTemplateUrl) {
	return {
        restrict: 'A', // can only be used as an attribute
        transclude: false, // the element should not contain any content so there's no need to transclude
        scope: {
            total: '=total' // inherit the total property from the controller scope
        },
        controller: function($scope, $routeParams, $location) {
            $scope.max = parseInt($routeParams.max) || 10;
            $scope.offset = parseInt($routeParams.offset) || 0;
            $scope.currentPage = Math.ceil($scope.offset / $scope.max);

            $scope.pages = function() {
                var pages = [];
                for (var i = 0; i < Math.ceil($scope.total / $scope.max); i++)
                    pages.push(i);
                return pages;
            };

            $scope.lastPage = function() {
                return $scope.pages().slice(-1)[0];
            };

			$scope.previousPage = function() {
				var offset = $scope.offset - $scope.max;
				if (offset < 0) {
					offset = 0;
				}
				$location.search('offset', offset);
			};

			$scope.nextPage = function() {
				$location.search('offset', $scope.offset + $scope.max);
			};

			$scope.goToPage = function(n) {
				$location.search('offset', n * $scope.max);
			};
        },
        templateUrl: commonTemplateUrl + '/pagination.html',
        replace: false
    }
});

/**
 * A directive for making a table header sortable.
 */
scaffoldingModule.directive('gasSortable', function(commonTemplateUrl) {
	return {
		restrict: 'A',
		transclude: true,
		scope: {
			//property: '@sortable'
			property: '@gasSortable'
		},
		controller: function($scope, $routeParams, $location) {
			$scope.isSortedBy = function() {
				return $routeParams.sort === $scope.property;
			};

			$scope.isSortedByAscending = function() {
				return $scope.isSortedBy() && $routeParams.order !== 'desc';
			};

			$scope.isSortedByDescending = function() {
				return $scope.isSortedBy() && $routeParams.order === 'desc';
			};

			$scope.sort = function() {
				var order;
				if ($scope.isSortedByAscending()) {
					order = 'desc';
				} else {
					order = 'asc';
				}
				$location.search('sort', $scope.property).search('order', order);
			};
		},
		link: function(scope, element) {
			element.bind('click', function() {
				scope.$apply(scope.sort);
			});
		},
		templateUrl: commonTemplateUrl + '/sortable.html',
		replace: false
	}
});

function toArray(element) {
    return Array.prototype.slice.call(element);
}

Function.prototype.curry = function() {
    if (arguments.length < 1) {
        return this; //nothing to curry with - return function
    }
    var __method = this;
    var args = toArray(arguments);
    return function() {
        return __method.apply(this, args.concat(toArray(arguments)));
    }
}

/**
 * Generic $resource error handler used by all controllers.
 */
function errorHandler($scope, $location, Flash, response) {
    switch (response.status) {
        case 404: // resource not found - return to the list and display message returned by the controller
            Flash.error(response.data.message);
            $location.path('/list');
            break;
        case 409: // optimistic locking failure - display error message on the page
            $scope.message = {level: 'error', text: response.data.message};
            break;
        case 422: // validation error - display errors alongside form fields
            $scope.errors = response.data.errors;
            break;
        default: // TODO: general error handling
    }
}

scaffoldingModule.controller("ListCtrl", function($scope, $routeParams, $location, Grails, Flash) {
    Grails.list($routeParams, function(list, headers) {
        $scope.list = list;
        $scope.total = parseInt(headers('X-Pagination-Total'));
        $scope.message = Flash.getMessage();
    }, errorHandler.curry($scope, $location, Flash));

	$scope.show = function(item) {
		$location.path('/show/' + item.id);
	};
});

scaffoldingModule.controller("ShowCtrl", function($scope, $routeParams, $location, Grails, Flash) {
    $scope.message = Flash.getMessage();

    Grails.get({id: $routeParams.id}, function(item) {
        $scope.item = item;
    }, errorHandler.curry($scope, $location, Flash));

    $scope.delete = function(item) {
        item.$delete(function(response) {
            Flash.success(response.message);
            $location.path('/list');
        }, errorHandler.curry($scope, $location, Flash));
    };
});

scaffoldingModule.controller("CreateCtrl", function($scope, $location, Grails, Flash) {
    $scope.item = new Grails;

    $scope.save = function(item) {
        item.$save(function(response) {
            Flash.success(response.message);
            $location.path('/show/' + response.id);
        }, errorHandler.curry($scope, $location, Flash));
    };
});

scaffoldingModule.controller("EditCtrl", function($scope, $routeParams, $location, Grails, Flash) {
    Grails.get({id: $routeParams.id}, function(item) {
        $scope.item = item;
    }, errorHandler.curry($scope, $location, Flash));

    $scope.update = function(item) {
        item.$update(function(response) {
            Flash.success(response.message);
            $location.path('/show/' + response.id);
        }, errorHandler.curry($scope, $location, Flash));
    };

    $scope.delete = function(item) {
        item.$delete(function(response) {
            Flash.success(response.message);
            $location.path('/list');
        }, errorHandler.curry($scope, $location, Flash));
    };
});
