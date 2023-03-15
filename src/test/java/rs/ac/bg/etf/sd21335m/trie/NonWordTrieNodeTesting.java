package rs.ac.bg.etf.sd21335m.trie;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class NonWordTrieNodeTesting extends TrieNodeTest {

    @Override
    protected TrieNode createTrieNodeForTesting() {
        return TrieNode.createNonWordTrieNode();
    }

    @Test
    public void nonTrieNodeCreation() {
        Assertions.assertFalse(trieNode.isWordTrieNode());
    }
}
