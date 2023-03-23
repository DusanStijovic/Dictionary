package rs.ac.bg.etf.sd21335m.trie;

import rs.ac.bg.etf.sd21335m.trie.exception.IllegalWordException;
import rs.ac.bg.etf.sd21335m.trie.exception.WordAlreadyExist;
import rs.ac.bg.etf.sd21335m.trie.exception.WordDoesntExist;

import java.util.*;

public class BasicTrie {
    private TrieNode root;

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

    public void addNewWord(String word) {
        checkInputAddWord(word);
        TrieNode currentNode = root;
        for (char c : word.toCharArray()) {
            Optional<TrieNode> childOfCurrent = currentNode.getChild(c);
            if (childOfCurrent.isEmpty()) {
                TrieNode newChild = currentNode.createNonWordChildAndReturnIt(c);
                currentNode = newChild;
            } else {
                currentNode = childOfCurrent.get();
            }
        }
        currentNode.setWordTrieNode(true);
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
        Optional<TrieNode> trieNodeOptional = getLastWordNode(word);
        trieNodeOptional.ifPresent(this::removeFromTrieIfWordNode);
    }

    private void removeFromTrieIfWordNode(TrieNode trieNode) {
        if (trieNode.isWordTrieNode()) {
            trieNode.setWordTrieNode(false);
            removeCurrentAndAllParentsWithoutChildExpectRoot(trieNode);
        }
    }

    private void removeCurrentAndAllParentsWithoutChildExpectRoot(TrieNode current) {
        while (current != root && !current.hasSomeChild() && !current.isWordTrieNode()) {
            Optional<Character> optionalCharacter = current.getCharacter();
            Optional<TrieNode> parentOfCurrent = current.getParent();
            if (parentOfCurrent.isEmpty() || optionalCharacter.isEmpty()) {
                break;
            }
            current = parentOfCurrent.get();
            current.removeChild(optionalCharacter.get());
        }
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
        for (TrieNode trieNode : node.getChildren()) {
            Optional<Character> optionalCharacter = trieNode.getCharacter();
            if (optionalCharacter.isPresent()) {
                currentWord.append(optionalCharacter.get());
                searchWordsWithPrefix(trieNode, currentWord, results);
                currentWord.deleteCharAt(currentWord.length() - 1);
            }
        }
    }

    public int getHits(String prefix) {
        return getWordsWithPrefix(prefix).size();
    }

    public int getNumberOfNodes() {
        return root.getNumberOfNodes();
    }
}
