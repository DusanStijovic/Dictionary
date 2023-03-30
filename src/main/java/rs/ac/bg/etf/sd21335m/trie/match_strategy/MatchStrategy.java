package rs.ac.bg.etf.sd21335m.trie.match_strategy;

import rs.ac.bg.etf.sd21335m.trie.TrieNode;

import java.util.Set;

public interface MatchStrategy {
    Set<String> search(TrieNode trieNode, String lookFor);
    void delete(TrieNode trieNode, String word);

    MatchStrategyType getType();
}
