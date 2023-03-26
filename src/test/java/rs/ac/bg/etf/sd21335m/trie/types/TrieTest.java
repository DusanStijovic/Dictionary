package rs.ac.bg.etf.sd21335m.trie.types;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import rs.ac.bg.etf.sd21335m.trie.delete_strategy.DeleteStrategy;
import rs.ac.bg.etf.sd21335m.trie.delete_strategy.PrefixDeleteStrategy;
import rs.ac.bg.etf.sd21335m.trie.delete_strategy.WildCardDeleteStrategy;
import rs.ac.bg.etf.sd21335m.trie.search_strategy.PrefixSearchStrategy;
import rs.ac.bg.etf.sd21335m.trie.exception.IllegalWordException;
import rs.ac.bg.etf.sd21335m.trie.exception.WordAlreadyExist;
import rs.ac.bg.etf.sd21335m.trie.exception.WordDoesntExist;
import rs.ac.bg.etf.sd21335m.trie.search_strategy.WildCardSearchStrategy;

import java.util.*;

abstract class TrieTest {

    protected Trie trie;
    protected PrefixSearchStrategy prefixSearchStrategy;
    protected WildCardSearchStrategy wildCardSearchStrategy;
    protected PrefixDeleteStrategy prefixDeleteStrategy;
    private DeleteStrategy wildcardDeleteStrategy;

    @BeforeEach
    void setUp() {
        trie = makeTrie();
        prefixSearchStrategy = new PrefixSearchStrategy();
        wildCardSearchStrategy = new WildCardSearchStrategy();
        prefixDeleteStrategy = new PrefixDeleteStrategy();
        wildcardDeleteStrategy = new WildCardDeleteStrategy();
    }

    protected abstract Trie makeTrie();

    @Test
    void addNewWordCheckIfPresent() {
        trie.addNewWord("dusan");
        Assertions.assertTrue(trie.wordExist("dusan"));
    }

    @Test
    void addNewWordCheckIfSomeOtherExist() {
        trie.addNewWord("dusan");
        Assertions.assertFalse(trie.wordExist("non"));
    }

    @Test
    void addTwoWordsCheckIfBothExist() {
        trie.addNewWord("dusan s");
        trie.addNewWord("dusan m");
        Assertions.assertTrue(trie.wordExist("dusan s"));
        Assertions.assertTrue(trie.wordExist("dusan m"));
    }

    @Test
    void addTwoWordsCheckIfThirdExist() {
        trie.addNewWord("dusan s");
        trie.addNewWord("dusan m");
        Assertions.assertFalse(trie.wordExist("dusan d"));
    }

    @Test
    void addNullToTrie() {
        Assertions.assertThrows(IllegalWordException.class, () -> trie.addNewWord(null));
    }

    @Test
    void deleteExistingWordCheckIfExist() {
        trie.addNewWord("dusan");
        trie.removeWord("dusan");
        Assertions.assertFalse(trie.wordExist("dusan"));
    }

    @Test
    void deleteNonExistingWord() {
        Assertions.assertThrows(WordDoesntExist.class, () -> trie.removeWord("dusan"));
    }

    @Test
    void isEmptyWhenCreate() {
        Assertions.assertTrue(trie.isEmpty());
    }

    @Test
    void isNotEmptyWhenAddWord() {
        trie.addNewWord("dusan");
        Assertions.assertFalse(trie.isEmpty());
    }

    @Test
    void isEmptyAfterAddTwoDeleteOne() {
        trie.addNewWord("test1");
        trie.addNewWord("test2");
        trie.removeWord("test1");
        Assertions.assertFalse(trie.isEmpty());
    }

    @Test
    void deleteNullWord() {
        Assertions.assertThrows(IllegalWordException.class, () -> trie.removeWord(null));
    }

    @Test
    void removeAllWordsCheckIfEmpty() {
        trie.addNewWord("test1");
        trie.addNewWord("test2");
        trie.removeAllWords();
        Assertions.assertTrue(trie.isEmpty());
    }

    @Test
    void addTwoSameWord() {
        trie.addNewWord("dusan");
        Assertions.assertThrows(WordAlreadyExist.class, () -> trie.addNewWord("dusan"));
    }


    @Test
    void searchPrefixNoResult() {
        trie.addNewWord("dusan rec1");
        trie.addNewWord("dusan rec2");
        Set<String> wordsPrefix = trie.search(prefixSearchStrategy, "non");
        Assertions.assertTrue(wordsPrefix.isEmpty());
    }

    @Test
    void searchPrefixOneWordWhole() {
        trie.addNewWord("dusan");
        trie.addNewWord("rec2");
        Set<String> wordsPrefix = trie.search(prefixSearchStrategy, "dusan");
        Assertions.assertFalse(wordsPrefix.isEmpty());
    }


    @Test
    void searchPrefixOneWord() {
        trie.addNewWord("dusan");
        trie.addNewWord("rec2");
        Set<String> wordsPrefix = trie.search(prefixSearchStrategy, "dus");
        Assertions.assertFalse(wordsPrefix.isEmpty());
    }

    @Test
    void searchPrefixTwoWordNonMatch() {
        trie.addNewWord("dusan");
        trie.addNewWord("rec2");
        Set<String> wordsPrefix = trie.search(prefixSearchStrategy, "non");
        Assertions.assertTrue(wordsPrefix.isEmpty());
    }

    @Test
    void searchPrefixThreeWordsTwoMatch() {
        trie.addNewWord("sok kok");
        trie.addNewWord("sok fant");
        trie.addNewWord("voda");
        Set<String> wordsPrefix = trie.search(prefixSearchStrategy, "sok");
        Assertions.assertFalse(wordsPrefix.isEmpty());
        Assertions.assertEquals(wordsPrefix, new HashSet<>(Arrays.asList("sok kok", "sok fant")));
    }

    @Test
    void getNumberOfNodesInTreeForOneWord() {
        trie.addNewWord("dusan");
        int numberOfNodes = trie.getNumberOfNodes();
        Assertions.assertEquals(6, numberOfNodes);
    }

    @Test
    void getNumberOfNodesTwoWord() {
        trie.addNewWord("dusan");
        trie.addNewWord("duca");
        int numberOfNodes = trie.getNumberOfNodes();
        Assertions.assertEquals(8, numberOfNodes);
    }

    @Test
    void addNewWordCheckIfExistWithVariousCases() {
        trie.addNewWord("dusan");
        Assertions.assertFalse(trie.wordExist("DUSAN"));
        Assertions.assertFalse(trie.wordExist("Dusan"));
        Assertions.assertFalse(trie.wordExist("DusaN"));
    }

    @Test
    void addWordSearchWithWildCard() {
        trie.addNewWord("dasan");
        trie.addNewWord("dusan");
        Set<String> wildCardResult = trie.search(wildCardSearchStrategy, "d?san");
        Assertions.assertEquals(new HashSet<>(Arrays.asList("dusan", "dasan")), wildCardResult);
    }

    @Test
    void addWordSearchWithTwoWildCard() {
        trie.addNewWord("dasan");
        trie.addNewWord("dusun");
        Set<String> wildCardResult = trie.search(wildCardSearchStrategy, "d?s?n");
        Assertions.assertEquals(new HashSet<>(Arrays.asList("dusun", "dasan")), wildCardResult);
    }

    @Test
    void removeWithPrefix() {
        trie.addNewWord("dusan");
        trie.addNewWord("dus");
        trie.delete(prefixDeleteStrategy, "du");
        Assertions.assertFalse(trie.wordExist("dusan"));
        Assertions.assertFalse(trie.wordExist("dus"));
        Assertions.assertEquals(1, trie.getNumberOfNodes());
    }

    @Test
    void removeWithNonExistingPrefix() {
        trie.addNewWord("dusan");
        trie.addNewWord("dus");
        trie.delete(prefixDeleteStrategy, "ne");
        Assertions.assertTrue(trie.wordExist("dusan"));
        Assertions.assertTrue(trie.wordExist("dus"));
    }

    @Test
    void removeWithWildCard(){
        trie.addNewWord("dusan");
        trie.addNewWord("dasan");
        trie.delete(wildcardDeleteStrategy, "d?san");
        Assertions.assertFalse(trie.wordExist("dusan"));
        Assertions.assertFalse(trie.wordExist("dasan"));
    }

    @Test
    void removeWithAllWildCards(){
        trie.addNewWord("dusan");
        trie.addNewWord("nasud");
        trie.delete(wildcardDeleteStrategy, "?????");
        Assertions.assertFalse(trie.wordExist("dusan"));
        Assertions.assertFalse(trie.wordExist("nasud"));
    }

    @Test
    void removePrefixAllWord(){
        trie.addNewWord("dusan");
        trie.delete(prefixDeleteStrategy, "");
        Assertions.assertFalse(trie.wordExist("dusan"));
    }
}
