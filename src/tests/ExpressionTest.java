package tests;

import static org.junit.Assert.*;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import simplex.Expression;

public class ExpressionTest {
	
	@Rule
	public final ExpectedException exception = ExpectedException.none();
	
	@Test
	public void throwFunctionObjectiveExceptionAboveMax() throws Exception {
		Expression e;
		
		exception.expect(Exception.class);
		exception.expectMessage("Invalid objective.");
		e = new Expression(Expression.OBJECTIVE_MAX+1, new int[0], new int[0][0], new int[0], new int [0]);	
	}
	
	@Test
	public void throwFunctionObjectiveExceptionBelowMin() throws Exception {
		Expression e;
		
		exception.expect(Exception.class);
		exception.expectMessage("Invalid objective.");
		e = new Expression(Expression.OBJECTIVE_MIN-1, new int[0], new int[0][0], new int[0], new int [0]);	
	}
	
	@Test
	public void throwObjectiveFunctionArraySizeException() throws Exception {
		Expression e;
		
		exception.expect(Exception.class);
		exception.expectMessage("Neither objective function, constratints nor constratint signs length should be 0.");
		e = new Expression(Expression.OBJECTIVE_MAX, new int[0], new int[1][1], new int[1], new int [1]);	
	}
	
	@Test
	public void throwConstraintsArraySizeException() throws Exception {
		Expression e;
		
		exception.expect(Exception.class);
		exception.expectMessage("Neither objective function, constratints nor constratint signs length should be 0.");
		e = new Expression(Expression.OBJECTIVE_MAX, new int[1], new int[0][0], new int[1], new int [1]);	
	}
	
	@Test
	public void throwConstraintSignsArraySizeException() throws Exception {
		Expression e;
		
		exception.expect(Exception.class);
		exception.expectMessage("Neither objective function, constratints nor constratint signs length should be 0.");
		e = new Expression(Expression.OBJECTIVE_MAX, new int[1], new int[1][0], new int[0], new int [1]);	
	}
	
	@Test
	public void throwBArraySizeException() throws Exception {
		Expression e;
		
		exception.expect(Exception.class);
		exception.expectMessage("Neither objective function, constratints nor constratint signs length should be 0.");
		e = new Expression(Expression.OBJECTIVE_MAX, new int[1], new int[1][0], new int[1], new int [0]);	
	}
	
	@Test
	public void throwNumberOfDecisionsVariablesVsConstraintsException() throws Exception {
		Expression e;
		
		exception.expect(Exception.class);
		exception.expectMessage("Number of decision variables or constraints incompatible.");
		e = new Expression(Expression.OBJECTIVE_MAX, new int[2], new int[1][0], new int[1], new int [1]);
	}
	
	@Test
	public void throwNumberOfDecisionsVariablesVsConstraintsException2() throws Exception {
		Expression e;
		
		exception.expect(Exception.class);
		exception.expectMessage("Number of decision variables or constraints incompatible.");
		e = new Expression(Expression.OBJECTIVE_MAX, new int[2], new int[2][1], new int[2], new int [2]);
	}
	
	@Test
	public void throwConstraintsVsConstraintSignsException() throws Exception {
		Expression e;
		
		exception.expect(Exception.class);
		exception.expectMessage("Number of decision variables or constraints incompatible.");
		e = new Expression(Expression.OBJECTIVE_MAX, new int[2], new int[3][2], new int[2], new int [3]);
	}
	
	@Test
	public void throwConstraintsVsBSignsException() throws Exception {
		Expression e;
		
		exception.expect(Exception.class);
		exception.expectMessage("Number of decision variables or constraints incompatible.");
		e = new Expression(Expression.OBJECTIVE_MAX, new int[2], new int[3][2], new int[3], new int [2]);
	}
	
	@Test
	public void throwConstraintSignException() throws Exception {
		Expression e;
		
		exception.expect(Exception.class);
		exception.expectMessage("Invalid constraint sign.");
		int[] constraintSign = {Expression.CONSTRAINT_SIGN_EQ, Expression.CONSTRAINT_SIGN_LT+1, Expression.CONSTRAINT_SIGN_GT};
		e = new Expression(Expression.OBJECTIVE_MAX, new int[2], new int[3][2], constraintSign, new int [3]);
	}
}