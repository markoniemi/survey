app.service('loginService', function($rootScope, $http, $localStorage, $log, jwtHelper) {
    this.login = function(user, success, error) {
        $http({
            method : 'POST',
            url : '/survey-backend/api/rest/login',
            data : {
                username : user.username,
                password : user.password
            }
        }).success(function(authenticationToken) {
            $log.debug("authenticationToken: " + authenticationToken);
            $localStorage.authenticationToken = authenticationToken;
            $localStorage.user = jwtHelper.decodeToken(authenticationToken);
            $rootScope.$broadcast('login', $localStorage.user);
            $log.debug("login successful: " + user.username);
            success();
        }).error(function() {
            // Erase the token if the user fails to login
            delete $localStorage.authenticationToken;
            delete $localStorage.user;
            $log.debug("login error");
            error();
        });
    };
    this.logout = function() {
        $http({
            method : 'POST',
            url : '/survey-backend/api/rest/logout'
        }).success(function() {
            // $localStorage.$reset();
            delete $localStorage.authenticationToken;
            delete $localStorage.user;
            $log.debug("log out successful");
            $rootScope.$broadcast('logout');
        }).error(function() {
            $log.debug("unable log out");
        });
    };
});
