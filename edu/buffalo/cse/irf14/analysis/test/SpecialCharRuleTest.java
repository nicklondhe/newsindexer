/**
 * 
 */
package edu.buffalo.cse.irf14.analysis.test;

import static org.junit.Assert.*;

import org.junit.Test;

import edu.buffalo.cse.irf14.analysis.TokenFilterType;
import edu.buffalo.cse.irf14.analysis.TokenizerException;

/**
 * @author nikhillo
 *
 */
public class SpecialCharRuleTest extends TFRuleBaseTest {
	
	@Test
	public void testRule() {
			try {
					//special symbols one by one
					assertArrayEquals(new String[]{"destructor", "is", "method"}, 
							runTest(TokenFilterType.SPECIALCHARS, "destructor is ~method()")); //tilda, brackets
					assertArrayEquals(new String[]{"email", "is", "testbuffalo.edu"}, 
							runTest(TokenFilterType.SPECIALCHARS, "email is test@buffalo.edu")); //@
					assertArrayEquals(new String[]{"call", "555-5555"}, 
							runTest(TokenFilterType.SPECIALCHARS, "call #555-5555")); //hash
					assertArrayEquals(new String[]{"total", "is", "5000.00"}, 
							runTest(TokenFilterType.SPECIALCHARS, "total is $5000.00")); //dollar
					assertArrayEquals(new String[]{"discounted", "at", "15"}, 
							runTest(TokenFilterType.SPECIALCHARS, "discounted at 15%")); //percentage
					assertArrayEquals(new String[]{"x2", "xx"}, 
							runTest(TokenFilterType.SPECIALCHARS, "x^2 = x*x")); //crows feet, asterisk and equal to
					assertArrayEquals(new String[]{"proctor", "gamble"}, 
							runTest(TokenFilterType.SPECIALCHARS, "proctor & gamble")); //&
					assertArrayEquals(new String[]{"abc"}, 
							runTest(TokenFilterType.SPECIALCHARS, "a+b-c")); //+, -
					assertArrayEquals(new String[]{"case", "x", "continue"}, 
							runTest(TokenFilterType.SPECIALCHARS, "case x: continue;")); //: ;
					assertArrayEquals(new String[]{"stdin", "cut", "-f1", "sort", "myfile"}, 
							runTest(TokenFilterType.SPECIALCHARS, "stdin < cut -f1 | sort > myfile")); //< > |
					assertArrayEquals(new String[]{"pray", "to"}, 
							runTest(TokenFilterType.SPECIALCHARS, "pray to __/\\__"));
			
			} catch (TokenizerException e) {
				fail("Exception thrown when not expected!");	
			}
	}
}
