package rs.ac.bg.etf.sd21335m.trie;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class TrieNode {

    private final boolean isWordTreeNode;
    private final Map<Character, TrieNode> children;

    public TrieNode() {
        this.children = new HashMap<>();
        this.isWordTreeNode = false;
    }

    public TrieNode(boolean isWordTreeNode) {
        this.children = new HashMap<>();
        this.isWordTreeNode = isWordTreeNode;
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
        return isWordTreeNode;
    }

    public void addChild(char character, TrieNode child) {
        checkIfNodeNull(child);
        checkIfExistChildWithCharacter(character);
        children.put(character, child);
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
        children.remove(character);
    }
}
