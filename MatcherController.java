import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.io.*;

/** Create and manipulate Markov models and model matchers for lists of training data 
 * a test data String and generate output from it for convenient display.
 * 
 * @author Suyash Vasist 22003936, Zhishen(Mike) Xu 21977643
 * @version 29/05/2017
 *
 */
public class MatcherController {

    /** list of training data string used to generate markov models */
    ArrayList<String> trainingDataList;
    /** test data to be matched with the models */
    String testData;
    /** order of the markov models*/
    int k;
    /** generated list of markov models for the given training data*/
    ArrayList<MarkovModel> modelList;
    /** generated list of matchers for the given markov models and test data*/
    ArrayList<ModelMatcher> matcherList;

    /** Generate models for analysis
     * @param k order of the markov models to be used
     * @param testData String to check against different models
     * @throws IllegalArgumentException if the input order or data inputs are invalid
     */
    public MatcherController(int k, ArrayList<String> trainingDataList, String testData) 
    {

        if (testData == "" || testData == null || k <= 0 || k > testData.length()) {
            throw new IllegalArgumentException("Unsuitable input.");
        }
        else{
            this.k=k;
            this.testData = testData;
            this.trainingDataList = trainingDataList;
            for(String str: trainingDataList){
                MarkovModel model = new MarkovModel(k,str);
                modelList.add(model);
            }
            for(MarkovModel model: modelList){
                ModelMatcher modelMatcher = new ModelMatcher(model, testData);
                matcherList.add(modelMatcher);
            }
            ModelMatcher bestMatch = getBestMatch(matcherList);
            String reason = explainBestMatch(bestMatch);//TODO
        }
    }

    /** Takes a file and returns the lines in the file as a String.
     * @param name of the file
     * @return a string containing all lines from a file 
     * ff file contents can be got, otherwise null
     * This method also catches the FileNotFound and IO exceptions from the FileIO class.
     */
    private static  String getFileContents(String filename) {
        try {

            ArrayList<String> trainingDataList = FileIO.readFile(filename);
            String listString = "";
            for (String s : trainingDataList) {
                listString = listString + "\n" + s ;
            }
            return listString;
        }
        catch(FileNotFoundException e) {
            displayError("Unable to open " + filename);
            return null;
        }
        catch(IOException e) {
            displayError("A problem was encountered reading " + filename);
            return null;
        }
    }

    /**
     * @param an ArrayList of model matchers
     * @return the ModelMatcher object that has the highest average loglikelihood
     * (where all candidates are trained for the same test string)
     */
    public ModelMatcher getBestMatch(ArrayList<ModelMatcher> candidates) 
    {
        double maxValue = -100;
        int indexBestMatch = 0;
        for (ModelMatcher m : candidates) {
            double mLikelihood = m.getAverageLogLikelihood();
            if (mLikelihood > maxValue) {
                maxValue = mLikelihood;
                indexBestMatch = candidates.indexOf(m);
            }
        }
        return candidates.get(indexBestMatch);
    }

    /**
     * @return the ModelMatcher object that has the highest average loglikelihood in
     * the field variable matcherList
     */
    public ModelMatcher getBestMatchFromList()
    {
        return getBestMatch(matcherList);
    }

    /**
     * @param the ModelMatcher object with the highest log likelihood (typically obtained from
     * the getBestMatch method)
     * @return String an *explanation* of
     * why the test string is the match from the candidate models
     */
    public String explainBestMatch(ModelMatcher best) {
        String explanation = "After getting the Laplace estimates and log likelihoods for the given test " +
            "string, the best Markov model is the one that maximizes the likelihood of the string under the model."
            + "That is, this model has the highest average log likelihood of any other model in the list. For " +
            "clarity, here is a summary of the log likelihood statistics for the best model: \n" +
            best.toString();
        return explanation;
    }

    /** Display an error to the user in a manner appropriate
     * for the interface being used.
     * 
     * @param message
     */
    public static void displayError(String message) {

        // LEAVE THIS METHOD EMPTY
    }

}
