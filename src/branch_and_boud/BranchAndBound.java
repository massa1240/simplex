package branch_and_boud;

import simplex.Expression;
import simplex.Simplex;
import simplex.SimplexTable;

public class BranchAndBound {
	
	private static BranchAndBound instance;
	private double otimo;

	private BranchAndBound() {
	}

	public static BranchAndBound getInstance() {
		if (instance == null) {
			instance = new BranchAndBound();
		}

		return instance;
	}

	public double[] start(Expression e) throws Exception {
		double[] answer = Simplex.getInstance().start(e);

		int nDecVariables = e.getObjectiveFunction().length;
		int nVariablesNotIntegers = 0;

		double[] constraints = new double[nDecVariables];

		for (int i = 0; i < nDecVariables; i++) {
			constraints[i] = answer[(i + 1)];
			if ((answer[(i + 1)] % 1) != 0) {
				nDecVariables++;
			}
		}		

		if (nVariablesNotIntegers != 0) {
			e = getNewExpression(nVariablesNotIntegers, constraints, e);
			start(e);
		} else if( (e.isObjectiveMax() && answer[0] > otimo ) || (!e.isObjectiveMax() && answer[0] < otimo)){
			otimo = answer[0];
		}		

		return answer;

	}

	public Expression getNewExpression(int nVariablesNotIntegers, double[] constraints, Expression e) throws Exception {
		int [][] newConstraints = new int[(2*nVariablesNotIntegers) + e.getConstraints().length][e.getConstraints()[0].length];
		int [] newConstraintSigns = new int[(2*nVariablesNotIntegers) + e.getConstraints().length]; 
		
		for (int i = 0; i < e.getConstraints().length; i++) {
			for (int j = 0; j < e.getConstraints()[0].length; j++) {
				newConstraints[i][j] = e.getConstraints()[i][j];
			}
			newConstraintSigns[i] = e.getConstraintSigns()[i];
		}
		
		for (int i = 0; i < ( 2 * nVariablesNotIntegers); i+=2) {
			for (int j = 0; j < constraints.length; j++) {
				if ((constraints[(j + 1)] % 1) != 0) {
					newConstraints[(i+e.getConstraints()[0].length)][j] = ((int)Math.floor(constraints[(j + 1)]));
					newConstraintSigns[(i+e.getConstraints()[0].length)] = Expression.CONSTRAINT_SIGN_LT;
					newConstraints[((i+e.getConstraints()[0].length) + 1)][j] = ((int)Math.ceil(constraints[(j + 1)]));
					newConstraintSigns[((i+e.getConstraints()[0].length) + 1)] = Expression.CONSTRAINT_SIGN_GT;
				}
			}
		}		
		
		return new Expression(e.getObjective(), e.getObjectiveFunction(), newConstraints, newConstraintSigns, e.getB());
		
	}

}
