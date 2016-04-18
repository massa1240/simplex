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

    public int getPermittedLine(int permittedColumn) {
        int permittedLine = -1;
        double minQuotient = Double.MAX_VALUE;

        for(int i = 1; i < simplexTable.length; i+=1) {
            double freeMember = simplexTable[i][0].getTop();
            double permittedElement = simplexTable[i][permittedColumn].getTop();

            if ( (freeMember <= 0 && permittedElement < 0) || (freeMember >= 0 && permittedElement > 0)) {
                double x = freeMember / permittedElement;
                if ( x < minQuotient ) {
                    minQuotient = x;
                    permittedLine = i;
                }
            }
        }

        return permittedLine;
    }

}
