/**
 * EnglishLetter enum
 *
 * The EnglishLetter enum holds the lowercase and uppercase character
 * representations for each of the English letters.
 *
 * EnglishLetter also provides various utility methods for working with the
 * Vigenere cipher, such as adding and subtracting letters, getting the letter
 * at a specific index in the alphabet, and vice-versa.
 *
 * Letters are indexed 0 to 25: 0 --> A, 1 --> B, ..., 25 --> Z.
 *
 * @author John Pettenger
 * @version 1.0, 02/09/16
 */
public enum EnglishLetter{

  // Declare all 26 of the english letters.
  A('a', 'A'), B('b', 'B'), C('c', 'C'), D('d', 'D'), E('e', 'E'),
  F('f', 'F'), G('g', 'G'), H('h', 'H'), I('i', 'I'), J('j', 'J'),
  K('k', 'K'), L('l', 'L'), M('m', 'M'), N('n', 'N'), O('o', 'O'),
  P('p', 'P'), Q('q', 'Q'), R('r', 'R'), S('s', 'S'), T('t', 'T'),
  U('u', 'U'), V('v', 'V'), W('w', 'W'), X('x', 'X'), Y('y', 'Y'),
  Z('z', 'Z');

  /**
   * The lower case character representation of this English letter.
   */
  private char lowerCase;

  /**
   * The upper case character representation of this English letter.
   */
  private char upperCase;

  /**
   * The number of letters (single case) in the English alphabet.
   */
  public static final int NUM_LETTERS = values().length;

  /**
   * The integer returned when a letter-to-index conversion fails.
   */
  public static final int UNKNOWN_INDEX = Integer.MIN_VALUE;

  /**
   * The character returned when an index-to-letter conversion fails.
   */
  public static final char UNKNOWN_LETTER = '?';

  /**
   * Creates a new EnglishLetter
   *
   * @param lowerCase The letter's lowercase character
   * @param upperCase The letter's uppercase character
   */
  EnglishLetter(char lowerCase, char upperCase){
    this.lowerCase = lowerCase;
    this.upperCase = upperCase;
  }

  /**
   * Returns the lowercase character representation of this letter.
   *
   * @return The lowercase character
   */
  public char lowerCase(){
    return this.lowerCase;
  }

  /**
   * Returns the uppercase character representation of this letter.
   *
   * @return The uppercase character
   */
  public char upperCase(){
    return this.upperCase;
  }

  /**
   * Returns the index of a specified letter.
   *
   * @param letter The letter
   * @return The index
   */
  public static int letterToIndex(EnglishLetter letter){
    return letter.ordinal();
  }

  /**
   * Returns the index of a specified character representation of a letter.
   *
   * @param letter The character representation of the letter, lower or
   *               uppercase
   * @return The index of the of the letter if the character is a valid English
   *         letter.
   *         EnglishLetter.UNKNOWN_INDEX if the character is not an English
   *         letter.
   */
  public static int letterToIndex(char letter){
    int index;
    try{
      index = valueOf(Character.toString(letter).toUpperCase()).ordinal();
    } catch(IllegalArgumentException e){
      index = UNKNOWN_INDEX;
    }

    return index;
  }

  /**
   * Returns the letter at a specified index.
   *
   * @param index The index of the letter
   * @return The letter if the index is valid
   *         null if the index is UNKNOWN_INDEX
   */
  public static EnglishLetter indexToLetter(int index){
    if(index != UNKNOWN_INDEX){
      return values()[normalizeIndex(index)];
    } else{
      return null;
    }
  }

  /**
   * Normalizes an english letter index so that it wraps if it is out of
   * bounds.
   *
   * Examples: normalizeIndex(14) = 14
   *           normalizeIndex(27) = 2
   *           normalizeIndex(-2) = 23
   *
   * @param i The index to normalize.
   * @return The normalized index.
   */
  public static int normalizeIndex(int i){
    return (i % NUM_LETTERS + NUM_LETTERS) % NUM_LETTERS;
  }

  /**
   * Adds two character letters. If successful, the returned character is
   * always uppercase.
   *
   * @param a The character representation of the first letter.
   * @param b The character representation of the second letter.
   * @return The addition result (a + b) if a and b are valid English
   *         letters.
   *         UNKNOWN_LETTER if a or b is not a valid English letter.
   */
  public static char addLetters(char a, char b){
    int aInt = letterToIndex(a);
    int bInt = letterToIndex(b);
    char letterChar;

    if(aInt != UNKNOWN_INDEX && bInt != UNKNOWN_INDEX){
      letterChar = indexToLetter(aInt + bInt).upperCase();
    } else{
      letterChar = UNKNOWN_LETTER;
    }

    return letterChar;
  }

  /**
   * Subtracts two characters letters. If successful, the returned character is
   * always uppercase.
   *
   * @param a The character representation of the first letter.
   * @param b The character representation of the second letter.
   * @return The subtraction result (a - b) if a and b are valid English
   *         letters.
   *         UNKNOWN_LETTER if a or b is not a valid English letter.
   */
  public static char subLetters(char a, char b){
    int aInt = letterToIndex(a);
    int bInt = letterToIndex(b);
    char letterChar;

    if(aInt != UNKNOWN_INDEX && bInt != UNKNOWN_INDEX){
      letterChar = indexToLetter(aInt - bInt).upperCase();
    } else{
      letterChar = UNKNOWN_LETTER;
    }

    return letterChar;
  }

  /**
   * Checks if a specified character is a valid English letter.
   *
   * @param c The character to check
   * @return true if the character is valid
   *         false otherwise
   */
  public static boolean contains(char c){
    return letterToIndex(c) != UNKNOWN_INDEX;
  }

  /**
   * Returns a string containing the lowercase and uppercase character of this
   * letter.
   *
   * Example for letter A:
   * ['a', 'A']
   *
   * @return The string representation of this letter
   */
  public String toString(){
    return String.format("['%s', '%s']", lowerCase, upperCase);
  }
}
