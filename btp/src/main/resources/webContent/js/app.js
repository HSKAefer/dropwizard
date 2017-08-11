var app = angular.module('btpModul', ['ngRoute', 'ngResource']);

app.config(function($routeProvider) {
	$routeProvider
	.when('/teams', { templateUrl: '/views/teams.htm', controller: 'TeamListController' })
	.when('/players', { templateUrl: '/views/players.htm' })
	.when('/matches', { templateUrl: '/views/matches.htm' })
	.when('/about', { template: 'Informationen ueber die App und den Ersteller' })
	.otherwise({ redirectTo: '/' });
});

app.factory('Team', function($resource) {
	var Team = $resource('/api/teams/:teamid'); 
	//result of function call has 5 methods by default get, query, save, remove, delete 
	return Team;
});

app.controller({
	TeamListController: function($scope, Team) {
		$scope.teams = Team.query(); //fetch all teams. Isses a GET to /api/teams - $scope.teams is important to iterate thru the teams
	}
});



// workaround for the new angularjs 1.6.5 version. where hash-baning (#!) replaced #
// -,- grml bullshit - alternatively use the exclamation mark on the server side urls -> <a href="#!/teams">..
app.config(['$locationProvider', function($locationProvider) {
	  $locationProvider.hashPrefix('');
	}]);