

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * The test class ProjectTest for student test cases.
 * Add all new test cases to this task.
 *
 * @author  Suyash Vasist 22003936, Zhishen(Mike) Xu 21977643
 * @version 29/05/2017
 */
public class ProjectTest
{
    /**
     * Default constructor for test class ProjectTest
     */
    public ProjectTest()
    {
    }

    /**
     * Sets up the test fixture.
     *
     * Called before every test case method.
     */
    @Before
    public void setUp()
    {
    }

    /**
     * Tears down the test fixture.
     *
     * Called after every test case method.
     */
    @After
    public void tearDown()
    {
    }
    
    //TODO add new test cases from here include brief documentation
    
     @Test(timeout=1000)
    public void testSensibleToStringSize() {
        NgramAnalyser ngramAna1 = new NgramAnalyser(3, "abbc");        
        assertTrue(ngramAna1.getAlphabetSize() + 1 <= ngramAna1.countLines()); //TODO replace with test code
    }

   
    @Test(timeout=1000)
    public void testGetDistinctNgrams() {
         assertEquals(0,1); //TODO replace with test code
    }
    
    @Test(timeout=1000)
    public void testLaplaceExample() {
        MarkovModel markovMo1 = new MarkovModel(2, "aabcabaacaac");
        assertEquals(0.5000, markovMo1.laplaceEstimate("aac"), 0.0001);
        assertEquals(0.1667, markovMo1.laplaceEstimate("aaa"), 0.0001);
        assertEquals(0.3333, markovMo1.laplaceEstimate("aab"), 0.0001);

    }
    
    @Test(timeout=1000)
    public void testSimpleExample() {
        MarkovModel markovMo1 = new MarkovModel(2, "aabcabaacaac");
        assertEquals(0.6667, markovMo1.simpleEstimate("aac"), 0.0001);
        assertEquals(0.0000, markovMo1.simpleEstimate("aaa"), 0.0001);
        assertEquals(0.3333, markovMo1.simpleEstimate("aab"), 0.0001);; //TODO replace with test code
    }


    @Test
    public void testTask3example() 
    {
        MarkovModel model = new MarkovModel(2,"aabcabaacaac");
        ModelMatcher match = new ModelMatcher(model,"aabbcaac"); //TODO replace with test code
    }
}
