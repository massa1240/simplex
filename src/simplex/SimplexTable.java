package simplex;

class SimplexTable {

    Expression e;
    private Cell[][] simplexTable;
    private int[] basicVariables;
    private int[] nonBasicVariables;

    public SimplexTable(Expression e) {
        this.e = e;
        this.createSimplexTable();
        this.populateObjectiveFunction();
        this.populateConstraints();
        this.setNonBasicVariables();
        this.setBasicVariables();
    }

    private void createSimplexTable() {
        this.basicVariables = new int[e.countBasicVariables()];
        this.nonBasicVariables = new int[e.countNonBasicVariables()];
        int rows = basicVariables.length + 1;//+ objective function
        int cols = nonBasicVariables.length + 1;//+ 1 col for looseness/excess
        simplexTable = new Cell[rows][cols];
    }

    private void populateObjectiveFunction() {
        simplexTable[0][0] = new Cell();

        double[] objectiveFunction = e.getObjectiveFunction();
        int multiplier;
        if (e.getObjective() == Expression.OBJECTIVE_MAX) {
            multiplier = 1;
        } else {
            multiplier = -1;
        }

        for ( int i = 1; i <= objectiveFunction.length; i+=1 ) {
            simplexTable[0][i] = new Cell(objectiveFunction[i-1]*multiplier);
        }
    }
    
    private void populateConstraints() {
        for ( int i = 1; i <= basicVariables.length; i+=1) {
            int multiplier;
            if ( e.getConstraintSign(i-1) == Expression.CONSTRAINT_SIGN_GT ) {
                multiplier = -1;
            } else {
                multiplier = 1;
            }
            simplexTable[i][0] = new Cell(e.getB(i-1)*multiplier);
            for ( int j = 1; j <= nonBasicVariables.length; j+=1) {
                simplexTable[i][j] = new Cell(e.getConstraint(i-1,j-1)*multiplier);
            }
        }
    }
    
    private void setBasicVariables() {
    	for (int i = 0; i < basicVariables.length; i+=1 ) {
    		basicVariables[i] = i + nonBasicVariables.length;
    	}
    }
    
    private void setNonBasicVariables() {
    	for (int i = 0; i < nonBasicVariables.length; i+=1 ) {
    		nonBasicVariables[i] = i;
    	}
    }

    public int getNegativeFreeMemberIndex() {
        for(int i = 1; i < simplexTable.length; i+=1 ) {
            if ( simplexTable[i][0].getTop() < 0 ) { // if free member is negative
                return i;
            }
        }
        return -1;
    }

    public int getPositiveFXIndex() {
        for(int i = 1; i < simplexTable[0].length; i+=1 ) {
            if ( simplexTable[0][i].getTop() == 0 ) {//unlimited solution
                return -2;
            } else if ( simplexTable[0][i].getTop() > 0 ) { // if fx is positive
                return i;
            }
        }
        return -1;
    }

    public int getColumnWithNegativeElementOf(int line) {
        for(int i = 1; i < simplexTable[line].length; i+=1 ) {
            if ( simplexTable[line][i].getTop() < 0 ) { // if member is negative
                return i;
            }
        }
        return -1;
    }

    public void changeAlgorithm(int permittedLine, int permittedColumn){
        double allowedElement = simplexTable[permittedLine][permittedColumn].getTop();
        //Calculates the multiplicative inverse of the allowed element
        double inverse = Math.pow(allowedElement, -1);
        simplexTable[permittedLine][permittedColumn].setBottom(inverse);

        // Multiply each element from the selected line and column
        // by the allowed element and stores on the bottom cell
        multiplyPermittedLineByInverse(permittedLine, permittedColumn, inverse);
        multiplyPermittedColumnByNegativeInverse(permittedLine, permittedColumn, inverse);

        // for every lower sub-cells, multiply the top of the permitted row by bottom of permitted column
        multiplyLowerSubcells(permittedLine, permittedColumn);

        redoTable(permittedLine, permittedColumn);

        swapBasicWithNonBasicVariable(permittedLine, permittedColumn);

    }

    private void multiplyPermittedLineByInverse(int permittedLine, int permittedColumn, double inverse) {
        for ( int j = 0; j < simplexTable[0].length; j+=1 ) {
            if( j != permittedColumn ){	// IS NOT ALLOWED ELEMENT
                simplexTable[permittedLine][j].setBottom((simplexTable[permittedLine][j].getTop())*inverse);
            }
        }
    }

    private void multiplyPermittedColumnByNegativeInverse(int permittedLine, int permittedColumn, double inverse) {
        for( int i = 0; i < simplexTable.length; i+=1 ){
            if( i != permittedLine ){   // IS NOT ALLOWED ELEMENT
                simplexTable[i][permittedColumn].setBottom((simplexTable[i][permittedColumn].getTop())*-inverse);
            }
        }
    }

    private void multiplyLowerSubcells(int permittedLine, int permittedColumn) {
        for ( int i = 0; i < simplexTable.length; i+=1 ) {
            if ( i != permittedLine ) {
                for (int j = 0; j < simplexTable[i].length; j += 1) {
                    if (j != permittedColumn) {
                        double value = simplexTable[permittedLine][j].getTop() * simplexTable[i][permittedColumn].getBottom();
                        simplexTable[i][j].setBottom(value);
                    }
                }
            }
        }
    }

    private void redoTable(int permittedLine, int permittedColumn) {
        Cell[][] newTable = new Cell[simplexTable.length][simplexTable[0].length];
        
        copySubCells(newTable, permittedLine, permittedColumn);
        sumLeftSubCells(newTable, permittedLine, permittedColumn);

        simplexTable = newTable;
    }

    private void copySubCells(Cell[][] newTable, int permittedLine, int permittedColumn) {
        // copy lower sub-cells of permitted line and column to upper sub-cells
        for ( int i = 0; i < newTable.length; i+=1 ) {
            newTable[i][permittedColumn] = new Cell(simplexTable[i][permittedColumn].getBottom());
        }

        for ( int j = 0; j < newTable[0].length; j+=1 ) {
            newTable[permittedLine][j] = new Cell(simplexTable[permittedLine][j].getBottom());
        }
    }

    private void sumLeftSubCells(Cell[][] newTable, int permittedLine, int permittedColumn) {
        
    	for ( int i = 0; i < newTable.length; i+=1 ) {
    		if ( i != permittedLine ) {
	    		for ( int j = 0; j < newTable[i].length; j+=1 ) {
	    			if ( j != permittedColumn ) {
	    				double value = simplexTable[i][j].getTop() + simplexTable[i][j].getBottom();
	    				newTable[i][j] = new Cell(value);
	    			}
	    		}
    		}
    	}
    }

    private void swapBasicWithNonBasicVariable(int permittedLine, int permittedColumn) {
    	int correctIndex = -1;
        int currentNonBasicVariable = nonBasicVariables[permittedColumn+correctIndex];
        nonBasicVariables[permittedColumn+correctIndex] = basicVariables[permittedLine+correctIndex];
        basicVariables[permittedLine+correctIndex] = currentNonBasicVariable;
    }

    public int getPermittedLine(int permittedColumn) {
        int permittedLine = -1;
        double minQuotient = Double.MAX_VALUE;

        for(int i = 1; i < simplexTable.length; i+=1) {
            double freeMember = simplexTable[i][0].getTop();
            double permittedElement = simplexTable[i][permittedColumn].getTop();

            if ( (freeMember < 0 && permittedElement < 0) || (freeMember > 0 && permittedElement > 0)) {
                double x = freeMember / permittedElement;
                if ( x < minQuotient ) {
                    minQuotient = x;
                    permittedLine = i;
                }
            }
        }

        return permittedLine;
    }

    public double[] getCurrentVariablesValues() {
        double[] values = new double[basicVariables.length + nonBasicVariables.length+1];

        values[0] = simplexTable[0][0].getTop();
        for ( int i = 0; i < basicVariables.length; i+=1 ) {
            int index = basicVariables[i];
            values[index+1] = simplexTable[i+1][0].getTop();
        }

        for ( int j = 0; j < nonBasicVariables.length; j+=1 ) {
            int index = nonBasicVariables[j];
            values[index+1] = simplexTable[0][j+1].getTop();
        }

        return values;
    }


    public Cell[][] getSimplexTable() {
        return simplexTable;
    }

	public int[] getBasicVariables() {
		return basicVariables;
	}

	public int[] getNonBasicVariables() {
		return nonBasicVariables;
	}

}
