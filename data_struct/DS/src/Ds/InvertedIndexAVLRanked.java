
import java.util.function.Function;


@SuppressWarnings("unused")
public class InvertedIndexAVLRanked {
  
        class Frequency {
            int docID = 0;
            int f = 0;
        }
    
        AVLTree<Integer, AVLTree<String, Rank>> AVLrank;
        Frequency[] freqs = new Frequency[50];
    // O(1)
        public InvertedIndexAVLRanked() {
            AVLrank = new AVLTree<Integer, AVLTree<String, Rank>>();
        }
       //O(logn+logm)
        public boolean addNew(int docID, String word) {
            if (AVLrank.empty()) {
                AVLTree<String, Rank> miniRank = new AVLTree<String, Rank>();
                miniRank.insert(word, new Rank(word, 1));
                AVLrank.insert(docID, miniRank);
                return true;
            } else {
                if (AVLrank.find(docID)) {
                    AVLTree<String, Rank> miniRank = AVLrank.retrieve();
                    if (miniRank.find(word)) {
                        // Document available, word available - increment rank
                        Rank rank = miniRank.retrieve();
                        rank.addRank();
                        miniRank.update(rank);
                        AVLrank.update(miniRank);
                        return false;
                    }
                  
                    miniRank.insert(word, new Rank(word, 1));
                    AVLrank.update(miniRank);
                    return true;
                }
                
                AVLTree<String, Rank> miniRank = new AVLTree<String, Rank>();
                miniRank.insert(word, new Rank(word, 1));
                AVLrank.insert(docID, miniRank);
                return true;
            }
        }
    //O(logn+logm)
        public boolean found(int docID, String word) {
            if (AVLrank.find(docID))
                if (AVLrank.retrieve().find(word))
                    return true;
            return false;
        }
    //O(logn+logm)
        public int getRank(int docID, String word) {
            if (AVLrank.find(docID)) {
                if (AVLrank.retrieve().find(word)) {
                    return AVLrank.retrieve().retrieve().getRank();
                }
            }
            return 0;
        }
    //(m*n)
        public void printDocument() {
            AVLrank.TraverseT();
        }
    //O(n⋅m+klogk)
        public void calculateTF(String str) {
            str = str.toLowerCase().trim();
            String[] words = str.split(" ");
    
            int index = 0;
            for (int docID = 0; docID < 50; docID++) {
                int count = 0;
                for (int j = 0; j < words.length; j++) {
                    count += this.getRank(docID, words[j]);
                }
                if (count > 0) {
                    freqs[index] = new Frequency();
                    freqs[index].docID = docID;
                    freqs[index].f = count;
                    index++;
                }
            }
    
            mergesort(freqs, 0, index - 1);
    
            for (int x = 0; x < index; x++) {
                System.out.println(freqs[x].docID + "\t\t" + freqs[x].f);
            }
        }
    
        //O(k)
        public static void mergesort(Frequency[] A, int start, int end) {
            if (start >= end) return;
            int mid = start + (end - start) / 2;  
            mergesort(A, start, mid);
            mergesort(A, mid + 1, end);
            inPlace(A, start, mid, end);
        
         }
         //O(k)
            private static void inPlace(Frequency[] A, int start, int mid, int end) {
                int i = start, j = mid + 1;

               
                while (i <= mid && j <= end) {
                    if (A[i].f >= A[j].f) {
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