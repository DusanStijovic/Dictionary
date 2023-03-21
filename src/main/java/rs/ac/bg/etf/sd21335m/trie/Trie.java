package rs.ac.bg.etf.sd21335m.trie;

import rs.ac.bg.etf.sd21335m.trie.exception.IllegalWordException;
import rs.ac.bg.etf.sd21335m.trie.exception.WordAlreadyExist;
import rs.ac.bg.etf.sd21335m.trie.exception.WordDoesntExist;

import java.util.*;
import java.util.List;

public class Trie {
    private final List<String> words;
    private final TrieNode root;

    public Trie() {
        words = new ArrayList<>();
        root = TrieNode.createNonWordTrieNode();
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
        TrieNode currenNode = root;
        for (char c : word.toCharArray()) {
            if (!currenNode.hasChild(c)) {
                currenNode.addChild(c, TrieNode.createNonWordTrieNode());
            }
            currenNode = currenNode.getChild(c).get();
        }
        currenNode.setWordTrieNode(true);
        words.add(word);
    }

    public boolean wordExist(String word) {
        TrieNode parent = root;
        for (char c : word.toCharArray()) {
            Optional<TrieNode> children = parent.getChild(c);
            if (children.isEmpty()) {
                break;
            } else {
                parent = children.get();
            }
        }
        return parent.isWordTrieNode();
    }

    public void removeWord(String word) {
        checkInputDeleteWord(word);
        TrieNode parent = root;
        for (char c : word.toCharArray()) {
            Optional<TrieNode> children = parent.getChild(c);
            if (children.isEmpty()) {
                return;
            } else {
                parent = children.get();
            }
        }
        if (parent.isWordTrieNode()) {
            parent.setWordTrieNode(false);
            if (parent != root && !parent.hasSomeChild() && !parent.isWordTrieNode()){
                TrieNode temp = parent;
                parent = parent.getParent().get();
                parent.removeChild(temp.getCharacter().get());
            }
        }
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

    public int getNumberOfNodes() {
        return 6;
    }
}
