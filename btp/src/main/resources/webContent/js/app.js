var app = angular.module('btpModul', ['ngRoute', 'ngResource', 'ngAnimate']);

app.config(function($routeProvider) {
	$routeProvider
	.when('/', { templateUrl: '/views/home.htm', controller: 'GameDetailController' })
	.when('/teams', { templateUrl: '/views/teams.htm', controller: 'TeamListController' })
	.when('/teams/:id', { templateUrl: '/views/teams_details.htm', controller: 'TeamDetailController' })
	.when('/players', { templateUrl: '/views/players.htm', controller: 'PlayerListController' })
	.when('/players/:id', { templateUrl: '/views/players_details.htm', controller: 'PlayerDetailController' })
	.when('/games', { templateUrl: '/views/games.htm', controller: 'GameListController' })
	.when('/games/:id', { templateUrl: '/views/games_details.htm', controller: 'GameDetailController' })
//	.when('/current_game', { templateUrl: '/views/current_game.htm', controller: 'CurrentGameController' })
//	.when('/current_game/:id', { templateUrl: '/views/current_game_details.htm', controller: 'CurrentGameDetailController' })
	.when('/about', { template: 'Informationen ueber die App und den Ersteller' });
//	.otherwise({ redirectTo: '/' });
});

app.factory('Player', function($resource) {
	var Player = $resource('/api/players/:id',  //resource needs the full endpoint path
			{id: '@id'},
			{update: {method: 'PUT'}},
			{},
			{query: {method: 'GET', isArray: true}});		
	
			Player.prototype.isNew = function() {
				return (typeof(this.id) === 'undefined');
			}
	return Player;
});


app.factory('Team', function($resource) {
	var Team = $resource('/api/teams/:id',
			{id: '@id'},
			{update: {method: 'PUT'}},
			{},
			{query: {method: 'GET', isArray: true}}); 
			
			//define function Team.xxx.isNew is required for the save action. see line number ...!
			Team.prototype.isNew = function() {
				return (typeof(this.id) === 'undefined');
			}
	//result of function call has 5 methods by default get, query, save, remove, delete 
	return Team;
});


app.factory('Game', function($resource) {
	console.log("wann komm ich in die factory von game rein? ");
	var Game = $resource('/api/games/:id', 
			{id: '@id'},
			{update: {method: 'PUT'}});
//			{query: {method: 'GET', isArray: true}});
	
	Game.prototype.isNew = function() {
		return (typeof(this.id) === 'undefined');
	}
	
	console.log("und wieder raus");
	return Game;
});

app.controller({
	GameListController: function($scope, Game) {
		$scope.games = Game.query();
		console.log("gib mir alle games aus");
	}
});

app.controller({
	GameDetailController: function($scope, $routeParams, $location, Game) {
		console.log("in die funktion");
		var id = $routeParams.id;
		
		
		if(id == 'new') {
			console.log("im new zweig von if");
			$scope.game = new Game();
			$scope.showSave = true;
			console.log("inhalt von scope.game" + $scope.game);	
		} else {
			console.log("im else zweig von new");
//			$scope.game = Game.get({id: id});
			$scope.game = Game.query();
			$scope.showSave = false;
		}
		
		
		
		$scope.save = function() {
			console.log("save button gedrückt");
			if ($scope.game.isNew()) {
				$scope.game.$save(function(game, headers) {
					var location = headers('Location');
					var id = location.substring(location.lastIndexOf('/') + 1);
					$location.path('/' + id); 
				});
			} else {
				$scope.game.$update(function() {
					$location.path('/games');					
				});
			}
		};
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
//		$scope.players = []
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
app.controller("selection", function($scope, Team) {
	$scope.teams = Team.query();
});

app.controller('doit', function($scope, Player, Team) {
	$scope.players = Player.query(function() {
		console.log("$scope.players =" + $scope.players);
	});
	$scope.teams = Team.query(function() {
		console.log("$scope.teams =" + $scope.teams);
	});
	
	$scope.getPlayers = function(id) {
		console.log("länge der player =" + $scope.players.length);
		console.log("spieler =" + $scope.players);
		for (var i = 0; i < $scope.players.length; i++) {
			console.log("aktueller spieler =" + $scope.players[i].firstname);
			console.log("$scope.players.firstname =" + $scope.players.firstname);
			console.log("$scope.players =" + $scope.players);
			
			if ($scope.players[i].team.id === id) {
				return $scope.players[i].firstname;
			}
		};
	};
});



app.controller("selectionTwo", function($scope, Game, Team) {
	console.log("in the selectiontwo controller: ");
	$scope.teams = Team.query();
	$scope.games = Game.query();
	console.log($scope.game);

});




// workaround for the new angularjs 1.6.5 version. where hash-baning (#!) replaced #
// -,- grml bullshit - alternatively use the exclamation mark on the server side urls -> <a href="#!/teams">..
app.config(['$locationProvider', function($locationProvider) {
	  $locationProvider.hashPrefix('');
	}]);