package rs.ac.bg.etf.sd21335m.trie;

import rs.ac.bg.etf.sd21335m.trie.exception.IllegalWordException;
import rs.ac.bg.etf.sd21335m.trie.exception.WordDoesntExist;

import java.util.ArrayList;
import java.util.List;

public class Trie {
    private List<String> words;

    public Trie() {
        words = new ArrayList<>();
    }

    public void addNewWord(String word) {
        if (word == null) {
            throw new IllegalWordException();
        }
        words.add(word);
    }

    public boolean wordExist(String word) {
        return words.contains(word);
    }

    public void removeWord(String word) {
        if (!wordExist(word)) {
            throw new WordDoesntExist();
        }
        words.remove(word);
    }

    public boolean isEmpty() {
        return words.isEmpty();
    }
}
