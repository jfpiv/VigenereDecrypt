import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Scanner;

/**
 * This class represents a string of cipher text generated with the Vigenere
 * cipher. This class contains methods for predicting the key length and the
 * key for a given key length.
 *
 * Case is ignored in any cipher text or key passed to this class, but the text
 * is expected to only contain standard English letters (A-Z). Unpredictable
 * behavior may occur for other characters.
 *
 * @author John Pettenger
 * @version 1.0, 02/09/16
 */
public class VigenereCipherText{

  /**
   * String to hold the cipher text.
   */
  private String cipherText;

  /**
   * Creates a VigenreCipherText object with an initial cipher text string.
   *
   * @param cipherText The cipher text
   */
  public VigenereCipherText(String cipherText){
    this.cipherText = cipherText;
  }

  /**
   * Creates a VigenereCipherText object with an empty cipher text string.
   */
  public VigenereCipherText(){
    this("");
  }

  /**
   * Clears the cipher text. That is, sets it to an empty string.
   */
  public void clearClipherText(){
    setCipherText("");
  }

  /**
   * Computes the frequencies of letters in a string using a given "skip".
   *
   * The skip represents how many consecutive characters (minus 1) to ignore
   * after each counted character. For example, with a skip of 5, and start
   * index of 1, the characters at indices 1, 6, 11, ..., 1+5i <= n would be
   * counted.
   *
   * Case is ignored and all letters are assumed to be part of the standard
   * English alphabet (A-Z).
   *
   * @param start The start index
   * @param skip  The skip to use
   * @return A LinkedHashMap mapping each letter to its frequency. The keys
   * are stored in alphabetical order.
   */
  public LinkedHashMap<EnglishLetter, Double> computeFrequencies(
    int start, int skip){

    LinkedHashMap<EnglishLetter, Integer> counts = initializeCountHashMap();
    LinkedHashMap<EnglishLetter, Double> frequencies = new LinkedHashMap<>();

    // Count the relevant letters in str.
    for(int i = start; i < cipherText.length(); i += skip){
      char c = cipherText.charAt(i);
      EnglishLetter letter;
      int count;

      if(EnglishLetter.contains(c)){
        letter = EnglishLetter.valueOf(Character.toString(c).toUpperCase());
        count = counts.get(letter);
        counts.put(letter, ++count);
      }
    }

    // Compute the frequencies of the counted letters.
    for(EnglishLetter letter : counts.keySet()){
      double freq = (double) counts.get(letter) / (double) cipherText.length();
      frequencies.put(letter, freq);
    }

    return frequencies;
  }

  /**
   * This method emulates writing the cipher text on two strips of paper and
   * putting one strip above the other displaced by a specified amount.
   * The number of times a letter on the top strip of paper matches a letter
   * directly below it is counted and this value is returned.
   *
   * @param displacement How much to displace the top strip of paper.
   * @return The number matches as described above.
   */
  private int countMatches(int displacement){
    int matches = 0;

    for(int i = 0; i < cipherText.length() - displacement; ++i){
      int displacedIndex = i + displacement;
      char c1 = cipherText.charAt(i);
      char c2 = cipherText.charAt(displacedIndex);

      if(c1 == c2){
        ++matches;
      }
    }

    return matches;
  }

  /**
   * Attempts to decrypt this cipher text with a given key.
   *
   * @param key The key
   * @return The decrypted message
   */
  public VigenereMessage decrypt(String key){
    StringBuilder message = new StringBuilder();
    VigenereMessage vigenereMessage = new VigenereMessage();

    for(int i = 0; i < cipherText.length(); ++i){
      message.append(
        EnglishLetter.subLetters(
          cipherText.charAt(i), key.charAt(i % key.length())
        )
      );
    }

    vigenereMessage.setMessage(message.toString());

    return vigenereMessage;
  }

  /**
   * Calculates the dot product of two vectors using a given shift for
   * both vectors.
   *
   * The "shift" is the distance to move to the right each of the elements from
   * their starting position, wrapping when needed. For example, with a shift
   * of 2, the vector [1, 2, 3, 4, 5] becomes [4, 5, 1, 2, 3].
   *
   * @param vector1 The first vector
   * @param vector2 The second vector
   * @param shift1  The amount to shift the elements in the first vector
   * @param shift2  The amount to shift the elements in the second vector
   * @return The dot product
   */
  private static double dotProduct(
    Double[] vector1, Double[] vector2, int shift1, int shift2){

    double sum = 0.d;

    for(int i = 0; i < vector1.length; ++i){
      int vector1Index = normalizeArrayIndex(i - shift1, vector1.length);
      int vector2Index = normalizeArrayIndex(i - shift2, vector2.length);

      sum += vector1[vector1Index] * vector2[vector2Index];
    }

    return sum;
  }

  /**
   * Calculates the dot product of two vectors using a given shift for the
   * first vector.
   *
   * The "shift" is the distance to move to the right each of the elements from
   * their starting position, wrapping when needed. For example, with a shift
   * of 2, the vector [1, 2, 3, 4, 5] becomes [4, 5, 1, 2, 3].
   *
   * @param vector1 The first vector
   * @param vector2 The second vector
   * @param shift1  The amount to shift the elements in the first vector
   * @return The dot product
   */
  private static double dotProduct(
    Double[] vector1, Double[] vector2, int shift1){

    return dotProduct(vector1, vector2, shift1, 0);
  }

  /**
   * Returns the cipher text.
   *
   * @return The cipher text
   */
  public String getCipherText(){
    return this.cipherText;
  }

  /**
   * Initializes a LinkedHashMap containing Character-Integer key-value
   * pairs for each of the letters in the English alphabet. All Character
   * keys are upper case. All Integers values are initialized to 0. Iterating
   * through the keys in the resulting LinkedHashMap will be in
   * alphabetical (A-Z) order.
   *
   * This method is meant for use in computeFrequencies().
   *
   * @return The initialized hash map.
   */
  private static LinkedHashMap<EnglishLetter, Integer> initializeCountHashMap(){

    LinkedHashMap<EnglishLetter, Integer> emptyCounts = new LinkedHashMap<>();

    for(EnglishLetter letter : EnglishLetter.values()){
      emptyCounts.put(letter, 0);
    }

    return emptyCounts;
  }

  /**
   * Returns the length of the cipher text.
   * @return The length
   */
  public int length(){
    return cipherText.length();
  }

  /**
   * Normalizes the index for an array based on its length so that it wraps
   * if it is out of bounds. This operation allows the array to be treated as
   * circular.
   *
   * @param index  The index to normalize
   * @param length The length of the array
   * @return The normalized index.
   */
  private static int normalizeArrayIndex(int index, int length){
    return (index % length + length) % length;
  }

  /**
   * Predicts the most likely key for cipher text that was generated with
   * the Vigenere Cipher, given a known key length.
   *
   * This method uses the process given in section 2.3.3 of Introduction to
   * Cryptography (page 23).
   *
   * @param keyLength The known key length
   * @return The key prediction.
   */
  public KeyPrediction predictKey(int keyLength){

    Double[] englishFrequencies = EnglishLetterFrequency.orderedFrequencies();
    KeyPrediction keyPrediction = new KeyPrediction();

    // Find the most likely letter for each position in the key.
    for(int keyIndex = 0; keyIndex < keyLength; ++keyIndex){
      Double[] cipherFrequencies = new Double[EnglishLetter.NUM_LETTERS];
      double maxDotProduct = 0.d;
      int maxDotProductShift = 0;

      // Compute the appropriate letter frequencies for the current key index
      // and store them in englishFrequencies. See this method's documentation
      // for more detailed information.
      computeFrequencies(keyIndex, keyLength)
        .values()
        .toArray(cipherFrequencies);

      // Find the shift that results in the largest dot product of
      // cipherFrequencies and englishFrequencies. This shift will correspond
      // to the most likely letter for the current key index
      // (0 --> A, 1 --> B, ..., 25 --> Z)
      for(int shift = 0; shift < EnglishLetter.NUM_LETTERS; ++shift){
        double dot = dotProduct(englishFrequencies, cipherFrequencies, shift);
        if(dot > maxDotProduct){
          maxDotProduct = dot;
          maxDotProductShift = shift;
        }
      }

      // Append the most likely character to the key prediction.
      keyPrediction.keyPredictionBuilder.append(
        EnglishLetter.indexToLetter(maxDotProductShift).upperCase()
      );

      // Add the dot product to this key prediction's score.
      keyPrediction.score += maxDotProduct;
    }

    return keyPrediction;
  }

  /**
   * Predicts possible key lengths for this cipher text.
   *
   * This method uses the process given in section 2.3.1 of Introduction to
   * Cryptography by Trappe and Washington (page 19).
   *
   * The list of predictions returned is sorted from least likely to most
   * likely.
   *
   * @return The list of KeyLengthPredictions, sorted in ascending order by
   * score
   */
  public ArrayList<KeyLengthPrediction> predictManyKeyLengths(){

    ArrayList<KeyLengthPrediction> predictions = new ArrayList<>();

    // If the cipher text is empty, then we return an empty list of predictions.
    if(cipherText != null && !cipherText.isEmpty()){
      final int MIN_KEY_LENGTH = 1;
      final int MAX_KEY_LENGTH = cipherText.length();

      // Calculate the "score" of each key length (offset). The score
      // represents the number of matching characters by comparing the original
      // strip of paper and the displaced strip of paper.
      for(int offset = MIN_KEY_LENGTH; offset < MAX_KEY_LENGTH; ++offset){
        int numMatches;
        KeyLengthPrediction prediction = new KeyLengthPrediction();

        // Count the number of character matches for this offset
        numMatches = countMatches(offset);

        // Set the length prediction and its score and add it to the list of
        // predictions
        prediction.lengthPrediction = offset;
        prediction.score = numMatches;
        predictions.add(prediction);
      }
    }

    // Sort the predictions in ascending order by "score".
    Collections.sort(predictions);

    return predictions;
  }

  /**
   * Reads cipher text from a Scanner. The read cipher text overwrites any
   * existing cipher text.
   *
   * @param input The input Scanner
   */
  public void readCipherText(Scanner input){
    StringBuilder builder = new StringBuilder();

    while(input.hasNext()){
      builder.append(input.next().toUpperCase());
    }
    input.close();

    cipherText = builder.toString();
  }

  /**
   * Sets the cipher text to a specified string.
   *
   * @param cipherText The cipher text
   */
  public void setCipherText(String cipherText){
    this.cipherText = cipherText;
  }

  /**
   * Returns a formatted representation of the cipher text. The cipher text
   * is split into lines for a maximum of 70 columns per line.
   *
   * @return The formatted cipher text
   */
  public String toString(){
    final int MAX_COLUMNS = 70;

    StringBuilder builder = new StringBuilder();

    for(int beginIndex = 0; beginIndex < cipherText.length();
        beginIndex += MAX_COLUMNS){

      int endIndex = beginIndex + MAX_COLUMNS;
      int trimmedEndIndex = endIndex < cipherText.length()
        ? endIndex : cipherText.length();

      builder.append(cipherText.substring(beginIndex, trimmedEndIndex));
      builder.append(System.lineSeparator());
    }

    return builder.toString();
  }

  /**
   * This nested class represents a single key length prediction.
   * It wraps the actual key length prediction and the "score" for that
   * prediction.
   */
  public static class KeyLengthPrediction
    implements Comparable<KeyLengthPrediction>{

    /**
     * This prediction's score.
     */
    private int score;

    /**
     * The actual length prediction.
     */
    private int lengthPrediction;

    /**
     * Creates a new KeyLengthPrediction with an initial length prediction of 0
     * and score of 0.
     *
     * This constructor is private as this nested class should not be
     * instantiated outside of VigenereCipherText.
     */
    private KeyLengthPrediction(){
      // This nested class should not be instantiated outside of
      // VigenereCipherText.

      this.score = 0;
      this.lengthPrediction = 0;
    }

    /**
     * Returns the score for this prediction.
     *
     * @return The score
     */
    public int score(){
      return this.score;
    }

    /**
     * Returns the length prediction.
     *
     * @return The length prediction
     */
    public final int lengthPrediction(){
      return this.lengthPrediction;
    }

    /**
     * compareTo method for Comparable interface.
     *
     * Compares key length predictions by their score.
     *
     * @param o The key prediction to compare to
     * @return 0 if the predictions have equal scores.
     *       < 0 if this prediction's score is the smallest.
     *       > 0 if this prediction's score is the largest.
     */
    @Override
    public int compareTo(KeyLengthPrediction o){
      return Integer.compare(this.score, o.score);
    }

    /**
     * Returns a string representation of this key length prediction.
     * Contains the actual length prediction and the score.
     *
     * @return This prediction's string representation
     */
    public String toString(){
      return String.format(
        "length %3d | score %2d", lengthPrediction(), score()
      );
    }
  }

  /**
   * This nested class represents a single key prediction.
   * It wraps the actual key prediction and the "score" for that prediction.
   */
  public static class KeyPrediction implements Comparable<KeyPrediction>{

    /**
     * This prediction's score
     */
    private double score;

    /**
     * The actual key prediction.
     */
    private StringBuilder keyPredictionBuilder;

    /**
     * Creates a new KeyPrediction with an empty key prediction
     * and score of 0.0.
     *
     * This constructor is private as this nested class should not be
     * instantiated outside of VigenereCipherText.
     */
    private KeyPrediction(){
      // This nested class should not be instantiated outside of
      // VigenereCipherText.

      this.score = 0.d;
      this.keyPredictionBuilder = new StringBuilder();
    }

    /**
     * Returns this key prediction's score.
     *
     * @return The score
     */
    public double score(){
      return this.score;
    }

    /**
     * Returns the key prediction.
     *
     * @return The prediction
     */
    public String keyPrediction(){
      return this.keyPredictionBuilder.toString();
    }

    /**
     * compareTo method for Comparable interface.
     *
     * Compares key predictions by their score.
     *
     * @param o The key prediction to compare to
     * @return 0 if the predictions have equal scores.
     * < 0 if this prediction's score is the smallest.
     * > 0 if this prediction's score is the largest.
     */
    @Override
    public int compareTo(KeyPrediction o){
      return Double.compare(this.score, o.score);
    }

    /**
     * Returns a string representation of this key prediction.
     * Contains the actual key prediction and the score.
     *
     * @return This key prediction's string representation
     */
    public String toString(){
      return String.format("key %s | score %.5f", keyPrediction(), score());
    }
  }
}
