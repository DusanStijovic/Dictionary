package rs.ac.bg.etf.sd21335m.trie.types;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import rs.ac.bg.etf.sd21335m.trie.InitConfigManager;
import rs.ac.bg.etf.sd21335m.trie.exception.IllegalWordException;
import rs.ac.bg.etf.sd21335m.trie.exception.WordAlreadyExist;
import rs.ac.bg.etf.sd21335m.trie.match_strategy.PrefixMatchStrategy;
import rs.ac.bg.etf.sd21335m.trie.match_strategy.WildCardMatchStrategy;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@ExtendWith({InitConfigManager.class})
abstract class TrieTest {

    protected Trie trie;
    protected PrefixMatchStrategy prefixMatchStrategy;
    protected WildCardMatchStrategy wildCardMatchStrategy;

    @BeforeEach
    void setUp() {
        trie = makeTrie();
        prefixMatchStrategy = new PrefixMatchStrategy();
        wildCardMatchStrategy = new WildCardMatchStrategy();
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
    void addWordsSamePrefix() {
        trie.addNewWord("aaaa");
        trie.addNewWord("aaaabbb");
        Assertions.assertTrue(trie.wordExist("aaaa"));
        Assertions.assertTrue(trie.wordExist("aaaabbb"));
    }

    @Test
    void searchPrefixNoResult() {
        trie.addNewWord("dusan rec1");
        trie.addNewWord("dusan rec2");
        Set<String> wordsPrefix = trie.searchByStrategy(prefixMatchStrategy, "non");
        Assertions.assertTrue(wordsPrefix.isEmpty());
    }

    @Test
    void searchPrefixOneWordWhole() {
        trie.addNewWord("dusan");
        trie.addNewWord("rec2");
        Set<String> wordsPrefix = trie.searchByStrategy(prefixMatchStrategy, "dusan");
        Assertions.assertFalse(wordsPrefix.isEmpty());
    }


    @Test
    void searchPrefixOneWord() {
        trie.addNewWord("dusan");
        trie.addNewWord("rec2");
        Set<String> wordsPrefix = trie.searchByStrategy(prefixMatchStrategy, "dus");
        Assertions.assertFalse(wordsPrefix.isEmpty());
    }

    @Test
    void searchPrefixTwoWordNonMatch() {
        trie.addNewWord("dusan");
        trie.addNewWord("rec2");
        Set<String> wordsPrefix = trie.searchByStrategy(prefixMatchStrategy, "non");
        Assertions.assertTrue(wordsPrefix.isEmpty());
    }

    @Test
    void searchPrefixThreeWordsTwoMatch() {
        trie.addNewWord("sok kok");
        trie.addNewWord("sok fant");
        trie.addNewWord("voda");
        Set<String> wordsPrefix = trie.searchByStrategy(prefixMatchStrategy, "sok");
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
        Set<String> wildCardResult = trie.searchByStrategy(wildCardMatchStrategy, "d?san");
        Assertions.assertEquals(new HashSet<>(Arrays.asList("dusan", "dasan")), wildCardResult);
    }

    @Test
    void addWordSearchWithTwoWildCard() {
        trie.addNewWord("dasan");
        trie.addNewWord("dusun");
        Set<String> wildCardResult = trie.searchByStrategy(wildCardMatchStrategy, "d?s?n");
        Assertions.assertEquals(new HashSet<>(Arrays.asList("dusun", "dasan")), wildCardResult);
    }

    @Test
    void removeWithPrefix() {
        trie.addNewWord("dusan");
        trie.addNewWord("dus");
        trie.removeByStrategy(prefixMatchStrategy, "du");
        Assertions.assertFalse(trie.wordExist("dusan"));
        Assertions.assertFalse(trie.wordExist("dus"));
        Assertions.assertEquals(1, trie.getNumberOfNodes());
    }

    @Test
    void removeWithNonExistingPrefix() {
        trie.addNewWord("dusan");
        trie.addNewWord("dus");
        trie.removeByStrategy(prefixMatchStrategy, "ne");
        Assertions.assertTrue(trie.wordExist("dusan"));
        Assertions.assertTrue(trie.wordExist("dus"));
    }

    @Test
    void removeWithWildCard() {
        trie.addNewWord("dusan");
        trie.addNewWord("dasan");
        trie.removeByStrategy(wildCardMatchStrategy, "d?san");
        Assertions.assertFalse(trie.wordExist("dusan"));
        Assertions.assertFalse(trie.wordExist("dasan"));
    }

    @Test
    void removeWithAllWildCards() {
        trie.addNewWord("dusan");
        trie.addNewWord("nasud");
        trie.removeByStrategy(wildCardMatchStrategy, "?????");
        Assertions.assertFalse(trie.wordExist("dusan"));
        Assertions.assertFalse(trie.wordExist("nasud"));
    }

    @Test
    void removePrefixAllWord() {
        trie.addNewWord("dusan");
        trie.removeByStrategy(prefixMatchStrategy, "");
        Assertions.assertFalse(trie.wordExist("dusan"));
    }

    @Test
    void addCharacterWithIligalCharacters() {
        trie.addNewWord("dusan1.");
        Assertions.assertFalse(trie.wordExist("dusan1."));
        Assertions.assertTrue(trie.wordExist("dusan1"));
    }
}
