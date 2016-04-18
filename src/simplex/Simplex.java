package simplex;

public class Simplex{

	private static Simplex instance;

	private SimplexTable table;

	private Simplex(){}

	public static Simplex getInstance(){
		if(instance == null){
			instance = new Simplex();
		}

		return instance;
	}
	
	public void start(Expression e) {
		table = new SimplexTable(e);
	}
}