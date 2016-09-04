function loginCtrl($location, $scope, $localStorage, loginService) {
    $scope.$storage = $localStorage;
    $scope.login = function() {
        loginService.login({
            username : $scope.username,
            password : $scope.password
        }, success, error);
//        $location.path("/users/users");
    };
    success = function() {
        $scope.authenticationToken = $localStorage.authenticationToken;
        $location.path("/users/users");
    };
    error = function() {
        $scope.errorMessage = "Login error";
//        $location.path("/login/login");
    };
    $scope.logout = function() {
        loginService.logout();
        $location.path("/login/login");
    };
}