package tests;

import static org.junit.Assert.*;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import simplex.Expression;
import simplex.Simplex;

public class SimplexTest {
	
	@Rule
	public final ExpectedException exception = ExpectedException.none();
	
	@Test
	public void results_1() throws Exception {
		int objective = Expression.OBJECTIVE_MAX;
		int[] objectiveFunction = {80, 60};
		int[][] constraints = {{4, 6}, {4, 2}, {0, 1}};
		int[] constraintSigns = {Expression.CONSTRAINT_SIGN_GT, Expression.CONSTRAINT_SIGN_LT, Expression.CONSTRAINT_SIGN_LT};
		int[] b = {24, 16, 3};
		
		Expression e = new Expression(objective, objectiveFunction, constraints, constraintSigns, b);
		Simplex s = Simplex.getInstance();
		
		double[] answer = {-380.0, 2.5, 3.0, 4.0, -20.0, -20.0};
		assertArrayEquals(answer, s.start(e), 1);		
	}
	
	@Test
	public void results_2() throws Exception {
		int objective = Expression.OBJECTIVE_MIN;
		int[] objectiveFunction = {80, 60};
		int[][] constraints = {{4, 6}, {4, 2}, {0, 1}};
		int[] constraintSigns = {Expression.CONSTRAINT_SIGN_GT, Expression.CONSTRAINT_SIGN_LT, Expression.CONSTRAINT_SIGN_LT};
		int[] b = {24, 16, 3};
		
		Expression e = new Expression(objective, objectiveFunction, constraints, constraintSigns, b);
		Simplex s = Simplex.getInstance();
		
		double[] answer = {300, 1.5, 3.0, -20.0, 4.0, -60.0};
		assertArrayEquals(answer, s.start(e), 1);
	}
	
	@Test
	public void results_3() throws Exception {
		exception.expect(Exception.class);
		exception.expectMessage("Unlimited solution.");
		
		int objective = Expression.OBJECTIVE_MIN;
		int[] objectiveFunction = {20, 16, 24, 10, 10, 8, 12, 18, 10};
		int[][] constraints = {	{-1, -1, -1, 0, 0, 0, 0, 0, 0},
								{0, 0, 0, -1, -1, -1, 0, 0, 0},
								{0, 0, 0, 0, 0, 0, -1, -1, -1},
								{1, 0, 0, 1, 0, 0, 1, 0, 0},
								{0, 1, 0, 0, 1, 0, 0, 1, 0},
								{0, 0, 1, 0, 0, 1, 0, 0, 1}
							  };
		int[] constraintSigns = {Expression.CONSTRAINT_SIGN_EQ, Expression.CONSTRAINT_SIGN_EQ, Expression.CONSTRAINT_SIGN_EQ, Expression.CONSTRAINT_SIGN_EQ, Expression.CONSTRAINT_SIGN_EQ, Expression.CONSTRAINT_SIGN_EQ};
		int[] b = {-300, -500, -200, 300, 400, 300};
		
		Expression e = new Expression(objective, objectiveFunction, constraints, constraintSigns, b);
		Simplex.getInstance().start(e);
	}
	
	@Test
	public void results_4() throws Exception {
		exception.expect(Exception.class);
		exception.expectMessage("Permissible solution does not exist.");
		
		int objective = Expression.OBJECTIVE_MIN;
		int[] objectiveFunction = {4, 6, 7, 3, 4, 5, 6, 7, 8, 8, 5, 3};
		int[][] constraints = {	{-1, -1, -1, 0, 0, 0, 0, 0, 0, 0, 0, 0},
								{0, 0, 0, -1, -1, -1, 0, 0, 0, 0, 0, 0},
								{0, 0, 0, 0, 0, 0, -1, -1, -1, 0, 0, 0},
								{1, 0, 0, 1, 0, 0, 1, 0, 0, -1, 0, 0},
								{0, 1, 0, 0, 1, 0, 0, 1, 0, 0, -1, 0},
								{0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, -1},
								{0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1}
							  };
		int[] constraintSigns = {Expression.CONSTRAINT_SIGN_EQ, Expression.CONSTRAINT_SIGN_EQ, Expression.CONSTRAINT_SIGN_EQ, Expression.CONSTRAINT_SIGN_EQ, Expression.CONSTRAINT_SIGN_EQ, Expression.CONSTRAINT_SIGN_EQ, Expression.CONSTRAINT_SIGN_EQ};
		int[] b = {-90, -20, -10, 30, 15, 15, 50};
		
		Expression e = new Expression(objective, objectiveFunction, constraints, constraintSigns, b);
		Simplex s = Simplex.getInstance();
		
		double[] answer = {0, 100, 0, 300.0, 0, 200.0, 0, 300.0, 200, 0, 0, 0, 0, 0, 0, 0};
		assertArrayEquals(answer, s.start(e), 1);
	}
}