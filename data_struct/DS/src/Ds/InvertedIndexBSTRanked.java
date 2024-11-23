
import java.util.function.Function;
@SuppressWarnings("unused")
public class InvertedIndexBSTRanked {
    class Frequency {
        int documentID = 0;
        int termFrequency = 0;
    }

    BST<Integer, BST<String, Rank>> documentIndex; // Outer BST for documents, inner BST for terms
    Frequency[] frequencies = new Frequency[50]; // Frequency array for ranking


//O(1)
    public InvertedIndexBSTRanked() {
        documentIndex = new BST<Integer, BST<String, Rank>>();
    }

   //O(logn+logm)
    public boolean addNew(int documentID, String term) {
        if (documentIndex.empty()) {
            BST<String, Rank> termIndex = new BST<String, Rank>();
            termIndex.insert(term, new Rank(term, 1));
            documentIndex.insert(documentID, termIndex);
            return true;
        } else {
            if (documentIndex.find(documentID)) {
                BST<String, Rank> termIndex = documentIndex.retrieve();
                if (termIndex.find(term)) {
                    // Document and term found, increment rank
                    Rank rank = termIndex.retrieve();
                    rank.addRank();
                    termIndex.update(rank);
                    documentIndex.update(termIndex);
                    return false;
                }
                
                termIndex.insert(term, new Rank(term, 1));
                documentIndex.update(termIndex);
                return true;
            }
          
            BST<String, Rank> termIndex = new BST<String, Rank>();
            termIndex.insert(term, new Rank(term, 1));
            documentIndex.insert(documentID, termIndex);
            return true;
        }
    }

//O(logn+logm)
    public boolean found(int documentID, String term) {
        if (documentIndex.find(documentID)) {
            return documentIndex.retrieve().find(term);
        }
        return false;
    }

   //O(logn+logm)
    public int getRank(int documentID, String term) {
        if (documentIndex.find(documentID)) {
            if (documentIndex.retrieve().find(term)) {
                return documentIndex.retrieve().retrieve().getRank();
            }
        }
        return 0;
    }

   //O(n⋅m)
    public void printDocument() {
        documentIndex.TraverseT();
    }
    //O(n⋅m)
    public void displayDataRecursive() {
        if (documentIndex != null) {
            documentIndex.Traverse();  
        } else {
            System.out.println("BST is not initialized");
        }
    }

    // O(n⋅m+k⋅logk)
    public void calculateTF(String query) {
        query = query.toLowerCase().trim();
        String[] terms = query.split(" ");
        int index = 0;

        for (int documentID = 0; documentID < 50; documentID++) {
            int count = 0;
            for (int j = 0; j < terms.length; j++) {
                count += this.getRank(documentID, terms[j]);
            }
            if (count > 0) {
                frequencies[index] = new Frequency();
                frequencies[index].documentID = documentID;
                frequencies[index].termFrequency = count;
                index++;
            }
        }

       
        mergesort(frequencies, 0, index - 1);

        
        System.out.println("\nDocID\tScore");
        for (int i = 0; i < index; i++) {
            System.out.println(frequencies[i].documentID + "\t\t" + frequencies[i].termFrequency);
        }
    }

    //O(nlogn)
    public static void mergesort(Frequency[] A, int start, int end) {
        if (start >= end) return;
        int mid = start + (end - start) / 2;  
        mergesort(A, start, mid);
        mergesort(A, mid + 1, end);
        inPlaceMerge(A, start, mid, end);
    
     }
     //O(K)
        private static void inPlaceMerge(Frequency[] A, int start, int mid, int end) {
            int i = start, j = mid + 1;

           
            while (i <= mid && j <= end) {
                if (A[i].termFrequency >= A[j].termFrequency) {
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
        }}
