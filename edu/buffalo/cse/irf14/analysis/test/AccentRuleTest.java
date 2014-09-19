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
public class AccentRuleTest extends TFRuleBaseTest {
	
	@Test
	public void testRule() {
		try {
			assertArrayEquals(new String[]{"The", "urban", "counterpart", "of", "chateau", "is", "palais"},
					runTest(TokenFilterType.ACCENT, "The urban counterpart of château is palais"));
			assertArrayEquals(new String[]{"The", "expression", "hotel", "particulier", "is", "used", "for", "an", "urban", "'private", "house'"}, 
					runTest(TokenFilterType.ACCENT, "The expression hôtel particulier is used for an urban 'private house'"));
			assertArrayEquals(new String[]{"Resumes", "can", "be", "used", "for", "a", "variety", "of", "reasons"}, 
					runTest(TokenFilterType.ACCENT, "Résumés can be used for a variety of reasons"));
			assertArrayEquals(new String[]{"for", "example", "vis-a-vis", "piece", "de", "resistance", "and", "creme", "brulee"}, 
					runTest(TokenFilterType.ACCENT, "for example vis-à-vis pièce de résistance and crème brûlée"));
			assertArrayEquals(new String[]{"Spanish", "pinguino", "French", "aigue", "or", "aigue"}, 
					runTest(TokenFilterType.ACCENT, "Spanish pingüino French aiguë or aigüe"));
			} catch (TokenizerException e) {
				fail("Exception thrown when not expected");
		}
	}

}