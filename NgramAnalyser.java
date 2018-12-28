import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import java.util.HashSet;
import java.util.Arrays;

import java.util.TreeMap;
import java.util.Map;

/**
 * Perform n-gram analysis of a string.
 * 
 * Analyses the frequency with which distinct n-grams, of length n,
 * appear in an input string. For the purposes of all analyses of the input
 * string, the final n-1 n-grams appearing in the string should be
 * "filled out" to a length of n characters, by adding
 * a sequence of contiguous characters from the start of the string.
 * e.g. "abbc" includes "bca" and "cab" in its 3-grams
 * 
 * @author Suyash Vasist 22003936, Zhishen(Mike) Xu 21977643
 * @version 29.5.17
 */
public class NgramAnalyser
{
    /** dictionary of all distinct n-grams and their frequencies */
    private HashMap<String,Integer> ngram;

    /** number of distinct characters in the input */
    private int alphabetSize;

    /** n-gram size for this object (new field) */
    private int ngramSize;
    


    /** 
     * Analyse the frequency with which distinct n-grams, of length n,
     * appear in an input string. 
     * n-grams at the end of the string wrap to the front
     * e.g. "abbbbc" includes "bca" and "cab" in its 3-grams
     * @param int n size of n-grams to create
     * @param String inp input string to be modelled
     */
    public NgramAnalyser(int n, String inp) 
    { 
        if (inp == "" || inp == null || n <= 0 || n > inp.length()) {
            throw new IllegalArgumentException("Unsuitable input.");
        }
        else {
            HashSet<Character> distinctLetters = new HashSet<>();
            for (int i = 0; i < inp.length(); i++) {
                distinctLetters.add(inp.charAt(i));
            }
            alphabetSize = distinctLetters.size();

            ArrayList<String> ngramList = new ArrayList<String>();
            for(int i1 = 0; i1 < inp.length(); i1++){//i1 is the index for the character at the start of the string
                String str = "";//there'a new string everytime we loop i1
                for(int i2 = 0; i2 < n; i2++){//i2 helps us to loop n times for a Ngram
                    char c = inp.charAt((i1+i2)%inp.length());//%inp.length() allows wrap around
                    str = str + Character.toString(c);
                }
                ngramList.add(str);
            }
            ngramSize = ngramList.size();

            ngram = new HashMap<String,Integer>();
            for(String s: ngramList){
                ngram.put(s, 0);
            }
            for(String s: ngramList){
                int a = ngram.get(s);
                a++;
                ngram.put(s, a);
            }
        }
    }

    /** 
     * Analyses the input text for n-grams of size 1.
     * @param String inp input string to be modelled
     */
    public NgramAnalyser(String inp) 
    {
        this(1,inp);
    }

    /**
     * @return int the size of the alphabet of a given input
     */
    public int getAlphabetSize() {
        return alphabetSize;
    }

    /**
     * @return the total number of distinct n-grams appearing
     *         in the input text.
     */
    public int getDistinctNgramCount() {
        return ngram.size();
    }

    /** 
     * @return Return a set containing all the distinct n-grams
     *         in the input string.
     */
    public Set<String> getDistinctNgrams() {
        return ngram.keySet();
    }

    /**
     * @return the total number of n-grams appearing
     * in the input text (not requiring them to be distinct)
     */
    public int getNgramCount() {
        return ngramSize;
    }

    /** Return the frequency with which a particular n-gram appears
     * in the text. If it does not appear at all, return 0.
     * 
     * @param ngram The n-gram to get the frequency of
     * @return The frequency with which the n-gram appears.
     */
    public int getNgramFrequency(String ngram) {        
        if (this.ngram.get(ngram) == null) {
            return 0;
        }
        else {
            return this.ngram.get(ngram);
        }
    }
    


    /**
     * Generate a summary of the ngrams for this object.
     * @return a string representation of the n-grams in the input text 
     * comprising the ngram size and then each ngram and its frequency
     * where ngrams are presented in alphabetical order.    
     */
    public String toString()
    {
        TreeMap<String,Integer> orderedNgram = new TreeMap<String,Integer>(ngram);
        ArrayList<String> ngramsAndFrequencies = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : orderedNgram.entrySet()) {
            ngramsAndFrequencies.add(entry.getKey() + " " + entry.getValue());
        }
        String listString = Integer.toString(orderedNgram.firstKey().length());
        for (String s : ngramsAndFrequencies) {
            listString = listString + "\n" + s ;
        }

        return listString;
    }
    
        /**
     * A helper method to help find the number of lines the toString method returns.
     */
    public int countLines(){
        String giles = toString();
        String[] lines = giles.split("\r\n|\r|\n");
        return  lines.length;
    }
}
