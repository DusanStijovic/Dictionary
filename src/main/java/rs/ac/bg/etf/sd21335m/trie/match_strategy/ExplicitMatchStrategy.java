package rs.ac.bg.etf.sd21335m.trie.match_strategy;

import rs.ac.bg.etf.sd21335m.trie.types.TrieNode;

import java.util.*;

public class ExplicitMatchStrategy implements MatchStrategy {
    private TrieNode root;

    @Override
    public Set<String> search(TrieNode trieNode, String lookFor) {
        Set<String> results = new HashSet<>();
        StringBuilder wordBuilder = new StringBuilder();
        TrieNode parent = trieNode;
        for (char c : lookFor.toCharArray()) {
            Optional<TrieNode> children = parent.getChild(c);
            if (children.isEmpty()) {
                break;
            } else {
                parent = children.get();
                wordBuilder.append(c);
            }
        }
        if (parent.isWordTrieNode() && isWordSameLength(lookFor, wordBuilder)) {
            results.add(wordBuilder.toString());
        }
        return results;
    }

    private boolean isWordSameLength(String lookFor, StringBuilder wordBuilder) {
        return wordBuilder.length() == lookFor.length();
    }

    @Override
    public void delete(TrieNode trieNode, String word) {
        this.root = trieNode;
        Optional<TrieNode> trieNodeOptional = getLastWordNode(word);
        trieNodeOptional.ifPresent(this::removeFromTrieIfWordNode);
    }

    @Override
    public MatchStrategyType getType() {
        return MatchStrategyType.EXACT;
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
