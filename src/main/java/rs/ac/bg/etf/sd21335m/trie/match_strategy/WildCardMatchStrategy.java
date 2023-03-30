package rs.ac.bg.etf.sd21335m.trie.match_strategy;

import rs.ac.bg.etf.sd21335m.trie.TrieNode;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class WildCardMatchStrategy implements MatchStrategy {

    public static final char WILD_CARD_MARK = '?';
    private TrieNode root;
    private String word;
    @Override
    public Set<String> search(TrieNode trieNode, String wildcard) {
        Set<String> results = new HashSet<>();
        searchHelper(wildcard.toCharArray(), 0, trieNode, new StringBuilder(), results);
        return results;
    }

    private void searchHelper(char[] query, int index, TrieNode node, StringBuilder currentWord, Set<String> results) {
        if (index == query.length) {
            if (node.isWordTrieNode()) {
                results.add(currentWord.toString());
            }
            return;
        }
        char c = query[index];
        if (c == '?') {
            for (TrieNode child : node.getChildren()) {
                Optional<Character> optionalCharacter = child.getCharacter();
                if (optionalCharacter.isPresent()) {
                    currentWord.append(optionalCharacter.get());
                    searchHelper(query, index + 1, child, currentWord, results);
                    currentWord.deleteCharAt(currentWord.length() - 1);
                }
            }
        } else {
            Optional<TrieNode> childOptional = node.getChild(c);
            if (childOptional.isPresent()) {
                currentWord.append(c);
                searchHelper(query, index + 1, childOptional.get(), currentWord, results);
                currentWord.deleteCharAt(currentWord.length() - 1);
            }
        }
    }

    @Override
    public void delete(TrieNode trieNode, String word) {
        this.root = trieNode;
        this.word = word;
        removeSubTrie(root, 0);
    }

    @Override
    public MatchStrategyType getType() {
        return MatchStrategyType.WILD_CARD;
    }

    private boolean removeSubTrie(TrieNode node, int index) {
        if (index == word.length()) {
            return pathOfWordLengthFound(node, index);
        } else {
            return pathLessThanWordsLength(node, index);
        }
    }

    private boolean pathLessThanWordsLength(TrieNode node, int index) {
        char c = word.charAt(index);
        if (c == WILD_CARD_MARK) {
            boolean allSubtreesDeleted = tryToDeleteAllChildrenSubtrees(node, index + 1);
            if (allSubtreesDeleted) {
                return !node.isWordTrieNode() && node != root;
            }
            return false;
        } else {
            Optional<TrieNode> childOptional = node.getChild(c);
            if (childOptional.isEmpty()) {
                return false;
            }
            TrieNode child = childOptional.get();
            boolean allChildSubtreesDeleted = removeSubTrie(child, index + 1);
            if (allChildSubtreesDeleted) {
                node.removeChild(c);
                return node != root || !node.getChildren().isEmpty();
            } else {
                return false;
            }
        }
    }

    private boolean pathOfWordLengthFound(TrieNode node, int index) {
        markNodeAsNonWord(node);

        if (!node.hasSomeChild()) {
            return true;
        }
        boolean allSubtreesDeleted = tryToDeleteAllChildrenSubtrees(node, index);
        if (allSubtreesDeleted) {
            return !node.isWordTrieNode();
        } else {
            return false;
        }
    }

    private boolean tryToDeleteAllChildrenSubtrees(TrieNode node, int index) {
        for (TrieNode child : node.getChildren()) {
            if (removeSubTrie(child, index)) {
                Optional<Character> characterOptional = child.getCharacter();
                characterOptional.ifPresent(node::removeChild);
            } else {
                return true;
            }
        }
        return false;
    }

    private void markNodeAsNonWord(TrieNode node) {
        node.setWordTrieNode(false);
    }
}
