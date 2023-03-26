package rs.ac.bg.etf.sd21335m.trie.delete_strategy;

import rs.ac.bg.etf.sd21335m.trie.TrieNode;

public interface DeleteStrategy {
    void delete(TrieNode trieNode, String word);
}
