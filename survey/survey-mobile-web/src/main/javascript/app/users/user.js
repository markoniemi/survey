app.controller('UserController', function($scope, $location, UserService,
		$routeParams, $log) {
	// if editing, get user from service
	var editing = $routeParams.id != null;
	$log.debug("editing user: " + $routeParams.id);
	if (editing) {
		$scope.user = UserService.get({
			username : $routeParams.id
		});
	}
	// values for role select
	$scope.roles = [{name: 'User', value: 'ROLE_USER'},
	                {name: 'Admin', value: 'ROLE_ADMIN'}];
	// success callback
	function success() {
		$log.log('success');
		$scope.users = UserService.query();
		$location.path('/users/users');
	}
	// failure callback
	function failure(httpResponse) {
		$scope.error = "Unable to save user.";
	};
	// called when user presses submit button
	$scope.saveUser = function saveUser(user) {
		if (editing) {
			UserService.update(user, success, failure);
		} else {
			UserService.save(user, success, failure);
		}
	};
	// called when user presses cancel
	$scope.cancel = function() {
		$location.path('/users/users');
	};
});
