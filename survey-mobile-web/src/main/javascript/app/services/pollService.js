'use strict';

app.factory("PollService", function(config, $resource, $location, $translate) {
	var apiUrl = $location.protocol() + "://" + $location.host() + ":"
			+ $location.port() + config.apiEndPoint;
	var service = $resource(apiUrl + "polls/:name", null, {
		update : {
			method : 'PUT'
		}
	});
	service.getTypes = function() {
		return [{name: $translate.instant("TYPE_LABEL"), value: 'LABEL'},
                {name: $translate.instant("TYPE_TEXT"), value: 'TEXT'},
                {name: $translate.instant("TYPE_BOOLEAN"), value: 'BOOLEAN'}];
	}
	return service;
});
