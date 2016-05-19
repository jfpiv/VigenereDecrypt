import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * CSCE 557 Program 1
 *
 * This program reads a text file containing cipher text created with the
 * Vigenere cipher and provides the user tools to assist in decrypting the
 * cipher text.
 *
 * After the cipher text is read, the user interacts with the program via a
 * simple command line interface with a menu.
 *
 * The main tools provided are:
 *   - Predicting all possible key lengths and their corresponding likelihoods.
 *   - Predicting the most likely key for a given key length.
 *   - Decrypting the cipher text with a given key.
 *
 * Input:
 *   - Path to the text file containing the cipher text (as a program argument)
 *   - User commands via stdin
 *
 * Output:
 *   - Responses to user commands.
 *
 * @author John Pettenger
 * @version 1.0, 02/09/16
 */
public class Driver{

  /**
   * The index of the cipher text file name in the arguments array.
   */
  private static final int CIPHERTEXT_FILE_NAME_ARG = 0;

  /**
   * The number of expected arguments. We only expect the file name for the
   * cipher text.
   */
  private static final int NUM_EXPECTED_ARGUMENTS = 1;

  /**
   * The exit code returned when an error occurs.
   */
  private static final int FAIL_EXIT_CODE = -1;

  /**
   * Constant ids for the main menu.
   */
  private static final int MAIN_PREDICT_KEYS_LENGTHS = 0;
  private static final int MAIN_PREDICT_KEYS = 1;
  private static final int MAIN_DECRYPT = 2;
  private static final int MAIN_PRINT_MENU = 3;
  private static final int MAIN_GIVE_UP = 4;
  private static final int MAIN_PRINT_CIPHER_TEXT = 5;

  /**
   * The cipher text object. Handles predicting key lengths and actual keys.
   */
  private static VigenereCipherText cipherText = null;

  /**
   * The scanner to read user input from the standard input stream.
   */
  private static Scanner stdin = null;

  /**
   * The main menu object. Handles receiving stdin input from the user.
   */
  private static Menu mainMenu;

  public static void main(String[] args){

    // This flag is set to true when the user chooses to exit
    boolean exit = false;

    // Check arguments
    if(args.length != NUM_EXPECTED_ARGUMENTS){
      System.err.println("Error: Expected file name.");
      System.exit(FAIL_EXIT_CODE);
    }

    // Initialize the ciphertext.
    initCipherText(args[CIPHERTEXT_FILE_NAME_ARG]);

    // Initialize the main menu.
    initMainMenu();

    // Initialize the standard input scanner.
    stdin = new Scanner(System.in);

    // Begin getting and responding to user input.
    while(!exit){
      int menuItemId;
      menuItemId = mainMenu.queryUser(stdin);

      switch(menuItemId){
        case MAIN_PREDICT_KEYS_LENGTHS:
          outputKeyLengthPredictions();
          break;
        case MAIN_PREDICT_KEYS:
          outputKeyPrediction();
          break;
        case MAIN_PRINT_MENU:
          System.out.println(mainMenu);
          break;
        case MAIN_DECRYPT:
          outputDecrypt();
          break;
        case MAIN_GIVE_UP:
          exit = true;
          break;
        case MAIN_PRINT_CIPHER_TEXT:
          System.out.println(cipherText);
          break;
        default:
          System.out.println("Something went wrong.");
          break;
      }
    }

    stdin.close();
  }

  /**
   * Initializes the CipherText object with the ciphertext file.
   *
   * Exits this program with FAIL_EXIT_CODE if the file cannot be found.
   *
   * @param fileName The ciphertext file name
   */
  private static void initCipherText(String fileName){
    File cipherTextFile = null;
    Scanner cipherTextScanner = null;

    cipherTextFile = new File(fileName);
    cipherText = new VigenereCipherText();
    try{
      cipherTextScanner = new Scanner(cipherTextFile);
    } catch(FileNotFoundException e){
      System.err.println("Could not find cipher text file.");
      System.exit(FAIL_EXIT_CODE);
    }
    cipherText.readCipherText(cipherTextScanner);
    cipherTextScanner.close();
  }

  /**
   * Initializes the main menu and populates it with each menu item.
   */
  private static void initMainMenu(){
    mainMenu = new Menu("Main Menu");

    // Build the main menu
    mainMenu.addItem(
      MAIN_PRINT_CIPHER_TEXT,
      "Display cipher text",
      "Displays the cipher text."
    );
    mainMenu.addItem(
      MAIN_PREDICT_KEYS_LENGTHS,
      "Predict key lengths",
      "Ranks possible key lengths using the process given in section 2.3.1" +
        " (page 19) of Introduction to Cryptography by Trappe and Washington."
    );
    mainMenu.addItem(
      MAIN_PREDICT_KEYS,
      "Predict key",
      "Determines the most likely key for a given key length using the" +
        " process given in section 2.3.3 (page 23) of Introduction to" +
        " Cryptography by Trappe and Washington."
    );
    mainMenu.addItem(
      MAIN_DECRYPT,
      "Decrypt cipher text",
      "Attempts to decrypt the cipher text using a given key."
    );
    mainMenu.addItem(
      MAIN_GIVE_UP,
      "Exit",
      "Exits the program."
    );
  }

  /**
   * Asks the user for a key and outputs the corresponding decrypt of the
   * cipher text.
   */
  private static void outputDecrypt(){
    String key;
    VigenereMessage message;

    do{
      System.out.printf("Enter key: ");
      key = stdin.nextLine();

      if(key != null && key.length() > 0){
        message = cipherText.decrypt(key);
        System.out.println(message);
      } else{
        System.out.println("The key cannot be empty.");
      }

    } while(key == null || key.length() <= 0);

  }

  /**
   * Outputs the predicted key lengths.
   */
  private static void outputKeyLengthPredictions(){
    ArrayList<VigenereCipherText.KeyLengthPrediction> predictions;
    predictions = cipherText.predictManyKeyLengths();
    for(VigenereCipherText.KeyLengthPrediction p : predictions){
      System.out.println(p);
    }
  }

  /**
   * Asks the user to input a key length and outputs the predicted key for
   * that length.
   */
  private static void outputKeyPrediction(){
    int keyLength = 0;
    VigenereCipherText.KeyPrediction prediction;
    boolean validLength = false;

    while(!validLength){
      System.out.printf("Enter key length: ");

      try{
        keyLength = Integer.parseInt(stdin.nextLine());
        if(keyLength > 0 && keyLength <= cipherText.length()){
          validLength = true;
        } else{
          validLength = false;
        }
      } catch(NumberFormatException e){
        validLength = false;
      }

      if(!validLength){
        System.out.println("That is not a valid key length.");
      }
    }

    prediction = cipherText.predictKey(keyLength);
    System.out.println(prediction);
  }
}
