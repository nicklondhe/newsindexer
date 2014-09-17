/**
 * 
 */
package edu.buffalo.cse.irf14.index.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import edu.buffalo.cse.irf14.analysis.Analyzer;
import edu.buffalo.cse.irf14.analysis.AnalyzerFactory;
import edu.buffalo.cse.irf14.analysis.TokenStream;
import edu.buffalo.cse.irf14.analysis.Tokenizer;
import edu.buffalo.cse.irf14.analysis.TokenizerException;
import edu.buffalo.cse.irf14.document.Document;
import edu.buffalo.cse.irf14.document.FieldNames;
import edu.buffalo.cse.irf14.index.IndexReader;
import edu.buffalo.cse.irf14.index.IndexType;
import edu.buffalo.cse.irf14.index.IndexWriter;
import edu.buffalo.cse.irf14.index.IndexerException;

/**
 * @author nikhillo
 *
 */
public class IndexerTest {
	private IndexReader reader;
	
	@BeforeClass
	public final static void setupIndex() throws IndexerException {
		String[] strs = {"new home sales top sales forecasts", "home sales rise in july", 
				"increase in home sales in july", "july new home sales rise"};
		int len = strs.length;
		Document d;
		String dir = System.getProperty("INDEX.DIR");
		IndexWriter writer = new IndexWriter(dir); //set this beforehand
		for (int i = 0; i < len; i++) {
			d = new Document();
			d.setField(FieldNames.FILEID, "0000"+(i+1));
			d.setField(FieldNames.CONTENT, strs[i]);
			writer.addDocument(d);
		}
		
		writer.close();
	}

	@Before
	public final void before() {
		reader = new IndexReader(System.getProperty("INDEX.DIR"), IndexType.TERM);
	}
	
	/**
	 * Test method for {@link edu.buffalo.cse.irf14.index.IndexReader#getTotalKeyTerms()}.
	 */
	@Test
	public final void testGetTotalKeyTerms() {
		assertEquals(8.0d, reader.getTotalKeyTerms(), 1); //12.5% error tolerated
	}

	/**
	 * Test method for {@link edu.buffalo.cse.irf14.index.IndexReader#getTotalValueTerms()}.
	 */
	@Test
	public final void testGetTotalValueTerms() {
		assertEquals(4.0d, reader.getTotalValueTerms(), 0); //there's just four docs
	}

	/**
	 * Test method for {@link edu.buffalo.cse.irf14.index.IndexReader#getPostings(java.lang.String)}.
	 */
	@Test
	public final void testGetPostings() {
		String query = getAnalyzedTerm("home");
		Map<String, Integer> map = reader.getPostings(query);
		assertNotNull(map);
		assertEquals(4, map.size(), 0);
		assertTrue(map.containsKey("00001"));
		assertEquals(1, map.get("00001"), 0);
		assertTrue(map.containsKey("00002"));
		assertEquals(1, map.get("00002"), 0);
		assertTrue(map.containsKey("00003"));
		assertEquals(1, map.get("00003"), 0);
		assertTrue(map.containsKey("00004"));
		assertEquals(1, map.get("00004"), 0);
		
		query = getAnalyzedTerm("forecasts");
		map = reader.getPostings(query);
		assertEquals(1, map.size(), 0);
		assertTrue(map.containsKey("00001"));
		assertEquals(1, map.get("00001"), 0);
		
		query = getAnalyzedTerm("null");
		map = reader.getPostings(query);
		assertNull(map);
	}

	private static String getAnalyzedTerm(String string) {
		Tokenizer tknizer = new Tokenizer();
		AnalyzerFactory fact = AnalyzerFactory.getInstance();
		try {
			TokenStream stream = tknizer.consume(string);
			Analyzer analyzer = fact.getAnalyzerForField(FieldNames.CONTENT, stream);
			
			while (analyzer.increment()) {
				
			}
			
			stream.reset();
			return stream.next().toString();
		} catch (TokenizerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Test method for {@link edu.buffalo.cse.irf14.index.IndexReader#getTopK(int)}.
	 */
	@Test
	public final void testGetTopK() {
		//positive cases
		List<String> topK = null;
		String[] vals = {"sales", "home", "july"};
		
		for (int i = 0; i < 3; i++) {
			vals[i] = getAnalyzedTerm(vals[i]);
		}
		
		for (int i = 0; i < 3; i++) {
			topK = reader.getTopK(i + 1);
			assertNotNull(topK);
			assertEquals(i + 1, topK.size(), 0);
			
			for (int j = 0; j <=i; j++) {
				assertEquals(vals[j], topK.get(j));
			}
		}
		
		//negative case
		assertNull(reader.getTopK(-1));
		assertNull(reader.getTopK(0));
	}

	/**
	 * Test method for {@link edu.buffalo.cse.irf14.index.IndexReader#query(java.lang.String[])}.
	 */
	@Test
	public final void testQuery() {
		String[] queryTerms = {"sales", "home", "july", "forecasts", "increase"};
		int len = queryTerms.length;
		
		
		for (int i = 0; i <len; i++) {
			queryTerms[i] = getAnalyzedTerm(queryTerms[i]);
		}
		
		/*
		 * Dummy inverted index
		 */
		HashMap<String, Integer>[] invIdx = prepareIndex(queryTerms);
		HashMap<String, Integer> expected;
		
		Map<String, Integer> results;
		String key;
		int value;
		for (int i = 0; i < len; i++) {
			results = reader.query(Arrays.copyOfRange(queryTerms, 0, i + 1));
			expected = (HashMap<String, Integer>) intersect(Arrays.copyOfRange(invIdx, 0, i+1));
			
			if (expected.isEmpty()) {
				assertNull(results);
			} else {
				assertEquals(expected.size(), results.size(), 0);
				
				for (Entry<String, Integer> etr : expected.entrySet()) {
					key = etr.getKey();
					value = etr.getValue();
					
					assertTrue(results.containsKey(key));
					assertEquals(value, results.get(key), 0);
				}
			}
		}
	}

	private Map<String, Integer> intersect(HashMap<String, Integer>...hashMaps) {
		HashMap<String, Integer> basemap = hashMaps[0];
		
		int len = hashMaps.length;
		String key;
		int value;
		for (int i = 1; i < len; i++) {
			basemap.keySet().retainAll(hashMaps[i].keySet());
			
			for (Entry<String, Integer> etr : hashMaps[i].entrySet()) {
				key = etr.getKey();
				value = etr.getValue();
				
				if (basemap.containsKey(key)) {
					basemap.put(key, basemap.get(key) + value);
				}
			}
		}
		
		return basemap;
	}
	
	
	@SuppressWarnings("unchecked")
	private HashMap<String, Integer>[] prepareIndex(
			String[] queryTerms) {
		List<HashMap<String, Integer>> retlist = new ArrayList<HashMap<String,Integer>>();
		String[] docids = {"00001","00002","00003","00004"};
		String[] counts = {"2/1/1/1", "1/1/1/1", "0/1/1/1", "1/0/0/0","0/0/1/0"};

		HashMap<String, Integer> temp = null;
		String[] splits;
		int val;

		for (String cnt : counts) {
			temp = new HashMap<String, Integer>();
			splits = cnt.split("/");
			
			for (int i = 0; i < 4; i++) {
				val = Integer.valueOf(splits[i]);

				if (val > 0)
					temp.put(docids[i], val);
			}
			
			retlist.add(temp);
		}
		
		return (HashMap<String, Integer>[]) retlist.toArray();
	}

}
