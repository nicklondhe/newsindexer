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
public class NumberRuleTest extends TFRuleBaseTest {

	@Test
	public void testRule() {
			try {
					assertArrayEquals(
							new String[] { "The", "App", "Store", "offered", "more", "than", "apps", "by", "Apple", "and", "third", "parties." },
							runTest(TokenFilterType.NUMERIC, "The App Store offered more than 775,000 apps by Apple and third parties."));
					assertArrayEquals(
							new String[] { "The", "game", "received", "average", "review", "scores", "of", "%", "and", "/", "for", "the", "Xbox", "version" },
							runTest(TokenFilterType.NUMERIC, "The game received average review scores of 96.92% and 98/100 for the Xbox 360 version"));
					assertArrayEquals(
							new String[] { "The", "number", "is", "the", "sixth", "prime", "number" },
							runTest(TokenFilterType.NUMERIC, "The number 13 is the sixth prime number"));

			} catch (TokenizerException e) {
				fail("Exception thrown when not expected!");
			}
	}

}
