app.controller('FileCtrl', function($scope, $location, $translate, FileService,
		$routeParams, $log) {
	// if editing, get file from service
//	var editing = $routeParams.id != null;
//	$log.debug("editing file: " + $routeParams.id);
//	if (editing) {
//		$scope.file = FileService.get({
//			filename : $routeParams.id
//		});
//	}
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
	// called when user presses submit button
//	$scope.saveFile = function saveFile(file) {
//	    if (editing) {
//	        FileService.update(file, success, failure);
//	    } else {
//	        FileService.save(file, success, failure);
//	    }
//	};
	// TODO add success and error handling
	$scope.uploadFile = function uploadFile(file) {
	    FileService.upload(file);
	}
//	$scope.uploadFile = function uploadFile(file) {
//        FileService.update({
//            filename: file.name,
//            mimeType: file.type,
//            content: file,
//            createTime: file.lastModified,
//            size : file.size
//        }, success, failure);
//	};
	// called when user presses cancel
	$scope.cancel = function() {
		$location.path('/files/files');
	};
});
