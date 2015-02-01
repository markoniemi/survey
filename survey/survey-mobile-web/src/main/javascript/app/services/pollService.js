'use strict';

app.factory("PollService", function(config, $resource, $location, $translate) {
	var apiUrl = $location.protocol() + "://" + $location.host() + ":"
			+ $location.port() + config.apiEndPoint;
	var service = $resource(apiUrl + "polls/:name", null, {
		update : {
			method : 'PUT'
		}
	});
	service.getRoles = function() {
		return [{name: $translate.instant("TYPE_LABEL"), value: 'Question'},
                {name: $translate.instant("TYPE_TEXT"), value: 'TextQuestion'},
                {name: $translate.instant("TYPE_BOOLEAN"), value: 'BooleanQuestion'}];
	}
	return service;
});
