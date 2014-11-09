//'use strict';
app.config([ '$routeProvider', function($routeProvider) {
	$routeProvider.when('/users', {
		templateUrl : 'app/users/users.xhtml',
		controller : 'UserListController',
		title : 'Users'
	});
	$routeProvider.when('/user', {
		templateUrl : 'app/users/user.xhtml',
		controller : 'UserController',
		title : 'Create user'
	});
	$routeProvider.when('/user/:username', {
		templateUrl : 'app/users/user.xhtml',
		controller : 'UserController',
		title : 'Edit user'
	});
	$routeProvider.when('/about', {
		templateUrl : 'app/about/about.xhtml',
		controller : 'AboutController',
		title : 'About Survey'
	});
	$routeProvider.otherwise({
		redirectTo : '/users'
	});
} ]);
