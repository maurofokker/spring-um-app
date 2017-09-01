angular.module('hello', [ 'ngResource', 'ngRoute' ]).config(function($routeProvider, $httpProvider) {
//angular.module('hello', [ 'ngRoute' ]).config(function($routeProvider) {

    $routeProvider.when('/', {
        templateUrl : 'home.html',
        controller : 'home'
    }).when('/login', {
        templateUrl : 'login.html',
        controller : 'navigation'
    }).otherwise('/');

    $httpProvider.defaults.headers.common['X-Requested-With'] = 'XMLHttpRequest';
    /*
    $httpProvider.defaults.headers.common['Access-Control-Allow-Origin'] = "*";
    $httpProvider.defaults.headers.common['Access-Control-Allow-Methods'] = "GET,PUT,POST,DELETE,OPTIONS";
    $httpProvider.defaults.headers.common['Access-Control-Allow-Headers'] = "Content-Type, Authorization, Content-Length, X-Requested-With";
    */

}).controller('navigation',

    function($rootScope, $scope, $http, $httpParamSerializer, $location, $route, $window) {

        $scope.tab = function(route) {
            return $route.current && route === $route.current.controller;
        };

        var authenticate = function(credentials, callback) {
            $scope.data = {grant_type:"password", username: credentials.username, password: credentials.password, client_id: "um"};

            // req access token
            var req = {
                method: 'POST',
                url: "http://localhost:8086/um-web/oauth/token",
                headers: {
                    "Authorization": "Basic " + btoa("um" + ":" + "VXB0YWtlLUlyb24h"),
                    "Content-type": "application/x-www-form-urlencoded; charset=utf-8"
                },
                data: $httpParamSerializer($scope.data) // send data as json and not as http parameters for security bc we r passing sensitive data
            }
            // send the request
            $http(req).then(
                function(data){ // success
                    if (data.data.access_token) {
                        $rootScope.authenticated = true;
                        $window.sessionStorage.accessToken = data.data.access_token; // store token in sessionStorage, is better to do this in a cookie
                        // to send the token in other requests to the api
                    } else {
                        $rootScope.authenticated = false;
                        delete $window.sessionStorage.accessToken; // delete access token
                    }
                    callback && callback($rootScope.authenticated);
                }, function(){ // failure
                    $rootScope.authenticated = false;
                    delete $window.sessionStorage.accessToken; // delete access token
                    callback && callback(false);
                }
            );
        }

        // authenticate();

        $scope.credentials = {};
        $scope.login = function() {
            authenticate($scope.credentials, function(authenticated) {
                if (authenticated) {
                    console.log("Login succeeded")
                    $location.path("/");
                    $scope.error = false;
                    $rootScope.authenticated = true;
                } else {
                    console.log("Login failed")
                    $location.path("/login");
                    $scope.error = true;
                    $rootScope.authenticated = false;
                }
            })
        };
    }).controller('home', function($scope, $http, $window) {
    var headers = {
        "Accept" : "application/json",
        authorization : "Bearer " + $window.sessionStorage.accessToken  // send access token
    };

    $http.get("http://localhost:8086/um-web/api/roles/1", {
        headers : headers
    }).success(function(data) {
        $scope.role = data;
    })
});

/*
 // with basic auth
 .controller('home', function($scope, $http, $window) {
 var headers = {
 "Accept" : "application/json",
 //"Authorization" : "Basic dXNlckBmYWtlLmNvbTp1c2VycGFzcw==",
 };

 $http.get("http://localhost:8086/api/roles/7", {
 headers : headers
 }).success(function(data) {
 $scope.role = data;
 })
 });
 */