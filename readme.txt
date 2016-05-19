CSCE 557 Program 1 readme
Last modified: 02/09/16
Author: John Pettenger

This program reads a text file containing cipher text created with the
Vigenere cipher and provides the user tools to assist in decrypting the
cipher text.

After the cipher text is read, the user interacts with the program via a
simple command line interface with a menu.

The main tools provided are:
  - Predicting all possible key lengths and their corresponding likelihoods.
  - Predicting the most likely key for a given key length.
  - Decrypting the cipher text with a given key.

Input:
  - Path to the text file containing the cipher text (as a program argument)
  - User commands via stdin

Output:
  - Responses to user commands.

******************************* How to Run ***********************************

This Java program requires at least JDK 7 to compile. I have provided bash
scripts to compile and run the program. Those scripts are "compile.sh" and
"run.sh", respectively.

The program is initially set up to read ./cipher.txt for the cipher text.
This text file already contains the cipher text to decrypt for this
assignment. The cipher text file to read can be changed in the run.sh script.

To compile:
./compile.sh

To run:
./run.sh

Alternatively, you can run the program in IntelliJ IDEA or another IDE of
your choice. This repo is an IntelliJ project directory, so it is already
set up to be opened there.

When the program starts, you will be presented with the following menu:

~~~ Main Menu ~~~
 0. Display menu
    Displays this menu.
 1. Display cipher text
    Displays the cipher text.
 2. Predict key lengths
    Ranks possible key lengths using the process given in section 2.3.1
    (page 19) of Introduction to Cryptography by Trappe and Washington.
 3. Predict key
    Determines the most likely key for a given key length using the
    process given in section 2.3.3 (page 23) of Introduction to
    Cryptography by Trappe and Washington.
 4. Decrypt cipher text
    Attempts to decrypt the cipher text using a given key.
 5. Exit
    Exits the program.

Choosing option 3 and inputting 5 for the key length will produce the
correct key for this assignment's cipher text. To get the decrypt, choose
option 4 and input the key.