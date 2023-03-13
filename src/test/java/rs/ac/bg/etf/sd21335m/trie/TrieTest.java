package rs.ac.bg.etf.sd21335m.trie;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TrieTest {

    @Test
    public void createTrie() {
        Trie trie = new Trie();
    }

    @Test
    public void addNewWord() {
        Trie trie = new Trie();
        trie.addNewWord("dusan");
    }

    @Test
    public void addNewWordCheckIfPresent() {
        Trie trie = new Trie();
        trie.addNewWord("dusan");
        Assertions.assertTrue(trie.wordExist("dusan"));
    }

    @Test
    public void addNewWordCheckIfSomeOtherExist() {
        Trie trie = new Trie();
        trie.addNewWord("dusan");
        Assertions.assertFalse(trie.wordExist("non"));
    }

    @Test
    public void addTwoWordsCheckIfBothExist() {
        Trie trie = new Trie();
        trie.addNewWord("dusan s");
        trie.addNewWord("dusan m");
        Assertions.assertTrue(trie.wordExist("dusan s"));
        Assertions.assertTrue(trie.wordExist("dusan m"));
    }
}
