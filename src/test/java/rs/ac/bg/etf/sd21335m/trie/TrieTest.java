package rs.ac.bg.etf.sd21335m.trie;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import rs.ac.bg.etf.sd21335m.trie.exception.IllegalWordException;
import rs.ac.bg.etf.sd21335m.trie.exception.WordDoesntExist;

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

    @Test
    public void addTwoWordsCheckIfThirdExist() {
        Trie trie = new Trie();
        trie.addNewWord("dusan s");
        trie.addNewWord("dusan m");
        Assertions.assertFalse(trie.wordExist("dusan d"));
    }

    @Test
    public void addNullToTrie() {
        Trie trie = new Trie();
        Assertions.assertThrows(IllegalWordException.class, () -> trie.addNewWord(null));
    }

    @Test
    public void deleteExistingWordCheckIfExist() {
        Trie trie = new Trie();
        trie.addNewWord("dusan");
        trie.removeWord("dusan");
        Assertions.assertFalse(trie.wordExist("dusan"));
    }

    @Test
    public void deleteNonExistingWord() {
        Trie trie = new Trie();
        Assertions.assertThrows(WordDoesntExist.class, () -> trie.removeWord("dusan"));
    }

    @Test
    public void isEmptyWhenCreate() {
        Trie trie = new Trie();
        Assertions.assertTrue(trie.isEmpty());
    }

    @Test
    public void isEmptyWhenAddWord() {
        Trie trie = new Trie();
        trie.addNewWord("dusan");
        Assertions.assertFalse(trie.isEmpty());
    }

    @Test
    public void isEmptyAfterAddTwoDeleteOne() {
        Trie trie = new Trie();
        trie.addNewWord("test1");
        trie.addNewWord("test2");
        trie.removeWord("test1");
        Assertions.assertFalse(trie.isEmpty());
    }

}
