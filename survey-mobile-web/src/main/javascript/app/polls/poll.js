app.controller('PollCtrl', function($scope, $location, $translate, PollService,
		$routeParams, $log) {
	var editing = $routeParams.id != null;
	$log.debug("editing poll: " + $routeParams.id);
	if (editing) {
		// if editing, get poll from service
		$scope.poll = PollService.get({
			name : $routeParams.id
		});
	} else {
		// if new, create new question
		$scope.poll = {
			name : "",
			questions : new Array()
		};
	}
	// values for role select
	$scope.types = PollService.getTypes();
	// success callback
	function success() {
		$log.log('success');
		$scope.polls = PollService.query();
		$location.path('/polls/polls');
	}
	// failure callback
	function failure(httpResponse) {
		$scope.error = $translate.instant('ADD_POLL_ERROR');
	};
	// called when user presses add question button
	$scope.addQuestion = function addQuestion() {
		// values for role select
		$scope.types = PollService.getTypes();
		// add a question to poll.questions
		$scope.poll.questions.push({
		    type : "LABEL",
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
