package main;

import simplex.Expression;
import simplex.Simplex;

public class Main {

	public static void main(String[] args) {

		Expression exp;
		int objective = Expression.OBJECTIVE_MAX;
		int[] objectiveFunction = {80, 60};
		int[][] constraints = {{4, 6}, {4, 2}, {0, 1}};
		int[] constraintSigns = {Expression.CONSTRAINT_SIGN_GT, Expression.CONSTRAINT_SIGN_LT, Expression.CONSTRAINT_SIGN_LT};
		int[] b = {24, 16, 3};
		try {
			exp = new Expression(objective, objectiveFunction, constraints, constraintSigns, b);
			double[] answer = Simplex.getInstance().start(exp);
			
			System.out.println("f(x)="+answer[0]);
			for ( int i = 1; i < answer.length; i+=1 ) {
				System.out.println("x"+i+"= "+answer[i]);
			}
		} catch ( Exception e ) {
			System.out.println("Ooops");
			e.printStackTrace();
		}

	}
}
