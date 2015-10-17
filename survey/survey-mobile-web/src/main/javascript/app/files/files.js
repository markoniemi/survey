app.controller('FilesCtrl', function($scope, $location, $timeout, $log, FileService, config
		) {
	$log.debug('FilesCtrl');
	// get files from service
	$scope.files = FileService.query();
	// called when file presses edit button
	$scope.editFile = function editFile(file) {
		$location.path('/files/file/' + file.id);
	};
	// called when file presses delete button
	$scope.deleteFile = function deleteFile(file) {
		FileService.remove(file);
		// delay is needed since service is yet deleting the file
		$timeout(function() {
			$scope.files = FileService.query();
		}, 1000);
	};
});
