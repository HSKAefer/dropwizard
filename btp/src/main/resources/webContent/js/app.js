var app = angular.module('btpModul', ['ngRoute', 'ngResource', 'ngAnimate']);

app.config(function($routeProvider) {
	$routeProvider
	.when('/', { templateUrl: '/views/home.htm', controller: 'GameController' })
	.when('/teams', { templateUrl: '/views/teams.htm', controller: 'TeamListController' })
	.when('/teams/:id', { templateUrl: '/views/teams_details.htm', controller: 'TeamDetailController' })
	.when('/players', { templateUrl: '/views/players.htm', controller: 'PlayerListController' })
	.when('/players/:id', { templateUrl: '/views/players_details.htm', controller: 'PlayerDetailController' })
	.when('/games', { templateUrl: '/views/games.htm', controller: 'GameListController' })
	.when('/about', { template: 'Informationen ueber die App und den Ersteller' });
//	.otherwise({ redirectTo: '/' });
});

app.factory('Player', function($resource) {
	var Player = $resource('/api/players/:id',
			{id: '@id'},
			{update: {method: 'PUT'}});
	
			Player.prototype.isNew = function() {
				return (typeof(this.id) === 'undefined');
			}
	return Player;
});


app.factory('Team', function($resource) {
	var Team = $resource('/api/teams/:id',
			{id: '@id'},
			{update: {method: 'PUT'}}); 
			
			//define function Team.xxx.isNew is required for the save action. see line number ...!
			Team.prototype.isNew = function() {
				return (typeof(this.id) === 'undefined');
			}
	//result of function call has 5 methods by default get, query, save, remove, delete 
	return Team;
});







app.factory('Game', function($resource) {
	var Game = $resource('/api/games/:id', {id: '@id'});
	
	Game.prototype.isNew = function() {
		return (typeof(this.id) === 'undefined');
	}
	
	return Game;
});

app.controller({
	GameListController: function($scope, Game) {
		$scope.games = Game.query();
	}
});

app.controller({
	GameController: function($scope, $routeParams, $location, Game) {
		
		var id = $routeParams.id;
		
		if(id == 'new') {
			$scope.game = new Game();
			$scope.showSave = true;
		} else {
			$scope.showSave = false;
		}
		
//		$scope.save = function() {
//			if ($scope.team.isNew()) {
//				$scope.team.$save(function(game, headers) {
//					var id = location.substring(location.lastIndexOf('/'));
//					$location.path('/' + id); 
//				});
//			} else {
//				$location.path('/games');
//			}
//		};
	}
});


app.controller({
	TeamListController: function($scope, Team) {
		$scope.teams = Team.query(); //fetch all teams. Isses a GET to /api/teams - $scope.teams is important to iterate thru the teams
		
		$scope.deleteTeam = function(team) {
			team.$delete(function() {
				$scope.teams.splice($scope.teams.indexOf(team),1);
			});
		}
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
					$location.path('/teams');					
				});
			}
		};
	}
});


app.controller({
	PlayerListController: function($scope, Player) {
		$scope.players = Player.query();
		
		$scope.deletePlayer = function(player) {
			player.$delete(function() {
				$scope.players.splice($scope.players.indexOf(player),1);
			});
		}
	}
});

app.controller({
	PlayerDetailController: function($scope, $routeParams, $location, Player) {
		var id = $routeParams.id;
		
		if (id == 'new') {
			$scope.player = new Player();
			$scope.showSave = true;
		} else {
			$scope.player = Player.get({id: id});
			$scope.showSave = false;
		}
		
		$scope.save = function() {
			if ($scope.player.isNew()) {
				$scope.player.$save(function(player, headers) {
					var location = headers('Location');
					var id = location.substring(location.lastIndexOf('/') + 1);
					//$location.path() gets the curent url path. $location.path(/value) changes the path 
					// see: https://docs.angularjs.org/guide/$location
					$location.path('/' + id); 
				});
			} else {
				
					$scope.player.$update(function() {
						$location.path('/players');					
					});
				
			}
		};
	}
});

//for the dropdownmenu for players to select a suitable team
app.controller('selection', function($scope, Team) {
	$scope.teams = Team.query();
});

// workaround for the new angularjs 1.6.5 version. where hash-baning (#!) replaced #
// -,- grml bullshit - alternatively use the exclamation mark on the server side urls -> <a href="#!/teams">..
app.config(['$locationProvider', function($locationProvider) {
	  $locationProvider.hashPrefix('');
	}]);