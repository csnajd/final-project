



public class InvertedIndex {
    LinkedList<TermTF> invertedIndex;


    public InvertedIndex() {
        invertedIndex = new LinkedList<TermTF>();
    }


    public int size() {
        return invertedIndex.size();
    }
    public TermTF retrieve() {
        return invertedIndex.retrieve();
    }
    
  
    public boolean addNew(int docID, String word) {
        if (invertedIndex.empty()) {
            TermTF newTerm = new TermTF();
            newTerm.setWord(word);
            newTerm.addDocID(docID);
            invertedIndex.insert(newTerm);
            return true;
        } else {
            invertedIndex.findFirst();
            while (!invertedIndex.last()) {
                if (invertedIndex.retrieve().getWord().equals(word)) {
                    TermTF existingTerm = invertedIndex.retrieve();
                    existingTerm.addDocID(docID);
                    invertedIndex.update(existingTerm);
                    return false;
                }
                invertedIndex.findNext();
            }

            if (invertedIndex.retrieve().getWord().equals(word)) {
                TermTF existingTerm = invertedIndex.retrieve();
                existingTerm.addDocID(docID);
                invertedIndex.update(existingTerm);
                return false;
            } else {
                TermTF newTerm = new TermTF();
                newTerm.setWord(word);
                newTerm.addDocID(docID);
                invertedIndex.insert(newTerm);
            }
            return true;
        }
    }

 
    public boolean found(String word) {
        if (invertedIndex.empty()) {
            System.out.println("Inverted index is empty.");
            return false;
        }
    
        invertedIndex.findFirst();
        for (int i = 0; i < invertedIndex.size(); i++) {
            String indexedWord = invertedIndex.retrieve().getWord();
            if (indexedWord.equals(word)) {
                return true;
            }
            invertedIndex.findNext();
        }

        return false;
    }
    public boolean[] searchWithLogicalOperators(String str) {
  
        str = str.toLowerCase().trim();

     
        if (!str.contains(" or ") && !str.contains(" and ")) {
            return handleSingleTerm(str);
        }

  
        if (str.contains(" or ") && str.contains(" and ")) {
            String[] AND_ORs = str.split(" or ");
            boolean[] result = AND_Function(AND_ORs[0]);

            for (int i = 1; i < AND_ORs.length; i++) {
                boolean[] tempResult = AND_Function(AND_ORs[i]);
                for (int j = 0; j < result.length; j++) {
                    result[j] = result[j] || tempResult[j];
                }
            }
            return result;
        }

      
        if (str.contains(" and ")) {
            return AND_Function(str);
        }

 
        return OR_Function(str);
    }

    public boolean[] AND_Function(String str) {
        String[] ANDs = str.split(" and ");
        boolean[] result = new boolean[50];

        if (this.found(ANDs[0].toLowerCase().trim())) {
            result = this.invertedIndex.retrieve().getDocs();
        }

        for (int i = 1; i < ANDs.length; i++) {
            boolean[] tempResult = new boolean[50];
            if (this.found(ANDs[i].toLowerCase().trim())) {
                tempResult = this.invertedIndex.retrieve().getDocs();
            }
            for (int j = 0; j < result.length; j++) {
                result[j] = result[j] && tempResult[j];
            }
        }

        return result;
    }
    public boolean[] OR_Function(String str) {
        String[] ORs = str.split(" or ");
        boolean[] result = new boolean[50];

        if (this.found(ORs[0].toLowerCase().trim())) {
            result = this.invertedIndex.retrieve().getDocs();
        }

        for (int i = 1; i < ORs.length; i++) {
            boolean[] tempResult = new boolean[50];
            if (this.found(ORs[i].toLowerCase().trim())) {
                tempResult = this.invertedIndex.retrieve().getDocs();
            }
            for (int j = 0; j < result.length; j++) {
                result[j] = result[j] || tempResult[j];
            }
        }

        return result;
    }
    private boolean[] handleSingleTerm(String term) {
        boolean[] result = new boolean[50];
        if (this.found(term)) {
            result = this.invertedIndex.retrieve().getDocs();
        }
        return result;
    }


}