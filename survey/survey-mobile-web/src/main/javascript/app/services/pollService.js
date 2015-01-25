'use strict';

app.factory("PollService", function(config, $resource, $location) {
	var apiUrl = $location.protocol() + "://" + $location.host() + ":"
		+ $location.port() + config.apiEndPoint;
	return $resource(apiUrl + "polls/:name", null, {
		update : {
			method : 'PUT'
		}
	});
});
