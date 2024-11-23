
public class IndexRanked {
    class frequency
        {
            int docID = 0;
            int f = 0;
        }
    
       
        
        Document [] indexes;
        frequency [] freqs;
    
        //O(n)
        public IndexRanked() {
            freqs = new frequency [50];
            indexes = new Document [50];
            for ( int i = 0 ; i < indexes.length ; i++)
            {
                indexes [i] = new Document();
                indexes [i].docID = i;
            }
        }
           
        
        //O(1)
        public void addDocument ( int docID, String data)
        {
            indexes[docID].addNew(data);
        }
        


        //O(n)
        public void printDocment (int docID)
        {
            if ( indexes[docID].index.empty())
                System.out.println("Empty Document");
            else
            {
                indexes[docID].index.findFirst();
                for ( int i = 0; i< indexes[docID].index.size ; i++)
                {
                    System.out.print (indexes[docID].index.retrieve() + " ");
                    indexes[docID].index.findNext();
                }
            }
        }
    

//O(n⋅m)
    public  boolean [] getDocs (String str)
    {
        boolean [] result = new boolean [50];
      
        
        for (int i = 0 ; i < result.length ; i++)
            if (indexes[i].found(str))
                result[i] = true;
              else 
              result[i] = false;
    
        return result;
    }
    
    
   //O(k⋅n⋅m)
    public void calculateTF(String query) {
        query = query.toLowerCase().trim();
        String[] words = query.split(" ");
        freqs = new frequency[50];
    
        for (int i = 0; i < freqs.length; i++) {
            freqs[i] = new frequency();
            freqs[i].docID = i;
            freqs[i].f = 0;
        }
    
       
        for (int wordIndex = 0; wordIndex < words.length; wordIndex++) {
            String word = words[wordIndex];
            System.out.println("Processing term: " + word);
            for (int docID = 0; docID < indexes.length; docID++) {
                if (indexes[docID].found(word)) { 
                    indexes[docID].index.findFirst();
                    int wordCount = 0;
    
                    
                    for (int j = 0; j < indexes[docID].index.size(); j++) {
                        if (indexes[docID].index.retrieve().equals(word)) {
                            wordCount++;
                        }
                        indexes[docID].index.findNext();
                    }
    
                    
                    freqs[docID].f += wordCount;
                }
            }
        
        }
    
  
        mergesort(freqs, 0, freqs.length - 1);
    
      
        System.out.println("\nDocID\tScore");
        for (frequency freq : freqs) {
            if (freq.f > 0) {
                System.out.printf("%d\t%d\n", freq.docID, freq.f);
            }
        }
    }
    
        
         public static void mergesort(frequency[] A, int start, int end) {
            if (start >= end) return;
            int mid = start + (end - start) / 2;  
            mergesort(A, start, mid);
            mergesort(A, mid + 1, end);
            inPlaceMerge(A, start, mid, end);
        
         }
            private static void inPlaceMerge(frequency[] A, int start, int mid, int end) {
                int i = start, j = mid + 1;

               
                while (i <= mid && j <= end) {
                    if (A[i].f >= A[j].f) {
                        i++; 
                    } else {

                        frequency temp = A[j];
                        int k = j;
                        while (k > i) {
                            A[k] = A[k - 1];
                            k--;
                        }
                        A[i] = temp;
            
                        
                        i++;
                        mid++;
                        j++;
                    }
                }
            }










            }
          