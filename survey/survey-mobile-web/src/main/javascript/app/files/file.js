app.controller('FileCtrl', function($scope, $location, $translate, FileService,
		$routeParams, $log) {
	// success callback
	function success() {
		$log.log('success');
		$scope.files = FileService.query();
		$location.path('/files/files');
	}
	// failure callback
	function failure(httpResponse) {
		$scope.error = $translate.instant('ADD_FILE_ERROR');
	};
	$scope.uploadFile = function uploadFile(file) {
	    FileService.upload(file, success, failure);
	}
	// called when user presses cancel
	$scope.cancel = function() {
		$location.path('/files/files');
	};
});
