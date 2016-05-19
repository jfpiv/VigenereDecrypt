import java.util.Scanner;

/**
 * This class represents a string of a message that can be encrypted with the
 * Vigenere cipher.
 *
 * Case is ignored in any message or key passed to this class, but the text
 * is expected to only contain standard English letters (A-Z). Unpredictable
 * behavior may occur for other characters.
 *
 * @author John Pettenger
 * @version 1.0, 02/09/16
 */
public class VigenereMessage{

  /**
   * String to hold the message.
   */
  private String message;

  /**
   * Creates a VigenereMessage object with a specified message.
   *
   * @param message The unencrypted message
   */
  public VigenereMessage(String message){
    this.message = message;
  }

  /**
   * Creates a VigenereMessage object with an empty message.
   */
  public VigenereMessage(){
    this("");
  }

  /**
   * Encrypts this message with a given key.
   *
   * @param key The key
   * @return The resulting cipher text
   */
  public VigenereCipherText encrypt(String key){
    StringBuilder cipherText = new StringBuilder();
    VigenereCipherText vigenereCipherText = new VigenereCipherText();

    for(int i = 0; i < cipherText.length(); ++i){
      cipherText.append(
        EnglishLetter.addLetters(
          cipherText.charAt(i), key.charAt(i % key.length())
        )
      );
    }

    vigenereCipherText.setCipherText(cipherText.toString());

    return vigenereCipherText;
  }

  /**
   * Returns the message.
   *
   * @return The message
   */
  public String getMessage(){
    return this.message;
  }

  /**
   * Returns the length of the message.
   *
   * @return The length
   */
  public int length(){
    return message.length();
  }

  /**
   * Reads a message from a Scanner. The read message overwrites any existing
   * message.
   *
   * @param input The input Scanner.
   */
  public void readMessage(Scanner input){
    StringBuilder builder = new StringBuilder();

    while(input.hasNext()){
      builder.append(input.next());
    }
    input.close();

    this.message = builder.toString();
  }

  /**
   * Sets the message to a specified string.
   *
   * @param message The message
   */
  public void setMessage(String message){
    this.message = message;
  }

  /**
   * Returns a formatted representation of the message. The message is split
   * into lines for a maximum of 70 columns per line.
   *
   * @return The formatted cipher text
   */
  public String toString(){
    final int MAX_COLUMNS = 70;

    StringBuilder builder = new StringBuilder();

    for(int beginIndex = 0; beginIndex < message.length();
        beginIndex += MAX_COLUMNS){
      int endIndex = beginIndex + MAX_COLUMNS;
      int trimmedEndIndex = endIndex < message.length()
        ? endIndex : message.length();

      builder.append(message.substring(beginIndex, trimmedEndIndex));
      builder.append(System.lineSeparator());
    }

    return builder.toString();
  }
}
