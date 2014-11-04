'use strict';

var baseUrl = "${http.protocol}://localhost:${http.port}/survey-web/api/rest/";
app.factory("UserService", function($resource) {
	return $resource(baseUrl + "users/:username", null, {
		update: { method: 'PUT' }
	});
});
