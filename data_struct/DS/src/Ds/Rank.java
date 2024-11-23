
public class Rank {
    String word; // Correct type
    int rank;

    // Default constructor
    public Rank() {
        rank = 0;
        word = ""; // Initialize to an empty string
    }

    // Parameterized constructor
    public Rank(String word, int rank) {
        this.word = word; // Assign the string directly
        this.rank = rank;
    }

    // Increment the rank
    public int addRank() {
        return ++rank;
    }

    // Set the word
    public void setWord(String word) {
        this.word = word;
    }

    // Get the word
    public String getWord() {
        return word;
    }

    // Get the rank
    public int getRank() {
        return this.rank;
    }

    // Override toString to display the word and rank
    @Override
    public String toString() {
        return "(" + word + ", " + rank + ")";
    }
}
