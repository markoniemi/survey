app.controller('UserCtrl', function($scope, $location, $translate, UserService,
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
	$scope.roles = UserService.getRoles();
	// success callback
	function success() {
		$log.log('success');
		$scope.users = UserService.query();
		$location.path('/users/users');
	}
	// failure callback
	function failure(httpResponse) {
		$scope.error = $translate.instant('ADD_USER_ERROR');
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
