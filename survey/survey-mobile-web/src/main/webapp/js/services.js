'use strict';
var baseUrl = "http://localhost:8082/survey-web/api/rest/";
app.factory("UserService", function($resource) {
	return $resource(baseUrl + "users/:username", null, {
		update: { method: 'PUT' }
	});
});
app.factory("FileService", function($resource) {
	return $resource(baseUrl + "files/:id");
});
