package branch_and_boud;

import simplex.Expression;
import simplex.Simplex;

public class BranchAndBound {

	private static BranchAndBound instance;
	private double optimal;
	
	

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
				nVariablesNotIntegers++;
			}
		}

		if (nVariablesNotIntegers != 0) {
			for (int i = 0; i < nDecVariables; i++) {
				if ((constraints[i] % 1) != 0) {
					e = getNewExpressionFloor(constraints[i], i, e);
					start(e);
					e = getNewExpressionCeil(constraints[i], i, e);
					start(e);
				}
			}
		} else if ((e.isObjectiveMax() && answer[0] > optimal) || (!e.isObjectiveMax() && answer[0] < optimal)) {
			optimal = answer[0];
		}

		return answer;

	}

	public Expression getNewExpressionFloor(double constraint, int position, Expression e) throws Exception {
		
		double[][] newConstraints = new double[e.getConstraints().length + 1][e.getConstraints()[0].length];
		double[] newConstraintSigns = new double[e.getConstraints().length + 1]; 
		
		for (int i = 0; i < e.getConstraints().length; i++) {
			for (int j = 0; j < e.getConstraints()[0].length; j++) {
				newConstraints[i][j] = e.getConstraints()[i][j];
			}
			newConstraintSigns[i] = e.getConstraintSigns()[i];
		}
				
		newConstraints[(e.getConstraints()[0].length) + 1][position] = ((int)Math.floor(constraint));
		newConstraintSigns[(e.getConstraints()[0].length) + 1] = Expression.CONSTRAINT_SIGN_LT;				
				
		return new Expression(e.getObjective(), e.getObjectiveFunction(), newConstraints, newConstraintSigns, e.getB());
		
	}

	public Expression getNewExpressionCeil(double constraint, int position, Expression e) throws Exception {
		
		double[][] newConstraints = new double[e.getConstraints().length + 1][e.getConstraints()[0].length];
		double[] newConstraintSigns = new double[e.getConstraints().length + 1]; 
		
		for (int i = 0; i < e.getConstraints().length; i++) {
			for (int j = 0; j < e.getConstraints()[0].length; j++) {
				newConstraints[i][j] = e.getConstraints()[i][j];
			}
			newConstraintSigns[i] = e.getConstraintSigns()[i];
		}		
		
		newConstraints[(e.getConstraints()[0].length + 1)][position] = ((int)Math.ceil(constraint));
		newConstraintSigns[(e.getConstraints()[0].length + 1)] = Expression.CONSTRAINT_SIGN_GT;
		
		return new Expression(e.getObjective(), e.getObjectiveFunction(), newConstraints, newConstraintSigns, e.getB());
		
	}

}
