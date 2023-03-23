package rs.ac.bg.etf.sd21335m.trie;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class NonWordBasicTrieNodeTest extends BasicTrieNodeTest {

    @Override
    protected TrieNode createTrieNodeForTesting() {
        return TrieNode.createNonWordTrieNode();
    }

    @Test
    void nonTrieNodeCreation() {
        Assertions.assertFalse(trieNode.isWordTrieNode());
    }
}
