
public class Keyword {
    String term; 
    int[] documentFlags; 

   // O(n) ( O(1))
    public Keyword() {
        documentFlags = new int[50]; 
        for (int i = 0; i < documentFlags.length; i++) {
            documentFlags[i] = 0; 
        }
        term = ""; }

//O(m)
    public Keyword(String term, int[] documentFlags) {
        this.term = term;
        this.documentFlags = new int[documentFlags.length];
        for (int i = 0; i < documentFlags.length; i++) {
            this.documentFlags[i] = documentFlags[i]; 
        }
    }

    //	O(1)
    public void addDocument(int documentID) {
       
            this.documentFlags[documentID]++;
         
    }
//	O(1)
    public void setTerm(String term) {
        this.term = term;
    }
    //	O(1)
    public String getTerm() {
        return term;
    }

//   O(n)

    public int[] getDocs() {
        int[] documentFlagsCopy = new int[documentFlags.length];
        for (int i = 0; i < documentFlags.length; i++) {
            documentFlagsCopy[i] = documentFlags[i];
        }
        return documentFlagsCopy;
    }

   
    @Override
    //O(n)
    public String toString() {
        String docs = "";
        for (int i = 0, j = 0 ; i < documentFlags.length; i++)
            if ( j == 0 && documentFlags [i] != 0 )
            {
                docs += " " + String.valueOf(i) ;
                j++;
            }
            else if ( documentFlags [i] != 0 )
            {
                docs += ", " + String.valueOf(i) ;
                j++;
            }
        
        return term + "[" + docs + ']';
    }
}
