/**
 * 
 */
package edu.buffalo.cse.irf14.analysis.test;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import edu.buffalo.cse.irf14.analysis.Token;
import edu.buffalo.cse.irf14.analysis.TokenStream;
import edu.buffalo.cse.irf14.analysis.Tokenizer;
import edu.buffalo.cse.irf14.analysis.TokenizerException;

/**
 * @author nikhillo
 *
 */
public class TokenizerTest {
	private static Tokenizer spaceTknizer;
	private static Tokenizer delimTknizer;
	
	@BeforeClass
	public static final void beforeClass() {
		spaceTknizer = new Tokenizer();
		delimTknizer = new Tokenizer("_");
	}
	
	@AfterClass
	public static final void afterClass() {
		spaceTknizer = null;
		delimTknizer = null;
	}
	
	/**
	 * Test method for {@link edu.buffalo.cse.irf14.analysis.Tokenizer#consume(java.lang.String)}.
	 */
	@Test
	public final void testNull() {
		try {
			spaceTknizer.consume(null);
			fail("Exception not thrown when expected!");
		} catch (TokenizerException e) {
			assertNotNull(e);
		}
	}
	
	/**
	 * Test method for {@link edu.buffalo.cse.irf14.analysis.Tokenizer#consume(java.lang.String)}.
	 */
	@Test
	public final void testEmpty() {
		try {
			spaceTknizer.consume("");
			fail("Exception not thrown when expected!");
		} catch (TokenizerException e) {
			assertNotNull(e);
		}
	}
	
	/**
	 * Test method for {@link edu.buffalo.cse.irf14.analysis.Tokenizer#consume(java.lang.String)}.
	 */
	@Test
	public final void testOneToken() {
		try {
			TokenStream ts = spaceTknizer.consume("test");
			assertArrayEquals(new String[]{"test"}, serializeStream(ts));
			
			ts = delimTknizer.consume("test");
			assertArrayEquals(new String[]{"test"}, serializeStream(ts));
		} catch (TokenizerException e) {
			fail("Exception thrown when not expected!");
		}
	}
	
	/**
	 * Test method for {@link edu.buffalo.cse.irf14.analysis.Tokenizer#consume(java.lang.String)}.
	 */
	@Test
	public final void testMultipleTokens() {
		try {
			TokenStream ts = spaceTknizer.consume("This is a longer test");
			assertArrayEquals(new String[]{"This", "is", "a", "longer", "test"}, serializeStream(ts));
			
			ts = delimTknizer.consume("This_is_a_longer_test");
			assertArrayEquals(new String[]{"This", "is", "a", "longer", "test"}, serializeStream(ts));
		} catch (TokenizerException e) {
			fail("Exception thrown when not expected!");
		}
	}
	
	/**
	 * Test method for {@link edu.buffalo.cse.irf14.analysis.Tokenizer#consume(java.lang.String)}.
	 */
	@Test
	public final void testMultipleTokensWithMultipleDelims() {
		try {
			TokenStream ts = spaceTknizer.consume("   This    is  a     longer     test    ");
			assertArrayEquals(new String[]{"This", "is", "a", "longer", "test"}, serializeStream(ts));
			
			ts = delimTknizer.consume("__This____is___a__longer____test_____");
			assertArrayEquals(new String[]{"This", "is", "a", "longer", "test"}, serializeStream(ts));
		} catch (TokenizerException e) {
			fail("Exception thrown when not expected!");
		}
	}
	
	private static final String[] serializeStream(TokenStream stream) {
		ArrayList<String> list = new ArrayList<String>();
		Token t;
		String str;
		stream.reset();
		
		while (stream.hasNext()) {
			t = stream.next();
			
			if (t != null) {
				str = t.toString();
				
				if (str != null && !str.isEmpty())
					list.add(str);
			}
		}
		
		String[] rv = new String[list.size()];
		rv = list.toArray(rv);
		return rv;
		
	}

}
