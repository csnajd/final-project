

class BSTInvertedIndex {
    BST<String, TermTF> invertedIndexBST;
    int termCount = 0;
     
    
    //O(1)
        public BSTInvertedIndex() {
            invertedIndexBST = new BST<String, TermTF>();
        }
    
      //O(1)
        public int size() {
            return invertedIndexBST.count;
        }
            // O(n)
        public void displayDataRecursive() {
            if (invertedIndexBST != null) {
                invertedIndexBST.Traverse();  
            } else {
                System.out.println("BST is not initialized");
            }
        }
    
 //O(logn)
        public boolean addNew(int documentID, String term) {
            if (invertedIndexBST.empty()) {
                termCount++;
                TermTF newTerm = new TermTF();
                newTerm.setWord(term);
                newTerm.addDocID(documentID);
                invertedIndexBST.insert(term, newTerm);
                return true;
            } else {
                if (invertedIndexBST.find(term)) {
                    TermTF existingTerm = invertedIndexBST.retrieve();
                    existingTerm.addDocID(documentID);
                    invertedIndexBST.update(existingTerm);
                    return false;
                }
    
                termCount++;
                TermTF newTerm = new TermTF();
                newTerm.setWord(term);
                newTerm.addDocID(documentID);
                invertedIndexBST.insert(term, newTerm);
                return true;
            }
        }
    
       //O(logn)
        public boolean found(String term) {
            return invertedIndexBST.find(term);
        }
    
          // O(n)
        public void printDocument() {
            invertedIndexBST.Traverse();
        }
    
       //O(m⋅n)
        public boolean[] searchWithLogicalOperators(String str) {
            str = str.toLowerCase().trim();
            System.out.println("Query: " + str);
        
            if (!str.contains(" or ") && !str.contains(" and ")) {
                System.out.println("Single term query.");
                return getDocsForTerm(str);
            } else if (str.contains(" or ") && str.contains(" and ")) {
                System.out.println("Mixed query with OR and AND.");
                String[] ORGroups = str.split(" or ");
                boolean[] result = searchWithAND(ORGroups[0]);
                System.out.println("First OR group: " + ORGroups[0]);
        
                for (int i = 1; i < ORGroups.length; i++) {
                    System.out.println("Processing OR group: " + ORGroups[i]);
                    boolean[] tempResult = searchWithAND(ORGroups[i]);
                    result = combineResults(result, tempResult, "OR");
                }
                return result;
            } else if (str.contains(" and ")) {
                System.out.println("AND query.");
                return searchWithAND(str);
            } else {
                System.out.println("OR query.");
                return searchWithOR(str);
            }
        }
        //O(k⋅n)
        public boolean[] searchWithAND(String str) {
            String[] terms = str.split(" and ");
            boolean[] result = getDocsForTerm(terms[0]);
            System.out.print("Initial AND result for '" + terms[0] + "': ");
        
        
            for (int i = 1; i < terms.length; i++) {
                boolean[] tempResult = getDocsForTerm(terms[i]);
                System.out.print("AND result for '" + terms[i] + "': ");
              
                result = combineResults(result, tempResult, "AND");
            }
            return result;
        }
        //O(k⋅n)
        public boolean[] searchWithOR(String str) {
            String[] terms = str.split(" or ");
            boolean[] result = getDocsForTerm(terms[0]);
            System.out.print("Initial OR result for '" + terms[0] + "': ");
          
        
            for (int i = 1; i < terms.length; i++) {
                boolean[] tempResult = getDocsForTerm(terms[i]);
                System.out.print("OR result for '" + terms[i] + "': ");
               
                result = combineResults(result, tempResult, "OR");
            }
            return result;
        }

         //O(logn)
        
        private boolean[] getDocsForTerm(String term) {
            boolean[] result = new boolean[50];
            System.out.println("Searching for term: " + term);
        
            if (this.found(term)) {
                result = invertedIndexBST.retrieve().getDocs();
                System.out.print("Docs for term '" + term + "': ");
               
            } else {
                System.out.println("Term '" + term + "' not found in the inverted index.");
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
            
            return combined;
        }
        
   
      
}  