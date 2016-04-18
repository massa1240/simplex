package simplex;

public class Simplex{

    public static int STATUS_OPTIMAL_FOUND = 0;
    public static int STATUS_UNLIMITED = 1;
    public static int STATUS_IMPOSSIBLE = 2;

	private static Simplex instance;

	private SimplexTable table;

    private int status;

	private Simplex(){}

	public static Simplex getInstance(){
		if(instance == null){
			instance = new Simplex();
		}

		return instance;
	}
	
	public int[] start(Expression e) throws Exception {
		table = new SimplexTable(e);
        firstPhase();

        return new int[0];
	}

	private void firstPhase() throws Exception {
        int negativeFreeMemberIndex;
		while( (negativeFreeMemberIndex = table.getNegativeFreeMemberIndex()) != -1 ) {
            int permittedLine;
            int permittedColumn = table.getColumnWithNegativeElementOf(negativeFreeMemberIndex);

            if ( permittedColumn == -1) {
                status = STATUS_IMPOSSIBLE;
                throw new Exception("Permissible solution does not exist.");
            }

            permittedLine = table.getPermittedLine(permittedColumn);

            table.changeAlgorithm(permittedLine, permittedColumn);
        }
	}
}