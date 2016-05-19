/**
 * EnglishLetterFrequency enum
 *
 * The EnglishLetterFrequency enum holds the frequencies of each English
 * letter. The frequencies provided here are as specified in Table 2.1
 * (page 17) of Introduction to Cryptography by Trappe and Washington.
 *
 * EnglishLetterFrequency also provides a utility method to generate an ordered
 * (A-Z) array containing only the double frequency values.
 *
 * @author John Pettenger
 * @version 1.0, 02/09/16
 */
public enum EnglishLetterFrequency{

  // Declare the frequencies for all 26 of the English letters.
  A(0.082), B(0.015), C(0.028), D(0.043), E(0.127), F(0.022), G(0.020),
  H(0.061), I(0.070), J(0.002), K(0.008), L(0.040), M(0.024), N(0.067),
  O(0.075), P(0.019), Q(0.001), R(0.060), S(0.063), T(0.091), U(0.028),
  V(0.010), W(0.023), X(0.001), Y(0.020), Z(0.001);

  /**
   * double to hold this letter's frequency.
   */
  private double freq;

  /**
   * Creates a new EnglishLetterFrequency.
   *
   * @param freq This letter's frequency
   */
  EnglishLetterFrequency(double freq){
    this.freq = freq;
  }

  /**
   * Returns the frequency for this letter.
   *
   * @return The frequency
   */
  public double freq(){
    return freq;
  }

  /**
   * Generates an array of frequencies in alphabetical (A-Z) order for each of
   * the letters in the English alphabet.
   *
   * @return The array of frequencies
   */
  public static Double[] orderedFrequencies(){
    EnglishLetterFrequency[] letterFrequencies = values();
    Double[] frequenciesOnly = new Double[letterFrequencies.length];

    for(int i = 0; i < frequenciesOnly.length; ++i){
      frequenciesOnly[i] = letterFrequencies[i].freq();
    }

    return frequenciesOnly;
  }
}
