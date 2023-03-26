package rs.ac.bg.etf.sd21335m.trie.nodes;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import rs.ac.bg.etf.sd21335m.trie.TrieNode;

class NonWordTrieNodeTest extends TrieNodeTest {

    @Override
    protected TrieNode createTrieNodeForTesting() {
        return TrieNode.createNonWordTrieNode();
    }

    @Test
    void nonTrieNodeCreation() {
        Assertions.assertFalse(trieNode.isWordTrieNode());
    }
}
