package rs.ac.bg.etf.sd21335m.trie.types;

import rs.ac.bg.etf.sd21335m.trie.TrieNode;
import rs.ac.bg.etf.sd21335m.trie.delete_strategy.DeleteStrategy;
import rs.ac.bg.etf.sd21335m.trie.delete_strategy.ExplicitDeleteStrategy;
import rs.ac.bg.etf.sd21335m.trie.exception.IllegalWordException;
import rs.ac.bg.etf.sd21335m.trie.exception.WordAlreadyExist;
import rs.ac.bg.etf.sd21335m.trie.exception.WordDoesntExist;
import rs.ac.bg.etf.sd21335m.trie.search_strategy.ExplicitSearchStrategy;
import rs.ac.bg.etf.sd21335m.trie.search_strategy.SearchStrategy;

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
    public Set<String> search(SearchStrategy searchStrategy, String lookFor) {
        return searchStrategy.search(root, lookFor);
    }

    @Override
    public void delete(DeleteStrategy deleteStrategy, String query) {
        deleteStrategy.delete(root, query);
    }

    @Override
    public boolean wordExist(String word) {
        ExplicitSearchStrategy explicitSearchStrategy = new ExplicitSearchStrategy();
        return !explicitSearchStrategy.search(root, word).isEmpty();
    }

    @Override
    public void removeWord(String word) {
        checkInputDeleteWord(word);
        ExplicitDeleteStrategy explicitDeleteStrategy = new ExplicitDeleteStrategy();
        explicitDeleteStrategy.delete(root, word);
    }

    private void checkInputDeleteWord(String word) {
        if (word == null) {
            throw new IllegalWordException();
        }
        if (!wordExist(word)) {
            throw new WordDoesntExist();
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

}
