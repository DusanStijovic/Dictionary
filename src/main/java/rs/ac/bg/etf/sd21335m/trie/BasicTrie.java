package rs.ac.bg.etf.sd21335m.trie;

import rs.ac.bg.etf.sd21335m.trie.exception.IllegalWordException;
import rs.ac.bg.etf.sd21335m.trie.exception.WordAlreadyExist;
import rs.ac.bg.etf.sd21335m.trie.exception.WordDoesntExist;

import java.util.*;
import java.util.List;

public class BasicTrie {
    private final List<String> words;
    private TrieNode root;

    public BasicTrie() {
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
        TrieNode parent = getLastWordNode(word).get();
        if (parent.isWordTrieNode()) {
            parent.setWordTrieNode(false);
            if (parent != root && !parent.hasSomeChild() && !parent.isWordTrieNode()) {
                TrieNode temp = parent;
                parent = parent.getParent().get();
                parent.removeChild(temp.getCharacter().get());
            }
        }
        words.remove(word);
    }

    private Optional<TrieNode> getLastWordNode(String word) {
        TrieNode parent = root;
        for (char c : word.toCharArray()) {
            Optional<TrieNode> children = parent.getChild(c);
            if (children.isEmpty()) {
                return Optional.empty();
            } else {
                parent = children.get();
            }
        }
        return Optional.of(parent);
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
        return !root.hasSomeChild();
    }

    public void removeAllWords() {
        root = TrieNode.createNonWordTrieNode();
    }

    public Set<String> getWordsWithPrefix(String prefix) {
        Optional<TrieNode> lastCommonNode = getLastCommonNode(prefix);
        if (lastCommonNode.isEmpty()) {
            return Collections.emptySet();
        }
        Set<String> results = new HashSet<>();
        searchWordsWithPrefix(lastCommonNode.get(), new StringBuilder(prefix), results);
        return results;
    }

    private Optional<TrieNode> getLastCommonNode(String prefix) {
        Optional<TrieNode> currentNode = Optional.of(root);
        for (char c : prefix.toCharArray()) {
            currentNode = currentNode.get().getChild(c);
            if (currentNode.isEmpty()) {
                break;
            }
        }
        return currentNode;
    }

    private void searchWordsWithPrefix(TrieNode node, StringBuilder currentWord, Set<String> results) {
        if (node.isWordTrieNode()) {
            results.add(currentWord.toString());
        }
        for (char c : node.getFirstChildrenCharacters()) {
            currentWord.append(c);
            searchWordsWithPrefix(node.getChild(c).get(), currentWord, results);
            currentWord.deleteCharAt(currentWord.length() - 1);
        }
    }

    public int getHits(String prefix) {
        return getWordsWithPrefix(prefix).size();
    }

    public int getNumberOfNodes() {
        return root.getNumberOfNodes();
    }
}
