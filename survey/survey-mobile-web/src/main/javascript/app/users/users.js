projectVersion = "${project.version}";
httpPort = "${http.port}";
app.controller('UserListController', function($scope, $location, UserService,
		$timeout) {
	$scope.projectVersion = projectVersion;
	$scope.httpPort = httpPort;
	// get users from service
	$scope.users = UserService.query();
	// called when user presses edit button
	$scope.editUser = function editUser(user) {
		$location.path('/user/' + user.username);
	};
	// called when user presses delete button
	$scope.deleteUser = function deleteUser(user) {
		UserService.remove(user);
		// delay is needed since service is yet deleting the user
		$timeout(function() {
			$scope.users = UserService.query();
		}, 1000);
	};
});
