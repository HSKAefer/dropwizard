var app = angular.module('btpModul', ['ngRoute', 'ngResource']);

app.config(function($routeProvider) {
	$routeProvider
	.when('/', { templateUrl: '/views/home.htm' })
	.when('/teams', { templateUrl: '/views/teams.htm', controller: 'TeamListController' })
	.when('/teams/:id', { templateUrl: '/views/teams_details.htm', controller: 'TeamDetailController' })
	.when('/players', { templateUrl: '/views/players.htm', controller: 'PlayerListController' })
	.when('/matches', { templateUrl: '/views/matches.htm' })
	.when('/about', { template: 'Informationen ueber die App und den Ersteller' });
//	.otherwise({ redirectTo: '/' });
});

app.factory('Player', function($resource) {
	var Player = $resource('/api/players/:id');
	return Player;
});

app.factory('Team', function($resource) {
	var Team = $resource('/api/teams/:id',
			{id: '@id'},
			{update: {method: 'PUT'}}); 
			
			//define function Team.xxx.isNew is required for the save action. see line number 58!
			Team.prototype.isNew = function() {
				return (typeof(this.id) === 'undefined');
			}
	//result of function call has 5 methods by default get, query, save, remove, delete 
	return Team;
});

app.controller({
	PlayerListController: function($scope, Player) {
		$scope.players = Player.query();
	}
});

app.controller({
	TeamListController: function($scope, Team) {
		$scope.teams = Team.query(); //fetch all teams. Isses a GET to /api/teams - $scope.teams is important to iterate thru the teams
		
	}
});

app.controller({
	//$location parses the URL in the browser address bar and makes it available in the application
	TeamDetailController: function($scope, $routeParams, $location, Team) {
		var id = $routeParams.id;
		
		if (id == 'new') {
			$scope.team = new Team();
			$scope.showSave = true;
		} else {
			$scope.team = Team.get({id: id});
			$scope.showSave = false;
		}
		
		$scope.save = function() {
			if ($scope.team.isNew()) {
				$scope.team.$save(function(team, headers) {
					var location = headers('Location');
					var id = location.substring(location.lastIndexOf('/') + 1);
					//$location.path() gets the curent url path. $location.path(/value) changes the path 
					// see: https://docs.angularjs.org/guide/$location
					$location.path('/' + id); 
				});
			} else {
				$scope.team.$update(function() {
					$location.path('/');					
				});
			}
		};
	}
});


// workaround for the new angularjs 1.6.5 version. where hash-baning (#!) replaced #
// -,- grml bullshit - alternatively use the exclamation mark on the server side urls -> <a href="#!/teams">..
app.config(['$locationProvider', function($locationProvider) {
	  $locationProvider.hashPrefix('');
	}]);