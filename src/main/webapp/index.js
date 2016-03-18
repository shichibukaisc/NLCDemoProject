/*Angular Modules take a name, best practice is lowerCamelCase, and a list of*/
/*added the second jdule as dependencies */
var myApp =  angular.module('pageModule',[]);

myApp.controller('pageCtrl',function ($scope, $http) {

 	$scope.userInputs = [];
 	$scope.watsonthinking = false;
 	
 	$scope.classify = function(keyEvent) {
 		if (keyEvent.which === 13) {
 			$scope.watsonthinking = true;
 			var dataObj = {
 				message: $scope.enterText
 			};
 			$http.post('/rest/jsonServices/restaurantanswer', dataObj, {headers: {'Content-Type': 'application/json'} })
 			.success(function(data, status, headers, config) {
 				var data = {
 					userInput:$scope.enterText,
 					response:data.message,
 					confidence:data.confidence
 				};
 				$scope.userInputs.push(data);
 				$scope.enterText = "";
 				$scope.watsonthinking = false;
 			})
 			.error(function(data, status, headers, config) {
 				alert( "failure message: " + JSON.stringify({data: data}));
 				$scope.watsonthinking = false;
 			});
 		}
	}

});