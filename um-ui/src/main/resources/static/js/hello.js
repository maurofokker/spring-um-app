angular.module('hello', [ 'ngRoute' ]).config(function($routeProvider) {

    $routeProvider.when('/', {
        templateUrl : 'home.html',
        controller : 'home'
    }).otherwise('/');

}).controller('home', function($scope, $http, $window) {
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
