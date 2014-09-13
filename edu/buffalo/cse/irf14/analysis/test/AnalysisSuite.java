package edu.buffalo.cse.irf14.analysis.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ CapitalizationRuleTest.class,
				AccentRuleTest.class,
				DateRuleTest.class,
				NumberRuleTest.class,
				SpecialCharRuleTest.class,
				StopWordsRuleTest.class,
				SymbolRuleTest.class})
public class AnalysisSuite {

}
