angular.module('survey-mobile-web.config', []).constant('config', {
	'apiEndPoint' : "/survey-backend/api/rest/",
	'projectVersion' : "${project.version}",
	'angularVersion' : "${angularjs.version}",
	'angularUiBootstrapVersion' : "${angular-ui-bootstrap.version}"
})
