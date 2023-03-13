package rs.ac.bg.etf.sd21335m.trie;

import java.util.ArrayList;
import java.util.List;

public class Trie {
    private List<String> words;

    public Trie() {
        words = new ArrayList<>();
    }

    public void addNewWord(String word) {
        this.words.add(word);
    }

    public boolean wordExist(String word) {
        return this.words.contains(word);
    }
}
