package rs.ac.bg.etf.sd21335m.trie;

import org.junit.platform.commons.util.CollectionUtils;
import rs.ac.bg.etf.sd21335m.trie.exception.IllegalWordException;
import rs.ac.bg.etf.sd21335m.trie.exception.WordAlreadyExist;
import rs.ac.bg.etf.sd21335m.trie.exception.WordDoesntExist;

import java.awt.*;
import java.util.*;
import java.util.List;

public class Trie {
    private List<String> words;

    public Trie() {
        words = new ArrayList<>();
    }

    private void checkInputAddWord(String word) {
        if (word == null) {
            throw new IllegalWordException();
        }
        if (wordExist(word)) {
            throw new WordAlreadyExist();
        }
    }

    public void addNewWord(String word) {
        checkInputAddWord(word);
        words.add(word);
    }

    public boolean wordExist(String word) {
        return words.contains(word);
    }

    public void removeWord(String word) {
        checkInputDeleteWord(word);
        words.remove(word);
    }

    private void checkInputDeleteWord(String word) {
        if (word == null) {
            throw new IllegalWordException();
        }
        if (!wordExist(word)) {
            throw new WordDoesntExist();
        }
    }

    public boolean isEmpty() {
        return words.isEmpty();
    }

    public void removeAllWords() {
        words.clear();
    }

    public List<String> getWordsWithPrefix(String prefix) {
        List<String> prefixWords = new ArrayList<>();
        for (String word : words) {
            if (word.startsWith(prefix)) {
                prefixWords.add(word);
            }
        }
        return prefixWords;
    }

    public int getHits(String prefix) {
        return getWordsWithPrefix(prefix).size();
    }
}
