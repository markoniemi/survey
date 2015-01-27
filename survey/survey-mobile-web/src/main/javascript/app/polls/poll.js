app.controller('PollCtrl', function($scope, $location, $translate, PollService,
		$routeParams, $log) {
	// if editing, get poll from service
	var editing = $routeParams.id != null;
	$log.debug("editing poll: " + $routeParams.id);
	if (editing) {
		$scope.poll = PollService.get({
			name : $routeParams.id
		});
	} else {
		$scope.poll = {
			name : "",
			questions : new Array()
		};
	}
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
	// called when user presses add question button
	$scope.addQuestion = function addQuestion() {
		$scope.poll.questions.push({
			text : ""
		});
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
