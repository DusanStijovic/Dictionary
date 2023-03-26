package rs.ac.bg.etf.sd21335m.trie.search_strategy;

import rs.ac.bg.etf.sd21335m.trie.TrieNode;

import java.util.*;

public class ExplicitSearchStrategy implements SearchStrategy {
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
        if (parent.isWordTrieNode()) {
            results.add(wordBuilder.toString());
        }
        return results;
    }

}
