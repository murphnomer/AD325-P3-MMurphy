import TreePackage.BinaryNode;
import TreePackage.BinarySearchTree;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

public class Parser {

    // BST to hold the list of reserved words found in the program
    BinarySearchTree<String> reservedWords;

    // BST to hold the list of user defined identifiers found in the program
    BinarySearchTree<String> identifiers;

    // master list of what is considered a reserved word
    Set<String> listOfReservedWords;

    /**
     * Default constructor, initializes all of the data structures and calls the initializer for the reserved words
     */
    public Parser() {
        // Create the BSTs to hold the parsed entries
        reservedWords = new BinarySearchTree<>();
        identifiers = new BinarySearchTree<>();

        listOfReservedWords = new HashSet<>();

        // call the method to read the list of reserved words from the file
        this.initializeReservedWords("reservedWords.txt");
    }

    /**
     * Reads in a file and populates the list of possible reserved words
     * @param wordsFile is the name of the file to read
     */
    private void initializeReservedWords(String wordsFile) {
        Scanner in = null;
        try {
            in = new Scanner(new File(wordsFile));
        } catch (FileNotFoundException e) {
            System.out.println(e);
            return;
        }

        while (in.hasNext()) {
            listOfReservedWords.add(in.next().replaceAll("\\h",""));
        }
    }

    /**
     * Balances a binary search tree
     * @param root is the root of the tree we want to balance.
     */
    private void setBalancedBST(BinaryNode root) {

    }

    /**
     * Parses a file, pulling out the reserved words and user defined identifiers found therein.
     * @param fileToParse is the name of the file to examine.
     */
    public void parse(String fileToParse) {
        // variable to stream through the file
        Scanner in = null;
        // represents the current token being considered
        String cur;

        // read in the file one item at a time, if it's not there, print a message and exit
        try {
            in = new Scanner(new File(fileToParse));
        } catch (FileNotFoundException e) {
            System.out.println(e);
            return;
        }

        // keep going until we reach the end of the file
        while (in.hasNext()) {
            // read an item
            cur = in.next();

            // if it's in the list of reserved words, add it to the reservedWords BST
            if (listOfReservedWords.contains(cur)) {
                reservedWords.add(cur);

            // otherwise add it to the identifiers BST
            } else {
                identifiers.add(cur);
            }
        }
    }

    /**
     * Parses a file and adds the user defined identifiers to the appropriate BST
     * @param fileToParse
     */
    private void getIdentifiers(String fileToParse) {
        Scanner in = null;
        String cur;
        try {
            in = new Scanner(new File(fileToParse));
        } catch (FileNotFoundException e) {
            System.out.println(e);
            return;
        }

        while (in.hasNext()) {

        }
    }

    /**
     * Utility method to print the list of reserved words found in the file
     */
    public void printReservedWords() {
        Iterator it = reservedWords.getInorderIterator();

        while (it.hasNext()) {
            System.out.println(it.next());
        }
    }

    /**
     * Utility method to print the list of identifiers found in the file
     */
    public void printIdentifiers() {
        Iterator it = identifiers.getInorderIterator();

        while (it.hasNext()) {
            System.out.println(it.next());
        }
    }


}
