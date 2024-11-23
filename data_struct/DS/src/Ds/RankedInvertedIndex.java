
public class RankedInvertedIndex {
    class Frequency {
        int docID = 0; 
        int frequency = 0; 
    }

    LinkedList<Keyword> invertedIndex; 
    Frequency[] frequencies; 


    public RankedInvertedIndex() {
        invertedIndex = new LinkedList<Keyword>();
        frequencies = new Frequency[50];
        for (int i = 0; i < frequencies.length; i++) {
            frequencies[i] = new Frequency();
            frequencies[i].docID = i; 
        }
    }

  
   

    public boolean addNew(int docID, String word) {
        if (invertedIndex.empty()) {
            Keyword newKeyword = new Keyword();
            newKeyword.setTerm(word);
            newKeyword.addDocument(docID);
            invertedIndex.insert(newKeyword);
            return true;
        } else {
            invertedIndex.findFirst();
            while (!invertedIndex.last()) {
                if (invertedIndex.retrieve().getTerm().equals(word)) {
                    Keyword existingKeyword = invertedIndex.retrieve();
                    existingKeyword.addDocument(docID);
                    invertedIndex.update(existingKeyword);
                    return false;
                }
                invertedIndex.findNext();
            }

            if (invertedIndex.retrieve().getTerm().equals(word)) {
                Keyword existingKeyword = invertedIndex.retrieve();
                existingKeyword.addDocument(docID);
                invertedIndex.update(existingKeyword);
                return false;
            } else {
                Keyword newKeyword = new Keyword();
                newKeyword.setTerm(word);
                newKeyword.addDocument(docID);
                invertedIndex.insert(newKeyword);
            }
            return true;
        }
    }
    public int size() {
        return invertedIndex.size();
    }

 
    public boolean found(String word) {
        if (invertedIndex.empty()) {
            return false;
        }

        invertedIndex.findFirst();
        for (int i = 0; i < invertedIndex.size(); i++) {
            if (invertedIndex.retrieve().getTerm().equals(word)) {
                return true;
            }
            invertedIndex.findNext();
        }
        return false;
    }
    public void printIndex() {
        if (invertedIndex.empty()) {
            System.out.println("Empty Inverted Index");
        } else {
            invertedIndex.findFirst();
            while (!invertedIndex.last()) {
                System.out.println(invertedIndex.retrieve());
                invertedIndex.findNext();
            }
            System.out.println(invertedIndex.retrieve());
        }
    }

    public void calculateTF(String query) {
        query = query.toLowerCase().trim();
        String[] words = query.split(" ");

 
        for (int i = 0; i < frequencies.length; i++) {
            frequencies[i].frequency = 0;
        }

        for (int i = 0; i < words.length; i++) {
            if (found(words[i])) {
                int[] docs = invertedIndex.retrieve().getDocs();
                for (int docID = 0; docID < docs.length; docID++) {
                    frequencies[docID].frequency += docs[docID];
                }
            }
        }

        mergesort(frequencies, 0, frequencies.length - 1);

     
        System.out.println("\nDocID\tScore");
        for (int i = 0; i < frequencies.length; i++) {
            if (frequencies[i].frequency > 0) {
                System.out.println(frequencies[i].docID + "\t\t" + frequencies[i].frequency);
            }
        }
    }


    
    public static void mergesort(Frequency[] A, int start, int end) {
        if (start >= end) return;
        int mid = start + (end - start) / 2;  
        mergesort(A, start, mid);
        mergesort(A, mid + 1, end);
        inPlaceMerge(A, start, mid, end);
    
     }
        private static void inPlaceMerge(Frequency[] A, int start, int mid, int end) {
            int i = start, j = mid + 1;

           
            while (i <= mid && j <= end) {
                if (A[i].frequency >= A[j].frequency) {
                    i++; 
                } else {

                    Frequency temp = A[j];
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
