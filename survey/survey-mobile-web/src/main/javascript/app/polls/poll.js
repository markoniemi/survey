app.controller('PollCtrl', function($scope, $location, $translate, PollService,
		$routeParams, $log) {
	// if editing, get poll from service
	var editing = $routeParams.id != null;
	$log.debug("editing poll: " + $routeParams.id);
	if (editing) {
		$scope.poll = PollService.get({
			name : $routeParams.id
		});
	}
	// values for role select
	$scope.roles = [{name: $translate.instant("ROLE_USER"), value: 'ROLE_USER'},
	                {name: $translate.instant("ROLE_ADMIN"), value: 'ROLE_ADMIN'}];
	// success callback
	function success() {
		$log.log('success');
		$scope.polls = PollService.query();
		$location.path('/polls/polls');
	}
	// failure callback
	function failure(httpResponse) {
		$scope.error = $translate.instant('ADD_USER_ERROR');
	};
	// called when user presses submit button
	$scope.savePoll = function savePoll(poll) {
		if (editing) {
			PollService.update(poll, success, failure);
		} else {
			PollService.save(poll, success, failure);
		}
	};
	// called when user presses cancel
	$scope.cancel = function() {
		$location.path('/polls/polls');
	};
});
