package edu.buffalo.cse.irf14.analysis.test;

import static org.junit.Assert.*;

import org.junit.Test;

import edu.buffalo.cse.irf14.analysis.TokenFilterType;
import edu.buffalo.cse.irf14.analysis.TokenizerException;

public class SymbolRuleTest extends TFRuleBaseTest {
		
		@Test
		public void apostropheTest() {
			try {
				//basic rules
				assertArrayEquals(new String[]{"Finland"}, runTest(TokenFilterType.SYMBOL, "Finland's"));
				assertArrayEquals(new String[]{"Gladys", "house"}, runTest(TokenFilterType.SYMBOL, "Gladys' house"));
	
				// contractions (Common contractions should be replaced with expanded 
				// forms but treated as one token. (e.g. should’ve => should have). 
				assertArrayEquals(new String[]{"is not"}, runTest(TokenFilterType.SYMBOL, "isn't"));
				assertArrayEquals(new String[]{"do not"}, runTest(TokenFilterType.SYMBOL, "don't"));
				assertArrayEquals(new String[]{"will not"}, runTest(TokenFilterType.SYMBOL, "won't"));
				assertArrayEquals(new String[]{"shall not"}, runTest(TokenFilterType.SYMBOL, "shan't"));
				assertArrayEquals(new String[]{"I am"}, runTest(TokenFilterType.SYMBOL, "I'm"));
				assertArrayEquals(new String[]{"we are"}, runTest(TokenFilterType.SYMBOL, "we're"));
				assertArrayEquals(new String[]{"they are"}, runTest(TokenFilterType.SYMBOL, "they're"));
				assertArrayEquals(new String[]{"I have"}, runTest(TokenFilterType.SYMBOL, "I've"));
				assertArrayEquals(new String[]{"Should have"}, runTest(TokenFilterType.SYMBOL, "Should've"));
				assertArrayEquals(new String[]{"They would"}, runTest(TokenFilterType.SYMBOL, "They'd"));
				assertArrayEquals(new String[]{"She will"}, runTest(TokenFilterType.SYMBOL, "She'll"));
				assertArrayEquals(new String[]{"Put", "them"}, runTest(TokenFilterType.SYMBOL, "Put 'em"));
				
				//as single quotes
				assertArrayEquals(new String[]{"quote","test"}, runTest(TokenFilterType.SYMBOL, "'quote test'"));
				assertArrayEquals(new String[]{"f(x)","=","df/dx"},runTest(TokenFilterType.SYMBOL, "f'(x) = df/dx"));
				assertArrayEquals(new String[]{"f(x)","=","df/dx" }, runTest(TokenFilterType.SYMBOL, "f''(x) = df'/dx"));
			} catch (TokenizerException e) {

			}
		}
		
		@Test
		public void punctuationTest() {
			try {
				// Any punctuation marks that possibly mark the end of a sentence 
				// (. ! ?) should be removed. Obviously if the symbol appears within 
				// a token it should be retained (a.out for example).
				
				//single mark at the end, one token
				assertArrayEquals(new String[] {"Remove", "at", "the", "end"}, runTest(TokenFilterType.SYMBOL, "Remove at the end."));
				assertArrayEquals(new String[] {"Remove", "at", "the", "end"}, runTest(TokenFilterType.SYMBOL, "Remove at the end?"));
				assertArrayEquals(new String[] {"Remove", "at", "the", "end"}, runTest(TokenFilterType.SYMBOL, "Remove at the end!"));
				
				//one token with multiple marks
				assertArrayEquals(new String[] {"Remove", "from", "sentence", "1", "Remove", "from", "sentence", "2"},
						runTest(TokenFilterType.SYMBOL, "Remove from sentence 1. Remove from sentence 2."));
				assertArrayEquals(new String[] {"Remove", "from", "sentence", "1", "Remove", "from", "sentence", "2"},
						runTest(TokenFilterType.SYMBOL, "Remove from sentence 1. Remove from sentence 2?"));
				assertArrayEquals(new String[] {"Remove", "from", "sentence", "1", "Remove", "from", "sentence", "2"},
						runTest(TokenFilterType.SYMBOL, "Remove from sentence 1. Remove from sentence 2!"));
				assertArrayEquals(new String[] {"Remove", "from", "sentence", "1", "Remove", "from", "sentence", "2"},
						runTest(TokenFilterType.SYMBOL, "Remove from sentence 1? Remove from sentence 2?"));
				assertArrayEquals(new String[] {"Remove", "from", "sentence", "1", "Remove", "from", "sentence", "2"},
						runTest(TokenFilterType.SYMBOL, "Remove from sentence 1? Remove from sentence 2!"));
				assertArrayEquals(new String[] {"Remove", "from", "sentence", "1", "Remove",  "from", "sentence", "2"},
						runTest(TokenFilterType.SYMBOL, "Remove from sentence 1! Remove from sentence 2!"));
				
				
				//negative cases
				assertArrayEquals(new String[]{"192.168.10.124"}, runTest(TokenFilterType.SYMBOL, "192.168.10.124"));
				assertArrayEquals(new String[]{"!true"}, runTest(TokenFilterType.SYMBOL, "!true"));
				assertArrayEquals(new String[]{"the","search","query","was","em?ty"},
						runTest(TokenFilterType.SYMBOL, "the search query was em?ty"));
				
				//mixed pos, neg, multiple
				assertArrayEquals(new String[]{"Is","your","ip","address","192.168.10.124"}, 
						runTest(TokenFilterType.SYMBOL, "Is your ip address 192.168.10.124?"));
				assertArrayEquals(new String[]{"Your","query","em?ty","returned","0","results"}, 
						runTest(TokenFilterType.SYMBOL, "Your query 'em?ty' returned 0 results!"));
				assertArrayEquals(new String[]{"Say", "what"},
						runTest(TokenFilterType.SYMBOL, "Say what??!!!!??"));
			} catch (TokenizerException e){
				fail("Exception thrown when not expected!");
			}
		}

		@Test
		public void hyphenTest() {
			try {
				// If a hyphen occurs within a alphanumeric
				// token it should be retained (B-52, at least one
				// of the two constituents must have a number). If both are 
				// alphabetic, it should be replaced with a whitespace and
				// retained as a single token (week-day => week day). 
				// Any other hyphens padded by spaces on either or both sides 
				// should be removed.
				
				//whitespace padded hyphens
				assertArrayEquals(new String[]{"hyphen", "test"}, runTest(TokenFilterType.SYMBOL, "hyphen - test"));
				assertArrayEquals(new String[]{"hyphen", "test"}, runTest(TokenFilterType.SYMBOL, "hyphen -- test"));
				
				//alphanumeric
				assertArrayEquals(new String[]{"B-52"}, runTest(TokenFilterType.SYMBOL, "B-52"));
				assertArrayEquals(new String[]{"12-B"}, runTest(TokenFilterType.SYMBOL, "12-B"));
				assertArrayEquals(new String[]{"6-6"}, runTest(TokenFilterType.SYMBOL, "6-6"));
				assertArrayEquals(new String[]{"D-BB3"}, runTest(TokenFilterType.SYMBOL, "D-BB3"));
				assertArrayEquals(new String[]{"week day"}, runTest(TokenFilterType.SYMBOL, "week-day"));
				
				//code style
				assertArrayEquals(new String[]{"c"}, runTest(TokenFilterType.SYMBOL, "c--"));
				assertArrayEquals(new String[]{"c"}, runTest(TokenFilterType.SYMBOL, "--c"));
			}catch (TokenizerException e){
				
			}
		}
}
