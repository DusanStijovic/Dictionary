package rs.ac.bg.etf.sd21335m.trie.search_strategy;

import rs.ac.bg.etf.sd21335m.trie.TrieNode;

import java.util.*;

public class PrefixSearchStrategy implements SearchStrategy {

    private TrieNode root;
    private String prefix;

    @Override
    public Set<String> search(TrieNode trieNode, String prefix) {
        this.root = trieNode;
        this.prefix = prefix;
        Optional<TrieNode> lastCommonNode = getLastCommonNode();
        if (lastCommonNode.isEmpty()) {
            return Collections.emptySet();
        }
        Set<String> results = new HashSet<>();
        searchWordsWithPrefix(lastCommonNode.get(), new StringBuilder(prefix), results);
        return results;
    }

    private Optional<TrieNode> getLastCommonNode() {
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
}
