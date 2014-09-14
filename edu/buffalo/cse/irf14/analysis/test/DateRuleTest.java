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
public class DateRuleTest extends TFRuleBaseTest {

	@Test
	public void testRule() {
		
		try{
				assertArrayEquals(
						new String[] { "Vidya", "Balan", "born",
								"19780101", "is", "an", "Indian",
								"actress." },
						runTest(TokenFilterType.DATE, "Vidya Balan born 1 January "
								+ "1978 is an Indian actress."));
				assertArrayEquals(
						new String[] { "President", "Franklin", "D.",
								"Roosevelt", "to", "proclaim", "19411207,",
								"'a", "date", "which", "will", "live",
								"in", "infamy'" },
						runTest(TokenFilterType.DATE, "President Franklin D. Roosevelt "
								+ "to proclaim December 7, "
								+ "1941, 'a date which will "
								+ "live in infamy'"));
				assertArrayEquals(
						new String[] { "The", "Academy", "operated",
								"until", "it", "was", "destroyed", "by",
								"Lucius", "Cornelius", "Sulla", "in",
								"-00840101" },
						runTest(TokenFilterType.DATE, "The Academy operated until "
								+ "it was destroyed by Lucius "
								+ "Cornelius Sulla in 84 BC"));
				assertArrayEquals(
						new String[] { "For", "instance,", "the",
								"19480101", "ABL", "finalist", "Baltimore",
								"Bullets", "moved", "to", "the", "BAA",
								"and", "won", "that", "league's",
								"19480101", "title." },
						runTest(TokenFilterType.DATE, "For instance, the 1948 ABL "
								+ "finalist Baltimore Bullets "
								+ "moved to the BAA and won "
								+ "that league's 1948 title."));
				assertArrayEquals(
						new String[] { "It", "was", "now", "about",
								"10:15:00." },
						runTest(TokenFilterType.DATE, "It was now about 10:15 am."));
				assertArrayEquals(
						new String[] { "Godse", "approached", "Gandhi",
								"on", "19480130", "during", "the",
								"evening", "prayer", "at", "17:15:00." },
						runTest(TokenFilterType.DATE, "Godse approached Gandhi on "
								+ "January 30, 1948 during the "
								+ "evening prayer at 5:15PM."));
				assertArrayEquals(
						new String[] { "Pune", "is", "known", "to", "have",
								"existed", "as", "a", "town", "since",
								"08470101." },
						runTest(TokenFilterType.DATE, "Pune is known to have existed as a town since 847AD."));
				assertArrayEquals(
						new String[] { "The", "20040101", "Indian",
								"Ocean", "earthquake", "was", "an",
								"undersea", "megathrust", "earthquake",
								"that", "occurred", "at",
								"20041226 00:58:53" },
						runTest(TokenFilterType.DATE, "The 2004 Indian Ocean "
								+ "earthquake was an under sea "
								+ "megathrust earthquake that"
								+ " occurred at 00:58:53UTC on "
								+ "Sunday, 26 December 2004"));
				assertArrayEquals(
						new String[] { "19000411", "is", "the", "101st",
								"day", "of", "the", "year", "(102nd", "in",
								"leap", "years)", "in", "the", "Gregorian",
								"calendar." },
						runTest(TokenFilterType.DATE, "April 11 is the 101st day "
								+ "of the year (102nd in "
								+ "leap years) in the Gregorian "
								+ "calendar."));
				assertArrayEquals(
						new String[] { "Apple", "is", "one", "of", "the",
								"world's", "most", "valuable", "publicly",
								"traded", "companies", "in",
								"20110101-20120101." },
						runTest(TokenFilterType.DATE, "Apple is one of the "
								+ "world's most valuable publicly "
								+ "traded companies in 2011-12."));

			} catch (TokenizerException e) {
				fail("Exception thrown when not expected!");
			}
	}
}
