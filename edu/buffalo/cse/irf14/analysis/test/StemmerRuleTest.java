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
public class StemmerRuleTest extends TFRuleBaseTest {

	@Test
	public final void testRule() {
		try {
			assertArrayEquals(new String[]{"toni","and","oscar"}, 
					runTest(TokenFilterType.STEMMER, "tonies and oscars"));
			assertArrayEquals(new String[]{"decre", "and", "order"}, 
					runTest(TokenFilterType.STEMMER, "decreed and ordered"));
			assertArrayEquals(new String[]{"inflat", "fail", "troubl", "fill"}, 
					runTest(TokenFilterType.STEMMER, "inflated failing troubled filling"));
			assertArrayEquals(new String[]{"happi",  "and", "dizzi"},
					runTest(TokenFilterType.STEMMER, "happy and dizzy"));
			assertArrayEquals(new String[]{"condit", "oper"},  
					runTest(TokenFilterType.STEMMER, "conditional operator"));
			assertArrayEquals(new String[]{"microscop", "replac", "of", "inher", "good"},
					runTest(TokenFilterType.STEMMER, "microscopic replacement of inherent goodness"));
			assertArrayEquals(new String[]{"@goodness", "#gracious", "2getherness"}, 
					runTest(TokenFilterType.STEMMER, "@goodness #gracious 2getherness"));
		} catch (TokenizerException e) {
			fail("Exception thrown when not expected!");
		}
	}

}
