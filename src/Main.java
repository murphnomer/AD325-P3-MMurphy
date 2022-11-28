public class Main {
    public static void main(String[] args) {
        // Create a parser object
        Parser p = new Parser();

        // Parse the java program
        p.parse("Palindrome.java");

        // Output the results
        System.out.println("Reserved words found:\n");
        p.printReservedWords();
        System.out.println("\n");

        System.out.println("User defined identifiers found:\n");
        p.printIdentifiers();
    }
}