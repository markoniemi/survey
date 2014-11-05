'use strict';

app.factory("UserService", function($resource, $location) {
	var baseUrl = $location.protocol()+"://"+$location.host()+":"+$location.port()+"/survey-web/api/rest/";
	return $resource(baseUrl + "users/:username", null, {
		update: { method: 'PUT' }
	});
});
