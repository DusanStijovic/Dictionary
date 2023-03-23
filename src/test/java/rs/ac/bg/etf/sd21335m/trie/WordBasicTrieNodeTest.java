package rs.ac.bg.etf.sd21335m.trie;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class WordBasicTrieNodeTest extends BasicTrieNodeTest {

    @Override
    protected TrieNode createTrieNodeForTesting() {
        return TrieNode.createWordTrieNode();
    }

    @Test
    void wordTrieNodeCreation() {
        Assertions.assertTrue(trieNode.isWordTrieNode());
    }

    @Test
    void testChangedToNonWordTrie(){
        trieNode.setWordTrieNode(false);
        Assertions.assertFalse(trieNode.isWordTrieNode());

    }

}
