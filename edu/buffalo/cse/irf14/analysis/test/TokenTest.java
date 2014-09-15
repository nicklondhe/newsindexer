/**
 * 
 */
package edu.buffalo.cse.irf14.analysis.test;

import static org.junit.Assert.*;

import java.lang.reflect.Method;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
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
public class TokenTest {
	private static Method m;
	private TokenStream stream;
	
	@BeforeClass
	public static final void setupBeforeClass() throws Exception {
		m = Token.class.getDeclaredMethod("merge", Token[].class);
		m.setAccessible(true);
	}
	
	@AfterClass
	public static final void teardownAfterClass() {
		m = null;
	}
	
	@Before
	public final void setup() throws TokenizerException {
		Tokenizer tknizer = new Tokenizer();
		stream = tknizer.consume("test string with a lot of tokens that we are just going to keep testing on");
	}
	
	@After
	public final void teardown() {
		while (stream.hasNext()) {
			stream.next();
			stream.remove();
		}
	}
	
	@Test
	public final void testSingleMerge() throws Exception {
		//positive case -- test one merge
		stream.reset();
		Token t1 = stream.next(), t2 = stream.next();
		assertNotNull(t1);
		assertNotNull(t2);
		invokeMerge(t1, t2);
		String str = t1.toString();
		assertNotNull(str);
		assertEquals("test string", str);
	}

	@Test
	public final void testToString() throws Exception {
		//simple test
		assertNotNull(stream);
		stream.reset();
		
		Token t;
		String[] rv = {"test", "string"};
		String str;
		
		for (int i = 0; i < 2; i++) {
			t = stream.next();
			assertNotNull(t);
			str = t.toString();
			
			if (str != null && !str.isEmpty())
				assertEquals(rv[i], t.toString());
		}
	}
	
	@Test
	public final void testMultiMerge() throws Exception {
		//multi test
		assertNotNull(stream);
		stream.reset();
		
		Token tgt = null;
		Token[] tokens = new Token[15];
		int i = 0;
		while (stream.hasNext()) {
			if (tgt == null) {
				tgt = stream.next();
			} else {
				tokens[i++] = stream.next();
			}
			
		}
		
		invokeMerge(tgt, tokens);
		assertNotNull(tgt);
		assertEquals("test string with a lot of tokens that we are just going to keep testing on", tgt.toString());
	}
	
	@Test
	public final void testNegative() throws Exception {
		assertNotNull(stream);
		
		Token t = stream.next();
		assertNotNull(t);
		assertEquals("test", t.toString());
		
		invokeMerge(t, (Token[]) null);
		assertEquals("test", t.toString());
	}
	
	private static void invokeMerge(Token dest, Token... targets) throws Exception {
		Object[] param = {targets};
		m.invoke(dest, param);
	}

}
