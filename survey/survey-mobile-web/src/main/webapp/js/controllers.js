//'use strict';
app.config([ '$routeProvider', function($routeProvider) {
	$routeProvider.when('/users', {
		templateUrl : '../partials/users.xhtml',
		controller : 'UserListController',
		title : 'Users'
	});
	$routeProvider.when('/user', {
		templateUrl : '../partials/user.xhtml',
		controller : 'UserController',
		title : 'Create user'
	});
	$routeProvider.when('/user/:username', {
		templateUrl : '../partials/user.xhtml',
		controller : 'UserController',
		title : 'Edit user'
	});
	$routeProvider.otherwise({
		redirectTo : '/users'
	});
} ]);
app.controller('UserListController', function($scope, $location, UserService,
		$timeout) {
	$scope.users = UserService.query();
	$scope.currentUser = null;
	$scope.setCurrentUser = function setCurrentUser(user) {
		$scope.currentUser = user;
	};
	$scope.editUser = function editUser(user) {
		$location.path('/user/' + user.username);
	};
	$scope.deleteUser = function deleteUser(user) {
		UserService.remove(user);
		$timeout(function() {
			$scope.users = UserService.query();
		}, 1000);
	};
});
app.controller('UserController', function($scope, $location, UserService,
		FileService, $routeParams, $log) {
	var editing = $routeParams.username != null;
	if (editing) {
		$scope.user = UserService.get({
			username : $routeParams.username
		});
	}
	function success() {
		$log.log('success');
		$scope.users = UserService.query();
		$location.path('/users');
	}
	function failure(httpResponse) {
		$scope.error = "Unable to save user.";
	}
	;
	$scope.saveUser = function saveUser(user) {
		if (editing) {
			UserService.update(user, success, failure);
		} else {
			UserService.save(user, success, failure);
		}
	};
	$scope.cancel = function() {
		$location.path('/users');
	};
});
