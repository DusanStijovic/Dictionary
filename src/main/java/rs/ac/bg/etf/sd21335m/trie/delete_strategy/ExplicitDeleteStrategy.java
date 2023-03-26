package rs.ac.bg.etf.sd21335m.trie.delete_strategy;

import rs.ac.bg.etf.sd21335m.trie.TrieNode;

import java.util.Optional;

public class ExplicitDeleteStrategy implements DeleteStrategy {
    private TrieNode root;
    @Override
    public void delete(TrieNode trieNode, String word) {
        this.root = trieNode;
        Optional<TrieNode> trieNodeOptional = getLastWordNode(word);
        trieNodeOptional.ifPresent(this::removeFromTrieIfWordNode);
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

}
