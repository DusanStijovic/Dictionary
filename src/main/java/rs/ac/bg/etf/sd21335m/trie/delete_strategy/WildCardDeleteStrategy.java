package rs.ac.bg.etf.sd21335m.trie.delete_strategy;

import rs.ac.bg.etf.sd21335m.trie.TrieNode;

public class WildCardDeleteStrategy implements DeleteStrategy {

    private TrieNode root;

    @Override
    public void delete(TrieNode trieNode, String word) {
        root = trieNode;
        removeSubTrie(root, word, 0);
    }
    private boolean removeSubTrie(TrieNode node, String word, int index) {
        if (index == word.length()) {
            boolean canDelete = true;
            if (node.isWordTrieNode()) {
                node.setWordTrieNode(false);
            }
            if (!node.hasSomeChild()) {
                return true;
            }
            for (TrieNode child : node.getChildren()) {
                if (removeSubTrie(child, word, index)) {
                   node.removeChild(child.getCharacter().get());
                } else {
                    canDelete = false;
                }
            }
            if (canDelete) {
                return !node.isWordTrieNode();
            } else {
                return false;
            }
        } else {
            char c = word.charAt(index);
            if (c == '?') {
                boolean canDelete = true;
                for (TrieNode child : node.getChildren()) {
                    if (removeSubTrie(child, word, index + 1)) {
                      node.removeChild(child.getCharacter().get());
                    } else {
                        canDelete = false;
                    }
                }
                if (canDelete) {
                    return !node.isWordTrieNode() && node != root;
                } else {
                    return false;
                }
            } else {
                if (!node.hasChild(c)) {
                    return false;
                }
                TrieNode child = node.getChild(c).get();
                boolean canDelete = removeSubTrie(child, word, index + 1);
                if (canDelete) {
                    node.removeChild(c);
                    return node != root || !node.getChildren().isEmpty();
                } else {
                    return false;
                }
            }
        }
    }
}
