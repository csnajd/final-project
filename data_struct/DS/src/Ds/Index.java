


public class Index {

    Document[] documents;

    //O(n)
    public Index() {
        documents = new Document[50];
        for (int i = 0; i < documents.length; i++) {
            documents[i] = new Document();
            documents[i].docID = i;
        }
    }


    //\( O(1) \)
    public void addWordToDocument(int documentID, String word) {
        documents[documentID].addNew(word);
        
    }



// O(n)
      /**
     * @param documentID
     */
    public void printDocumentContent(int documentID) {
        if (documents[documentID].index.empty()) {
            System.out.println("Empty Document");
        } else {
            documents[documentID].index.findFirst();
            for (int i = 0; i < documents[documentID].index.size; i++) {
                System.out.print(documents[documentID].index.retrieve() + " ");
                documents[documentID].index.findNext();
            }
        }
    }
    
//O(n⋅m)
    public boolean[] getDocs(String str) {
        boolean[] result = new boolean[50];
        for (int i = 0; i < result.length; i++) {
            result[i] = documents[i].found(str);
            
        }
        return result;
    }
    

    /**
     * @param str
     * @return
     */

     //O(m * n)
     public boolean[] searchWithLogicalOperators(String str) {
       
        str = str.toLowerCase().trim();
        System.out.println("Query: " + str);
    
     
        if (!str.contains(" or ") && !str.contains(" and ")) {
            boolean[] singleTermResult = getDocs(str);
            System.out.print("Single Term Result: ");
            return singleTermResult;
        }
    
        if (str.contains(" or ") && str.contains(" and ")) {
        
            String[] orGroups = str.split(" or ");
            boolean[] result = AND(orGroups[0]); 
            System.out.print("Initial AND/OR Group Result: ");
    

            for (int i = 1; i < orGroups.length; i++) {
                boolean[] groupResult = AND(orGroups[i]); 
                for (int j = 0; j < result.length; j++) {
                    result[j] = result[j] || groupResult[j]; 
                }
            }
            return result;
        }
    
        
        if (str.contains(" and ")) {
            return AND(str);
        }
    
   
        return OR(str); 
    }
    

    //O(m⋅n)
    public boolean[] AND(String str) {
        String[] ANDs = str.split(" and ");
        boolean[] resultFlags = getDocs(ANDs[0].trim());
        System.out.print("Initial AND Result for '" + ANDs[0] + "': ");
    
    
        for (int i = 1; i < ANDs.length; i++) {
            boolean[] resultFlags2 = getDocs(ANDs[i].trim());
            System.out.print("AND Result for '" + ANDs[i] + "': ");
          
    
            for (int j = 0; j < 50; j++) {
                resultFlags[j] = resultFlags[j] && resultFlags2[j];
            }
            System.out.print("Intermediate AND Result: ");
           
        }
        System.out.print("Final AND Result: ");
      
        return resultFlags;
    }
    

    //O(m⋅n)
    public boolean[] OR(String str) {
        String[] ORs = str.split(" or ");
        boolean[] resultFlags = getDocs(ORs[0].trim());
        System.out.print("Initial OR Result for '" + ORs[0] + "': ");
      
    
        for (int i = 1; i < ORs.length; i++) {
            boolean[] currentTermFlags  = getDocs(ORs[i].trim());
            System.out.print("OR Result for '" + ORs[i] + "': ");
           
    
            for (int j = 0; j < 50; j++) {
                resultFlags[j] = resultFlags[j] || currentTermFlags[j];
            }
            System.out.print("Intermediate OR Result: ");
            
        }
        System.out.print("Final OR Result: ");
     
        return resultFlags;
    }
   
    
}    
    