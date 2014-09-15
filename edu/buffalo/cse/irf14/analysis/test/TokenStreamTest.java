/**
 * 
 */
package edu.buffalo.cse.irf14.analysis.test;

import static org.junit.Assert.*;

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
public class TokenStreamTest {
	private static Tokenizer tokenizer;
	private TokenStream stream;
	
	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		tokenizer = new Tokenizer();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		tokenizer = null;
	}

	@Before
	public void setup() throws TokenizerException {
		stream = tokenizer.consume("this is a test");
	}
	
	@After
	public void tearDown() {
		while (stream.hasNext()) {
			stream.next();
			stream.remove();
		}
	}
	
	/**
	 * Test method for {@link edu.buffalo.cse.irf14.analysis.TokenStream#hasNext()}.
	 */
	@Test
	public final void testHasNext() {
		stream.reset();
		
		//should not increment pointer
		int maxVal = (int) (100 * Math.random()) + 10;
		
		for (int i = 0; i < maxVal; i++)
			assertTrue(stream.hasNext());
		
		for (int i = 0; i < 4; i++) {
			assertTrue(stream.hasNext()); //four times for four tokens
			stream.next();
		}
		
		//should be false now
		assertFalse(stream.hasNext());
		
		//stream with no tokens!
		stream.reset();
		for (int i = 0; i < 4; i++) {
			stream.next();
			stream.remove();
		}
		
		assertFalse(stream.hasNext());
	}

	/**
	 * Test method for {@link edu.buffalo.cse.irf14.analysis.TokenStream#next()}.
	 */
	@Test
	public final void testNext() {
		//positive
		stream.reset();
		String[] rv = {"this", "is", "a", "test"};
		
		Token t;
		for (int i = 0; i < 4; i++) {
			t = stream.next();
			assertNotNull(t);
			assertEquals(rv[i], t.toString());
		}
		
		//should be null now
		assertNull(stream.next());
		
		//stream with no tokens!
		stream.reset();
		for (int i = 0; i < 4; i++) {
			stream.next();
			stream.remove();
		}
		
		assertNull(stream.next());
	}

	/**
	 * Test method for {@link edu.buffalo.cse.irf14.analysis.TokenStream#remove()}.
	 */
	@Test
	public final void testRemove() {
		//no-op before next() call
		int maxVal = (int) (100 * Math.random()) + 5;
		
		for (int i = 0; i < maxVal; i++) {
			stream.remove();
			assertTrue(true);
		}
		
		stream.reset();
		//correct removals
		for (int i = 0; i < 4; i++) {
			stream.next();
			stream.remove();
			assertTrue(true); //coz there's no pass() like fail()
		}
		
		//no-op at the end
		maxVal = (int) (100 * Math.random()) + 5;
		
		for (int i = 0; i < maxVal; i++) {
			stream.remove();
			assertTrue(true);
		}
	}

	/**
	 * Test method for {@link edu.buffalo.cse.irf14.analysis.TokenStream#reset()}.
	 */
	@Test
	public final void testReset() {
		//go to end and reset
		while (stream.hasNext()) {
			stream.next();
		}
		
		assertFalse(stream.hasNext());
		stream.reset();
		assertTrue(stream.hasNext());
		Token t = stream.next();
		assertNotNull(t);
		assertEquals("this", t.toString());
		stream.reset();
		//no-op on empty stream
		while (stream.hasNext()) {
			stream.next();
			stream.remove();
		}
		
		assertFalse(stream.hasNext());
		stream.reset();
		assertFalse(stream.hasNext());
	}

	/**
	 * Test method for {@link edu.buffalo.cse.irf14.analysis.TokenStream#append(edu.buffalo.cse.irf14.analysis.TokenStream)}.
	 */
	@Test
	public final void testAppend() throws TokenizerException {
		//positive -- tokens joined
		TokenStream other = tokenizer.consume("I told you so!");
		other.append(stream);
		other.reset();
		
		String[] rv = {"I", "told", "you", "so!", "this", "is", "a", "test"};
		Token t;
		for (int i = 0; i < 8; i++) {
			assertTrue(other.hasNext());
			t = other.next();
			assertNotNull(t);
			assertEquals(rv[i], t.toString());
		}
		
		//test for iterator no-op
		other = tokenizer.consume("I told you so!");
		other.reset();
		other.next();//I
		other.next();//told
		
		other.append(stream);
		t = other.next();
		assertNotNull(t);
		assertEquals("you", t.toString());
		
		//the hasNext() change
		other = tokenizer.consume("I told you so!");
		other.reset();
		
		while (other.hasNext()) {
			other.next();
		}
		
		assertFalse(other.hasNext()); //end of stream reached
		other.append(stream);
		assertTrue(other.hasNext()); //should be "this"
		t = other.next();
		assertNotNull(t);
		assertEquals("this", t.toString());
		
		//exception cases 
		//1. null
		other = tokenizer.consume("I told you so!");
		other.reset();
		
		while (other.hasNext()) {
			other.next();
		}
		
		assertFalse(other.hasNext()); //end of stream reached
		other.append(null); //should be no-op
		assertFalse(other.hasNext());
		
		//2. stream with no tokens
		stream.reset();
		while(stream.hasNext()) {
			stream.next();
			stream.remove();
		}
		assertFalse(stream.hasNext());
		assertFalse(other.hasNext());
		other.append(stream);
		assertFalse(other.hasNext());
	}

	/**
	 * Test method for {@link edu.buffalo.cse.irf14.analysis.TokenStream#getCurrent()}.
	 */
	@Test
	public final void testGetCurrent() {
		//null before next()
		stream.reset();
		assertNull(stream.getCurrent());
		
		//mirrors next
		Token tNext, tCurrent;
		while(stream.hasNext()) {
			tNext = stream.next();
			assertNotNull(tNext);
			tCurrent = stream.getCurrent();
			assertNotNull(tCurrent);
			assertEquals(tNext, tCurrent); //value
			assertTrue(tNext == tCurrent); //reference
		}
		
		//null at end
		assertFalse(stream.hasNext());
		assertNull(stream.next());
		assertNull(stream.getCurrent());
		
		//ensure doesnt move ptr
		stream.reset();
		
		while (stream.hasNext()) {
			tNext = stream.next();
			
			if (stream.hasNext()) {
				for (int i = 0; i < 5; i++) {
					tCurrent = stream.getCurrent();
					assertTrue(stream.hasNext());
					assertEquals(tNext, tCurrent);
				}
			}
		}
		
		//null on removal
		stream.reset();
		
		tNext = stream.next();
		tCurrent = stream.getCurrent();
		assertNotNull(tCurrent);
		assertEquals(tNext, tCurrent);
		
		tNext = stream.next();
		stream.remove();
		tCurrent = stream.getCurrent();
		assertNull(tCurrent);
		assertNotEquals(tNext, tCurrent);
	}

}
