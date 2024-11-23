
import java.io.File;
import java.util.Scanner;


public class Search_Engine {
    int tokenCount = 0;
    int vocabularyCount = 0;

  
    Index documentIndex;
    IndexRanked rankedIndex;
    InvertedIndex keywordInvertedIndex;
    RankedInvertedIndex rankedKeywordIndex;
    InvertedIndexAVL avlInvertedIndex;
    InvertedIndexAVLRanked rankedAVLInvertedIndex;
    BSTInvertedIndex bstInvertedIndex;
    InvertedIndexBSTRanked rankedBSTInvertedIndex;

  
    public Search_Engine() {
        documentIndex = new Index();
        rankedIndex = new IndexRanked();
        keywordInvertedIndex = new InvertedIndex();
        rankedKeywordIndex = new RankedInvertedIndex();
        avlInvertedIndex = new InvertedIndexAVL();
        rankedAVLInvertedIndex = new InvertedIndexAVLRanked();
        bstInvertedIndex = new BSTInvertedIndex();
        rankedBSTInvertedIndex = new InvertedIndexBSTRanked();
    }

    public void LoadData(String stopFile, String fileName) {


        try{
            File stopfile = new File (stopFile);
            Scanner reader = new Scanner (stopfile).useDelimiter("\\Z");
            String stops = reader.next();
            
            stops = stops.replaceAll("\n", " ");
            
            File docsfile = new File(fileName);
            Scanner reader1 = new Scanner (docsfile);
            String line = reader1.nextLine();
            
            for ( int lineID = 0 ; lineID <50 ; lineID ++ ) 
            {
                line = reader1.nextLine().toLowerCase();
                
                int pos = line.indexOf(',');
                int docID = Integer.parseInt( line .substring(0, pos));

                String data = line.substring(pos+1, line.length() - pos).trim();
                data = data.substring(0, data.length() - 1);

                data = data.toLowerCase();
                data =  data.replaceAll("[\']", " ");
                data = data.replaceAll("[^a-zA-Z0-9]", " ").trim();





                String [] words = data.split(" "); 

                for (int i = 0; i < words.length ; i++)
                {
                    String word = words[i].trim(); 
            
                    if ( word.compareToIgnoreCase("") != 0)
                       tokenCount++;

                    if ( ! stops.contains(word + " ")) 
                    { 
                        this.documentIndex.addWordToDocument(docID, word);
                        this.keywordInvertedIndex.addNew(docID, word);
                        this.bstInvertedIndex.addNew(docID, word);
                        this.avlInvertedIndex.addNew(docID, word);
                        
                        this.rankedIndex.addDocument(docID, word);
                        this.rankedKeywordIndex.addNew(docID, word);
                        this.rankedBSTInvertedIndex.addNew(docID, word);
                        this.rankedAVLInvertedIndex.addNew(docID, word);
                    }
                }

               
            }
         
            
            vocabularyCount = avlInvertedIndex.size();
            System.out.println("Total Tokens: " + tokenCount);
         
            //System.out.println("vocabs " + vocabularyCount);
            
            reader.close();
            reader1.close();
        }
        catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
}

    
    
    public boolean[] performBooleanQuery(String query, int structureType) {
        boolean[] matchingDocuments = new boolean[50];
        System.out.println("=== Boolean Query Execution ===");
        System.out.println("Query: " + query);

        switch (structureType) {
            case 1:
                System.out.println("Using Document Index");
                matchingDocuments = documentIndex.searchWithLogicalOperators(query);
                break;
            case 2:
                System.out.println("Using Keyword Inverted Index");
                matchingDocuments = keywordInvertedIndex.searchWithLogicalOperators(query);
                break;
            case 3:
                System.out.println("Using BST Inverted Index");
                matchingDocuments = bstInvertedIndex.searchWithLogicalOperators(query);
                break;
            case 4:
                System.out.println("Using AVL Inverted Index");
                matchingDocuments = avlInvertedIndex.searchWithLogicalOperators(query);
                break;
            default:
                System.out.println("Invalid Structure Type.");
        }

        System.out.println("Matching Documents:");
        boolean hasMatches = false;
        for (int i = 0; i < matchingDocuments.length; i++) {
            if (matchingDocuments[i]) {
                System.out.println("Document ID: " + i);
                hasMatches = true;
            }
        }

        if (!hasMatches) {
            System.out.println("No matching documents found.");
        }

        return matchingDocuments;
    }

    public void performRankedQuery(String query, int structureType) {
        System.out.println("=== Ranked Query Execution ===");
        switch (structureType) {
            case 1:
                System.out.println("Using Ranked Index");
                rankedIndex.calculateTF(query);
                break;
            case 2:
                System.out.println("Using Ranked Keyword Index");
                rankedKeywordIndex.calculateTF(query);
                break;
            case 3:
                System.out.println("Using Ranked BST Index");
                rankedBSTInvertedIndex.calculateTF(query);
                break;
            case 4:
                System.out.println("Using Ranked AVL Index");
                rankedAVLInvertedIndex.calculateTF(query);
                break;
            default:
                System.out.println("Invalid Structure Type.");
        }
    }

    public void displayIndexedTokens() {
       
        System.out.println("Token to Document Mappings:");
        avlInvertedIndex.displayDataRecursive();
        bstInvertedIndex.displayDataRecursive();
        System.out.println("Total Tokens: " + tokenCount);
       
    }

    public void displayIndexedDocuments() {
        System.out.println("Document Details:");
        for (int i = 0; i < 50; i++) {
            System.out.println("Document " + i + " contains " + documentIndex.documents[i].index.size() + " terms.");
        }
    }

    public boolean[] performTermQuery(String term, int structureType) {
        boolean[] matchingDocs = new boolean[50];
        System.out.println("=== Term Query Execution ===");
        System.out.println("Term: " + term);

        switch (structureType) {
            case 1:
                System.out.println("Using Document Index");
                matchingDocs = documentIndex.getDocs(term);
                break;
            case 2:
                System.out.println("Using Keyword Index");
                if (keywordInvertedIndex.found(term)) {
                    matchingDocs = keywordInvertedIndex.retrieve().getDocs();
                } else {
                    System.out.println("Term not found.");
                }
                break;
            case 3:
                System.out.println("Using BST Inverted Index");
                if (bstInvertedIndex.found(term)) {
                    matchingDocs = bstInvertedIndex.invertedIndexBST.retrieve().getDocs();
                } else {
                    System.out.println("Term not found.");
                }
                break;
            case 4:
                System.out.println("Using AVL Inverted Index");
                if (avlInvertedIndex.found(term)) {
                    matchingDocs = avlInvertedIndex.invertedIndexAVL.retrieve().getDocs();
                } else {
                    System.out.println("Term not found.");
                }
                break;
            default:
                System.out.println("Invalid Structure Type.");
        }

        System.out.println("Matching Documents:");
        boolean hasMatches = false;
        for (int i = 0; i < matchingDocs.length; i++) {
            if (matchingDocs[i]) {
                System.out.println("Document ID: " + i);
                hasMatches = true;
            }
        }

        if (!hasMatches) {
            System.out.println("No matching documents found.");
        }

        return matchingDocs;
    }
}