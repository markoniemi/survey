'use strict';

app.factory("UserService", function(config, $resource, $location) {
	var apiUrl = $location.protocol() + "://" + $location.host() + ":"
		+ $location.port() + config.apiEndPoint;
	return $resource(apiUrl + "users/:username", null, {
		update : {
			method : 'PUT'
		}
	});
//	service.getRoles = function() {
//		return [{name: $translate.instant("ROLE_USER"), value: 'ROLE_USER'},
//                {name: $translate.instant("ROLE_ADMIN"), value: 'ROLE_ADMIN'}];
//		
//	}
//	return service;
});
