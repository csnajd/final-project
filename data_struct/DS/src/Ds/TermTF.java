
public class TermTF {
    String word; 
    boolean[] docIDS; 

 // O(n)
    public TermTF() {
        docIDS = new boolean [50]; 
        for (int i = 0; i < docIDS.length; i++) {
            docIDS [i] = false; 
        }
        word = "";
    }

   // O(n)
    public TermTF(String word, boolean[] docIDS) {
        this.word = word; // Set the word
        this.docIDS = new boolean [docIDS.length];
        for (int i = 0 ; i < docIDS.length ; i++)
            this.docIDS [i] = docIDS[i];

    }
    
// O(n)
    public boolean [] getDocs ()
    {
        boolean [] test = new boolean [docIDS.length];
        for ( int i = 0 ; i < test.length ; i++)
            test[i] = docIDS[i];
        return test;
    }

   // O(1).
    public boolean addDocID(int docID) {
        
            if (! docIDS[docID])
            {
                this.docIDS[docID] = true;
                return true;
            }
            return false;
        }

   //O(1).
    public void setWord(String word) {
        this.word = word;
    }

   
    //O(1).
    public String getWord() {
        return word;
    }

   
    //O(n)
    public String toString() {
        String docs = "";
        for (int i = 0, j = 0 ; i < docIDS.length; i++)
            if ( j == 0 && docIDS [i]==true )
            {
                docs += " " + String.valueOf(i) ;
                j++;
            }
            else if ( docIDS [i]==true )
            {
                docs += ", " + String.valueOf(i) ;
                j++;
            }
        
        return word + "[" + docs + ']';
    }
    
     
}
