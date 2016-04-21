package main;

import simplex.Expression;
import simplex.Simplex;

public class Main {

	public static void main(String[] args) {

		int objective = Expression.OBJECTIVE_MAX;
		int[] objectiveFunction = {80, 60};
		int[][] constraints = {{4, 6}, {4, 2}, {0, 1}};
		int[] constraintSigns = {Expression.CONSTRAINT_SIGN_GT, Expression.CONSTRAINT_SIGN_LT, Expression.CONSTRAINT_SIGN_LT};
		int[] b = {24, 16, 3};
		try {
			Expression e = new Expression(objective, objectiveFunction, constraints, constraintSigns, b);
			Simplex.getInstance().start(e);
		} catch ( Exception e ) {
			System.out.println("Ooops");
			e.printStackTrace();
		}

	}
}
