
public class InvertedIndexAVL {

    AVLTree<String, TermTF> invertedIndexAVL; 


//O(1)
    public InvertedIndexAVL() {
        invertedIndexAVL = new AVLTree<String, TermTF>();
    }

 
 

   //O(logn)
    public boolean addNew(int docID, String word) {
        word = word.toLowerCase().trim(); 
        if (invertedIndexAVL.empty()) {
            TermTF term = new TermTF();
            term.setWord(word);
            term.addDocID(docID);
            invertedIndexAVL.insert(word, term);
           
            return true;
        } else {
            if (invertedIndexAVL.find(word)) {
                TermTF existingTerm = invertedIndexAVL.retrieve();
                existingTerm.addDocID(docID);
                invertedIndexAVL.update(existingTerm);
               
            }

            TermTF term = new TermTF();
            term.setWord(word);
            term.addDocID(docID);
            invertedIndexAVL.insert(word, term);
            
            return true;
        }
    }


//O(1)
    public int size() {
        return invertedIndexAVL.size();
    }


//O(logn)
    public boolean found(String word) {
        return invertedIndexAVL.find(word.toLowerCase().trim());
    }

   // O(n)
    public void printInvertedIndex() {
        if (invertedIndexAVL == null) {
            System.out.println("AVL is not initialized.");
            return;
        }

        System.out.println("Inverted Index Contents:");
        invertedIndexAVL.Traverse(); 
    }

   // O(n) 
    public void displayDataRecursive() {
        if (this.invertedIndexAVL != null) {
           this.invertedIndexAVL.Traverse();
        } else {
           System.out.println("BST is not initialized");
        }
  
     }
  
 //O(m⋅n)
    public boolean[] searchWithLogicalOperators(String str) {
        str = str.toLowerCase().trim(); 
        System.out.println("Query: " + str);

        if (!str.contains(" or ") && !str.contains(" and ")) {
        
            return getDocsForTerm(str);
        } else if (str.contains(" or ") && str.contains(" and ")) {
        
            String[] ORGroups = str.split(" or ");
            boolean[] result = AND(ORGroups[0]);

            for (int i = 1; i < ORGroups.length; i++) {
                boolean[] tempResult = AND(ORGroups[i]);
                result = combineResults(result, tempResult, "OR");
            }
            return result;
        } else if (str.contains(" and ")) {
           
            return AND(str);
        } else if (str.contains(" or ")) {
         
            return OR(str);
        }
        return new boolean[50]; 
    }

   // O(K⋅n)
    public boolean[] AND(String str) {
        String[] terms = str.split(" and ");
        boolean[] result = getDocsForTerm(terms[0]); // Get docs for the first term
        System.out.println("Initial AND result for '" + terms[0] + "': ");
        printBooleanArray(result);

        for (int i = 1; i < terms.length; i++) {
            boolean[] tempResult = getDocsForTerm(terms[i]); // Get docs for the next term
            System.out.println("AND result for '" + terms[i] + "': ");
            printBooleanArray(tempResult);
            result = combineResults(result, tempResult, "AND"); // Combine results with AND
        }
        return result;
    }

//// O(K⋅n)
    public boolean[] OR(String str) {
        String[] terms = str.split(" or ");
        boolean[] result = getDocsForTerm(terms[0]); // Get docs for the first term
        System.out.println("Initial OR result for '" + terms[0] + "': ");
        printBooleanArray(result);

        for (int i = 1; i < terms.length; i++) {
            boolean[] tempResult = getDocsForTerm(terms[i]); // Get docs for the next term
            System.out.println("OR result for '" + terms[i] + "': ");
            printBooleanArray(tempResult);
            result = combineResults(result, tempResult, "OR"); // Combine results with OR
        }
        return result;
    }

   // O(n)
    private boolean[] combineResults(boolean[] r1, boolean[] r2, String operation) {
        boolean[] combined = new boolean[50]; 
        System.out.println("Combining results with operation: " + operation);

        for (int i = 0; i < combined.length; i++) {
            if (operation.equals("AND")) {
                combined[i] = r1[i] && r2[i]; 
            } else if (operation.equals("OR")) {
                combined[i] = r1[i] || r2[i];
            }
        }

        System.out.print("Combined result: ");
        printBooleanArray(combined);
        return combined;
    }

    //O(n)
    private void printBooleanArray(boolean[] arr) {
        System.out.print("[");
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] ? "true" : "false");
            if (i < arr.length - 1) {
                System.out.print(", ");
            }
        }
        System.out.println("]");
    }

//O(logn)
    private boolean[] getDocsForTerm(String term) {
        boolean[] result = new boolean[50]; 
        System.out.println("Searching for term: " + term);

        if (this.found(term)) {
            result = this.invertedIndexAVL.retrieve().getDocs();
            System.out.print("Docs for term '" + term + "': ");
            printBooleanArray(result);
        } else {
            System.out.println("Term '" + term + "' not found in the inverted index.");
        }
        return result;
    }
}
