import java.util.Set;
/**
 * Construct a Markov model of order /k/ based on an input string.
 * 
 * @author Suyash Vasist 22003936, Zhishen(Mike) Xu 21977643
 * @version 29.5.17
 */
public class MarkovModel
{

    /** Markov model order parameter */
    int k; 
    /** ngram model of order k */
    NgramAnalyser ngram; 
    /** ngram model of order k+1 */
    NgramAnalyser n1gram; 

    /**
     * Construct an order-k Markov model from string s
     * @param k int order of the Markov model
     * @param s String input to be modelled
     */
    public MarkovModel(int k, String s) 
    {
        ngram = new NgramAnalyser(k,s);
        n1gram = new NgramAnalyser(k+1,s);
        this.k = k;
    }

    /**
     * @return order of this Markov model
     */
    public int getK()
    {
        return k;
    }

    /** Estimate the probability of a sequence appearing in the text 
     * using simple estimate of freq seq / frequency front(seq).
     * @param sequence String of length k+1
     * @return double probability of the last letter occuring in the 
     * context of the first ones or 0 if front(seq) does not occur.
     * @throws ArithmeticException when dividing by 0
     * This method also catches the IndexOutOFBoundsException when the 
     * input parameter is an empty string.
     */
    public double simpleEstimate(String sequence) {
        try {
            double freq_seq = n1gram.getNgramFrequency(sequence);
            String frontSeq = sequence.substring(0,sequence.length()-1);
            double freq_frontSeq = ngram.getNgramFrequency(frontSeq);
            if (freq_frontSeq == 0) {
                throw new ArithmeticException("Dividing by 0.");
            }
            return freq_seq/freq_frontSeq;
        }
        catch (IndexOutOfBoundsException e) {
            MatcherController.displayError("Warning: you have entered an empty string. Please try again.");
            return 0;
        }
        catch (ArithmeticException e) {
            MatcherController.displayError("Warning: there are no ngrams corresponding to this sequence.");
            return 0;
        }
        
    }



    /**
     * Calculate the Laplacian probability of string obs given this Markov model
     * @input sequence String of length k+1
     */
    public double laplaceEstimate(String sequence) 
    { 
        double freq_seq = n1gram.getNgramFrequency(sequence);
        String frontSeq = sequence.substring(0,sequence.length()-1);
        double freq_frontSeq = ngram.getNgramFrequency(frontSeq);
        return (freq_seq+1)/(freq_frontSeq+ngram.getAlphabetSize());
    }

    /**
     * @return String representing this Markov model
     */
    public String toString()
    {
        String listString = "K-value: " + Integer.toString(getK()) + "\n" + "Alphabet size: " + Integer.toString(n1gram.getAlphabetSize()) 
        + "\n" + ngram.toString() + "\n" + n1gram.toString();

        return listString;
    }

}
