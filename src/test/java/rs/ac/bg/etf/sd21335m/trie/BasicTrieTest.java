package rs.ac.bg.etf.sd21335m.trie;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import rs.ac.bg.etf.sd21335m.trie.exception.IllegalWordException;
import rs.ac.bg.etf.sd21335m.trie.exception.WordAlreadyExist;
import rs.ac.bg.etf.sd21335m.trie.exception.WordDoesntExist;

import java.util.*;

class BasicTrieTest {

    private BasicTrie basicTrie;

    @BeforeEach
    void setUp() {
        basicTrie = new BasicTrie();
    }

    @Test
    void addNewWordCheckIfPresent() {
        basicTrie.addNewWord("dusan");
        Assertions.assertTrue(basicTrie.wordExist("dusan"));
    }

    @Test
    void addNewWordCheckIfSomeOtherExist() {
        basicTrie.addNewWord("dusan");
        Assertions.assertFalse(basicTrie.wordExist("non"));
    }

    @Test
    void addTwoWordsCheckIfBothExist() {
        basicTrie.addNewWord("dusan s");
        basicTrie.addNewWord("dusan m");
        Assertions.assertTrue(basicTrie.wordExist("dusan s"));
        Assertions.assertTrue(basicTrie.wordExist("dusan m"));
    }

    @Test
    void addTwoWordsCheckIfThirdExist() {
        basicTrie.addNewWord("dusan s");
        basicTrie.addNewWord("dusan m");
        Assertions.assertFalse(basicTrie.wordExist("dusan d"));
    }

    @Test
    void addNullToTrie() {
        Assertions.assertThrows(IllegalWordException.class, () -> basicTrie.addNewWord(null));
    }

    @Test
    void deleteExistingWordCheckIfExist() {
        basicTrie.addNewWord("dusan");
        basicTrie.removeWord("dusan");
        Assertions.assertFalse(basicTrie.wordExist("dusan"));
    }

    @Test
    void deleteNonExistingWord() {
        Assertions.assertThrows(WordDoesntExist.class, () -> basicTrie.removeWord("dusan"));
    }

    @Test
    void isEmptyWhenCreate() {
        Assertions.assertTrue(basicTrie.isEmpty());
    }

    @Test
    void isNotEmptyWhenAddWord() {
        basicTrie.addNewWord("dusan");
        Assertions.assertFalse(basicTrie.isEmpty());
    }

    @Test
    void isEmptyAfterAddTwoDeleteOne() {
        basicTrie.addNewWord("test1");
        basicTrie.addNewWord("test2");
        basicTrie.removeWord("test1");
        Assertions.assertFalse(basicTrie.isEmpty());
    }

    @Test
    void deleteNullWord() {
        Assertions.assertThrows(IllegalWordException.class, () -> basicTrie.removeWord(null));
    }

    @Test
    void removeAllWordsCheckIfEmpty() {
        basicTrie.addNewWord("test1");
        basicTrie.addNewWord("test2");
        basicTrie.removeAllWords();
        Assertions.assertTrue(basicTrie.isEmpty());
    }

    @Test
    void addTwoSameWord() {
        basicTrie.addNewWord("dusan");
        Assertions.assertThrows(WordAlreadyExist.class, () -> basicTrie.addNewWord("dusan"));
    }

    @Test
    void searchPrefixNoResult() {
        basicTrie.addNewWord("dusan rec1");
        basicTrie.addNewWord("dusan rec2");
        Set<String> wordsPrefix = basicTrie.getWordsWithPrefix("non");
        Assertions.assertTrue(wordsPrefix.isEmpty());
    }

    @Test
    void searchPrefixOneWordWhole() {
        basicTrie.addNewWord("dusan");
        basicTrie.addNewWord("rec2");
        Set<String> wordsPrefix = basicTrie.getWordsWithPrefix("dusan");
        Assertions.assertFalse(wordsPrefix.isEmpty());
    }


    @Test
    void searchPrefixOneWord() {
        basicTrie.addNewWord("dusan");
        basicTrie.addNewWord("rec2");
        Set<String> wordsPrefix = basicTrie.getWordsWithPrefix("dus");
        Assertions.assertFalse(wordsPrefix.isEmpty());
    }

    @Test
    void searchPrefixTwoWordNonMatch() {
        basicTrie.addNewWord("dusan");
        basicTrie.addNewWord("rec2");
        Set<String> wordsPrefix = basicTrie.getWordsWithPrefix("non");
        Assertions.assertTrue(wordsPrefix.isEmpty());
    }

    @Test
    void searchPrefixThreeWordsTwoMatch() {
        basicTrie.addNewWord("sok kok");
        basicTrie.addNewWord("sok fant");
        basicTrie.addNewWord("voda");
        Set<String> wordsPrefix = basicTrie.getWordsWithPrefix("sok");
        Assertions.assertFalse(wordsPrefix.isEmpty());
        Assertions.assertEquals(wordsPrefix, new HashSet<>(Arrays.asList("sok kok", "sok fant")));
    }

    @Test
    void searchPrefixThreeWordsNumOfHits() {
        basicTrie.addNewWord("sok kok");
        basicTrie.addNewWord("sok fant");
        basicTrie.addNewWord("voda");
        long hits = basicTrie.getHits("sok");
        Assertions.assertEquals(2, hits);
    }

    @Test
    void getNumberOfNodesInTreeForOneWord() {
        basicTrie.addNewWord("dusan");
        int numberOfNodes = basicTrie.getNumberOfNodes();
        Assertions.assertEquals(6, numberOfNodes);
    }

    @Test
    void getNumberOfNodesTwoWord() {
        basicTrie.addNewWord("dusan");
        basicTrie.addNewWord("duca");
        int numberOfNodes = basicTrie.getNumberOfNodes();
        Assertions.assertEquals(8, numberOfNodes);
    }
}
