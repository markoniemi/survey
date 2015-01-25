app.controller('PollsCtrl', function($scope, $location, $timeout, $log, PollService, config
		) {
	$log.debug('PollsCtrl');
	$scope.projectVersion = config.projectVersion;
	// get polls from service
	$scope.polls = PollService.query();
	// called when poll presses edit button
	$scope.editPoll = function editPoll(poll) {
		$location.path('/polls/poll/' + poll.name);
	};
	// called when poll presses delete button
	$scope.deletePoll = function deletePoll(poll) {
		PollService.remove(poll);
		// delay is needed since service is yet deleting the poll
		$timeout(function() {
			$scope.polls = PollService.query();
		}, 1000);
	};
});
