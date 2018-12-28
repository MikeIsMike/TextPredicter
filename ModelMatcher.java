import java.util.HashMap;
import java.util.Collection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;
import java.lang.Math;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Iterator;
import java.util.Map;

/**
 * Report the average log likelihood of a test String occuring in a 
 * given Markov model and detail the calculated values behind this statistic.
 * 
 * @author Suyash Vasist 22003936, Zhishen(Mike) Xu 21977643
 * @version 29/05/2017
 */
public class ModelMatcher
{

    /** log likelihoods for a teststring under a given model */
    private HashMap<String,Double> logLikelihoodMap;
    /** summary statistic for this setting */
    private double averageLogLikelihood;  

    /**
     * Constructor to initialise the fields for the log likelihood map for 
     * a test string and a given Markov model and 
     * the average log likelihood summary statistic
     * @param MarkovModel model a given Markov model object
     * @param String teststring
     */
    public ModelMatcher(MarkovModel model, String testString)
    {
        NgramAnalyser ngram = new NgramAnalyser(model.getK()+1, testString);
        logLikelihoodMap = new HashMap<>();
        Set<String> distNgramSet = ngram.getDistinctNgrams();
        for(String distNgram: distNgramSet){
            int ngramFreq = ngram.getNgramFrequency(distNgram);
            double ngramLaplaceLog_Markov = Math.log10(model.laplaceEstimate(distNgram));
            double logLikelihood_Markov = ngramLaplaceLog_Markov * ngramFreq;
            logLikelihoodMap.put(distNgram, logLikelihood_Markov);
        }
        averageLogLikelihood = averageLogLikelihood(logLikelihoodMap, ngram.getNgramCount());
    }

    /** Helper method that calculates the average log likelihood statistic
     * given a HashMap of strings and their Laplace probabilities
     * and the total number of ngrams in the model.
     * 
     * @param logs map of ngram strings and their log likelihood
     * @param ngramCount int number of ngrams in the original test string
     * @return average log likelihood: the total of loglikelihoods 
     *    divided by the ngramCount
     */
    private double averageLogLikelihood(HashMap<String,Double> logs, int ngramCount)
    {
        double avgLogLikelihood = totalLogLikelihood(logs) / ngramCount;
        return avgLogLikelihood;
    }

    /** Helper method to calculate the total log likelihood statistic
     * given a HashMap of strings and their Laplace probabilities
     * and the total number of ngrams in the model.
     * 
     * @param logs map of ngram strings and their log likelihood
     * @return total log likelihood: the sum of loglikelihoods in logs 
     */
    private double totalLogLikelihood(HashMap<String,Double> logs)
    {
        double sum = 0;
        for(HashMap.Entry<String, Double> entry : logs.entrySet()){
            sum = entry.getValue() + sum;
        }
        return sum;
    }

    /**
     * Helper method to order the logLikelihoodMap by values.
     * @param the map which you want to order
     * @returns the map sorted by values in descending order
     */

    private LinkedHashMap<String, Double> sortHashMapByValues(
    HashMap<String, Double> passedMap) {
        List<String> mapKeys = new ArrayList<>(passedMap.keySet());
        List<Double> mapValues = new ArrayList<>(passedMap.values());
        Collections.sort(mapValues);
        Collections.reverse(mapValues);
        Collections.sort(mapKeys);

        LinkedHashMap<String, Double> sortedMap =
            new LinkedHashMap<>();

        Iterator<Double> valueIt = mapValues.iterator();
        while (valueIt.hasNext()) {
            Double val = valueIt.next();
            Iterator<String> keyIt = mapKeys.iterator();

            while (keyIt.hasNext()) {
                String key = keyIt.next();
                Double comp1 = passedMap.get(key);
                Double comp2 = val;

                if (comp1.equals(comp2)) {
                    keyIt.remove();
                    sortedMap.put(key, val);
                    break;
                }
            }
        }
        return sortedMap;
    }

    /**
     * @return the average log likelihood statistic
     */
    public double getAverageLogLikelihood() 
    {
        return averageLogLikelihood;
    }

    /**
     * @return the log likelihood value for a given ngram from the input string
     */
    public double getLogLikelihood(String ngram) 
    {
        return (logLikelihoodMap.get(ngram));
    }

    /**
     * Make a String summarising the log likelihood map and its statistics
     * @return String of ngrams and their loglikeihood differences between the models
     * The likelihood table is ordered from highest to lowest likelihood
     */
    public String toString() 
    {
        HashMap<String, Double> orderedLogLikelihoodMap = sortHashMapByValues(logLikelihoodMap);
        ArrayList<String> ngramsAndLikelihoods = new ArrayList<>();
        for (Map.Entry<String, Double> entry : orderedLogLikelihoodMap.entrySet()) {
            ngramsAndLikelihoods.add(entry.getKey() + " " + entry.getValue());
        }
        String listString = "Average log likelihood: " + Double.toString(getAverageLogLikelihood())
        + "\n" + "Log likelihood probabilities:";
        for (String s : ngramsAndLikelihoods) {
            listString = listString + "\n" + s ;
        }
        System.out.println(listString);
        return listString;
    }

}
