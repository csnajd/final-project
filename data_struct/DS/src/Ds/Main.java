
import java.io.File;
import java.util.Scanner;

@SuppressWarnings("unused")
public class Main {
    private static final Scanner input = new Scanner(System.in);
    private static final Search_Engine searchEngine = new Search_Engine();

    // Display the main menu and return user's choice
    private static int showMainMenu() {
        System.out.println("\n**************************************************************");
        System.out.println("******************** Welcome to Search Engine ****************");
        System.out.println("**************************************************************");
        System.out.println("1. Search by Term");
        System.out.println("2. Search with Boolean Operators");
        System.out.println("3. Search Ranked Results");
        System.out.println("4. View Indexed Documents");
        System.out.println("5. View Indexed Tokens");
        System.out.println("6. Exit");
        System.out.println("**************************************************************");
        System.out.print("Enter your choice: ");
        int choice = input.nextInt();
        input.nextLine(); // Consume newline left-over
        return choice;
    }

    // Print results for a boolean array
    private static void displayBooleanResults(boolean[] results) {
        boolean found = false;
        System.out.println("\n**************************************************************");
        System.out.println("Matching Document IDs:");
        for (int i = 0; i < results.length; i++) {
            if (results[i]) {
                System.out.println("--> Document " + i);
                found = true;
            }
        }
        if (!found) {
            System.out.println("--> No matching documents found.");
        }
        System.out.println("**************************************************************");
    }

    // Menu for searching by term
    private static void termSearchMenu() {
        System.out.println("\n**************************************************************");
        System.out.println("*********************** Search by Term ***********************");
        System.out.println("**************************************************************");
        System.out.println("1. Basic Index");
        System.out.println("2. Inverted Index");
        System.out.println("3. BST-based Index");
        System.out.println("4. AVL-based Index");
        System.out.print("Choose retrieval method: ");
        int choice = input.nextInt();

        System.out.print("Enter term: ");
        String term = input.next().trim().toLowerCase();

        searchEngine.performTermQuery(term, choice);
    }

    // Menu for searching with boolean operators
    private static void booleanSearchMenu() {
        System.out.println("\n**************************************************************");
        System.out.println("**************** Search with Boolean Operators ***************");
        System.out.println("**************************************************************");
        System.out.println("1. Basic Index");
        System.out.println("2. Inverted Index");
        System.out.println("3. BST-based Index");
        System.out.println("4. AVL-based Index");
        System.out.print("Choose retrieval method: ");
        int choice = input.nextInt();

        System.out.print("Enter boolean query (AND/OR): ");
        input.nextLine(); // Consume newline
        String query = input.nextLine().trim();

        searchEngine.performBooleanQuery(query, choice);
    }

    // Menu for retrieving ranked search results
    private static void rankedSearchMenu() {
        System.out.println("\n**************************************************************");
        System.out.println("********************** Search Ranked Results *****************");
        System.out.println("**************************************************************");
        System.out.println("1. Basic Index");
        System.out.println("2. Inverted Index");
        System.out.println("3. BST-based Index");
        System.out.println("4. AVL-based Index");
        System.out.print("Choose retrieval method: ");
        int choice = input.nextInt();

        System.out.print("Enter term: ");
        input.nextLine(); // Consume newline
        String query = input.nextLine().trim();

        System.out.println("\n**************************************************************");
        System.out.println("Results (DocID --> Score):");
        switch (choice) {
            case 1 : searchEngine.performRankedQuery(query, 1); break;
            case 2 : searchEngine.performRankedQuery(query, 2);break;
            case 3 : searchEngine.performRankedQuery(query, 3);break;
            case 4 : searchEngine.performRankedQuery(query, 4);break;
            default : System.out.println("--> Invalid choice.");
        }
        System.out.println("**************************************************************");
    }

    // Display all indexed documents
    private static void viewIndexedDocuments() {
        System.out.println("\n**************************************************************");
        System.out.println("********************** Indexed Documents *********************");
        System.out.println("**************************************************************");
        searchEngine.displayIndexedDocuments();
        System.out.println("**************************************************************");
    }

    // Display all indexed tokens
    private static void viewIndexedTokens() {
        System.out.println("\n**************************************************************");
        System.out.println("*********************** Indexed Tokens ***********************");
        System.out.println("**************************************************************");
        searchEngine.displayIndexedTokens();
        System.out.println("**************************************************************");
    }

    // Main function
    public static void main(String[] args) {
        String stopFile = "C:\\Users\\hp\\Desktop\\final project\\data_struct\\DS\\src\\Ds\\data\\stop.txt";
        String datasetFile = "C:\\Users\\hp\\Desktop\\final project\\data_struct\\DS\\src\\Ds\\data\\dataset.csv";

        // Welcome Message
        System.out.println("**************************************************************");
        System.out.println("************ Welcome to the  Search Engine *******");
        System.out.println("**************************************************************");

        // Load data from files
        System.out.println("Loading data...");
        searchEngine.LoadData(stopFile, datasetFile);
        System.out.println("Data loading complete!");

        // Display menu and process user input
        int choice;
        do {
            choice = showMainMenu();
            switch (choice) {
                case 1:
                    termSearchMenu();
                    break;
                case 2:
                    booleanSearchMenu();
                    break;
                case 3:
                    rankedSearchMenu();
                    break;
                case 4:
                    viewIndexedDocuments();
                    break;
                case 5:
                    viewIndexedTokens();
                    break;
                case 6:
                    System.out.print("\nAre you sure you want to exit? (yes/no): ");
                    String confirmExit = input.nextLine().trim().toLowerCase();
                    if (confirmExit.equals("yes")) {
                        System.out.println("\n**************************************************************");
                        System.out.println("************ Thank you for using the Search Engine! **********");
                        System.out.println("**************************************************************");
                        return; // Exit the program
                    } else {
                        System.out.println("Returning to the main menu...");
                    }
                    break;
                default:
                    System.out.println("--> Invalid choice. Please try again.");
                }
            } while (true);
        }
    }