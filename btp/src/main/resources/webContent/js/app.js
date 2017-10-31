var app = angular.module('btpModul', ['ngRoute', 'ngResource', 'ngAnimate']);

app.config(function($routeProvider) {
	$routeProvider
	.when('/', { templateUrl: '/views/home.htm' })
	.when('/teams', { templateUrl: '/views/teams.htm', controller: 'TeamListController' })
	.when('/teams/:id', { templateUrl: '/views/teams_details.htm', controller: 'TeamDetailController' })
	.when('/players', { templateUrl: '/views/players.htm', controller: 'PlayerListController' })
	.when('/players/:id', { templateUrl: '/views/players_details.htm', controller: 'PlayerDetailController' })
	.when('/games', { templateUrl: '/views/games.htm', controller: 'GameListController' })
	.when('/games/:id', { templateUrl: '/views/games_details.htm', controller: 'GameDetailController' })
	.when('/current_game', { templateUrl: '/views/current_game.htm', controller: 'CurrentGameController' })
	.when('/current_game/:id', { templateUrl: '/views/current_game_details.htm', controller: 'GameDetailController' })
	.when('/about', { template: "<p>Informationen ueber die App und den Ersteller</p> <p><a href=\"/api/swagger\">Link zur API Dokumentation mit Swagger</a></p>" });
//	.otherwise({ redirectTo: '/' });
});

app.controller("CurrentGameController", function($scope, Game) {
	$scope.games = [];
	$scope.games = Game.query();

});

//the $resource requires a classic RESTful backend with endpoints.
//see: https://www.sitepoint.com/creating-crud-app-minutes-angulars-resource/
app.factory("Player", function($resource) {
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

//the basic operations are already provided with the $resource service 
//query() save() get() remove() delete()
app.factory("Team", function($resource) {
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


app.factory("Game", function($resource) {
	var Game = $resource('/api/games/:id', 
			{id: '@id'},
			{update: {method: 'PUT'}},
			{},
			{query: {method: 'GET', isArray: true}});
	
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

//app.service("sharetheteams", function(Team) {
//	var shareteams = Team.query();
//	
//	return { 
//	  	getSharedTeams: function() { 
//	    	return shareteams; 
//	 		},
//	  	setSharedTeams: function(value) {
//	    	shareteams = value;
//	    }
//	 };
//});

app.controller(
	"GameDetailController", function($scope, $filter, $routeParams, $location, Game, Team, Player) {
		var id = $routeParams.id;
		
		if(id == 'new') {
			$scope.players = Player.query();
			
			$scope.inputs = [];
			
			$scope.addSingleToTeam = function() {
				$scope.inputs.push({});
			};
			
			$scope.teams = Team.query();
			$scope.teams.players = [];
			$scope.teams.players = Player.query();

			$scope.game = new Game();
			$scope.game.teams = [];
			$scope.game.teams.players = [];
			$scope.game.teams.players = Player.query();
			$scope.game.date = new Date();
			
//			$scope.game.date = ($filter)('date')(new Date(Date.now()), 'dd.MM.yyyy');
			$scope.showSave = true;	
			
			console.log("Game.query() = " + Game.query());
			console.log("Team.query() = " + Team.query());
			console.log("inhalt von scope.game.teams = [] ist : " + $scope.game.teams);
			console.log("value of date: " + $scope.game.date);
		} else {
			$scope.game = Game.get({id: id});
			$scope.showSave = false;
		}
		
		//$scope.save triggers a POST request 
		$scope.save = function() {
//			$scope.game.date = $filter('date')(new Date($scope.game.date), 'dd.MM.yyyy');
			$scope.game.date = $filter('date')($scope.game.date, 'dd.MM.yyyy'); //filter transform the date object again in a string. required in the game.java
			
			if ($scope.game.isNew()) {
			
				//for instance the $save method is used:
				// $scope.team = new Team();
				// $scope.team.$save(function() { //data saved. $scope.team is sent as the post body });
				$scope.game.$save(function(game, headers) {
//					var location = headers('Location');
//					var id = location.substring(location.lastIndexOf('/') + 1);
//					$location.path('/' + id);
					$location.path('/current_game');
					
				});
			} else {
				$scope.game.$update(function() {
					$location.path('/games');					
				});
			}
		};
	}
);

app.service("popupService", ['$window',function($window){
    this.showPopup=function(message){
        return $window.confirm(message); //Ask the users if they really want to delete the post entry
    }}]);


app.controller("TeamListController", function($scope, Team, popupService) {
	//{id: $scope.id} id: in the url is replaced with $scope.id. empty object which is populated when the data comes from server
	//var _team = Team.get({id: $scope.id}, function () {
	//	console.log("_team=" + _team); //get returns a single entry
	//});	
//	var _teams = Team.query(function() {
//	console.log("_teams=" + _teams); //query returns all elements
//});
//$scope.teams = new Team(); //instantiate resource class
//Team.save($scope.teams, function() {
	//save an entry assuming $scope.team is the Team object
//});
	$scope.teams = Team.query(function() {console.log("scope.teams" + $scope.teams); } );  //fetch all teams. Isses a GET to /api/teams - $scope.teams is important to iterate thru the teams
		
	$scope.deleteTeam = function(team) {
		if (popupService.showPopup('Really delete this Team?')) {
			team.$delete(function() {
				$scope.teams.splice($scope.teams.indexOf(team),1);
			});
		}
	}
});

app.controller(
	//$location parses the URL in the browser address bar and makes it available in the application
	"TeamDetailController", function($scope, $routeParams, $location, Team) {
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
);


app.controller("PlayerListController", function($scope, Player) {
//		$scope.players = []
		$scope.players = Player.query();
		
		$scope.deletePlayer = function(player) {
			player.$delete(function() {
				$scope.players.splice($scope.players.indexOf(player),1);
			});
		}
});

app.controller("PlayerDetailController", function($scope, $routeParams, $location, Player) {
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
});

//for the dropdownmenu for players to select a suitale team
app.controller("selection", function($scope, Team) {
	$scope.teams = [];
	$scope.teams = Team.query();
});


// workaround for the new angularjs 1.6.5 version. where hash-baning (#!) replaced #
// -,- grml bullshit - alternatively use the exclamation mark on the server side urls -> <a href="#!/teams">..
app.config(['$locationProvider', function($locationProvider) {
	  $locationProvider.hashPrefix('');
	}]);