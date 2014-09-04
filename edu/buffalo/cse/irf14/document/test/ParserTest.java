package edu.buffalo.cse.irf14.document.test;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Test;

import edu.buffalo.cse.irf14.document.Document;
import edu.buffalo.cse.irf14.document.FieldNames;
import edu.buffalo.cse.irf14.document.Parser;
import edu.buffalo.cse.irf14.document.ParserException;

public class ParserTest {

	/* For testing purposes we are assuming that the corpus data is in the project directory. 
	 * If you are going to be using these tests locally and your corpus data is not in your project
	 * directory then feel free to change this. */
	private static final String BASE_USER_DIR = 
			System.getProperty("user.dir") + File.separatorChar + "training" ;
	
	private static final String[] titles = {"COMPUTER TERMINAL SYSTEMS <CPML> COMPLETES SALE",
		"DUTCH PLANNING AGENCY FORECASTS LOWER GROWTH", "WESTMIN TO RAISE MYRA FALLS CAPACITY BY 33 PCT",
		"ANALYSTS SAY U.K. BUDGET POINTS TO BASE RATE CUTS"};
	private static final String[] categories = {"acq", "dfl", "gold", "money-supply"};
	private static final String[] places = {"COMMACK, N.Y.", "THE HAGUE", "CALGARY, Alberta",
		"LONDON"};
	private static final String[] fileids = {"0000005", "0001973", "0009544", "0004283"};
	private static final String[] authors = {null, null, null, "Simon Cox"};
	private static final String[] authororgs = {null, null, null, "Reuters"};
	private static final String[] newsdates = {"Feb 26", "March 9", "April 7", "March 17"};
	private static final String[] filenames = {
		BASE_USER_DIR + File.separatorChar + "acq" + File.separatorChar + "0000005",
		BASE_USER_DIR + File.separatorChar + "dfl" + File.separatorChar + "0001973", 
		BASE_USER_DIR + File.separatorChar + "gold" + File.separatorChar + "0009544",
		BASE_USER_DIR + File.separatorChar + "money-supply" + File.separatorChar + "0004283"};
	
	// Document object to test with
	private Document d = null;
	
	@Test
	public void testParseNullFile(){
		// Null file name (Test that the method will throw an exception)
		try {
			 d = Parser.parse(null);
			fail("ParserException not thrown.");
		} catch (ParserException e) {
			assertNotNull(e);
		}
	}
	
	@Test
	public void testParseBlankFile(){
		// Blank file name (Test that the method will throw an exception)
		try {
			 d = Parser.parse("");
			fail("ParserException not thrown.");
		} catch (ParserException e) {
			assertNotNull(e);
		}
	}
	
	@Test
	public void testParseInvalidFile(){
		// Invalid file name (Test that the method will throw an exception)
		try {
			 d = Parser.parse("$%^thisFileNameWillNeverExist.txt");
			fail("ParserException not thrown.");
		} catch (ParserException e) {
			assertNotNull(e);
		}
	}
	
	@Test
	public void testParseValidFileName() {		
		// Valid file name with document testing
		try {
			for(int i = 0; i < filenames.length; i++){
				d = Parser.parse(filenames[i]);
				validateTitle(d, i);
				validateFileId(d, i);
				validateCategory(d, i);
				validateAuthorOrg(d, i);
				validatePlace(d, i);
				validateNewsDate(d, i);
				validateAuthor(d, i);
			}
		} catch (ParserException e) {
			e.printStackTrace(); // So that debugging may be a bit easier.
			fail("A ParserException was thrown when it should not have been thrown.");
		}
	}
	
	private void validateTitle(Document d, int count){
		assertEquals(titles[count],
				d.getField(FieldNames.TITLE)[0]);
	}
	
	private void validateFileId(Document d, int count){
		assertEquals(fileids[count],
				d.getField(FieldNames.FILEID)[0]);
	}
	
	private void validateCategory(Document d, int count){
		assertEquals(categories[count],
				d.getField(FieldNames.CATEGORY)[0]);
	}
	
	private void validateAuthorOrg(Document d, int count){
		String authorOrg = authororgs[count];

		if (authorOrg == null) {
			assertNull(d.getField(FieldNames.AUTHORORG));
		} else {
			assertEquals(authorOrg,
				d.getField(FieldNames.AUTHORORG)[0]);	
		} 
	}
	
	private void validatePlace(Document d, int count){
		assertEquals(places[count],
				d.getField(FieldNames.PLACE)[0]);
	}
	
	private void validateNewsDate(Document d, int count){
		assertEquals(newsdates[count],
				d.getField(FieldNames.NEWSDATE)[0]);
	}
	
	private void validateAuthor(Document d, int count){
		String author = authors[count];

		if (author == null) {
			assertNull(d.getField(FieldNames.AUTHOR));
		} else {
			assertEquals(author,
				d.getField(FieldNames.AUTHOR)[0]);	
		}
		
	}
}
