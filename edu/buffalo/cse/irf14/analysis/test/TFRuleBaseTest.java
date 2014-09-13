package edu.buffalo.cse.irf14.analysis.test;

import java.util.ArrayList;

import edu.buffalo.cse.irf14.analysis.Token;
import edu.buffalo.cse.irf14.analysis.TokenFilter;
import edu.buffalo.cse.irf14.analysis.TokenFilterFactory;
import edu.buffalo.cse.irf14.analysis.TokenFilterType;
import edu.buffalo.cse.irf14.analysis.TokenStream;
import edu.buffalo.cse.irf14.analysis.Tokenizer;
import edu.buffalo.cse.irf14.analysis.TokenizerException;

public class TFRuleBaseTest {
	public final String[] runTest(TokenFilterType type, String str) throws TokenizerException {
		Tokenizer tkizer = new Tokenizer();
		TokenStream tstream = tkizer.consume(str);
		TokenFilterFactory factory = TokenFilterFactory.getInstance();
		TokenFilter filter = factory.getFilterByType(type, tstream);
		
		while (filter.increment()) {
			//Do nothing :/
		}
		
		tstream = filter.getStream();
		tstream.reset();
		
		ArrayList<String> list = new ArrayList<String>();
		String s;
		Token t;

		while (tstream.hasNext()) {
			t = tstream.next();

			if (t != null) {
				s = t.toString();
				
				if (s!= null && !s.isEmpty())
					list.add(s);	
			}
		}
		
		String[] rv = new String[list.size()];
		rv = list.toArray(rv);
		tkizer = null;
		tstream = null;
		filter = null;
		list = null;
		return rv;
	}

}
