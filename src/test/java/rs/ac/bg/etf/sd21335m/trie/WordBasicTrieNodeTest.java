package rs.ac.bg.etf.sd21335m.trie;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class WordBasicTrieNodeTest extends BasicTrieNodeTest {

    @Override
    protected TrieNode createTrieNodeForTesting() {
        return TrieNode.createWordTrieNode();
    }

    @Test
    public void wordTrieNodeCreation() {
        Assertions.assertTrue(trieNode.isWordTrieNode());
    }

    @Test
    public void testChangedToNonWordTrie(){
        trieNode.setWordTrieNode(false);
    }

}
