package rs.ac.bg.etf.sd21335m.trie;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class TrieNode {

    private final Map<Character, TrieNode> children;
    private Optional<TrieNode> parent;
    private Optional<Character> character;
    private boolean wordTreeNode;

    public TrieNode() {
        this(false);
    }

    public TrieNode(boolean wordTreeNode) {
        this.children = new HashMap<>();
        this.wordTreeNode = wordTreeNode;
        this.parent = Optional.empty();
        this.character = Optional.empty();
    }

    public static TrieNode createWordTrieNode() {
        return new TrieNode(true);
    }

    public static TrieNode createNonWordTrieNode() {
        return new TrieNode();
    }

    private static void checkIfNodeNull(TrieNode node) {
        if (node == null) {
            throw new AddingNullChildException();
        }
    }

    public boolean isWordTrieNode() {
        return wordTreeNode;
    }

    public void setWordTrieNode(boolean wordTreeNode) {
        this.wordTreeNode = wordTreeNode;
    }

    public void addChild(char character, TrieNode child) {
        checkIfNodeNull(child);
        checkIfExistChildWithCharacter(character);
        children.put(character, child);
        child.parent = Optional.of(this);
        child.character = Optional.of(character);
    }

    private void checkIfExistChildWithCharacter(char character) {
        Optional<TrieNode> child = getChild(character);
        if (child.isPresent()) {
            throw new ChildWithCharacterExist();
        }
    }

    public Optional<TrieNode> getChild(char character) {
        TrieNode treeNode = children.get(character);
        return Optional.ofNullable(treeNode);
    }

    public void removeChild(char character) {
        Optional<TrieNode> child = getChild(character);
        if (child.isEmpty()) {
            throw new ChildWithCharacterDoesNotExist();
        }
        child.get().parent = Optional.empty();
        children.remove(character);

    }

    public boolean hasChild(char character) {
        return children.containsKey(character);
    }

    public boolean hasSomeChild() {
        return !children.isEmpty();
    }

    public Optional<TrieNode> getParent() {
        return parent;
    }

    public Optional<Character> getCharacter() {
        return character;
    }
}
