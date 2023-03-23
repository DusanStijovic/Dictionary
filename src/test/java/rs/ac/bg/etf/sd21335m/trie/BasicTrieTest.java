package rs.ac.bg.etf.sd21335m.trie;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import rs.ac.bg.etf.sd21335m.trie.exception.IllegalWordException;
import rs.ac.bg.etf.sd21335m.trie.exception.WordAlreadyExist;
import rs.ac.bg.etf.sd21335m.trie.exception.WordDoesntExist;

import java.util.*;

public class BasicTrieTest {

    private BasicTrie basicTrie;

    @BeforeEach
    public void setUp() {
        basicTrie = new BasicTrie();
    }

    @Test
    public void addNewWord() {
        basicTrie.addNewWord("dusan");
    }

    @Test
    public void addNewWordCheckIfPresent() {
        basicTrie.addNewWord("dusan");
        Assertions.assertTrue(basicTrie.wordExist("dusan"));
    }

    @Test
    public void addNewWordCheckIfSomeOtherExist() {
        basicTrie.addNewWord("dusan");
        Assertions.assertFalse(basicTrie.wordExist("non"));
    }

    @Test
    public void addTwoWordsCheckIfBothExist() {
        basicTrie.addNewWord("dusan s");
        basicTrie.addNewWord("dusan m");
        Assertions.assertTrue(basicTrie.wordExist("dusan s"));
        Assertions.assertTrue(basicTrie.wordExist("dusan m"));
    }

    @Test
    public void addTwoWordsCheckIfThirdExist() {
        basicTrie.addNewWord("dusan s");
        basicTrie.addNewWord("dusan m");
        Assertions.assertFalse(basicTrie.wordExist("dusan d"));
    }

    @Test
    public void addNullToTrie() {
        Assertions.assertThrows(IllegalWordException.class, () -> basicTrie.addNewWord(null));
    }

    @Test
    public void deleteExistingWordCheckIfExist() {
        basicTrie.addNewWord("dusan");
        basicTrie.removeWord("dusan");
        Assertions.assertFalse(basicTrie.wordExist("dusan"));
    }

    @Test
    public void deleteNonExistingWord() {
        Assertions.assertThrows(WordDoesntExist.class, () -> basicTrie.removeWord("dusan"));
    }

    @Test
    public void isEmptyWhenCreate() {
        Assertions.assertTrue(basicTrie.isEmpty());
    }

    @Test
    public void isNotEmptyWhenAddWord() {
        basicTrie.addNewWord("dusan");
        Assertions.assertFalse(basicTrie.isEmpty());
    }

    @Test
    public void isEmptyAfterAddTwoDeleteOne() {
        basicTrie.addNewWord("test1");
        basicTrie.addNewWord("test2");
        basicTrie.removeWord("test1");
        Assertions.assertFalse(basicTrie.isEmpty());
    }

    @Test
    public void deleteNullWord() {
        Assertions.assertThrows(IllegalWordException.class, () -> basicTrie.removeWord(null));
    }

    @Test
    public void removeAllWordsCheckIfEmpty() {
        basicTrie.addNewWord("test1");
        basicTrie.addNewWord("test2");
        basicTrie.removeAllWords();
        Assertions.assertTrue(basicTrie.isEmpty());
    }

    @Test
    public void addTwoSameWord() {
        basicTrie.addNewWord("dusan");
        Assertions.assertThrows(WordAlreadyExist.class, () -> basicTrie.addNewWord("dusan"));
    }

    @Test
    public void searchPrefixNoResult() {
        basicTrie.addNewWord("dusan rec1");
        basicTrie.addNewWord("dusan rec2");
        Set<String> wordsPrefix = basicTrie.getWordsWithPrefix("non");
        Assertions.assertTrue(wordsPrefix.isEmpty());
    }

    @Test
    public void searchPrefixOneWordWhole() {
        basicTrie.addNewWord("dusan");
        basicTrie.addNewWord("rec2");
        Set<String> wordsPrefix = basicTrie.getWordsWithPrefix("dusan");
        Assertions.assertFalse(wordsPrefix.isEmpty());
    }


    @Test
    public void searchPrefixOneWord() {
        basicTrie.addNewWord("dusan");
        basicTrie.addNewWord("rec2");
        Set<String> wordsPrefix = basicTrie.getWordsWithPrefix("dus");
        Assertions.assertFalse(wordsPrefix.isEmpty());
    }

    @Test
    public void searchPrefixTwoWordNonMatch() {
        basicTrie.addNewWord("dusan");
        basicTrie.addNewWord("rec2");
        Set<String> wordsPrefix = basicTrie.getWordsWithPrefix("non");
        Assertions.assertTrue(wordsPrefix.isEmpty());
    }

    @Test
    public void searchPrefixThreeWordsTwoMatch() {
        basicTrie.addNewWord("sok kok");
        basicTrie.addNewWord("sok fant");
        basicTrie.addNewWord("voda");
        Set<String> wordsPrefix = basicTrie.getWordsWithPrefix("sok");
        Assertions.assertFalse(wordsPrefix.isEmpty());
        Assertions.assertEquals(wordsPrefix, new HashSet<>(Arrays.asList("sok kok", "sok fant")));
    }

    @Test
    public void searchPrefixThreeWordsNumOfHits() {
        basicTrie.addNewWord("sok kok");
        basicTrie.addNewWord("sok fant");
        basicTrie.addNewWord("voda");
        long hits = basicTrie.getHits("sok");
        Assertions.assertEquals(2, hits);
    }

    @Test
    public void getNumberOfNodesInTreeForOneWord() {
        basicTrie.addNewWord("dusan");
        int numberOfNodes = basicTrie.getNumberOfNodes();
        Assertions.assertEquals(6, numberOfNodes);
    }

    @Test
    public void getNumberOfNodesTwoWord() {
        basicTrie.addNewWord("dusan");
        basicTrie.addNewWord("duca");
        int numberOfNodes = basicTrie.getNumberOfNodes();
        Assertions.assertEquals(8, numberOfNodes);
    }
}
