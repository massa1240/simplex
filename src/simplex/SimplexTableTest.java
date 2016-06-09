package simplex;

import org.junit.Test;
import simplex.Expression;

import static org.junit.Assert.*;

public class SimplexTableTest {

	private Expression getDefaultExpression() throws Exception {
		int objective = Expression.OBJECTIVE_MAX;
		double[] objectiveFunction = {80, 60};
		double[][] constraints = {{4, 6}, {4, 2}, {0, 1}};
		double[] constraintSigns = {Expression.CONSTRAINT_SIGN_GT, Expression.CONSTRAINT_SIGN_LT, Expression.CONSTRAINT_SIGN_LT};
		double[] b = {24, 16, 3};

		return new Expression(objective, objectiveFunction, constraints, constraintSigns, b);
	}
	
	@Test
	public void constructor() throws Exception {

		Expression e = getDefaultExpression();
		SimplexTable st = new SimplexTable(e);
		Cell[][] table = st.getSimplexTable();
		assertEquals(4, table.length);
		assertEquals(3, table[0].length);

		// objective function
		assertEquals(0, table[0][0].getTop(), 1);
		assertEquals(80, table[0][1].getTop(), 1);
		assertEquals(60, table[0][2].getTop(), 1);
		// first constraint
		assertEquals(-24, table[1][0].getTop(), 1);
		assertEquals(-4, table[1][1].getTop(), 1);
		assertEquals(-6, table[1][2].getTop(), 1);
		// second constraint
		assertEquals(16, table[2][0].getTop(), 1);
		assertEquals(4, table[2][1].getTop(), 1);
		assertEquals(2, table[2][2].getTop(), 1);
		// third constraint
		assertEquals(3, table[3][0].getTop(), 1);
		assertEquals(0, table[3][1].getTop(), 1);
		assertEquals(1, table[3][2].getTop(), 1);
	}

	@Test
	public void changeAlgorithm() throws Exception {

		Expression e = getDefaultExpression();
		SimplexTable st = new SimplexTable(e);
		st.changeAlgorithm(2, 1);

		Cell[][] table = st.getSimplexTable();
		assertEquals(4, table.length);
		assertEquals(3, table[0].length);

		// objective function
		assertEquals(-320, table[0][0].getTop(), 1);
		assertEquals(-20, table[0][1].getTop(), 1);
		assertEquals(20, table[0][2].getTop(), 1);
		// first constraint
		assertEquals(-8, table[1][0].getTop(), 1);
		assertEquals(1, table[1][1].getTop(), 1);
		assertEquals(-4, table[1][2].getTop(), 1);
		// second constraint
		assertEquals(4, table[2][0].getTop(), 1);
		assertEquals(1/4, table[2][1].getTop(), 1);
		assertEquals(1/2, table[2][2].getTop(), 1);
		// third constraint
		assertEquals(3, table[3][0].getTop(), 1);
		assertEquals(0, table[3][1].getTop(), 1);
		assertEquals(1, table[3][2].getTop(), 1);
	}
	
	@Test
	public void swapBasicWithNonBasicVariable() throws Exception {
		Expression e = getDefaultExpression();
		SimplexTable st = new SimplexTable(e);
		st.changeAlgorithm(2, 1);
		
		int[] basicVariables = st.getBasicVariables();
		int[] nonBasicVariables = st.getNonBasicVariables();
		
		assertEquals(nonBasicVariables[0], 3);//x4
		assertEquals(nonBasicVariables[1], 1);//x2
		
		assertEquals(basicVariables[0], 2);//x3
		assertEquals(basicVariables[1], 0);//x1
		assertEquals(basicVariables[2], 4);//x5
	}
	
	@Test
	public void getCurrentVariablesValues() throws Exception {

		Expression e = getDefaultExpression();
		SimplexTable st = new SimplexTable(e);
		st.changeAlgorithm(2, 1);
		double[] answer = st.getCurrentVariablesValues();
		assertEquals(6, answer.length);
		
		assertEquals(-320, answer[0], 1);//f(x)
		assertEquals(4, answer[1], 1);//x1
		assertEquals(20, answer[2], 1);//x2
		assertEquals(-8, answer[3], 1);//x3
		assertEquals(-20, answer[4], 1);//x4
		assertEquals(3, answer[5], 1);//x5
	}
}