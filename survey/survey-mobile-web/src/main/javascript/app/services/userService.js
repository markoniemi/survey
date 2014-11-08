'use strict';

app.factory("UserService", function(config, $resource, $location) {
	var apiUrl = $location.protocol() + "://" + $location.host() + ":"
		+ $location.port() + config.apiEndPoint;
	return $resource(apiUrl + "users/:username", null, {
		update : {
			method : 'PUT'
		}
	});
});
