 
//class represents a document



public class Document {
    int docID;                 
    LinkedList<String> index;   


    public Document() {
        docID = 0;
        index = new LinkedList <String>();
    }

    public void addNew (String word)
    {
        index.insert(word);
    }
  
    public boolean found(String word)
   { word = word.toLowerCase().trim();
    if (index.empty()) return false;

       index.findFirst();
       for ( int i = 0 ; i < index.size ; i++)
       {
           if ( index.retrieve().equals(word) )
               return true;
          index.findNext();
       }
       return false;
   }
}
