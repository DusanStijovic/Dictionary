package rs.ac.bg.etf.sd21335m.trie.nodes;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import rs.ac.bg.etf.sd21335m.trie.types.TrieNode;

class WordTrieNodeTest extends TrieNodeTest {

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
