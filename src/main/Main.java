package main;

import branch_and_boud.BranchAndBound;
import simplex.Expression;
import simplex.Simplex;

public class Main {

	private static int nVariablesIndex = 0;
	private static int nConstraintsIndex = 1;
	private static int objectiveIndex = 2;
	private static int objectiveFunctionIndex = 3;

	private static void printResult(double[] answer) {
		System.out.print("{");
		System.out.print("\"error\": " + false + ", ");
		System.out.print("\"z\": " + answer[0] + ", ");
		System.out.print("\"answers\": {");
		for (int i = 1; i < answer.length; i += 1) {
			System.out.print("\"x" + i + "\": " + answer[i]);
			if (i != answer.length - 1) {
				System.out.print(", ");
			}
		}
		System.out.print("}");
		System.out.print("}");
	}

	private static void printError(String error, int code) {
		System.out.print("{");
		System.out.print("\"error\": " + true + ", ");
		System.out.print("\"code\": " + code + ", ");
		System.out.print("\"msg\": \"" + error + "\"");
		System.out.print("}");
	}

	private static Expression readArgs(String[] args) throws Exception {
		int nVariables = Integer.parseInt(args[nVariablesIndex]);
		int nConstraints = Integer.parseInt(args[nConstraintsIndex]);
		int objective = Integer.parseInt(args[objectiveIndex]);

		double[] objectiveFunction = new double[nVariables];
		double[][] constraints = new double[nConstraints][nVariables];
		double[] constraintSigns = new double[nConstraints];
		double[] b = new double[nConstraints];

		int argIndex = objectiveFunctionIndex;
		// create objectiveFunction
		for (int i = 0; i < nVariables; i += 1) {
			objectiveFunction[i] = Double.parseDouble(args[argIndex]);
			argIndex += 1;
		}

		// create constraint matrix
		for (int i = 0; i < nConstraints; i += 1) {
			for (int j = 0; j < nVariables; j += 1) {
				constraints[i][j] = Double.parseDouble(args[argIndex]);
				argIndex += 1;
			}
		}

		// create constraint signs
		for (int i = 0; i < nConstraints; i += 1) {
			constraintSigns[i] = Double.parseDouble(args[argIndex]);
			argIndex += 1;
		}

		// create b
		for (int i = 0; i < nConstraints; i += 1) {
			b[i] = Double.parseDouble(args[argIndex]);
			argIndex += 1;
		}

		return new Expression(objective, objectiveFunction, constraints,
				constraintSigns, b);
	}

	/**
	 * Arg 1 - Number of variables Arg 2 - Number of constraints Arg 3 -
	 * Objective function MAX/MIN
	 * 
	 * The next args stands for Objective Function Constraint matrix Constraint
	 * signs B
	 * 
	 * @param args
	 */

	public static void main(String[] args) {

		Expression exp;

		try {
			exp = readArgs(args);
			
			
//			double[] answer = Simplex.getInstance().start(exp);

			double[] answer = BranchAndBound.getInstance().start(exp);
			
			printResult(answer);
			
			

		} catch ( Exception e ) {
			printError(e.getMessage(), Simplex.getInstance().getStatus());
		}

	}
}
