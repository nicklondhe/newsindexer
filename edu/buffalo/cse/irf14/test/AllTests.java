package edu.buffalo.cse.irf14.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import edu.buffalo.cse.irf14.analysis.test.AnalysisSuite;
import edu.buffalo.cse.irf14.document.test.DocumentSuite;
import edu.buffalo.cse.irf14.index.test.IndexerSuite;

@RunWith(Suite.class)
@SuiteClasses({AnalysisSuite.class, DocumentSuite.class, IndexerSuite.class})
public class AllTests {

}
