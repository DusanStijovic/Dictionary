package rs.ac.bg.etf.sd21335m.trie;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import rs.ac.bg.etf.sd21335m.trie.exception.IllegalWordException;
import rs.ac.bg.etf.sd21335m.trie.exception.WordAlreadyExist;
import rs.ac.bg.etf.sd21335m.trie.exception.WordDoesntExist;

import java.util.List;

public class TrieTest {

    private Trie trie;

    @BeforeEach
    public void setUp() {
        trie = new Trie();
    }

    @Test
    public void addNewWord() {
        trie.addNewWord("dusan");
    }

    @Test
    public void addNewWordCheckIfPresent() {
        trie.addNewWord("dusan");
        Assertions.assertTrue(trie.wordExist("dusan"));
    }

    @Test
    public void addNewWordCheckIfSomeOtherExist() {
        trie.addNewWord("dusan");
        Assertions.assertFalse(trie.wordExist("non"));
    }

    @Test
    public void addTwoWordsCheckIfBothExist() {
        trie.addNewWord("dusan s");
        trie.addNewWord("dusan m");
        Assertions.assertTrue(trie.wordExist("dusan s"));
        Assertions.assertTrue(trie.wordExist("dusan m"));
    }

    @Test
    public void addTwoWordsCheckIfThirdExist() {
        trie.addNewWord("dusan s");
        trie.addNewWord("dusan m");
        Assertions.assertFalse(trie.wordExist("dusan d"));
    }

    @Test
    public void addNullToTrie() {
        Assertions.assertThrows(IllegalWordException.class, () -> trie.addNewWord(null));
    }

    @Test
    public void deleteExistingWordCheckIfExist() {
        trie.addNewWord("dusan");
        trie.removeWord("dusan");
        Assertions.assertFalse(trie.wordExist("dusan"));
    }

    @Test
    public void deleteNonExistingWord() {
        Assertions.assertThrows(WordDoesntExist.class, () -> trie.removeWord("dusan"));
    }

    @Test
    public void isEmptyWhenCreate() {
        Assertions.assertTrue(trie.isEmpty());
    }

    @Test
    public void isNotEmptyWhenAddWord() {
        trie.addNewWord("dusan");
        Assertions.assertFalse(trie.isEmpty());
    }

    @Test
    public void isEmptyAfterAddTwoDeleteOne() {
        trie.addNewWord("test1");
        trie.addNewWord("test2");
        trie.removeWord("test1");
        Assertions.assertFalse(trie.isEmpty());
    }

    @Test
    public void deleteNullWord() {
        Assertions.assertThrows(IllegalWordException.class, () -> trie.removeWord(null));
    }

    @Test
    public void removeAllWordsCheckIfEmpty() {
        trie.addNewWord("test1");
        trie.addNewWord("test2");
        trie.removeAllWords();
        Assertions.assertTrue(trie.isEmpty());
    }

    @Test
    public void addTwoSameWord() {
        trie.addNewWord("dusan");
        Assertions.assertThrows(WordAlreadyExist.class, () -> trie.addNewWord("dusan"));
    }

    @Test
    public void searchPrefixNoResult() {
        trie.addNewWord("dusan rec1");
        trie.addNewWord("dusan rec2");
        List<String> wordsPrefix = trie.getWordsWithPrefix("non");
        Assertions.assertTrue(wordsPrefix.isEmpty());
    }

    @Test
    public void searchPrefixOneWordWhole() {
        trie.addNewWord("dusan");
        trie.addNewWord("rec2");
        List<String> wordsPrefix = trie.getWordsWithPrefix("dusan");
        Assertions.assertFalse(wordsPrefix.isEmpty());
    }


    @Test
    public void searchPrefixOneWord() {
        trie.addNewWord("dusan");
        trie.addNewWord("rec2");
        List<String> wordsPrefix = trie.getWordsWithPrefix("dus");
        Assertions.assertFalse(wordsPrefix.isEmpty());
    }

    @Test
    public void searchPrefixTwoWordNonMatch() {
        trie.addNewWord("dusan");
        trie.addNewWord("rec2");
        List<String> wordsPrefix = trie.getWordsWithPrefix("non");
        Assertions.assertTrue(wordsPrefix.isEmpty());
    }

    @Test
    public void searchPrefixThreeWordsTwoMatch() {
        trie.addNewWord("sok kok");
        trie.addNewWord("sok fant");
        trie.addNewWord("voda");
        List<String> wordsPrefix = trie.getWordsWithPrefix("sok");
        Assertions.assertFalse(wordsPrefix.isEmpty());
        Assertions.assertArrayEquals(wordsPrefix.toArray(), new String[]{"sok kok", "sok fant"});
    }

    @Test
    public void searchPrefixThreeWordsNumOfHits() {
        trie.addNewWord("sok kok");
        trie.addNewWord("sok fant");
        trie.addNewWord("voda");
        long hits = trie.getHits("sok");
        Assertions.assertEquals(2, hits);
    }

    @Test
    public void getNumberOfNodesInTreeForOneWord(){
        trie.addNewWord("dusan");
        int numberOfNodes = trie.getNumberOfNodes();
        Assertions.assertEquals(6, numberOfNodes);
    }
    @Test
    public void getNumberOfNodesTwoWord(){
//        trie.addNewWord("dusan");
//        trie.addNewWord("duca");
//        int numberOfNodes = trie.getNumberOfNodes();
//        Assertions.assertEquals(8, numberOfNodes);
    }
}
