package simplex;

class SimplexTable {

    private Cell[][] simplexTable;

    public SimplexTable(Expression e) {

    }

    public int getNegativeFreeMemberIndex() {
        for(int i = 1; i < simplexTable.length; i+=1 ) {
            if ( simplexTable[i][0].getTop() < 0 ) { // if free member is negative
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

        // Multiply each element from the line and column selecteds
        // by the allowed element and stores on the bottom cell
        for( int i = 0; i < simplexTable.length; i++ ){
            //MULTIPLY LINES
            if( i == permittedColumn ){	// ALLOWED ELEMENT
                continue;
            }else{
                simplexTable[permittedLine][i].setBottom((simplexTable[permittedLine][i].getTop())*inverse);
            }

            //MULTIPLY COLUMNS
            if( i < simplexTable[0].length ){
                if( i == permittedLine ){   // ALLOWED ELEMENT
                    continue;
                }else{
                    simplexTable[i][permittedColumn].setBottom((simplexTable[i][permittedColumn].getTop())*inverse);
                }
            }
        }
    }

}
