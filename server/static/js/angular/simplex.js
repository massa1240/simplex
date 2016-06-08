angular.module('simplexApp', ['timer'])
  .service('SimplexService', function ($http) {
   
    this.calculate = function (objective, objectiveFunction, constraints, constraintSigns, b) {

      return $http({
        method: 'POST',
        data: {
          objective: objective,
          objectiveFunction: objectiveFunction,
          constraints: constraints,
          constraintSigns: constraintSigns,
          b: b
        },
        url: '/simplex'
      });
    }
  })
  .controller('SimplexController', ['$scope','SimplexService', function($scope, SimplexService) {

    $scope.defaultSignValue = 2;
    $scope.defaultValue = 0;

    $scope.constraintSigns = [ $scope.defaultSignValue, $scope.defaultSignValue ]

    $scope.constraints = [
      [ $scope.defaultValue, $scope.defaultValue ],
      [ $scope.defaultValue, $scope.defaultValue ]
    ];

    $scope.objective = 2;

    $scope.b = [ $scope.defaultValue, $scope.defaultValue ];

    $scope.objectiveFunction = [ $scope.defaultValue, $scope.defaultValue ];

    $scope.calculate = function() {
      $scope.$broadcast('timer-start');
      SimplexService.calculate($scope.objective, $scope.objectiveFunction, $scope.constraints, $scope.constraintSigns, $scope.b)
        .then(function(response) {
          if (! response.data.error) {
            msg = "<strong>Z</strong> = "+response.data.z;
            angular.forEach(response.data.answers, function(v, k) {
              msg += ", <strong>"+k+"</strong> = "+v;
            });

            swal({title: "We have found the optimal result!",
                  type: "success",
                  text: msg,
                  html: true
                });
          } else {
            if ( response.data.code ) {
              swal(response.data.msg, "", "warning");
            }
          }
          $scope.$broadcast('timer-stop');
        });
    }

    $scope.addConstraint = function() {

      arr = [];
      for ( i = 0; i < $scope.constraints[0].length; i+=1 ) {
        arr.push($scope.defaultValue);
      }
      $scope.constraints.push( arr );
      $scope.b.push( $scope.defaultValue );
      $scope.constraintSigns.push( $scope.defaultSignValue );
    };
    
    $scope.removeVariable = function(index) {
      if ($scope.objectiveFunction.length > 2) {
        $scope.objectiveFunction.splice(index, 1);
        angular.forEach($scope.constraints, function(value) {
          value.splice(index, 1);
        });
      } else {
        alert("You must have at least two variables.");
      }
    }
    
    $scope.removeConstraint = function(index) {
      if ($scope.constraints.length > 2) {
        $scope.constraints.splice(index, 1);
        $scope.b.splice(index, 1);
        $scope.constraintSigns.splice(index, 1);
      } else {
        alert("You must have at least two constraints.");
      }
    }

    $scope.addVariable = function() {
      $scope.objectiveFunction.push( $scope.defaultValue );
      angular.forEach($scope.constraints, function(value) {
        value.push( $scope.defaultValue );
      });
    };
    
    
  }]);
