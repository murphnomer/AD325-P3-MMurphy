import TreePackage.BinaryNode;
import TreePackage.BinarySearchTree;
import TreePackage.BinaryTree;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

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
    } // Runtime: O(1)

    /**
     * Reads in a file and populates the list of possible reserved words
     * @param wordsFile is the name of the file to read
     */
    private void initializeReservedWords(String wordsFile) {
        // create a scanner for reading the file one token at a time
        Scanner in = null;
        // read in the file
        try {
            in = new Scanner(new File(wordsFile));
        } catch (FileNotFoundException e) {
            System.out.println(e);
            return;
        }

        while (in.hasNext()) {
            // the replace statement gets rid of non-breaking spaces
            listOfReservedWords.add(in.next().replaceAll("\\h",""));
        }

    } // Runtime: O(n)

    /**
     * Balances a binary search tree
     * @param tree is the tree we want to balance.
     */
    public void setBalancedBST(BinaryTree tree) {
        // check if the tree is already balanced, if not, rebuild it
        if (!isBalanced(tree.getRootNode())) {
            tree.setRootNode(rebuildBST(tree));
        }
    } // Runtime: O(n)

    /**
     * Outer function to start rebuilding of a balanced tree
     * @param tree is the tree to rebuild.
     * @return is the root node of the rebuilt tree.
     */
    private BinaryNode rebuildBST(BinaryTree tree) {
        // Create a list to store all of the values from the search tree in sorted order
        ArrayList list = new ArrayList();

        // In-order iteration will give us the nodes in sorted order
        Iterator it = tree.getInorderIterator();

        // go through every node
        while (it.hasNext()) {
            // and add it to the list
            list.add(it.next());
        }

        // then build a balanced tree out of the list
        return buildBST(list,0,list.size()-1);
    } // Runtime: O(n)

    /**
     * Builds a binary search tree in a balanced way from a list of values by pulling from the midpoint of each
     * segment for the next level nodes.
     * @param list is the list of values from which to build the tree.
     * @param start is the start of the segment of the list to pull from.
     * @param end is the end of the segment.
     * @return is the root of the constructed tree.
     */
    private BinaryNode<String> buildBST(ArrayList list, int start, int end) {
        // base case: the ends have superimposed
        if (start > end) return null;

        // pick the middle node of the segment
        int mid = (start + end) / 2;

        // create a new node from the value at the center node of the segment
        BinaryNode root = new BinaryNode(list.get(mid));

        // set the children of the new root node to tree constructed from the left and right segments of the
        // current list
        root.setLeftChild(buildBST(list,start,mid - 1));
        root.setRightChild(buildBST(list,mid + 1, end));

        // return the newly costructed root node
        return root;
    } // Runtime: O(n)

    /**
     * Determines whether the BST rooted at a given node is balanced.
     * @param root is the node at the root of the tree to be checked.
     * @return true if the tree is balanced, false if not.
     */
    public boolean isBalanced(BinaryNode root) {
        // base case: root is null, null trees are balanced
        if (root == null) return true;

        // the tree is balanced if the height of its right and left subtrees differ by no more than one,
        // and if both subtrees are also balanced.
        return isBalanced(root.getLeftChild()) && isBalanced(root.getRightChild()) &&
                Math.abs((root.getLeftChild() == null ? 0 : root.getLeftChild().getHeight()) -
                         (root.getRightChild() == null ? 0 : root.getRightChild().getHeight())) < 2;
    } // Runtime: O(n)

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

        // Create a temp string so we can filter out punctuation
        StringBuilder sb = new StringBuilder();

        // Read in the file one item at a time, replacing anything that's not a letter or digit with a space
        while (in.hasNext()) sb.append(in.next().replaceAll("[^\\w]", " ") + " ");

        // Reinitialize the reader to read all of the tokens after removing punctuation
        in = new Scanner(sb.toString());

        // keep going until we reach the end of the temp string
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

            // once we've created the trees, rebalance them
            setBalancedBST(reservedWords);
            setBalancedBST(identifiers);
        }
    } // Runtime: O(n)

    /*
     * Parses a file and adds the user defined identifiers to the appropriate BST
     * @param fileToParse

    /*   This function was combined with the parse function and is no longer necessary
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
    } // Runtime: O(n)
     */
    /**
     * Utility method to print the list of reserved words found in the file
     */
    public void printReservedWords() {
        Iterator it = reservedWords.getInorderIterator();

        while (it.hasNext()) {
            System.out.println(it.next());
        }
    } // Runtime: O(n)

    /**
     * Utility method to print the list of identifiers found in the file
     */
    public void printIdentifiers() {
        Iterator it = identifiers.getInorderIterator();

        while (it.hasNext()) {
            System.out.println(it.next());
        }
    } // Runtime: O(n)


}
