package rs.ac.bg.etf.sd21335m.trie.search_strategy;

import rs.ac.bg.etf.sd21335m.trie.TrieNode;

import java.util.Set;

public interface SearchStrategy {
    Set<String> search(TrieNode trieNode, String lookFor);
}
