app.controller('UserController', function($scope, $location, UserService,
		$routeParams, $log) {
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
