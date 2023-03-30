package rs.ac.bg.etf.sd21335m.trie.types;

import rs.ac.bg.etf.sd21335m.trie.TrieNode;
import rs.ac.bg.etf.sd21335m.trie.match_strategy.*;
import rs.ac.bg.etf.sd21335m.trie.exception.IllegalWordException;
import rs.ac.bg.etf.sd21335m.trie.exception.WordAlreadyExist;

import java.util.*;

public class BasicTrie implements Trie {
    protected TrieNode root;

    public BasicTrie() {
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

    @Override
    public void addNewWord(String word) {
        checkInputAddWord(word);
        TrieNode currentNode = root;
        for (char c : word.toCharArray()) {
            Optional<TrieNode> childOfCurrent = currentNode.getChild(c);
            if (childOfCurrent.isEmpty()) {
                currentNode = currentNode.createNonWordChildAndReturnIt(c);
            } else {
                currentNode = childOfCurrent.get();
            }
        }
        currentNode.setWordTrieNode(true);
    }

    @Override
    public Set<String> searchByStrategy(MatchStrategy matchStrategy, String lookFor) {
        return matchStrategy.search(root, lookFor);
    }

    @Override
    public void removeByStrategy(MatchStrategy matchStrategy, String query) {
        matchStrategy.delete(root, query);
    }

    @Override
    public boolean wordExist(String word) {
        ExplicitMatchStrategy explicitSearchStrategy = new ExplicitMatchStrategy();
        return !explicitSearchStrategy.search(root, word).isEmpty();
    }

    @Override
    public void removeWord(String word) {
        checkInputDeleteWord(word);
        ExplicitMatchStrategy explicitMatchStrategy = new ExplicitMatchStrategy();
        explicitMatchStrategy.delete(root, word);
    }

    private void checkInputDeleteWord(String word) {
        if (word == null) {
            throw new IllegalWordException();
        }
    }

    @Override
    public boolean isEmpty() {
        return !root.hasSomeChild();
    }

    @Override
    public void removeAllWords() {
        root = TrieNode.createNonWordTrieNode();
    }


    @Override
    public int getNumberOfNodes() {
        return root.getNumberOfNodes();
    }

    @Override
    public TriType getType() {
        return TriType.BASIC;
    }

}
