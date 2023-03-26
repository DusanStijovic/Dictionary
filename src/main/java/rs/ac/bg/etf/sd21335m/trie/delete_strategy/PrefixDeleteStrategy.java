package rs.ac.bg.etf.sd21335m.trie.delete_strategy;

import rs.ac.bg.etf.sd21335m.trie.TrieNode;

import java.util.Optional;

public class PrefixDeleteStrategy implements DeleteStrategy{
    @Override
    public void delete(TrieNode trieNode, String prefix) {
        TrieNode node = trieNode;
        for (char c : prefix.toCharArray()) {
            Optional<TrieNode> temp = node.getChild(c);
            if (temp.isEmpty()) {
                return;
            }
            node = temp.get();
        }
        removeSubTrie(node);
        if (trieNode.hasSomeChild()) {
            removeSubTrie(trieNode);
        }
    }

    private boolean removeSubTrie(TrieNode node) {
        if (node.isWordTrieNode()) {
            node.setWordTrieNode(false);
        }
        if (!node.hasSomeChild()) {
            return true;
        }
        boolean canDelete = true;
        for (TrieNode child : node.getChildren()) {
            if (removeSubTrie(child)) {
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
    }

}
