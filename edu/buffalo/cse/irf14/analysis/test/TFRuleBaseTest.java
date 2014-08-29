package edu.buffalo.cse.irf14.analysis.test;

import java.util.ArrayList;
import java.util.List;

import edu.buffalo.cse.irf14.analysis.TokenFilter;
import edu.buffalo.cse.irf14.analysis.TokenFilterFactory;
import edu.buffalo.cse.irf14.analysis.TokenFilterType;
import edu.buffalo.cse.irf14.analysis.TokenStream;
import edu.buffalo.cse.irf14.analysis.Tokenizer;
import edu.buffalo.cse.irf14.analysis.TokenizerException;

public class TFRuleBaseTest {
	public final String[] runTest(String str) throws TokenizerException {
		Tokenizer tkizer = new Tokenizer();
		TokenStream tstream = tkizer.consume(str);
		TokenFilterFactory factory = TokenFilterFactory.getInstance();
		TokenFilter filter = factory.getFilterByType(TokenFilterType.SPECIALCHARS, tstream);
		tstream.reset();
		
		while (tstream.hasNext()) {
			filter.increment();
		}
		
		tstream = filter.getStream();
		tstream.reset();
		
		ArrayList<String> list = new ArrayList<String>();
		
		while (tstream.hasNext()) {
			list.add(tstream.next().toString());
		}
		
		String[] rv = new String[list.size()];
		rv = list.toArray(rv);
		return rv;
	}

}
