angular.module('simplexApp', [])
  .controller('SimplexController', function($scope) {

    $scope.constraintSignOptions = [ 
      { id: 0, name: '≥' },
      { id: 1, name: '≤' },
      { id: 2, name: '=' }
    ]

    $scope.constraintSigns = [ 0, 0 ]

    $scope.defaultValue = 0;

    $scope.constraints = [
      [ $scope.defaultValue, $scope.defaultValue ],
      [ $scope.defaultValue, $scope.defaultValue ]
    ];

    $scope.b = [ $scope.defaultValue, $scope.defaultValue ];

    $scope.objectiveFunction = [ $scope.defaultValue, $scope.defaultValue ];
    
    $scope.addConstraint = function() {

      arr = [];
      for ( i = 0; i < $scope.constraints[0].length; i+=1 ) {
        arr.push($scope.defaultValue);
      }
      $scope.constraints.push( arr );
      $scope.b.push( $scope.defaultValue );
      $scope.constraintSigns.push( $scope.defaultValue );
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
    
    
  });
