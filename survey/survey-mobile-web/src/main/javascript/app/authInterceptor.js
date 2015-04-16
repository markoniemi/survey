app.factory('authInterceptor', function ($q, $localStorage, $location, $log) {
    return {
        request: function (config) {
            if($localStorage.authenticationToken) {
                $log.debug("sending authenticationToken");
                config.headers['Authorization'] = 'Bearer ' + $localStorage.authenticationToken;
            }
            return config;
        },
        responseError: function (response) {
            if(response.status === 401) {
                $log.debug("401");
            }
            $location.path('/login/login');
            return $q.reject(response);
        }
    };
});

app.config(function ($httpProvider) {
    $httpProvider.interceptors.push('authInterceptor');
});
