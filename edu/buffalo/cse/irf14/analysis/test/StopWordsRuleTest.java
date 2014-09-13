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
public class StopWordsRuleTest extends TFRuleBaseTest{

	
	@Test
	public void testRule() {
		try {
			assertArrayEquals(new String[]{"test"}, 
					runTest(TokenFilterType.STOPWORD, "this is a test"));
			assertArrayEquals(new String[]{}, 
					runTest(TokenFilterType.STOPWORD, "do not do this"));
			assertArrayEquals(new String[]{"ace", "spades"}, 
					runTest(TokenFilterType.STOPWORD, "ace of spades"));
			assertArrayEquals(new String[]{"valid", "sentence"}, 
					runTest(TokenFilterType.STOPWORD, "valid sentence"));
		} catch (TokenizerException e) {
			fail("Exception thrown when not expected!");
		}
	}
}
