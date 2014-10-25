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
