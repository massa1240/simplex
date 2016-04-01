package simplex;

public class Expression {

	public static int OBJECTIVE_MIN = 1;
	public static int OBJECTIVE_MAX = 2;
	
	private int objective;
	private int[] objectiveFunction;
	private int[][] constraints;
	private int[] constraintSigns;
	private int[] b;
	
	
	public Expression(int objective, int[] objectiveFunction, int[][] constraints, 
						int[] constraintSigns, int[] b) throws Exception {
		
		int nDecVariables = objectiveFunction.length;
		int nConstraints = constraints.length;
		
		if ( objective != OBJECTIVE_MIN && objective != OBJECTIVE_MAX ) {
			throw new Exception("Objective must be MIN or MAX.");
		}
		
		if ( objectiveFunction.length == 0 || constraints.length == 0 || constraintSigns.length == 0 || b.length == 0 ) {
			throw new Exception("Neither objective function, constratints nor constratint signs length should be 0.");
		}
		
		if ( nDecVariables != constraints[0].length || nConstraints != constraintSigns.length || nConstraints != b.length ) {
			throw new Exception("Number of decision variables or constraints incompatible.");
		}
		
		this.objective = objective;
		this.objectiveFunction = objectiveFunction;
		this.constraintSigns = constraintSigns;
		this.b = b;
	}
	
	public boolean isObjectiveMax() {
		return this.objective == OBJECTIVE_MAX;
	}


	public int getObjective() {
		return objective;
	}


	public int[] getObjectiveFunction() {
		return objectiveFunction;
	}


	public int[][] getConstraints() {
		return constraints;
	}


	public int[] getConstraint(int l) {
		return constraints[l];
	}


	public int[] getConstraintSigns() {
		return constraintSigns;
	}


	public int getConstraintSign(int l) {
		return constraintSigns[l];
	}


	public int[] getB() {
		return b;
	}


	public int getB(int l) {
		return b[l];
	}
}
