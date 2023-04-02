package rs.ac.bg.etf.sd21335m.trie.view_model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import rs.ac.bg.etf.sd21335m.trie.match_strategy.MatchStrategyType;
import rs.ac.bg.etf.sd21335m.trie.types.ListFormatter;
import rs.ac.bg.etf.sd21335m.trie.types.TriType;
import rs.ac.bg.etf.sd21335m.trie.types.WordInNewLineFormatter;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class TrieModelViewTest {

    protected TrieModelView trieModelView;
    protected ListFormatter listFormatter;

    @BeforeEach
    void setUp() {
        listFormatter = new WordInNewLineFormatter();
        trieModelView = new TrieModelView(listFormatter);
    }

    @Test
    void addNewWord() {
        trieModelView.addWord("dusan");
        Assertions.assertTrue(trieModelView.wordExist("dusan"));
        Assertions.assertEquals(ActionMessage.WORD_SUCCESSFULLY_ADDED, trieModelView.getLastActionMessage());
        Assertions.assertFalse(trieModelView.isEmpty());
    }

    @Test
    void addExistingWord() {
        trieModelView.addWord("dusan");
        trieModelView.addWord("dusan");
        Assertions.assertFalse(trieModelView.isEmpty());
        Assertions.assertEquals(ActionMessage.WORD_ALREADY_EXIST, trieModelView.getLastActionMessage());
    }

    @Test
    void addIllegalWord() {
        trieModelView.addWord(null);
        Assertions.assertTrue(trieModelView.isEmpty());
        Assertions.assertEquals(ActionMessage.ILLEGAL_WORD, trieModelView.getLastActionMessage());
    }

    @Test
    void chooseExactSearchStrategy() {
        trieModelView.chooseMatchStrategy(MatchStrategyType.EXACT);
        Assertions.assertEquals(MatchStrategyType.EXACT, trieModelView.getMatchStrategy());
    }

    @Test
    void choosePrefixSearchStrategy() {
        trieModelView.chooseMatchStrategy(MatchStrategyType.PREFIX);
        Assertions.assertEquals(MatchStrategyType.PREFIX, trieModelView.getMatchStrategy());
    }

    @Test
    void chooseWildCardSearchStrategy() {
        trieModelView.chooseMatchStrategy(MatchStrategyType.WILD_CARD);
        Assertions.assertEquals(MatchStrategyType.WILD_CARD, trieModelView.getMatchStrategy());
    }

    @Test
    void noActionOnBeginning(){
        Assertions.assertEquals(ActionMessage.NO_ACTION, trieModelView.getLastActionMessage());
    }

    @Test
    void checkDefaultTrie(){
        Assertions.assertEquals(TriType.BASIC, trieModelView.getTrieType());
    }

    @Test
    void checkDefaultMatchStrategy(){
        Assertions.assertEquals(MatchStrategyType.EXACT, trieModelView.getMatchStrategy());
    }

    @Test
    void searchWithExactStrategy(){
        trieModelView.addWord("dusan");
        trieModelView.chooseMatchStrategy(MatchStrategyType.EXACT);
        Set<String> expected = new HashSet<>(List.of("dusan"));
        Assertions.assertEquals(expected, trieModelView.searchByStrategy("dusan"));
        Assertions.assertEquals(ActionMessage.SEARCH_DONE_SUCCESSFULLY, trieModelView.getLastActionMessage());
    }

    @Test
    void searchWithPrefixStrategy(){
        trieModelView.addWord("dusan");
        trieModelView.addWord("dus");
        trieModelView.chooseMatchStrategy(MatchStrategyType.PREFIX);
        Set<String> expected = new HashSet<>(Arrays.asList("dusan", "dus"));
        Assertions.assertEquals(expected, trieModelView.searchByStrategy("dus"));
        Assertions.assertEquals(ActionMessage.SEARCH_DONE_SUCCESSFULLY, trieModelView.getLastActionMessage());
    }


    @Test
    void searchWithWildStrategy(){
        trieModelView.addWord("dusan");
        trieModelView.addWord("dasan");
        trieModelView.chooseMatchStrategy(MatchStrategyType.WILD_CARD);
        Set<String> expected = new HashSet<>(Arrays.asList("dusan", "dasan"));
        Assertions.assertEquals(expected, trieModelView.searchByStrategy("d?s?n"));
        Assertions.assertEquals(ActionMessage.SEARCH_DONE_SUCCESSFULLY, trieModelView.getLastActionMessage());
    }

    @Test
    void deleteWithExactStrategy(){
        trieModelView.addWord("dusan");
        trieModelView.chooseMatchStrategy(MatchStrategyType.EXACT);
        trieModelView.deleteByStrategy("dusan");
        Assertions.assertFalse(trieModelView.wordExist("dusan"));
        Assertions.assertEquals(ActionMessage.DELETE_DONE_SUCCESSFULLY, trieModelView.getLastActionMessage());
    }

    @Test
    void deleteWithPrefixStrategy(){
        trieModelView.addWord("dusan");
        trieModelView.addWord("dus");
        trieModelView.chooseMatchStrategy(MatchStrategyType.PREFIX);
        trieModelView.deleteByStrategy("dus");
        Assertions.assertFalse(trieModelView.wordExist("dusan"));
        Assertions.assertFalse(trieModelView.wordExist("dus"));
        Assertions.assertEquals(ActionMessage.DELETE_DONE_SUCCESSFULLY, trieModelView.getLastActionMessage());
    }

    @Test
    void deleteWithWildStrategy(){
        trieModelView.addWord("dusan");
        trieModelView.addWord("dasan");
        trieModelView.chooseMatchStrategy(MatchStrategyType.WILD_CARD);
        trieModelView.deleteByStrategy("d?s?n");
        Assertions.assertFalse(trieModelView.wordExist("dusan"));
        Assertions.assertFalse(trieModelView.wordExist("dus"));
        Assertions.assertEquals(ActionMessage.DELETE_DONE_SUCCESSFULLY, trieModelView.getLastActionMessage());
    }

    @Test
    void deleteNonExistingWordExactStrategy(){
        trieModelView.chooseMatchStrategy(MatchStrategyType.EXACT);
        trieModelView.deleteByStrategy("dusan");
        Assertions.assertEquals(ActionMessage.DELETE_DONE_SUCCESSFULLY, trieModelView.getLastActionMessage());
    }

    @Test
    void deleteNonExistingWordPrefixStrategy(){
        trieModelView.chooseMatchStrategy(MatchStrategyType.PREFIX);
        trieModelView.deleteByStrategy("dusan");
        Assertions.assertEquals(ActionMessage.DELETE_DONE_SUCCESSFULLY, trieModelView.getLastActionMessage());
    }

    @Test
    void deleteNonExistingWordWildStrategy(){
        trieModelView.chooseMatchStrategy(MatchStrategyType.WILD_CARD);
        trieModelView.deleteByStrategy("dusan");
        Assertions.assertEquals(ActionMessage.DELETE_DONE_SUCCESSFULLY, trieModelView.getLastActionMessage());
    }

}
