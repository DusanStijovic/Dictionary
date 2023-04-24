package rs.ac.bg.etf.sd21335m.trie.nodes;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import rs.ac.bg.etf.sd21335m.trie.InitConfigManager;
import rs.ac.bg.etf.sd21335m.trie.types.TrieNode;

@ExtendWith({InitConfigManager.class})
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
