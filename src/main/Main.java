package main;

import simplex.Expression;
import simplex.Simplex;

public class Main {

	private static int nVariablesIndex = 0;
	private static int nConstraintsIndex = 1;
	private static int objectiveIndex = 2;
	private static int objectiveFunctionIndex = 3;

	private static void printResult(double[] answer) {
		System.out.print("{");
		System.out.print("\"error\": "+false+", ");
		System.out.print("\"z\": "+answer[0]+", ");
		System.out.print("\"answers\": {");
		for ( int i = 1; i < answer.length; i+=1 ) {
			System.out.print("\"x"+i+"\": "+ answer[i]);
			if (i != answer.length-1) {
				System.out.print(", ");
			}
		}
		System.out.print("}");
		System.out.print("}");
	}

	private static void printError(String error, int code) {
		System.out.print("{");
		System.out.print("\"error\": "+true+", ");
		System.out.print("\"code\": "+code+", ");
		System.out.print("\"msg\": \""+error+"\"");
		System.out.print("}");
	}

	private static Expression readArgs(String[] args) throws Exception {
		int nVariables = Integer.parseInt(args[nVariablesIndex]);
		int nConstraints = Integer.parseInt(args[nConstraintsIndex]);
		int objective = Integer.parseInt(args[objectiveIndex]);

		int[] objectiveFunction = new int[nVariables];
		int[][] constraints = new int[nConstraints][nVariables];
		int[] constraintSigns = new int[nConstraints];
		int[] b = new int[nConstraints];

		int argIndex = objectiveFunctionIndex;
		// create objectiveFunction
		for( int i = 0; i < nVariables; i+=1 ) {
			objectiveFunction[i] = Integer.parseInt(args[argIndex]);
			argIndex += 1;
		}
		
		// create constraint matrix
		for (int i = 0; i < nConstraints; i+=1 ) {
			for ( int j = 0; j < nVariables; j+=1 ) {
				constraints[i][j] = Integer.parseInt(args[argIndex]); 
				argIndex+=1;
			}
		}
		
		// create constraint signs
		for (int i = 0; i < nConstraints; i+=1 ) {
			constraintSigns[i] = Integer.parseInt(args[argIndex]);
			argIndex+=1;
		}
		
		// create b
		for (int i = 0; i < nConstraints; i+=1 ) {
			b[i] = Integer.parseInt(args[argIndex]);
			argIndex+=1;
		}

		return new Expression(objective, objectiveFunction, constraints, constraintSigns, b);
	}


	/**
	 * Arg 1 - Number of variables
	 * Arg 2 - Number of constraints
	 * Arg 3 - Objective function MAX/MIN
	 *
	 * The next args stands for
	 * Objective Function
	 * Constraint matrix
	 * Constraint signs
	 * B
	 * @param args
     */

	public static void main(String[] args) {

		Expression exp;
		
		try {
			exp = readArgs(args);
			double[] answer = Simplex.getInstance().start(exp);

			printResult(answer);

		} catch ( Exception e ) {
			printError(e.getMessage(), Simplex.getInstance().getStatus());
		}

	}
}
